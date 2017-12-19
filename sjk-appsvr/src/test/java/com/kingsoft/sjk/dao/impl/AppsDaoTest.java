package com.kingsoft.sjk.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingsoft.sjk.BaseTest;
import com.kingsoft.sjk.dao.AppsDao;
import com.kingsoft.sjk.po.AndroidApp;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.AppType;
import com.kingsoft.sjk.po.Topic;

public class AppsDaoTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(AppsDaoTest.class);

    @Resource(name = "appsDaoImpl")
    private AppsDao dao;

    @Test
    public void testList() {
        List<AndroidApp> list = null;

        list = dao.list(null, null);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testFindByIds() {
        List<Integer> ids = new ArrayList<Integer>();
        List<App> list = null;

        ids.add(4);
        ids.add(5);
        ids.add(2);

        list = dao.findByIds(ids);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetAppTypes() {
        List<AppType> list = null;
        list = dao.getAppTypes(1);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetPowerTuiJian() {
        List<App> list = null;
        list = dao.getPowerTuiJian(1, 0, 20);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetPowerTuiJianTopicApps() {
        List<App> list = null;
        list = dao.getPowerTuiJianTopicApps(2, 0, 20);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetPowerTuiJianTopics() {
        List<Topic> list = null;
        // 上线的应用
        list = dao.getPowerTuiJianTopics(1, 0, 20);
        // 下线的应用
        if (list == null) {
            list = dao.getPowerTuiJianTopics(2, 0, 20);
        } else {
            List<Topic> another = dao.getPowerTuiJianTopics(2, 0, 200);
            if (another != null) {
                list.addAll(another);
            }
        }
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetPowerChannelTuiJianTopicApps() {
        List<App> list = null;
        list = dao.getPowerChannelTuiJianTopicApps(2, 0, 20);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetPowerChannelTuiJianTopics() {
        List<Topic> list = null;
        // 上线的应用
        list = dao.getPowerChannelTuiJianTopics(1, 0, 20);
        // 下线的应用
        if (list == null) {
            list = dao.getPowerChannelTuiJianTopics(2, 0, 20);
        } else {
            List<Topic> another = dao.getPowerChannelTuiJianTopics(2, 0, 200);
            if (another != null) {
                list.addAll(another);
            }
        }
        assertTrue(list.size() > 0);
    }

}
