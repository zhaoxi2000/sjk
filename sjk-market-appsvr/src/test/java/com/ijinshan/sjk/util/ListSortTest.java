package com.ijinshan.sjk.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.App;

public class ListSortTest {
    private static final Logger logger = LoggerFactory.getLogger(ListSortTest.class);

    @Test
    public void test() {
        List<Integer> ids = new ArrayList<Integer>();
        List<App> list = new ArrayList<App>();
        ids.add(10);
        ids.add(8);
        ids.add(1);
        ids.add(11);
        App a = new App();
        a.setId(1);
        list.add(a);
        a = new App();
        a.setId(10);
        list.add(a);
        a = new App();
        a.setId(11);
        list.add(a);

        int haveLen = list.size();
        // case
        ListSort.sort(ids, list, true);
        Assert.assertTrue("the same count!", haveLen == list.size());
        Assert.assertEquals(10, list.get(0).getId().intValue());
        Assert.assertEquals(1, list.get(1).getId().intValue());
        Assert.assertEquals(11, list.get(2).getId().intValue());

        // case
        ListSort.sort(ids, list, false);
        Assert.assertTrue("the same count!", haveLen == list.size());
        Assert.assertEquals(10, list.get(0).getId().intValue());
        Assert.assertEquals(1, list.get(1).getId().intValue());
        Assert.assertEquals(11, list.get(2).getId().intValue());
    }
}
