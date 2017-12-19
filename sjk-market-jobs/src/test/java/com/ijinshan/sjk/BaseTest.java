package com.ijinshan.sjk;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ijinshan.sjk.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/context.xml", "/servlet-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Before
    public void init() {
        Assert.assertNotNull(appConfig);
        logger.info("Test bootstrap OK!");
    }

    public static void main(String[] args) {
        System.out.println(4 / 3.0);
        System.out.println(658 / 1000.0d);
    }
}
