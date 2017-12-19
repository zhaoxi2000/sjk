package com.ijinshan.sjk.dao;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.CatalogConvertor;

public class CatalogConvertorDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CatalogConvertorDaoTest.class);

    @Resource(name = "catalogConvertorDaoImpl")
    private CatalogConvertorDao dao;

    @Test
    public void testgetByMarketApp() {
        String marketName = "eoemarket";
        short catalog = 2;
        int subCatalog = 15;
        CatalogConvertor convertor = dao.getByMarketApp(marketName, catalog, subCatalog);
        Assert.assertNotNull(convertor);
        Assert.assertTrue(convertor.getId() > 0);
    }
}
