package com.ijinshan.sjk.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.dao.impl.MarketAppDaoImpl;

public class MarketAppDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MarketAppDaoTest.class);

    @Resource(name = "marketAppDaoImpl")
    private MarketAppDaoImpl dao;

    @Test
    public void testGetPK() {
        Integer pk = dao.getPK("eoemarket", -11111111);
        Assert.assertNull(pk);

        pk = dao.getPK("eoemarket", 0);
        Assert.assertNull(pk);

        Session sess = dao.getSessions().openSession();
        Integer idOfTableMarketApp = dao.getPK(sess, "AppChina", 1952);
        Assert.assertNotNull(idOfTableMarketApp);
        sess.close();

    }

    @Test
    public void testgetPKByMarketApp() {
        String marketName = "eoemarket";
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(88318);
        ids.add(64572);
        ids.add(50568);
        ids.add(59215);
        ids.add(61618);
        List<Integer> pks = dao.getPKByMarketApp(marketName, ids);
        System.out.println(pks);
    }

    @Test
    public void testcountDownloads() {
        String pkname = "com.tencent.mobileqq";
        int id = 0;
        int downloads = dao.countDownloads(pkname, id);
        System.out.println(downloads);
    }
}
