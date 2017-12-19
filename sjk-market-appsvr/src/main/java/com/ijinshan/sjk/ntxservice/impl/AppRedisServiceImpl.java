package com.ijinshan.sjk.ntxservice.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.Sort;
import com.ijinshan.sjk.ntxservice.AppRedisService;
import com.ijinshan.sjk.ntxservice.ListCallback;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.Pager;

@Service
public class AppRedisServiceImpl implements AppRedisService {
    private static final int MAX_ROWS = 10000;

    private static final Logger logger = LoggerFactory.getLogger(AppRedisServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "jedisPool")
    private JedisPool jedisPool;

    @Resource(name = "appServiceImpl")
    private AppService appService;
    
    
    @Override
    public Pager<App4Summary> getHotDownload(int subCatalog, int currentPage, int pageSize,
            int sortType, int noAds, int noVirus, int official) {
        Assert.isTrue(subCatalog > 0, "catalog negative!");
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Assert.isTrue(offset < appConfig.getMaxOffset(), "offset outmax!");
        ListCallback<App4Summary> cb = new ListCallback<App4Summary>() {
            @Override
            public List<App4Summary> doIn(final List<Integer> ids) {
                return appService.getApps4Summary(ids);
            }
        };
        Pager<App4Summary> pager = getPager(cb, subCatalog, currentPage, pageSize, noAds, official,sortType);
        return pager;
    }
    
    

    @Override
    public Pager<LatestApp> getLatest(int subCatalog, Long date, int currentPage, int pageSize, int noAds, int noVirus,
            int official) {
        Assert.isTrue(subCatalog > 0, "catalog negative!");
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        Assert.isTrue(currentPage > 0, "currentPage negative!");

        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Assert.isTrue(offset < appConfig.getMaxOffset(), "offset outmax!");
     
        if (null != date) {
            Pager<LatestApp> pager = new Pager<LatestApp>();
            String key =toRedisKey(subCatalog,noAds,official,0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date(date);
            try {
                double timeMax = sd.parse((sdf.format(d) + " 23:59:59")).getTime();
                double timeMin = sd.parse((sdf.format(d) + " 00:00:00")).getTime();
                Pager<Integer> ints = getIdsByRedis(key,timeMax,timeMin,official,pageSize);
                if(null != ints){
                 pager.setResult(null != ints.getResult() ? appService.getLatest(ints.getResult()):null);
                 pager.setRows(ints.getRows());
                }
                return pager;
            } catch (ParseException e) {
               logger.error("exception:",e);
            }
        }

        ListCallback<LatestApp> cb = new ListCallback<LatestApp>() {
            @Override
            public List<LatestApp> doIn(final List<Integer> ids) {
                return appService.getLatest(ids);
            }
        };
        Pager<LatestApp> pager = getPager(cb, subCatalog, currentPage, pageSize, noAds, noVirus, official);
        return pager;
    }

    private final String toRedisKey(int subCatalog, int noAds, int official,int noVirus) {
        StringBuilder sb = new StringBuilder(25);
        sb.append("key_").append(noAds).append("_").append(official).append("_").append(noVirus).append("_")
                .append(subCatalog);
        return sb.toString();
    }


    @Override
    public Pager<String> getLatestDates(int subCatalog, int currentPage, int pageSize, int noAds, int noVirus,
            int official) {
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS,"pageSize invalid!");
        ListCallback<String> cb = new ListCallback<String>() {
            @Override
            public List<String> doIn(final List<Integer> ids) {
                return appService.getLatestDates(ids);
            }
        };
        Pager<String> pager = getPager(cb, subCatalog, currentPage, pageSize, noAds,official, noVirus);
        return pager;
    }


    public <T> Pager<T> getPager(ListCallback<T> cb, int subCatalog, int currentPage, int pageSize, int noAds,
             int official,int noVirus) {
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);       
        final String key = toRedisKey(subCatalog, noAds, official,noVirus);

        Pager<T> pager = new Pager<T>();
        final Jedis redis = jedisPool.getResource();

        final long total = getTotal(redis, key);
        pager.setRows(total);
        if (total <= 0) {
            return pager;
        }
        List<Integer> ids = getIdsByRedis(redis, key, offset, pageSize);
        if (null != ids && !ids.isEmpty()) {
            List<T> list = cb.doIn(ids);
            pager.setResult(list);
        }
        return pager;
    }

    private Pager<Integer> getIdsByRedis(String key,double max,double min, int offset, int row) {
        Pager<Integer> pager = new Pager<Integer>();
        List<Integer> lists = new ArrayList<Integer>();
        Jedis redis = jedisPool.getResource();
        Set<Tuple> setCount = redis.zrevrangeByScoreWithScores(key, max, min);
        int count = setCount.size();
        pager.setRows(count);
        if(count <= 0)
            return pager;
        
        Set<Tuple> set = redis.zrevrangeByScoreWithScores(key, max, min, offset, row);
        Iterator<Tuple> it = set.iterator();
        Tuple t =null;
        while (it.hasNext()) {
            t = it.next();
            lists.add(Integer.parseInt(t.getElement()));
        }
        pager.setResult(lists);
        return pager;
    }

    public final long getTotal(final Jedis redis, final String key) {
        return redis.zcard(key);
    }

    public List<Integer> getIdsByRedis(final Jedis redis, String key, int offset, int row) {
        Set<Tuple> set = redis.zrevrangeByScoreWithScores(key, appConfig.getRedisFindMax(), 0.0, offset, row);
        Iterator<Tuple> it = set.iterator();
        List<Integer> lists = new ArrayList<Integer>(set.size());
        Tuple t = null;
        while (it.hasNext()) {
             t= it.next();
            lists.add(Integer.parseInt(t.getElement()));
        }
        return lists;
    }



    @Override
    public List<SimpleRankApp> getSimpleRankApp(int subCatalog, int page, int rows, int sortType, int noAds, int noVirus, int official) {
        ListCallback<SimpleRankApp> cb = new ListCallback<SimpleRankApp>() {
            @Override
            public List<SimpleRankApp> doIn(final List<Integer> ids) {
                return appService.getSimpleRankApp(ids);
            }
        };
        Pager<SimpleRankApp> pager = getPager(cb, subCatalog, page, rows, noAds, official,sortType);
        return pager.getResult();
    }

}
