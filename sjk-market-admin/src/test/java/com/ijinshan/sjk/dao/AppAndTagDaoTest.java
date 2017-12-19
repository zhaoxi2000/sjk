package com.ijinshan.sjk.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.AppAndTag;

public class AppAndTagDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(AppAndTagDaoTest.class);

    @Resource(name = "appAndTagDaoImpl")
    private AppAndTagDao dao;

    @Test
    public void testlistTagByApp() {
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(11);
        List<AppAndTag> list = dao.listTagByApp(appIds);
        System.out.println(list);
    }
}
