package com.ijinshan.sjk;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
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

    @Before
    public void init() {
        Assert.assertNotNull(appConfig);
        System.out.println("Test bootstrap OK!");

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "{\"key\":\"jjG8LkC3MHyF9XcsVKh6FHxmtLCNYtMx\",\"marketName\":\"AppChina\",\"count\":0,\"data\":[]}";
        System.out.println(Base64.encodeBase64String(s.getBytes()));
        System.out.println(Base64.encodeBase64String(s.getBytes("UTF-8")));
    }
}
