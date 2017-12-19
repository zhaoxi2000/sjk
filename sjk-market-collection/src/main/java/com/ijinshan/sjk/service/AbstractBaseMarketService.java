package com.ijinshan.sjk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.AppStatus;
import com.ijinshan.sjk.dao.AccessMarketDao;
import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.service.adapter.MarketAppFromMarketAdapter;

public abstract class AbstractBaseMarketService implements BaseMarketService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseMarketService.class);
    private static final Logger dbExceptionAppLogger = LoggerFactory.getLogger("db.exception.app");

    public static final int TRY_TIME = 2;

    protected MarketAppFromMarketAdapter marketAppFromMarketAdapter;
    protected AccessMarketDao accessMarketDao;
    protected String marketName;

    @Resource(name = "appConfig")
    protected AppConfig appConfig;

    @Resource(name = "mergeServiceImpl")
    private MergeService mergeService;
    @Resource(name = "offAppServiceImpl")
    protected OffAppService offAppService;

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Resource(name = "marketDaoImpl")
    protected MarketDao marketDao;

    @Override
    public abstract void importFull();

    @Override
    public void savePaginationMarketApp(Session session, Market market, List<MarketApp> marketApps,
            List<MarketApp> offMarketApps) {
        if (marketApps.isEmpty()) {
            return;
        }
        int count = 0;
        List<MarketApp> exceptionApps = new ArrayList<MarketApp>();
        for (MarketApp mApp : marketApps) {
            try {
                if (++count % AppConfig.BATCH_SIZE == 0) {
                    session.flush();
                }
                do1MarketApp(market, mApp, session, offMarketApps);
            } catch (Exception e) {
                session.clear();
                exceptionApps.add(mApp);
                getDbexceptionapplogger().info(mApp.toString());
                if (e instanceof org.hibernate.NonUniqueObjectException) {
                    getDbexceptionapplogger().error("The reduplicated entity.");
                } else {
                    getDbexceptionapplogger().error("Exception", e);
                }
            }
        }
        // do again
        if (!exceptionApps.isEmpty()) {
            session.clear();
            marketApps.removeAll(exceptionApps);
            exceptionApps.clear();
            exceptionApps = null;
            savePaginationMarketApp(session, market, marketApps, offMarketApps);
        } else {
            session.flush();
        }
    }

    @Override
    public abstract void importIncrement();

    protected void do1MarketApp(Market market, MarketApp mApp, Session sess, List<MarketApp> offMarketApps)
            throws Exception {
        String marketName = market.getMarketName();
        preHandle(marketName, mApp);
        AppStatus status = AppStatus.appStatus(mApp.getStatus());
        switch (status) {
            case DELETE: {
                // deleteMarketAppTransaction(mApp, sess);
                if (offMarketApps != null && !offMarketApps.contains(mApp)) {
                    offMarketApps.add(mApp);
                }
                break;
            }
            case UNCHANGED: {
                break;
            }
            default: {
                saveOrUpdateMarketApp(market, mApp, sess);
                break;
            }
        }
    }

    /**
     * 公共方法,把市场的数据完全处理成我们数据库中的数据格式. <br />
     * TODO 修改方法签名, 根据返回值做废弃数据处理.
     * 
     * @param marketName
     * @param mApp
     */
    protected void preHandle(String marketName, MarketApp mApp) {
        // 非必要DI
        if (marketAppFromMarketAdapter != null) {
            marketAppFromMarketAdapter.preHandle(mApp);
        }
        // reset pk in db
        mApp.setId(null);
        mApp.setEnumStatus(AppStatus.appStatus(mApp.getStatus()).getName());
        mApp.setMarketName(marketName);
    }

    @Override
    public void deleteMarketAppTransaction(MarketApp mApp) {
        this.offAppService.deleteTransactionByManyId(mApp.getMarketName(), mApp.getAppId(), mApp.getApkId());
    }

    @Override
    public void deleteMarketAppsTransaction(List<MarketApp> mApps) {
        if (mApps != null && !mApps.isEmpty()) {
            for (MarketApp mApp : mApps) {
                deleteMarketAppTransaction(mApp);
            }
        }
    }

    @Override
    public void saveOrUpdateMarketApp(Market market, MarketApp mApp, Session session) throws Exception {
        this.mergeService.saveOrUpdateMarketApp(market, mApp, session);
    }

    @Override
    public void resetMarketForFull(Date lastReqDate) {
        Session session = sessions.openSession();
        try {
            Market market = getMarket(session);
            market.setFullLastReqCurrentPage(0);
            market.setFullTotalPages(0);
            if (lastReqDate != null) {
                market.setFullLastTime(lastReqDate);
            }
            marketDao.update(session, market);
            session.flush();
            session.clear();
            logger.debug("Reset {} market data for full! ", market.getMarketName());
        } finally {
            session.close();
        }
    }

    @Override
    public void resetMarketForIncrement(Date lastReqDate) {
        Session session = sessions.openSession();
        try {
            Market market = getMarket(session);
            market.setIncrementLastReqCurrentPage(0);
            market.setIncrementTotalPages(0);
            market.setIncrementLastTime(lastReqDate);
            marketDao.update(session, market);
            session.flush();
            session.clear();
            logger.debug("Reset market for increment! {}", market.getMarketName());
        } finally {
            session.close();
        }
    }

    @Override
    public Market getMarket(Session session) {
        Market market = this.marketDao.getByName(session, this.getMarketName());
        return market;
    }

    /**
     * sleep 2 mins
     */
    protected void sleepForTry() {
        final int millis = 60000 * 2;
        logger.info("Slepping {} millis...Market: {}", millis, this.getMarketName());
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    public static Logger getDbexceptionapplogger() {
        return dbExceptionAppLogger;
    }

    public MarketAppFromMarketAdapter getMarketAppFromMarketAdapter() {
        return marketAppFromMarketAdapter;
    }

    public void setMarketAppFromMarketAdapter(MarketAppFromMarketAdapter marketAppFromMarketAdapter) {
        this.marketAppFromMarketAdapter = marketAppFromMarketAdapter;
    }

    public AccessMarketDao getAccessMarketDao() {
        return accessMarketDao;
    }

    public void setAccessMarketDao(AccessMarketDao accessMarketDao) {
        this.accessMarketDao = accessMarketDao;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketName() {
        return marketName;
    }

}
