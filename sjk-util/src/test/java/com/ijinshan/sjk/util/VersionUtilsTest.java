package com.ijinshan.sjk.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.util.UnexceptedComparableException;
import com.ijinshan.sjk.util.VersionUtils;

public class VersionUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(VersionUtilsTest.class);

    @Test
    public void testToNumbericVersion() {
        String v1 = null;
        String v2 = null;
        try {
            v1 = "0.1";
            v2 = "1.1";
            assertTrue(VersionUtils.compare(v1, v2) < 0);
        } catch (UnexceptedComparableException e) {
            e.printStackTrace();
        }

        try {
            v1 = "1.1";
            v2 = "1.1";
            assertTrue(VersionUtils.compare(v1, v2) == 0);
        } catch (UnexceptedComparableException e) {
            e.printStackTrace();
        }

        try {
            v1 = "2.1";
            v2 = "1.1";
            assertTrue(VersionUtils.compare(v1, v2) > 0);
        } catch (UnexceptedComparableException e) {
            e.printStackTrace();
        }

        try {
            v1 = "2.1a";
            v2 = "1.1";
            assertTrue(VersionUtils.compare(v1, v2) > 0);
        } catch (UnexceptedComparableException e) {
            e.printStackTrace();
        }

        try {
            v1 = "2.1a";
            v2 = "1.1b";
            assertTrue(VersionUtils.compare(v1, v2) > 0);
        } catch (UnexceptedComparableException e) {
            e.printStackTrace();
        }

        try {
            v1 = "2.1a";
            v2 = "2.1b";
            assertTrue(VersionUtils.compare(v1, v2) < 0);
        } catch (UnexceptedComparableException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testtoNumbericVersion() {
        String version = "2.1a";
        String res = null;
        res = VersionUtils.toNumbericVersion(version);
        System.out.println(res);

        version = "2.1a.32.a";
        res = VersionUtils.toNumbericVersion(version);
        System.out.println(res);
    }

}
