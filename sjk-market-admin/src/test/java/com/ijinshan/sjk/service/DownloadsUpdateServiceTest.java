package com.ijinshan.sjk.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class DownloadsUpdateServiceTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(DownloadsUpdateServiceTest.class);

    @Resource(name = "downloadsUpdateServiceImpl")
    private DownloadsUpdateService service;

    @Test
    public void test() {
        service.update();
    }
}
