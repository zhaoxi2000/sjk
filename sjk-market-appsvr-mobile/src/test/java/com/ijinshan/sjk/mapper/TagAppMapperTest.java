package com.ijinshan.sjk.mapper;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.AppAndTag;

public class TagAppMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(TagAppMapperTest.class);

    @Resource(name = "tagAppMapper")
    private TagAppMapper tagAppMapper;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testget() {
        List<AppAndTag> list = tagAppMapper.getTags(4, 1);

        for (AppAndTag appAndTag : list) {
            logger.debug("AppAndTag:{}", appAndTag);
        }

        Assert.assertNotNull(list);

    }
}
