package com.ijinshan.sjk.service.programmeimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.config.ApkScanStatus;
import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.AppStatus;
import com.ijinshan.sjk.config.EnumCatalog;
import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.config.MarketStatus;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.dao.CatalogConvertorDao;
import com.ijinshan.sjk.dao.MarketAppDao;
import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.CatalogConvertor;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.po.marketmerge.BehaviorApkOfMarketApp;
//import com.ijinshan.sjk.po.marketmerge.BehaviorApkOfMarketApp;
import com.ijinshan.sjk.po.marketmerge.ManualApp;
import com.ijinshan.sjk.service.MergeAppComparator;
import com.ijinshan.sjk.service.MergeService;
import com.ijinshan.util.DefaultDateTime;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.Pager;

@Service
public class MergeServiceImpl implements MergeService {
    private static final Logger logger = LoggerFactory.getLogger(MergeServiceImpl.class);
    private static final Logger dbLogger = LoggerFactory.getLogger("db.market");
    private static final Logger dbExceptionAppLogger = LoggerFactory.getLogger("db.exception.app");

    @Resource(name = "appConfig")
    protected AppConfig appConfig;

    @Resource(name = "appDaoImpl")
    protected AppDao appDao;
    @Resource(name = "marketAppDaoImpl")
    protected MarketAppDao marketAppDao;

    @Resource(name = "mergeAppComparatorImpl")
    protected MergeAppComparator mergeAppComparator;
    @Resource(name = "catalogConvertorDaoImpl")
    protected CatalogConvertorDao catalogConvertorDao;

    @Resource(name = "marketDaoImpl")
    protected MarketDao marketDao;

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;
    private final String[] ignoreProperties = new String[] { "status", "imageUrls" };

    @Resource(name = "appHistory4IndexDaoImpl")
    private AppHistory4IndexDao appHistory4IndexDaoImpl;

    private void convertCatalogForApp(Session sess, MarketApp mApp, App app, boolean appExistsInDb) throws Exception {
        switch (EnumMarket.valueOf(mApp.getMarketName().toUpperCase())) {
            case SHOUJIKONG: {
                return;
            }
            case IJINSHAN: {
                return;
            }
            default: {
                break;
            }
        }
        if (!appExistsInDb || !app.isAuditCatalog()) {
            if (!appExistsInDb) {
                CatalogConvertor catalogConvertor = null;
                try {
                    catalogConvertor = catalogConvertorDao.getByMarketApp(sess, mApp.getMarketName(),
                            mApp.getCatalog(), mApp.getSubCatalog());
                    if (catalogConvertor != null) {
                        app.setCatalog(catalogConvertor.getTargetCatalog());
                        app.setSubCatalog(catalogConvertor.getTargetSubCatalog());
                    } else {
                        throw new RuntimeException();
                    }
                } catch (Exception e) {
                    catalogConvertor = null;
                    Object[] infos = new Object[] { mApp.getMarketName(), mApp.getCatalog(), mApp.getSubCatalog() };
                    dbLogger.error("Cannot convertCatalog~marketName: {} , catalog: {} , subCatalog: {}", infos);
                    throw new Exception("Cannot convertCatalog", e);
                }
            }
        }
    }

    @Override
    public void saveOrUpdateMarketApp(Market market, MarketApp mApp, Session session) throws Exception {
        Assert.isTrue(mApp.getAppId() > 0 || mApp.getApkId() > 0, "Market partner has a bug! AppId ,ApkId");
        // 对marketApp 作校验.
        if (mApp.getPkname() == null || mApp.getPkname().isEmpty()) {
            logger.error("invalid marketApp caused by pkname is empty. marketName:{}\t appId:{}\t apkId:{}",
                    mApp.getMarketName(), mApp.getAppId(), mApp.getApkId());
            return;
        }
        MarketApp marketAppOfDb = marketAppDao.get(session, mApp.getMarketName(), mApp.getPkname());
        Date now = new Date();
        if (marketAppOfDb != null) {
            if (mergeAppComparator.compare(mApp, marketAppOfDb, market, market) > 0) {
                // update Table MarketApp really.
                // restore variables. backup data , temp variables. 不要影响上次的扫描结果. 保存好id!
                BehaviorApkOfMarketApp backupBehaviorApk = new BehaviorApkOfMarketApp();
                BeanUtils.copyProperties(marketAppOfDb, backupBehaviorApk);
                BeanUtils.copyProperties(mApp, marketAppOfDb);
                // restore values.
                BeanUtils.copyProperties(backupBehaviorApk, marketAppOfDb);
                marketAppOfDb.setMarketUpdateTime(now);
                marketAppOfDb.setApkStatus(ApkScanStatus.NEED_SCAN.getVal());
                marketAppOfDb.setSignatureMd5(null);
                marketAppOfDb.setSignatureSha1(null);
                marketAppOfDb.setMd5(null);
                marketAppDao.update(session, marketAppOfDb);
            } else {
                return;
            }
        } else {
            // new
            mApp.setMarketUpdateTime(now);
            mApp.setMarketApkScanTime(DefaultDateTime.getDefaultDateTime());
            mApp.setAppFetchTime(DefaultDateTime.getDefaultDateTime());
            mApp.setApkStatus(ApkScanStatus.NEED_SCAN.getVal());
            marketAppDao.save(session, mApp);
        }
    }

