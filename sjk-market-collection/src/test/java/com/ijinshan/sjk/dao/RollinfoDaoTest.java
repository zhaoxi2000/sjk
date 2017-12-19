package com.ijinshan.sjk.dao;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ijinshan.sjk.service.RollinfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/context.xml", "/servlet-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class RollinfoDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(RollinfoDaoTest.class);

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Resource(name = "rollinfoServiceImpl")
    protected RollinfoService rollinfoServiceImpl;

    @Test
    @Transactional
    @Rollback(true)
    public void test() throws UnsupportedEncodingException {
        int appId = 1656;
        Session session = sessions.getCurrentSession();
        try {
            rollinfoServiceImpl.deleteByAppId(session, appId);
            System.out.println("Done!");
        } finally {
        }
    }
}
