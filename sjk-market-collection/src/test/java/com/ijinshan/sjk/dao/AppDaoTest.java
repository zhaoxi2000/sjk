package com.ijinshan.sjk.dao;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class AppDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoTest.class);

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Resource(name = "appDaoImpl")
    protected AppDao appDao;

    @Test
    public void test() {
        Session session = sessions.openSession();
        try {
            String pkname = "org.cocos2dx.FishGame";
            String officialSign = appDao.getOfficialSigSha1(session, pkname);
            Assert.assertNotNull(officialSign);
        } finally {
            session.close();
        }
    }
}
