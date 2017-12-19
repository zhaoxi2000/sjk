package com.ijinshan.sjk.mapper;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class TagAppMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(TagAppMapperTest.class);

    @Resource(name = "tagAppMapper")
    private TagAppMapper tagAppMapper;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testget() {

    }
}
