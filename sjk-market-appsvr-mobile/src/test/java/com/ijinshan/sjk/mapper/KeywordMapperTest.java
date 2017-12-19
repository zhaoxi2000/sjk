package com.ijinshan.sjk.mapper;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.Keyword;

public class KeywordMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(KeywordMapperTest.class);

    @Resource(name = "keywordMapper")
    private KeywordMapper keywordMapper;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testget() {
        Keyword keyword = keywordMapper.get("百度输入法");
        Assert.assertNotNull(keyword);

    }

}
