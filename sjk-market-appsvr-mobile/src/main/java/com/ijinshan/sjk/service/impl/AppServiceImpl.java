package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.Sort;
import com.ijinshan.sjk.mapper.AppMapper;
import com.ijinshan.sjk.mapper.BigGamePackMapper;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.CDNCache;
import com.ijinshan.sjk.vo.AppAndBigGamesVo;
import com.ijinshan.sjk.vo.AppVo;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.ScanApp;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.ScanApks;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.HighAndLowDate;
import com.ijinshan.util.Pager;

@Service
public class AppServiceImpl implements AppService {
    public static final int MAX_ROWS = 30;

    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appMapper")
    private AppMapper appMapper;

    @Resource(name = "cdnCacheImpl")
    private CDNCache cdnCache;

    @Resource(name = "bigGamePackMapper")
    private BigGamePackMapper bigGamePackMapper;

    @Override
    public List<Rollinfo> getRollinfo() {
        return appMapper.getRollinfo();
    }

    @Override
    public App get(int id) {
        Assert.isTrue(id > 0, "invalid id!");
        return appMapper.get(id);
    }

    @Override
    public List<LatestApp> getLatest(Short catalog, Integer subCatalog, Long date, int currentPage, int pageSize,
            Integer noAds, Integer noVirus, Integer official) {
        if (catalog != null) {
            Assert.isTrue(catalog > 0, "catalog negative!");
        }
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        Assert.isTrue(currentPage > 0, "currentPage negative!");
        if (subCatalog != null) {
            Assert.isTrue(subCatalog > -1, "date negative!");
        }
        if (date != null) {
            Assert.isTrue(date > -1, "date negative!");
        }
        Boolean bNoAds = null, bNoVirus = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (noVirus != null && noVirus.intValue() == 1) {
            bNoVirus = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }

        Date startDate = null;
        Date endDate = null;
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        if (date != null) {
            HighAndLowDate hld = new HighAndLowDate(date);
            startDate = hld.getLow();
            endDate = hld.getHigh();
        }
        return appMapper.getLatest(catalog, subCatalog, startDate, endDate, offset, pageSize, bNoAds, bNoVirus,
                bOfficial);
        // return appDao.getLatest(catalog, subCatalog, date, currentPage,
        // pageSize, bNoAds, bNoVirus, bOfficial);
    }

    @Override
    public long getLatestCount(Short catalog, Integer subCatalog, Long date, Integer noAds, Integer noVirus,
            Integer official) {
        if (catalog != null) {
            Assert.isTrue(catalog > 0, "catalog negative!");
        }
        if (subCatalog != null) {
            Assert.isTrue(subCatalog > -1, "date negative!");
        }
        if (date != null) {
            Assert.isTrue(date > -1, "date negative!");
        }
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        Date startDate = null;
        Date endDate = null;
        if (date != null) {
            HighAndLowDate hld = new HighAndLowDate(date);
            startDate = hld.getLow();
            endDate = hld.getHigh();
        }
        return appMapper.getLatestCount(catalog, subCatalog, startDate, endDate, bNoAds, bOfficial);
        // return appDao.getLatestCount(catalog, subCatalog, date, bNoAds,
        // bNoVirus, bOfficial);
    }

    @Override
    public long getHotDownloadCount(short catalog, Integer subCatalog, Integer noAds, Integer noVirus, Integer official) {
        Assert.isTrue(catalog > 0, "catalog negative!");
        if (subCatalog != null) {
            Assert.isTrue(subCatalog > -1, "date negative!");
        }
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        return appMapper.countByFilters(catalog, subCatalog, bNoAds, bOfficial);
        // return appDao.count(catalog, subCatalog, bNoAds, bNoVirus,
        // bOfficial);
    }

    @Override
    public List<App> getHotDownload(short catalog, Integer subCatalog, int currentPage, int pageSize, Integer sortType,
            Integer noAds, Integer noVirus, Integer official) {
        Assert.isTrue(catalog > 0, "catalog negative!");
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        if (subCatalog != null) {
            Assert.isTrue(subCatalog > -1, "date negative!");
        }
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        String name = null;
        if (sortType != null) {
            name = Sort.getColumnName(sortType);
        } else {
            name = Sort.DOWNLOADRANK.getDbColumnName();
        }
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        return appMapper.list(catalog, subCatalog, name, "DESC", offset, pageSize, bNoAds, bOfficial);
        // Boolean bNoVirus = null;
        // if (noVirus != null && noVirus.intValue() == 1) {
        // bNoVirus = Boolean.TRUE;
        // }
        // return appDao.query(catalog, subCatalog, name, "desc", currentPage,
        // pageSize, bNoAds, bNoVirus, bOfficial);
    }

    @Override
    public List<String> getLatestDates(short catalog, int subCatalog, int currentPage, int pageSize, Integer noAds,
            Integer official) {
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        return appMapper.getLatestDates(catalog, subCatalog, offset, pageSize, bNoAds, bOfficial);
        // return appDao.getLatestDates(catalog, subCatalog, currentPage,
        // pageSize, bNoAds, bOfficial);
    }

    @Override
    public long getLatestDatesCount(short catalog, int subCatalog, Integer noAds, Integer official) {
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        return appMapper.getLatestDatesCount(catalog, subCatalog, bNoAds, bOfficial);
    }

    @Override
    public List<LatestDate> getLatestDate(Integer noAds, Integer official) {
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        return appMapper.getLatestDate(bNoAds, bOfficial);
        // return appDao.getLatestDate(bNoAds, bOfficial);
    }

