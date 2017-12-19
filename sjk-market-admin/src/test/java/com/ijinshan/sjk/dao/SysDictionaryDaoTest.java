package com.ijinshan.sjk.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class SysDictionaryDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryDaoTest.class);

    @Resource(name = "sysDictionaryDaoImpl")
    private SysDictionaryDao sysDictionaryDao;

    @Test
    public void test() {
        long count = sysDictionaryDao.count();
        System.out.println(count);
    }
}