    @Override
    public void mergeTo1App(boolean forceMerge, MarketApp mApp, Session session) throws Exception {
        List<App> apps = appDao.getApps(session, mApp.getPkname());
        Date now = new Date();
        if (apps == null || apps.isEmpty()) {
            createAppFromMarketApp(mApp, session, now);
            return;
        } else {
            Assert.isTrue(!mApp.getSignatureSha1().isEmpty(), "请确认该应用已经扫描过了.Id of table MarketApp: " + mApp.getId());
            // 大游戏 . 做特殊处理.
            if (mApp.getCatalog() == EnumCatalog.BIGGAME.getCatalog()
                    && EnumMarket.valueOf(mApp.getMarketName().toUpperCase()) == EnumMarket.SHOUJIKONG) {
                App app = appDao.getByFKMarketAppId(session, mApp.getId(), mApp.getCatalog());
                if (app == null) {
                    createAppFromMarketApp(mApp, session, now);
                } else {
                    updateAppFromMarketApp(true, mApp, session, now, app);
                }
                return;
            } else {
                // 简化需求和逻辑. 签名一样的更新会导致一个市场有两个包存在.
                // 先保证一个包一个签名.
                App app = appDao.getNotBigGame(session, mApp.getPkname(), mApp.getSignatureSha1());
                if (app != null) {
                    updateAppFromMarketApp(forceMerge, mApp, session, now, app);
                } else {
                    createAppFromMarketApp(mApp, session, now);
                }
            }
            return;
        }
    }

    @Override
    public App createAppFromMarketApp(MarketApp mApp, Session session, Date now) throws Exception {
        final App newApp = new App();
        // init
        BeanUtils.copyProperties(mApp, newApp, ignoreProperties);
        if (mApp.getIndexImgUrl() == null) {
            newApp.setIndexImgUrl("");
        }
        if (mApp.getLanguage() == null) {
            newApp.setLanguage("");
        }
        // 新增的. 确保JVM不对初始值的影响
        newApp.setId(null);
        newApp.setAutoCover(true);
        // convert catalog infos
        convertCatalogForApp(session, mApp, newApp, false);
        // set forginer key
        newApp.setMarketAppId(mApp.getId());
        setOfficialSigSha1ByPkname(session, newApp);
        newApp.setStatus(AppStatus.ADD.getVal());
        // all properties , datetime
        newApp.setApkScanTime(mApp.getMarketApkScanTime());
        newApp.setLastFetchTime(now);
        appDao.save(session, newApp);
        mApp.setAppFetchTime(now);
        marketAppDao.update(session, mApp);

        // 为索引历史表 准备
        appHistory4IndexDaoImpl.saveOrUpdate(newApp.getId());
        return newApp;
    }

