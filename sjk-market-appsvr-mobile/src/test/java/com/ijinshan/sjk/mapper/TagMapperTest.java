package com.ijinshan.sjk.mapper;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.Tag;

public class TagMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(TagMapperTest.class);

    @Resource(name = "tagMapper")
    private TagMapper tagMapper;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testget() {
        List<Tag> list = tagMapper.getList();
        for (Tag tag : list) {
            logger.debug("tag:{}", tag.getName());
        }

        Assert.assertNotNull(list);
    }
}
