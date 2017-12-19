package com.ijinshan.sjk.mapper;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.CatalogInfo;

public class CatalogMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CatalogMapperTest.class);

    @Resource(name = "catalogMapper")
    private CatalogMapper catalogMapper;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testlistNoParameters() {
        List<Catalog> list = catalogMapper.list();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testlist() {
        int catalog = 2;
        List<Catalog> list = catalogMapper.listByCatalog(catalog);
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testlistCatalogInfo() {
        int catalog = 1;
        Boolean noAds = null;
        Boolean official = Boolean.FALSE;
        List<CatalogInfo> list = catalogMapper.listCatalogInfoByFilters(catalog, noAds, official);
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testlistCatalogInfoNoParameters() {
        List<CatalogInfo> list = catalogMapper.listCatalogInfo();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

}