    /**
     * 特殊逻辑处理,处理合作市场
     * 
     * @param forceMerge
     * @param mApp
     * @param session
     * @param now
     * @param updateApp
     */
    private void updateAppFromMarketApp(boolean forceMerge, MarketApp mApp, Session session, Date now, App updateApp) {
        // channel apk!!!
        // if (!mApp.getMarketName().equals(updateApp.getMarketName())) {
        // switch (EnumMarket.valueOf(updateApp.getMarketName().toUpperCase()))
        // {
        // case SHOUJIKONG_CHANNEL: {
        // // update appfetchtime
        // mApp.setAppFetchTime(now);
        // marketAppDao.update(session, mApp);
        // return;
        // }
        // default: {
        // break;
        // }
        // }
        // }

        if (appConfig.isUpdateAudit()) {
            if (updateApp.isAudit() && !updateApp.isAutoCover()) {
                // update appfetchtime
                mApp.setAppFetchTime(now);
                marketAppDao.update(session, mApp);
                return;
            }
        }

        Session sessionForSelect = sessions.openSession();
        // marketapp 所属的market
        // 需要更新的app 所属的market
        Market market = null, appWithSignatureOwnerMarket;
        try {
            sessionForSelect.setDefaultReadOnly(true);
            market = marketDao.getByName(sessionForSelect, mApp.getMarketName());
            appWithSignatureOwnerMarket = updateApp.getMarketName().equals(mApp.getMarketName()) ? market : marketDao
                    .getByName(sessionForSelect, updateApp.getMarketName());
        } finally {
            sessionForSelect.close();
        }

        // channel apk!!!
        // switch (EnumMarket.valueOf(mApp.getMarketName().toUpperCase())) {
        // case SHOUJIKONG_CHANNEL: {
        // // update appfetchtime
        // forceMerge = true;
        // break;
        // }
        // default: {
        // break;
        // }
        // }

        // 有签名
        if (forceMerge || mergeAppComparator.compare(mApp, updateApp, market, appWithSignatureOwnerMarket) > 0
                || StringUtils.isBlank(updateApp.getSignatureSha1())) {
            updateAppIfNeedUpdate(mApp, market, session, now, updateApp);
        }
        // finally set fetchTime
        // update appfetchtime
        mApp.setAppFetchTime(now);
        marketAppDao.update(session, mApp);
    }

    @Override
    public App updateAppIfNeedUpdate(MarketApp mApp, Market market, Session session, Date now, App updateApp) {
        boolean urlChanged = !mApp.getDownloadUrl().equals(updateApp.getDownloadUrl());
        // restore table app primary key.
        final Integer idOfTableAppInDb = updateApp.getId();
        final short catalog = updateApp.getCatalog();
        final int subCatalog = updateApp.getSubCatalog();
        final short targetCatalog = updateApp.getCatalog();
        final int targetSubCatalog = updateApp.getSubCatalog();
        // 判断是否为人工运用数据
        if (updateApp.isAudit()) {
            ManualApp backupManualApp = new ManualApp();
            BeanUtils.copyProperties(updateApp, backupManualApp);
            BeanUtils.copyProperties(mApp, updateApp, ignoreProperties);
            // restore
            BeanUtils.copyProperties(backupManualApp, updateApp);
        } else {
            BeanUtils.copyProperties(mApp, updateApp, ignoreProperties);
        }
        updateApp.setMarketAppId(mApp.getId());

        updateApp.setLastFetchTime(now);
        updateApp.setId(idOfTableAppInDb);
        if (updateApp.isAuditCatalog()) {
            updateApp.setCatalog(catalog);
            updateApp.setSubCatalog(subCatalog);
        } else {
            updateApp.setCatalog(targetCatalog);
            updateApp.setSubCatalog(targetSubCatalog);
        }
        setOfficialSigSha1ByPkname(session, updateApp);
        int status = updateApp.getStatus();
        updateApp.setStatus(status & ~AppStatus.MARKET_DATA_MASK.getVal() | AppStatus.UPDATE.getVal());
        // 更新了市场的数据,可以去掉隐藏.
        if (updateApp.isHidden()) {
            // caculate again
            status = updateApp.getStatus();

            // 市场状态
            if (MarketStatus.valueOf(market.getStatus().toUpperCase()) == MarketStatus.OK) {
                status = status & ~AppStatus.MARKET_DROP.getVal();
            }

            // 人工不恢复.
            if ((status & AppStatus.MANUAL_MASK.getVal()) != AppStatus.MANUAL_HIDDEN.getVal()) {
                if (urlChanged) {
                    // URL 变化下,恢复为显示
                    status = status & ~AppStatus.DETECT_MASK.getVal();
                    updateApp.setStatus(status);
                    updateApp.setHidden(false);
                }
            }
        }
        appDao.update(session, updateApp);
        appHistory4IndexDaoImpl.saveOrUpdate(updateApp.getId());
        return updateApp;
    }

