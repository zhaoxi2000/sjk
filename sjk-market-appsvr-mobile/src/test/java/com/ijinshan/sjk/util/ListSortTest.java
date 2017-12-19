package com.ijinshan.sjk.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.vo.MobileSearchApp;

public class ListSortTest {
    private static final Logger logger = LoggerFactory.getLogger(ListSortTest.class);

    @Test
    public void test() {
        List<Integer> ids = new ArrayList<Integer>();
        List<MobileSearchApp> list = new ArrayList<MobileSearchApp>();
        ids.add(10);
        ids.add(8);
        ids.add(1);
        ids.add(11);
        MobileSearchApp a = new MobileSearchApp();
        a.setId(1);
        list.add(a);
        a = new MobileSearchApp();
        a.setId(10);
        list.add(a);
        a = new MobileSearchApp();
        a.setId(11);
        list.add(a);

        int haveLen = list.size();
        // case
        ListSort.sort(ids, list, true);
        Assert.assertTrue("the same count!", haveLen == list.size());
        Assert.assertEquals(10, list.get(0).getId());
        Assert.assertEquals(1, list.get(1).getId());
        Assert.assertEquals(11, list.get(2).getId());

        // case
        ListSort.sort(ids, list, false);
        Assert.assertTrue("the same count!", haveLen == list.size());
        Assert.assertEquals(10, list.get(0).getId());
        Assert.assertEquals(1, list.get(1).getId());
        Assert.assertEquals(11, list.get(2).getId());
    }
}
