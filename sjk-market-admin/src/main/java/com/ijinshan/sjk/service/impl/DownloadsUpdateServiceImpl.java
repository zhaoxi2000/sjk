package com.ijinshan.sjk.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.MarketAppDao;
import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.dao.TopAppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.TopApp;
import com.ijinshan.sjk.service.DownloadsUpdateService;
import com.ijinshan.sjk.service.UpdateDownloadCallback;
import com.ijinshan.sjk.vo.TopAppVo;

@Service
public class DownloadsUpdateServiceImpl implements DownloadsUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(DownloadsUpdateServiceImpl.class);
    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    @Resource(name = "marketAppDaoImpl")
    private MarketAppDao marketAppDao;

    @Resource(name = "marketDaoImpl")
    protected MarketDao marketDao;

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Resource(name = "topAppDaoImpl")
    private TopAppDao topAppDao;

    // top2000自动更新
    @Override
    public void topUpdateData() {

        List<TopApp> topList = topAppDao.getAllTopAppList();
        Set<String> pnames = new HashSet<String>();
        for (TopApp ta : topList) {
            pnames.add(ta.getPkname());
        }

        List<TopAppVo> tappList = appDao.getAppList(pnames);

        Map<String, TopAppVo> map = new HashMap<String, TopAppVo>(tappList.size() * 4 / 3 - 1);
        for (int i = 0; i < tappList.size(); i++) {
            map.put(tappList.get(i).getPkname(), tappList.get(i));
        }

        List<TopApp> tpvolist = new ArrayList<TopApp>();
        // Set<TopApp> tpvoset = new HashSet<TopApp>(2000);
        // 得到所有的App表中对应的所有的TopAppVo里面的值。与通过TopApp表里面的pkname 相同，对比name 如果不相同则修改
        // TopApp表里的newName字段值
        for (TopApp ta : topList) {

            if (map.containsKey(ta.getPkname())
                    && !(map.get(ta.getPkname()).getName().trim().equals(ta.getName().trim()))) {
                ta.setLastUpdateTime(map.get(ta.getPkname()).getLastUpdateTime());
                ta.setNewName(map.get(ta.getPkname()).getName());
                //以下逻辑是由于导入的表中的数据信息不完整，这时以第一次运行结果作为基准，
                if(StringUtils.isEmpty(ta.getName()) &&  !StringUtils.isEmpty(map.get(ta.getPkname()).getName())){
                    ta.setName(map.get(ta.getPkname()).getName());
                }
                if(ta.getAppId()==0){
                    ta.setAppId(map.get(ta.getPkname()).getId());
                }
                tpvolist.add(ta);
            }

        }
        topList.clear();
        tappList.clear();
        Session session = sessions.openSession();
        try {
            session.setDefaultReadOnly(false);
            for (TopApp ta : tpvolist) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date baseDate = sdf.parse("2000-01-01 00:00:00");
                if(!(baseDate.compareTo(ta.getLastUpdateTime())==0)){
                    Integer it = Integer.parseInt("1".toString());
                    ta.setState(it.byteValue());
                }
                topAppDao.update(session, ta);
            }
            session.flush();
            session.clear();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public synchronized void update() {
        UpdateDownloadCallback cb = new UpdateDownloadCallback() {
            @Override
            public int doIn(Session session, App a, AppDao appDao) {
                return appDao.updateDownloadRank(session, a.getId());
            }
        };
        int effectRows = handlePagination(cb);
        logger.info("Updated! effectRows:{}", effectRows);
    }

    @Override
    public void updateDay() {
        logger.info("Begin updateDay...");
        UpdateDownloadCallback cb = new UpdateDownloadCallback() {
            @Override
            public int doIn(Session session, App a, AppDao appDao) {
                return appDao.updateDayDownload(session, a.getId());
            }
        };
        int effectRows = handlePagination(cb);
        logger.info("updateDay done! effectRows:{}", effectRows);
    }

    @Override
    public void updateWeek() {
        logger.info("Begin updateWeek...");
        UpdateDownloadCallback cb = new UpdateDownloadCallback() {
            @Override
            public int doIn(Session session, App a, AppDao appDao) {
                return appDao.updateWeekDownload(session, a.getId());
            }
        };
        int effectRows = handlePagination(cb);
        logger.info("updateWeek done! effectRows:{}", effectRows);
    }

    @Override
    public int handlePagination(UpdateDownloadCallback cb) {
        int effects = 0;
        Session session = sessions.openSession();
        try {
            session.setDefaultReadOnly(false);
            long count = appDao.count(session);
            final int pageSize = AppConfig.BATCH_SIZE_OF_TRANC;
            int totalPage = (int) (count / pageSize + 1);
            int currentPage = 1;
            int index = 0;
            for (; currentPage <= totalPage; currentPage++) {
                List<App> apps = appDao.listForDownloads(session, currentPage, pageSize);
                if (apps == null || apps.isEmpty()) {
                    break;
                }
                for (App a : apps) {
                    try {
                        effects += cb.doIn(session, a, appDao);
                        if (++index % AppConfig.BATCH_SIZE == 0) {
                            session.flush();
                            session.clear();
                        }
                    } catch (Exception e) {
                        session.clear();
                        logger.error("Exception", e);
                    }
                }
            }
            session.flush();
            session.clear();
        } finally {
            session.close();
        }
        return effects;
    }

    @Override
    public void updateDownloadsFromMarket() {
        Session session = sessions.openSession();
        try {
            session.setDefaultReadOnly(false);
            long allDownloads = 0;
            List<Market> markets = marketDao.list();
            Map<String, Market> marketMap = new HashMap<String, Market>(4, 1);
            for (Market market : markets) {
                long downloads = marketAppDao.countDownloads(session, market.getMarketName());
                // 设置市场的总下载量
                // market.setDownloads(downloads);
                marketDao.update(session, market);
                allDownloads += downloads;
                marketMap.put(market.getMarketName(), market);
            }
            logger.debug("allDownloads: {}", allDownloads);
            session.flush();
            session.clear();
            logger.info("Update market downloads!");
        } finally {
            session.close();
        }
    }

}
