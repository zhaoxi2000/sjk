package com.kingsoft.sjk;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.kingsoft.sjk.config.AppConfig;

/**
 * @author LinZuXiong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/context.xml", "/servlet-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Resource
    protected ApplicationContext ctx;

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    @Resource(name = "appConfig")
    protected AppConfig appConfig;

    @Test
    public void test() {

    }

}
