package com.ijinshan.sjk.service;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.MarketApp;

public class MergeServiceImplTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(MergeServiceImplTest.class);

    @Resource(name = "mergeServiceImpl")
    private MergeService mergeServiceImpl;

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Test
    public void testCreateApp() {
        logger.info("execute...");

        MarketApp mApp = null;
        Session session = null;
        App newApp = null;
        Date now = null;

        try {
            mApp = new MarketApp();
            mApp.setName("New");
            mApp.setUpdateInfo("更新info");

            newApp = new App();
            newApp.setName("Test");

            now = new Date();
            session = sessions.openSession();

            mergeServiceImpl.createAppFromMarketApp(mApp, session, now);

        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
        }

    }
}
