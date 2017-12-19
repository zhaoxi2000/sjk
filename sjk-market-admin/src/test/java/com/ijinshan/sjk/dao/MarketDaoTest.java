package com.ijinshan.sjk.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.dao.impl.MarketDaoImpl;
import com.ijinshan.sjk.po.Market;

public class MarketDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MarketDaoTest.class);

    @Resource(name = "marketDaoImpl")
    private MarketDaoImpl dao;

    @Override
    @Before
    public void init() {
        String marketName = "test";
        String key = "test";

        Market market = new Market();
        market.setMarketName(marketName);
        market.setAllowAccessKey(key);
        market.setLoginKey("");
        market.setSecurity("");
        dao.saveOrUpdate(market);
    }

    @Test
    public void testallowAccess() {
        String marketName = "test";
        String key = "test";

        assertTrue(dao.allowAccess(marketName, key));

        marketName = "eoemarket";
        key = "test";
        assertFalse(dao.allowAccess(marketName, key));

    }
}