    @Override
    public ScanApp getAppByPackageName(String packagename) {
        Assert.notNull(packagename, "packagename  must not be null");
        return appMapper.getAppByPackageName(packagename);
    }

    @Override
    public ScanApp getAppByApk(String pkname, String signaturesha1) {
        Assert.notNull(pkname, "pkname  must not be null");
        Assert.notNull(signaturesha1, "signaturesha1  must not be null");
        return appMapper.getAppByApk(pkname, signaturesha1);
    }

    @Override
    public List<App> getScanTop() {
        return appMapper.getScanTop();
    }

    @Override
    public List<App> getMaxDownloadOfDay() {
        return appMapper.getMaxDownloadOfDay();
    }

    @Override
    public List<App> getMaxDownloadOfWeek() {
        return appMapper.getMaxDownloadOfWeek();
    }

    @Override
    public List<App> getByPackageName(String pkname) {
        Assert.notNull(pkname, "pkname  must not be null");
        return appMapper.getByPackageName(pkname);
    }

    @Override
    public void postHandleInScan(HttpServletResponse resp, App app, List<App> apps) {
        // if (app == null || (apps == null || apps.isEmpty())) {
        // cdnCache.setCacheScanSoftIfMiss(resp);
        // } else {
        // cdnCache.setCacheScanSoftIfHit(resp);
        // }
    }

    @Override
    public List<AppAndBigGamesVo> getAppListByCatalog(short catalog, Integer subCatalog) {
        Assert.isTrue(catalog > 0, "catalog negative!");
        if (subCatalog != null) {
            Assert.isTrue(subCatalog > -1, "subCatalog negative!");
        }

        // 查询一个AppList集合
        List<AppAndBigGamesVo> appList = appMapper.getAppListByCatalog(catalog, subCatalog);
        List<Integer> ids = new ArrayList<Integer>();
        for (AppAndBigGamesVo appVo : appList) {
            ids.add(appVo.getMarketAppId());
        }

        if (null != ids && ids.size() > 0) {
            List<BigGamePack> bigGameList = bigGamePackMapper.getBigGameByMarkAppIds(ids);
            for (AppAndBigGamesVo appVo : appList) {
                for (BigGamePack bigGamePack : bigGameList) {
                    if (appVo.getMarketAppId() == bigGamePack.getMarketAppId()) {
                        appVo.getBigGamePackList().add(bigGamePack);
                    }
                }
            }
            bigGameList.clear();
        }
        ids.clear();
        return appList;
    }

    @Override
    public Pager<MobileSearchApp> getSearchAppListByParams(short catalog, Integer subCatalog, int currentPage,
            int pageSize, Integer sortType) {
        Assert.isTrue(catalog > 0, "catalog negative!");

        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        Assert.isTrue(currentPage > 0, "currentPage negative!");
        if (subCatalog != null) {
            Assert.isTrue(subCatalog > -1, "subCatalog negative!");
        }

        final int offset = HibernateHelper.firstResult(currentPage, pageSize);

        String name = null;
        if (sortType != null) {
            name = Sort.getColumnName(sortType);
        } else {
            name = Sort.DOWNLOADRANK.getDbColumnName();
        }
        Pager<MobileSearchApp> pager = new Pager<MobileSearchApp>();
        pager.setResult(appMapper.getSearchAppListByParams(catalog, subCatalog, offset, pageSize, name));
        // pager.setRows(appMapper.getAppOrderListByParamsCount(catalog,
        // subCatalog, name));
        return pager;
    }

    @Override
    public AppVo getAppVoById(int id) {
        return appMapper.getAppVoById(id);
    }

    @Override
    public List<ScanApp> getApks(String pkname, long versionCode) {
        Assert.isTrue(!pkname.isEmpty(), "pkname invalid");
        return appMapper.getApks(pkname, versionCode);
    }

    @Override
    public List<ScanApks> getScanAppTopN() {
        return appMapper.getScanAppTopN();
    }

    @Override
    public List<MobileSearchApp> getSearchAppByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return appMapper.getSearchAppByIds(ids);
    }

    @Override
    public Pager<App4Summary> getIncrement(int currentPage, int pageSize, Short catalog) {
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        Assert.isTrue(currentPage > 0, "currentPage negative!");
        // 因为在infoc 中 上报时将 type 2为应用，1为游戏，所以为了和现有的一致，做一个转换
        if (null != catalog) {
            catalog = catalog == (short) 1 ? (short) 2 : (short) 1;
        }
        Date date = DateUtils.addDays(new Date(), -1);
        long totalCount = appMapper.getIncrementCount(catalog, date);
        if (totalCount == 0) {
            date = DateUtils.addDays(new Date(), -2);// 因为数据是凌晨 5点钟导日昨天的数据，所以会出现
            // 昨天数据不会出来的现象
            totalCount = appMapper.getIncrementCount(catalog, date);
        }
        if (totalCount == 0) {
            date = DateUtils.addDays(new Date(), -3);// 如果取前天的数据还是没有，就去大后天的数据
            totalCount = appMapper.getIncrementCount(catalog, date);
        }
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Pager<App4Summary> pager = new Pager<App4Summary>();
        pager.setRows(totalCount);
        pager.setCurrentPage(currentPage);
        pager.setPageSize(pageSize);
        List<App4Summary> list = appMapper.getIncrement(offset, pageSize, catalog, date);
        pager.setResult(list);
        return pager;
    }
}
