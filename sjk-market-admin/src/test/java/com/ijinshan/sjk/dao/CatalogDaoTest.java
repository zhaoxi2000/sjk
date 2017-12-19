package com.ijinshan.sjk.dao;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.Catalog;

public class CatalogDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CatalogDaoTest.class);

    @Resource(name = "catalogDaoImpl")
    private CatalogDao catalogDao;

    @Test
    public void test() {
        int pid = 1;
        List<Catalog> list = catalogDao.list((short) pid);
        Assert.assertTrue(list.size() > 0);
    }
}
