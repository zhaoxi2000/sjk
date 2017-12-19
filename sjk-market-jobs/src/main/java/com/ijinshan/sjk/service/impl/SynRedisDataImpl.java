package com.ijinshan.sjk.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.Viapp;
import com.ijinshan.sjk.service.CommonRedis;
import com.ijinshan.sjk.service.SynRedisData;
import com.ijinshan.sjk.util.SetUtil;

@Service
public class SynRedisDataImpl implements SynRedisData {
    private static final Logger logger = LoggerFactory.getLogger(SynRedisDataImpl.class);

    @Resource(name = "appConfig")
    protected AppConfig appConfig;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;
    
    @Resource(name = "jedisPool")
    protected JedisPool jedisPool;

    @Resource(name = "commonRedisImpl")
    protected CommonRedis commonRedis;
    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    
    @Override
    public void synRedisByApp() {
        List<Viapp> listLift = null;
        TreeSet<Integer> newAppType = null;
        TreeSet<Integer> oldAppType = new TreeSet<Integer>();
        logger.info("brgingDataSynredisData.....");
        List<Viapp> list = appDao.getAllApp();
        newAppType = getNewType(list);
        long starts = System.currentTimeMillis();
        Map<String, TreeSet<String>> mapkey = getNewDataByKey(list);
        logger.info("OldDataWorded...time:{}ms", System.currentTimeMillis() - starts);

        long start = System.currentTimeMillis();
        Jedis redis = jedisPool.getResource();
        Set<String> keys = redis.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String keyName = it.next();
             String[] keyns = keyName.split("_"); // key_1_0_0_42
             int type = Integer.parseInt(keyns[4]);
             oldAppType.add(type);

            TreeSet<String> setApp = mapkey.get(keyName);
            TreeSet<String> rSet = new TreeSet<String>();
            Set<Tuple> set = redis.zrangeWithScores(keyName, 0, -1);
            Iterator<Tuple> its = set.iterator();
            while (its.hasNext()) {
                Tuple tp = its.next();
                rSet.add(tp.getElement() + "_" + tp.getScore());
            }

            TreeSet<String> dele = SetUtil.differenceT(rSet, setApp);
            Iterator<String> del = dele.iterator();
            while (del.hasNext()) {
                String rds = del.next();
                String[] rData = rds.split("_");
                redis.zrem(keyName, rData[0]);
                logger.debug("{}DeleteRedisDate ID:{}", keyName, rData[0]);
            }
            
            TreeSet<String> saveUp = SetUtil.differenceT(setApp, rSet);
            Iterator<String> sup = saveUp.iterator();
            while (sup.hasNext()) {
                String sups = sup.next();
                String[] rData = sups.split("_");
               
                 redis.zadd(keyName, Double.parseDouble(rData[1]),
                 rData[0].toString());
                logger.debug("{}saveOrupdateRedis ID:{}", keyName, rData[0]);
            }
        }
        // 判断是否新增类型数据
        TreeSet<Integer> newType = SetUtil.differenceT(newAppType, oldAppType);
        Iterator<Integer> sup = newType.iterator();
        while (sup.hasNext()) {
            int type = sup.next();
            listLift = getNewType(list, type);
            if (!CollectionUtils.isEmpty(listLift)) {
                saveRedisTo(listLift);
            }
        }
        logger.info("redisSynEND...time:{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 新增类型数据到Redis
     * 
     * @param app
     * @param redis
     */
    @SuppressWarnings("unused")
    private void saveRedisTo(final List<Viapp> list) {
        logger.info("newAppDataTypeRedis...");
        long start = System.currentTimeMillis();
        redisTemplate.execute(new RedisCallback<Object>() {  
            @Override  
            public Object doInRedis(RedisConnection connection)  
                    throws DataAccessException {
                commonRedis.saveCommonRedis(list,connection);
                return null;  
            }  
        });  
        logger.info("newAddTypeRedisEnd...time:{}ms", System.currentTimeMillis() - start);
    }

    @SuppressWarnings("unused")
    private List<Viapp> getNewType(List<Viapp> list, int type) {
        List<Viapp> listLift = new ArrayList<Viapp>();
        for (int i = 0; i < list.size(); i++) {
            Viapp vp = list.get(i);
            if (vp.getCatalog() == type || vp.getSubCatalog() == type) {
                listLift.add(vp);
            }
        }
        return listLift;
    }

    private TreeSet<Integer> getNewType(List<Viapp> list) {
        TreeSet<Integer> newAppType = new TreeSet<Integer>();
        Viapp vp =null;
        for (int i = 0; i < list.size(); i++) {
            vp = list.get(i);
            newAppType.add(vp.getSubCatalog());
            newAppType.add(vp.getCatalog());
        }
        return newAppType;
    }


    private Map<String, TreeSet<String>> getNewDataByKey(List<Viapp> viapp) {
        Map<String, TreeSet<String>> map = new HashMap<String, TreeSet<String>>(viapp.size() * 4 / 3 + 1);
        String k1, k2, k3, k4,k5,k6,k7,k8,k9,k10;
        String k11, k12, k13, k14, k15, k16, k17, k18, k19, k20;
        String k21, k22, k23, k24, k25, k26, k27, k28, k29, k30;
        String k31, k32, k33, k34, k35, k36, k37, k38, k39, k40;
        for (int i = 0; i < viapp.size(); i++) {
            Viapp vp = viapp.get(i);
            String id = vp.getId().toString();
            double lastUpdateTime = vp.getLastUpdateTime().getTime();
            double downloadRank = vp.getDownloadRank();
            double lastDayDelta = vp.getLastDayDelta();
            double lastWeekDelta = vp.getLastWeekDelta();
            double realDownload= vp.getRealDownload();
            // 0 time
            k1 = String.format("key_%d_%d_0_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
            k2 = String.format("key_%d_%d_0_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());
            // 1 download
            k3 = String.format("key_%d_%d_1_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
            k4 = String.format("key_%d_%d_1_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());
            
            // 2 lastDayDelta
            k5 = String.format("key_%d_%d_2_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
            k6 = String.format("key_%d_%d_2_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());
            
           // 3 lastWeekDelta
            k7 = String.format("key_%d_%d_3_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
            k8 = String.format("key_%d_%d_3_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());
            
            // 4 realDownload
            k9 = String.format("key_%d_%d_4_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
            k10 = String.format("key_%d_%d_4_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());
            
            addNewData(map, k1, id, lastUpdateTime);
            addNewData(map, k2, id, lastUpdateTime);
            addNewData(map, k3, id, downloadRank);
            addNewData(map, k4, id, downloadRank);
            addNewData(map, k5, id, lastDayDelta);
            addNewData(map, k6, id, lastDayDelta);
            addNewData(map, k7, id, lastWeekDelta);
            addNewData(map, k8, id, lastWeekDelta);
            addNewData(map, k9, id, realDownload);
            addNewData(map, k10, id, realDownload);
            if (vp.getNoAds() == 1) {
                k11 = String.format("key_0_%d_0_%d", vp.getOfficial(), vp.getCatalog());
                k12 = String.format("key_0_%d_0_%d", vp.getOfficial(), vp.getSubCatalog());
                // 1 download
                k13 = String.format("key_0_%d_1_%d", vp.getOfficial(), vp.getCatalog());
                k14 = String.format("key_0_%d_1_%d", vp.getOfficial(), vp.getSubCatalog());
                // 2 lastDayDelta
                k15 = String.format("key_0_%d_2_%d", vp.getOfficial(), vp.getCatalog());
                k16 = String.format("key_0_%d_2_%d", vp.getOfficial(), vp.getSubCatalog());
                // 3 lastWeekDelta
                k17 = String.format("key_0_%d_3_%d", vp.getOfficial(), vp.getCatalog());
                k18 = String.format("key_0_%d_3_%d", vp.getOfficial(), vp.getSubCatalog());
                // 4 realDownload
                k19 = String.format("key_0_%d_4_%d", vp.getOfficial(), vp.getCatalog());
                k20 = String.format("key_0_%d_4_%d", vp.getOfficial(), vp.getSubCatalog());

                addNewData(map,k11, id, lastUpdateTime);
                addNewData(map,k12, id, lastUpdateTime);
                addNewData(map,k13, id, downloadRank);
                addNewData(map,k14, id, downloadRank);
                addNewData(map,k15, id, lastDayDelta);
                addNewData(map,k16, id, lastDayDelta);
                addNewData(map,k17, id, lastWeekDelta);
                addNewData(map,k18, id, lastWeekDelta);
                addNewData(map,k19, id, realDownload);
                addNewData(map,k20, id, realDownload);
            }

            if (vp.getOfficial() == 1) {
                k21 = String.format("key_%d_0_0_%d", vp.getNoAds(), vp.getCatalog());
                k22 = String.format("key_%d_0_0_%d", vp.getNoAds(), vp.getSubCatalog());
                // 1 download
                k23 = String.format("key_%d_0_1_%d", vp.getNoAds(), vp.getCatalog());
                k24 = String.format("key_%d_0_1_%d", vp.getNoAds(), vp.getSubCatalog());
                // 2 lastDayDelta
                k25 = String.format("key_%d_0_2_%d", vp.getNoAds(), vp.getCatalog());
                k26 = String.format("key_%d_0_2_%d", vp.getNoAds(), vp.getSubCatalog());
                // 3 lastWeekDelta
                k27 = String.format("key_%d_0_3_%d", vp.getNoAds(), vp.getCatalog());
                k28 = String.format("key_%d_0_3_%d", vp.getNoAds(), vp.getSubCatalog());
                // 4 realDownload
                k29 = String.format("key_%d_0_4_%d", vp.getNoAds(), vp.getCatalog());
                k30 = String.format("key_%d_0_4_%d", vp.getNoAds(), vp.getSubCatalog());

                addNewData(map,k21, id, lastUpdateTime);
                addNewData(map,k22, id, lastUpdateTime);
                addNewData(map,k23, id, downloadRank);
                addNewData(map,k24, id, downloadRank);
                addNewData(map,k25, id, lastDayDelta);
                addNewData(map,k26, id, lastDayDelta);
                addNewData(map,k27, id, lastWeekDelta);
                addNewData(map,k28, id, lastWeekDelta);
                addNewData(map,k29, id, realDownload);
                addNewData(map,k30, id, realDownload);
            }
            if (vp.getNoAds() == 1 && vp.getOfficial() == 1) {
                k31 = String.format("key_0_0_0_%d", vp.getCatalog());
                k32 = String.format("key_0_0_0_%d", vp.getSubCatalog());
                // 1 download
                k33 = String.format("key_0_0_1_%d", vp.getCatalog());
                k34 = String.format("key_0_0_1_%d", vp.getSubCatalog());
                // 2 lastDayDelta
                k35 = String.format("key_0_0_2_%d", vp.getCatalog());
                k36 = String.format("key_0_0_2_%d", vp.getSubCatalog());
                // 3 lastWeekDelta
                k37 = String.format("key_0_0_3_%d", vp.getCatalog());
                k38 = String.format("key_0_0_3_%d", vp.getSubCatalog());
                // 4 realDownload
                k39 = String.format("key_0_0_4_%d", vp.getCatalog());
                k40 = String.format("key_0_0_4_%d", vp.getSubCatalog());

                addNewData(map,k31, id, lastUpdateTime);
                addNewData(map,k32, id, lastUpdateTime);
                addNewData(map,k33, id, downloadRank);
                addNewData(map,k34, id, downloadRank);
                addNewData(map,k35, id, lastDayDelta);
                addNewData(map,k36, id, lastDayDelta);
                addNewData(map,k37, id, lastWeekDelta);
                addNewData(map,k38, id, lastWeekDelta);
                addNewData(map,k39, id, realDownload);
                addNewData(map,k40, id, realDownload);
            }

        }
        return map;
    }

    private void addNewData(Map<String, TreeSet<String>> map, String key, String id, double score) {
        TreeSet<String> ts = null;
        if (map.containsKey(key)) {
            ts = map.get(key);
        } else {
            ts = new TreeSet<String>();
            map.put(key, ts);
        }
        ts.add(id + "_" + score);
    }

}
