package com.ijinshan.sjk.mapper;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.sjk.vo.ScanApp;
import com.ijinshan.util.HighAndLowDate;

public class AppMapperTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(AppMapperTest.class);

    @Resource(name = "appMapper")
    private AppMapper appMapper;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testget() {
        int id = 1;
        App app = null;
        while (true) {
            app = appMapper.get(id);
            id++;
            if (app != null) {
                break;
            }
        }
        Assert.assertNotNull(app.getId());
    }

    @Test
    public void testgetAppByPackageName() {
        int id = 1;
        App app = null;
        while (true) {
            app = appMapper.get(id);
            id++;
            if (app != null) {
                break;
            }
        }
        String pkname = app.getPkname();
        Assert.assertNotNull(pkname);
        ScanApp pknameApp = appMapper.getAppByPackageName(pkname);
        Assert.assertNotNull(pknameApp);
    }

    @Test
    public void testgetAppByApk() {
        int id = 1;
        App app = null;
        while (true) {
            app = appMapper.get(id);
            id++;
            if (app != null) {
                break;
            }
        }
        String pkname = app.getPkname();
        String signaturesha1 = app.getSignatureSha1();
        Assert.assertNotNull(pkname);
        ScanApp otherApp = appMapper.getAppByApk(pkname, signaturesha1);
        Assert.assertNotNull(otherApp);
    }

    @Test
    public void testrollinfo() {
        List<Rollinfo> rollinfo = appMapper.getRollinfo();
        Assert.assertNotNull(rollinfo);
        Assert.assertFalse(rollinfo.isEmpty());
    }

    @Test
    public void testgetLatest() {
        short catalog = 1;
        Integer subCatalog = null;
        Date startDate = null;
        Date endDate = null;
        int offset = 0;
        int rowCount = 30;
        Boolean noAds = false;
        Boolean noVirus = true;
        Boolean official = null;

        Long date = 1349913600000L;
        HighAndLowDate hld = new HighAndLowDate(date);
        startDate = hld.getLow();
        endDate = hld.getHigh();
        appMapper.getLatest(catalog, subCatalog, startDate, endDate, offset, rowCount, noAds, noVirus, official);
        noAds = null;
        appMapper.getLatest(catalog, subCatalog, startDate, endDate, offset, rowCount, noAds, noVirus, official);
    }

    @Test
    public void testgetMaxDownloadOfDay() {
        List<App> list = appMapper.getMaxDownloadOfDay();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testgetByIds() {
        List<Integer> ids = new ArrayList<Integer>(10);
        String order = null;
        String sortColumn = "id";
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(10);
        ids.add(11);
        ids.add(12);
        ids.add(10720);
        List<MobileSearchApp> list = appMapper.getByIds(ids, sortColumn, order);
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());

        sortColumn = "name";
        order = "desc";
        List<MobileSearchApp> list1 = appMapper.getByIds(ids, sortColumn, order);
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());

        boolean notSame = false;
        for (int i = 0; i < list1.size(); i++) {
            MobileSearchApp app = list.get(i);
            MobileSearchApp app1 = list1.get(i);
            if (!app.equals(app1)) {
                notSame = true;
                break;
            }
        }
        Assert.assertTrue(notSame);
    }

    @Test
    public void testgetLatestDates() {
        short catalog = 2;
        int subCatalog = 4;
        int offset = 0;
        int rowCount = 10;
        Boolean noAds = true;
        Boolean official = false;
        List<String> list = appMapper.getLatestDates(catalog, subCatalog, offset, rowCount, noAds, official);
        Assert.assertNotNull(list);
        logger.info("out lastestDates!");
        for (String date : list) {
            logger.info(date);
        }
    }

    @Test
    public void testgetLatestDate() {
        Boolean noAds = true;
        Boolean official = false;
        List<LatestDate> list = appMapper.getLatestDate(noAds, official);
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testcountName() {
        long count = appMapper.countName();
        assertTrue(count > 0);
    }

    @Test
    public void testupdateIncrementDownload() {
        short catalog = 1;
        Integer subCatalog = 3;
        String sort = "name";
        String order = "DESC";
        int offset = 0;
        int rowCount = 7;
        Boolean noAds = null;
        Boolean official = null;
        List<App> list = appMapper.list(catalog, subCatalog, sort, order, offset, rowCount, noAds, official);
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        App app = list.get(0);
        int rows = appMapper.updateIncrementDownload(app.getId(), 10);
        logger.info("Affected rows: {}", rows);
        assertTrue(rows > 0);

    }
}
