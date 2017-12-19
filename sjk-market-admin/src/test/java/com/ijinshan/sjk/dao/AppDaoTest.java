package com.ijinshan.sjk.dao;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Test
    public void testlistForBase() {
        short catalog = (short) 1;
        Integer subCatalog = 3;
        int currentPage = 1;
        int pageSize = 20;
        List<App> list = appDao.listForBase(catalog, subCatalog, currentPage, pageSize);
        for (App a : list) {
            System.out.println(" id : " + a.getId() + "\t audit : " + a.isAuditCatalog() + "\t name : " + a.getName());
        }
    }

    @Test
    public void testgetAuditedCatalog() {
        List<App> list = appDao.getAuditedCatalog();
        if (list != null) {
            for (App a : list) {
                assertTrue(a.isAuditCatalog());
            }
        }
    }

    @Test
    public void testgetAppByPackageName() {
        String[] packagename = null;
        packagename = new String[] { "ab.gobang", "ab.gobanga", "abacus.contentmenu", "Abacus.Menu", "abc.AsianMM" };
        for (String s : packagename) {
            appDao.getAppByPackageName(s);
        }

        Session session = sessions.openSession();
        appDao.getAppByPackageName(session, "com.g6677.android.bsg");
        session.close();
    }

    @Test
    public void testget() {
        Serializable id = 1;
        App app = appDao.get(id);
        logger.debug(app.getName());
    }

    @Test
    public void testsearchForRolling() {
        String keywords = null;
        String sort = null, order = null;
        List<Rollinfo> list = appDao.searchForRolling((short) 1, 3, 1, 20, keywords, sort, order);
        Assert.assertNotNull(list);
    }
}