    private void setOfficialSigSha1ByPkname(Session session, App app) {
        if (app.getOfficialSigSha1() == null || app.getOfficialSigSha1().isEmpty()) {
            String officialSigSha1 = appDao.getOfficialSigSha1(session, app.getPkname());
            if (officialSigSha1 != null) {
                app.setOfficialSigSha1(officialSigSha1);
            }
        }
    }

    @Override
    public void mergeToApp() {
        logger.info("Begin to merge for App...");
        Session session = sessions.openSession();
        try {
            final List<Integer> allUpdateIds = marketAppDao.getIdsOfUpdateNotScan(session);
            if (allUpdateIds != null && !allUpdateIds.isEmpty()) {
                final int pageSize = AppConfig.BATCH_SIZE_OF_TRANC;
                Pager<MarketApp> pager = new Pager<MarketApp>(pageSize, allUpdateIds.size());
                logger.info("getTotalPage: {} . Size: {}", pager.getTotalPage(), allUpdateIds.size());
                // save pagination for marketApp

                for (int i = 1; i <= pager.getTotalPage(); i++) {
                    pager.setCurrentPage(i);
                    List<Integer> pagination = new ArrayList<Integer>(pageSize);
                    final int offset = HibernateHelper.firstResult(i, pageSize);
                    final int end = Math.min(offset + pageSize, allUpdateIds.size());
                    for (int index = offset; index < end; index++) {
                        try {
                            pagination.add(allUpdateIds.get(index));
                        } catch (IndexOutOfBoundsException e) {
                            logger.error("{} , allUpdateIds.size():{}", index, allUpdateIds.size());
                        }
                    }
                    List<MarketApp> marketApps = marketAppDao.getByIds(session, pagination);
                    if (marketApps != null && !marketApps.isEmpty()) {
                        long start = System.currentTimeMillis();
                        batchIn1TransactionToApp(session, marketApps);
                        logger.info("batchIn1TransactionToApp{},耗时:{}", i, (System.currentTimeMillis() - start) / 1000
                                + " s!");
                    }
                    pagination.clear();
                    marketApps.clear();
                }
            } else {
                logger.info("No ids of updateNotScan!");
            }
            allUpdateIds.clear();
            session.clear();
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            session.close();
        }
        logger.info("Ending merge for App...");
    }

    private Transaction batchIn1TransactionToApp(Session session, List<MarketApp> marketApps) {
        Transaction tx = null;
        try {
            session.setDefaultReadOnly(false);
            tx = session.beginTransaction();
            long start = System.currentTimeMillis();
            mergePaginationToApp(session, marketApps);
            session.flush();
            session.clear();
            logger.info("mergePaginationToApp耗时:{}", (System.currentTimeMillis() - start) + " ms!");
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Exception", e);
        }
        return tx;
    }

    public void mergePaginationToApp(Session session, List<MarketApp> marketApps) {
        if (marketApps == null || marketApps.isEmpty()) {
            return;
        }
        int count = 0;
        List<MarketApp> exceptionApps = new ArrayList<MarketApp>();
        for (MarketApp mApp : marketApps) {
            try {
                if (++count % AppConfig.BATCH_SIZE == 0) {
                    session.flush();
                }
                do1App(mApp, session);
            } catch (Exception e) {
                session.clear();
                exceptionApps.add(mApp);
                dbExceptionAppLogger.error(mApp.toString());
                if (e instanceof org.hibernate.NonUniqueObjectException) {
                    dbExceptionAppLogger.error("The reduplicated entity.");
                } else {
                    dbExceptionAppLogger.error("Exception", e);
                }
            }
        }
        // do again
        if (!exceptionApps.isEmpty()) {
            session.clear();
            marketApps.removeAll(exceptionApps);
            exceptionApps.clear();
            exceptionApps = null;
            mergePaginationToApp(session, marketApps);
        } else {
            session.flush();
        }
    }

    protected void do1App(MarketApp mApp, Session session) throws Exception {
        handleMarketApp(mApp.getMarketName(), mApp);
        this.mergeTo1App(false, mApp, session);
    }

    private void handleMarketApp(String marketName, MarketApp mApp) {
        if (mApp.getStatus() == null || mApp.getStatus() == 0) {
            mApp.setEnumStatus(AppStatus.UPDATE.getName());
        } else {
            mApp.setEnumStatus(AppStatus.appStatus(mApp.getStatus()).getName());
        }
    }
}
