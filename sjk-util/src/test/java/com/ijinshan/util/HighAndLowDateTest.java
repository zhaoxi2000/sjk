package com.ijinshan.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.util.HighAndLowDate;

public class HighAndLowDateTest {
    private static final Logger logger = LoggerFactory.getLogger(HighAndLowDateTest.class);

    @Test
    public void test() {
        HighAndLowDate current = new HighAndLowDate(System.currentTimeMillis());
        System.out.println(current.getLow());
        System.out.println(current.getHigh());
        System.out.println(0xFFFF);
        System.out.println(0xFFF);
    }
}
