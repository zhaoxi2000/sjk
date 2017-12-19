package com.ijinshan.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HinbernateHelperTest {
    private static final Logger logger = LoggerFactory.getLogger(HinbernateHelperTest.class);

    @Test
    public void test() {
        // page=1684541847&rows=20
        int offset = HibernateHelper.firstResult(1684541847, 20);
        System.out.println(offset);
    }
}
