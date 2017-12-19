package com.ijinshan.sjk.service.programmeimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppStatus;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.dao.MarketAppDao;
import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.jsonpo.BrokenApp;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.service.MarketAppService;
import com.ijinshan.sjk.service.MergeService;
import com.ijinshan.sjk.service.OffAppService;
import com.ijinshan.sjk.service.RollinfoService;
import com.ijinshan.sjk.service.TagRelationshipService;

@Service
public class OffAppServiceImpl implements OffAppService {
    private static final Logger logger = LoggerFactory.getLogger(OffAppServiceImpl.class);
    private static final Logger dbDelete = LoggerFactory.getLogger("db.brokenlink");

    @Resource(name = "mergeServiceImpl")
    private MergeService mergeService;

    @Resource(name = "marketAppServiceImpl")
    private MarketAppService marketAppService;

    @Resource(name = "marketDaoImpl")
    protected MarketDao marketDao;

    @Resource(name = "marketAppDaoImpl")
    protected MarketAppDao marketAppDao;
    @Resource(name = "appDaoImpl")
    protected AppDao appDao;

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Resource(name = "rollinfoServiceImpl")
    protected RollinfoService rollinfoService;

    @Resource(name = "tagRelationshipServiceImpl")
    protected TagRelationshipService tagRelationshipService;

    @Resource(name = "appHistory4IndexDaoImpl")
    protected AppHistory4IndexDao appHistory4IndexDaoImpl;

    @Override
    public void deleteByBrokenApp(String marketName, List<BrokenApp> data) {
        List<Integer> apkIds = new ArrayList<Integer>();
        for (BrokenApp broken : data) {
            apkIds.add(broken.getId());
            Object[] infos = new Object[] { marketName, broken.getId(), broken.getLink(), broken.getStatusCode() };
            dbDelete.info("marketName:{}\t Id:{}\t Link:{}\t StatusCode:{}", infos);
        }
        deleteBatchByApkIdsOfMarketApp(marketName, apkIds);
    }

    @Override
    public void deleteBatchByApkIdsOfMarketApp(String marketName, List<Integer> apkIdsOfMarketApp) {
        try {
            for (Integer idInMarket : apkIdsOfMarketApp) {
                deleteTransactionByManyId(marketName, null, idInMarket);
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
        }
    }

    @Override
    public void deleteTransactionByManyId(String marketName, Integer appIdOfMarket, int apkIdOfMarketApp) {
        Session session = sessions.openSession();
        try {
            MarketApp mApp = marketAppDao.getByManyId(session, marketName, appIdOfMarket, apkIdOfMarketApp);
            if (mApp != null) {
                int pkOfMarketApp = mApp.getId().intValue();
                marketAppDao.delete(session, mApp);
                session.flush();
                dbDelete.info("Off {} , pkOfMarketApp:{} , name:{} , pkname:{} , appId:{} , apkId:{}", marketName,
                        pkOfMarketApp, mApp.getName(), mApp.getPkname(), mApp.getAppId(), mApp.getApkId());
                // 取出App中的数据, 尽量让App保留一条数据
                List<App> apps = appDao
                        .getByMarketApp(session, mApp.getMarketName(), mApp.getApkId(), mApp.getPkname());
                if (apps != null && !apps.isEmpty()) {
                    MarketApp topMarketApp = marketAppService
                            .getTop(session, mApp.getPkname(), mApp.getSignatureSha1());
                    for (App app : apps) {
                        if (app != null) {
                            if (topMarketApp != null) {
                                try {
                                    mergeService.mergeTo1App(true, topMarketApp, session);
                                    session.flush();
                                    dbDelete.info("Keep App from MarketApp . pkOfMarketApp:{} , appId:{} , apkId:{}",
                                            topMarketApp.getId(), topMarketApp.getAppId(), topMarketApp.getApkId());
                                } catch (Exception e) {
                                    session.clear();
                                    dbDelete.error("Cannot merge!It is very dangerous!", e);
                                }
                            } else {
                                Integer id = app.getId();
                                if (app.getRealDownload() < 1) {
                                    dbDelete.info("Delete data from App . Id:{} , Name:{} , Pkname:{}", id,
                                            app.getName(), app.getPkname());
                                    appDao.delete(session, app);
                                } else {
                                    dbDelete.info("Off app , hide it! Id:{}", id);
                                    int status = app.getStatus();
                                    app.setStatus(status & ~AppStatus.MARKET_DATA_MASK.getVal()
                                            | AppStatus.DELETE.getVal());
                                    app.setHidden(true);
                                    appDao.updateHide(session, app);
                                }
                                deleteOtherTransactionsByAppId(session, id);
                                session.flush();
                            }
                        }
                    }
                } // 回滚一个版本
                session.flush();
            } // end if
        } catch (Exception e) {
            session.clear();
            dbDelete.error("Exception", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteOtherTransactionsByAppId(Session session, int appId) {
        rollinfoService.deleteByAppId(session, appId);
        tagRelationshipService.deleteByAppId(session, appId);

        // 搜索索引修改
        appHistory4IndexDaoImpl.updateAppStatus2Del(appId);
    }
}
