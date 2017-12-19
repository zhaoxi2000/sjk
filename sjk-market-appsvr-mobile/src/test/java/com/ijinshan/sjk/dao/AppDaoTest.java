package com.ijinshan.sjk.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.hibernate.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;

public class AppDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoTest.class);

    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    @Test
    public void test() {
        List<Rollinfo> list = appDao.rollinfo();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testget() {
    }

    @Test
    public void testgetForIndex() {
        Session session = null;
        try {
            session = super.sessions.openSession();
            short catalog = 1;
            Integer subCatalog = 3;
            Integer currentPage = 1;
            Integer pageSize = 30;
            List<App> list = appDao.getForIndex(session, catalog, subCatalog, currentPage, pageSize);
            Assert.assertNotNull(list);
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Test
    public void testgetAppInfo() {
        // AppInfo app = appDao.getAppInfo(1);
        // assertNotNull(app);
    }

    @Test
    public void testcountName() {
        Session session = null;
        try {
            session = super.sessions.openSession();
            long count = appDao.countName(session);
            Assert.assertTrue(count > 0);
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Test
    public void testgetScanTop() {
        List<App> list = appDao.getScanTop();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testupdateIncrementDownload() {
        short catalog = 1;
        Integer subCatalog = 3;
        String sort = null;
        String order = null;
        int currentPage = 1;
        int pageSize = 10;
        Boolean noVirus = null;
        Boolean noAds = null;
        Boolean official = null;
        List<App> list = appDao.list(catalog, subCatalog, sort, order, currentPage, pageSize, noAds, noVirus, official);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        App a = list.get(0);
        logger.info("Id:{}", a.getId());
        Session session = sessions.openSession();
        int delta = 9;
        int rows = appDao.updateIncrementDownload(session, a.getId(), delta);
        session.flush();
        assertTrue(rows > 0);
        rows = appDao.updateIncrementDownload(session, a.getId(), -delta);
        session.flush();
        session.close();
    }

}
