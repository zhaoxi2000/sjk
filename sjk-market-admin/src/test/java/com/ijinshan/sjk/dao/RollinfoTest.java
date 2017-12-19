package com.ijinshan.sjk.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.vo.RollinfoDetail;

public class RollinfoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(RollinfoTest.class);

    @Resource(name = "rollinfoDaoImpl")
    private RollinfoDao dao;

    @Test
    public void testgetRollinfo() {
        long count = dao.count();
        List<RollinfoDetail> list = dao.getRollinfoDetail();
        Assert.assertEquals(count, list.size());
        logger.info("RollinfoDetail size : {}", list.size());
    }

    @Test
    public void testdeleteNotExistsApp() {
        int rows = dao.deleteNotExistsApp();
        logger.info("Delete {} rows!", rows);
    }

    @Test
    public void testupdateRecommand() {
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(314);
        dao.updateRecommand(appIds);
    }
}
