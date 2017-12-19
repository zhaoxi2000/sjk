package com.kingsoft.sjk;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.App;
import com.kingsoft.sjk.config.TagType;
import com.kingsoft.sjk.dao.AppDao;
import com.kingsoft.sjk.po.AppTags;

public class InfocDaImplTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(InfocDaImplTest.class);

    @Resource(name = "appDaoImpl")
    private AppDao appDaoImpl;

    @Test
    public void testDao3() {
        logger.debug("**********************");
        List<AppTags> list = appDaoImpl.getAppTags(TagType.NormalTag);

        for (AppTags appTags : list) {
            logger.debug("obj:{}", appTags);
        }

    }

    // @Test
    public void testDao() {
        System.out.println("adfasdfasdf");
        logger.debug("**********************");
        List<App> list = appDaoImpl.findData();
        logger.debug("list.size:{}", list.size());
        int i = 0;
        for (App app : list) {
            i++;
            logger.debug("app:{}", app.getName());
            System.out.println("sss");
            if (i > 3)
                break;
        }
    }

    // @Test
    public void testDao2() {
        System.out.println("adfasdfasdf");
        logger.debug("**********************");
        Date date = new Date();
        List<App> list = appDaoImpl.findData(date);
        logger.debug("list.size:{}", list.size());
        int i = 0;
        for (App app : list) {
            i++;
            logger.debug("app:{}", app.getName());
            System.out.println("sss");
            if (i > 3)
                break;
        }
    }
}
