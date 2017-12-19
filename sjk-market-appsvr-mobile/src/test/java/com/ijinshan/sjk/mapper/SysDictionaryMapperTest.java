package com.ijinshan.sjk.mapper;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ijinshan.sjk.BaseTest;

public class SysDictionaryMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryMapperTest.class);

    @Resource(name = "sysDictionaryMapper")
    private SysDictionaryMapper sysDictionaryMapper;

    @Test
    public void testSysDictionaryMapper() {
        int recommendNum = sysDictionaryMapper.getAppsRollRecommendNum();
        logger.debug("recommendNum:{}", recommendNum);
        Assert.isTrue(recommendNum > 0, "error.");
    }

}
