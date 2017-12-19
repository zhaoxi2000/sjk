package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.binding.BindingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.Sort;
import com.ijinshan.sjk.mapper.AppMapper;
import com.ijinshan.sjk.mapper.AppRedisMapper;
import com.ijinshan.sjk.mapper.BigGamePackMapper;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.vo.AppAndBigGamesVo;
import com.ijinshan.sjk.vo.AppForAllBigGameVo;
import com.ijinshan.sjk.vo.AppForBigGamePacksVo;
import com.ijinshan.sjk.vo.AppForBigGamesVo;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.ScanApks;
import com.ijinshan.sjk.vo.pc.ScanApp;
import com.ijinshan.sjk.vo.pc.SearchApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.HighAndLowDate;

@Service
public class AppServiceImpl implements AppService {
    private static final int MAX_ROWS = 10000;

    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appMapper")
    private AppMapper appMapper;

    @Resource(name = "appRedisMapper")
    private AppRedisMapper appRedisMapper;

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
    public List<LatestApp> getLatest(short catalog, Integer subCatalog, Long date, int currentPage, int pageSize, Integer noAds, Integer noVirus, Integer official) {
        Assert.isTrue(catalog > 0, "catalog negative!");
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
        Assert.isTrue(offset < appConfig.getMaxOffset(), "offset outmax!");
        if (date != null) {
            HighAndLowDate hld = new HighAndLowDate(date);
            startDate = hld.getLow();
            endDate = hld.getHigh();
        }
        return appMapper.getLatest(catalog, subCatalog, startDate, endDate, offset, pageSize, bNoAds, bNoVirus, bOfficial);
    }

    @Override
    public long getLatestCount(short catalog, Integer subCatalog, Long date, Integer noAds, Integer noVirus, Integer official) {
        Assert.isTrue(catalog > 0, "catalog negative!");
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
        long lg = 0;
        try {
            lg = appMapper.countByFilters(catalog, subCatalog, bNoAds, bOfficial);
        } catch (BindingException e) {
            logger.error("BindingException" + e);
        }
        return lg;
    }

    @Override
    public List<App4Summary> getHotDownload(short catalog, Integer subCatalog, int currentPage, int pageSize, Integer sortType, Integer noAds, Integer noVirus, Integer official) {
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
        Assert.isTrue(offset < appConfig.getMaxOffset(), "offset outmax!");
        return appMapper.getApps4Summary(catalog, subCatalog, name, "DESC", offset, pageSize, bNoAds, bOfficial);
    }

    @Override
    public List<SimpleRankApp> getSimpleRankApp(Integer catalog, Integer subCatalog, int currentPage, int pageSize, Integer sortType, Integer noAds, Integer noVirus, Integer official) {
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
        Assert.isTrue(offset < appConfig.getMaxOffset(), "offset outmax!");
        return appMapper.getSimpleRankApp(catalog, subCatalog, name, "DESC", offset, pageSize, bNoAds, bOfficial);
    }

    @Override
    public List<String> getLatestDates(short catalog, Integer subCatalog, int currentPage, int pageSize, Integer noAds, Integer official) {
        Assert.isTrue(pageSize > 0, "pageSize invalid!");
        // if (subCatalog != null) {
        // Assert.isTrue(subCatalog.intValue() > 0, "subCatalog negative!");
        // }
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Assert.isTrue(offset < appConfig.getMaxOffset(), "offset outmax!");
        return appMapper.getLatestDates(catalog, subCatalog, offset, pageSize, bNoAds, bOfficial);
    }

    @Override
    public long getLatestDatesCount(short catalog, Integer subCatalog, Integer noAds, Integer official) {
        // if (subCatalog != null) {
        // Assert.isTrue(subCatalog.intValue() > 0, "subCatalog negative!");
        // }
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
    }

    @Override
    public void postHandleInScan(HttpServletResponse resp, App app, List<App> apps) {
    }

    @Override
    public List<AppAndBigGamesVo> getAppListByCatalog(short catalog, Integer subCatalog) {
        // 查询一个AppList集合
        List<AppAndBigGamesVo> appList = appMapper.getApps4BigGameByCatalog(catalog, subCatalog);
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
    public List<AppForBigGamesVo> getAppList(Integer cputype) {

        List<BigGamePack> bigpacklist = bigGamePackMapper.getBigGameList(cputype);
        List<AppForBigGamesVo> appList = appMapper.getApps4BigGame(cputype);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(bigpacklist.size());

        for (int i = 0; i < bigpacklist.size(); i++) {
            map.put(bigpacklist.get(i).getMarketAppId(), i);
        }

        for (AppForBigGamesVo avo : appList) {
            try {
                avo.getApkPackList().add(bigpacklist.get(map.get(avo.getMarketAppId())));
            } catch (Exception e) {
                continue;
            }
        }
        return appList;
    }

    @Override
    public List<AppForAllBigGameVo> getApplistByParams(Integer cputype, String phoneId, String subCatalog) {
        // Assert.notNull(cputype, "cputype must not null!");

        List<AppForBigGamePacksVo> applist = appMapper.getApps4BigGameByCpuTypeOrCatalog(cputype, subCatalog);
        Map<Integer, AppForAllBigGameVo> map = new HashMap<Integer, AppForAllBigGameVo>();

        AppForAllBigGameVo apvo = null;
        if (null != phoneId) {
            for (AppForBigGamePacksVo vo : applist) {
                if (map.containsKey(vo.getId())) {
                    if ((null != vo.getUnsupportPhoneType() && vo.getUnsupportPhoneType().contains(phoneId + ";"))) {
                        continue;
                    }
                    apvo = map.get(vo.getId());
                    setBigPackList(apvo, vo);
                } else {
                    if ((null != vo.getUnsupportPhoneType() && vo.getUnsupportPhoneType().contains(phoneId + ";"))) {
                        continue;
                    }
                    apvo = new AppForAllBigGameVo();
                    apvo.setId(vo.getId());
                    apvo.setAdActionTypes(vo.getAdActionTypes());
                    apvo.setAdPopupTypes(vo.getAdPopupTypes());
                    apvo.setAdRisk(vo.getAdRisk());
                    apvo.setAdvertises(vo.getAdvertises());
                    apvo.setApkId(vo.getApkId());
                    apvo.setApkScanTime(vo.getApkScanTime());
                    apvo.setAppId(vo.getAppId());
                    apvo.setAudit(vo.isAudit());
                    apvo.setAuditCatalog(vo.isAuditCatalog());
                    apvo.setCatalog(vo.getCatalog());
                    apvo.setDeltaDownload(vo.getDeltaDownload());
                    apvo.setDescription(vo.getDescription());
                    apvo.setDetailUrl(vo.getDetailUrl());
                    apvo.setDownloadRank(vo.getDownloadRank());
                    apvo.setEngName(vo.getEngName());
                    apvo.setEnumStatus(vo.getEnumStatus());
                    apvo.setHidden(vo.isHidden());
                    apvo.setIndexImgUrl(vo.getIndexImgUrl());
                    apvo.setKeywords(vo.getKeywords());
                    apvo.setLanguage(vo.getLanguage());
                    apvo.setLastDayDelta(vo.getLastDayDelta());
                    apvo.setLastDayDownload(vo.getLastDayDownload());
                    apvo.setLastFetchTime(vo.getLastFetchTime());
                    apvo.setLastUpdateTime(vo.getLastUpdateTime());
                    apvo.setLastWeekDelta(vo.getLastWeekDelta());
                    apvo.setLastWeekDownload(vo.getLastWeekDownload());
                    apvo.setLogo1url(vo.getLogo1url());
                    apvo.setLogoUrl(vo.getLogoUrl());
                    apvo.setMarketName(vo.getMarketName());
                    apvo.setMd5(vo.getMd5());
                    apvo.setMinsdkversion(vo.getMinsdkversion());
                    apvo.setModels(vo.getModels());
                    apvo.setName(vo.getName());
                    apvo.setPrice(vo.getPrice());
                    apvo.setPublisherShortName(vo.getPublisherShortName());
                    apvo.setRealDownload(vo.getRealDownload());
                    apvo.setScreens(vo.getScreens());
                    apvo.setScSta(vo.getScSta());
                    apvo.setShortDesc(vo.getShortDesc());
                    apvo.setSignatureSha1(vo.getSignatureSha1());
                    apvo.setStarRating(vo.getStarRating());
                    apvo.setStrImageUrls(vo.getStrImageUrls());
                    apvo.setSubCatalog(vo.getSubCatalog());
                    apvo.setSupportpad(vo.getSupportpad());
                    apvo.setUpdateInfo(vo.getUpdateInfo());
                    apvo.setVersion(vo.getVersion());
                    apvo.setVersionCode(vo.getVersionCode());
                    apvo.setViewCount(vo.getViewCount());
                    apvo.setVirusBehaviors(vo.getVirusBehaviors());
                    apvo.setVirusKind(vo.getVirusKind());
                    apvo.setVirusName(vo.getVirusName());
                    setBigPackList(apvo, vo);
                    map.put(vo.getId(), apvo);
                }
            }
        }
        List<AppForAllBigGameVo> appvolist = new ArrayList<AppForAllBigGameVo>(map.size());
        Collection<AppForAllBigGameVo> coll = map.values();
        for (AppForAllBigGameVo appForAllBigGameVo : coll) {
            appvolist.add(appForAllBigGameVo);
        }
        map.clear();
        return appvolist;

    }

    @Override
    public ScanApp getAppByPkame(String pkname) {
        return appMapper.getAppByPackageName(pkname);
    }

    @Override
    public ScanApp getAppByApk(String pkname, String signaturesha1) {
        return appMapper.getAppByApk(pkname, signaturesha1);
    }

    @Override
    public List<ScanApp> getApks(String pkname, long versionCode) {
        Assert.isTrue(!pkname.isEmpty(), "pkname invalid");
        return appMapper.getApks(pkname, versionCode);
    }

    @Override
    public List<SearchApp> getSearchAppByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return appMapper.getSearchAppByIds(ids);
    }

    @Override
    public List<ScanApks> getScanAppTopN() {
        return appMapper.getScanAppTopN();
    }

    @Override
    public List<App4Summary> getIncrement(Short catalog) {

        // Assert.isTrue(catalog >= 0, "catalog invalid");
        // 因为在infoc 中 上报时将 type 2为应用，1为游戏，所以为了和现有的一致，做一个转换
        if (null != catalog) {
            catalog = catalog == (short) 1 ? (short) 2 : (short) 1;
        }

        Date date = DateUtils.addDays(new Date(), -1);
        List<App4Summary> list = appMapper.getIncrement(catalog, date);
        if (list == null || list.size() < 20) {
            date = DateUtils.addDays(new Date(), -2);// 因为数据是凌晨 5点钟导日昨天的数据，所以会出现
                                                     // 昨天数据不会出来的现象
            list = appMapper.getIncrement(catalog, date);
        }

        if (list == null || list.size() < 20) {
            date = DateUtils.addDays(new Date(), -10);// 因为数据是凌晨
                                                      // 5点钟导日昨天的数据，所以会出现
                                                      // 昨天数据不会出来的现象
            list = appMapper.getIncrement(catalog, date);
        }

        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<App4Summary>(0);
        }

        // 对list 中的集合中 pkName进行排重
        int len = list.size();
        Set<String> set = new HashSet<String>(len);
        List<App4Summary> resultList = new ArrayList<App4Summary>(len);
        for (App4Summary app4Summary : list) {
            if (set.contains(app4Summary.getPkname())) {
                continue;
            }
            set.add(app4Summary.getPkname());
            resultList.add(app4Summary);
        }
        list.clear();
        set.clear();
        return resultList;
    }

    @Override
    public List<AppForAllBigGameVo> getAllAppList() {
        List<AppForBigGamePacksVo> list = bigGamePackMapper.getAllBigGameList();

        Map<Integer, AppForAllBigGameVo> map = new HashMap<Integer, AppForAllBigGameVo>();

        AppForAllBigGameVo apvo = null;
        for (AppForBigGamePacksVo vo : list) {
            if (map.containsKey(vo.getId())) {
                apvo = map.get(vo.getId());
                setBigPackList(apvo, vo);
            } else {
                apvo = new AppForAllBigGameVo();
                apvo.setId(vo.getId());
                apvo.setAdActionTypes(vo.getAdActionTypes());
                apvo.setAdPopupTypes(vo.getAdPopupTypes());
                apvo.setAdRisk(vo.getAdRisk());
                apvo.setAdvertises(vo.getAdvertises());
                apvo.setApkId(vo.getApkId());
                apvo.setApkScanTime(vo.getApkScanTime());
                apvo.setAppId(vo.getAppId());
                apvo.setAudit(vo.isAudit());
                apvo.setAuditCatalog(vo.isAuditCatalog());
                apvo.setCatalog(vo.getCatalog());
                apvo.setDeltaDownload(vo.getDeltaDownload());
                apvo.setDescription(vo.getDescription());
                apvo.setDetailUrl(vo.getDetailUrl());
                apvo.setDownloadRank(vo.getDownloadRank());
                apvo.setEngName(vo.getEngName());
                apvo.setEnumStatus(vo.getEnumStatus());
                apvo.setHidden(vo.isHidden());
                apvo.setIndexImgUrl(vo.getIndexImgUrl());
                apvo.setKeywords(vo.getKeywords());
                apvo.setLanguage(vo.getLanguage());
                apvo.setLastDayDelta(vo.getLastDayDelta());
                apvo.setLastDayDownload(vo.getLastDayDownload());
                apvo.setLastFetchTime(vo.getLastFetchTime());
                apvo.setLastUpdateTime(vo.getLastUpdateTime());
                apvo.setLastWeekDelta(vo.getLastWeekDelta());
                apvo.setLastWeekDownload(vo.getLastWeekDownload());
                apvo.setLogo1url(vo.getLogo1url());
                apvo.setLogoUrl(vo.getLogoUrl());
                apvo.setMarketName(vo.getMarketName());
                apvo.setMd5(vo.getMd5());
                apvo.setMinsdkversion(vo.getMinsdkversion());
                apvo.setModels(vo.getModels());
                apvo.setName(vo.getName());
                apvo.setPrice(vo.getPrice());
                apvo.setPublisherShortName(vo.getPublisherShortName());
                apvo.setRealDownload(vo.getRealDownload());
                apvo.setScreens(vo.getScreens());
                apvo.setScSta(vo.getScSta());
                apvo.setShortDesc(vo.getShortDesc());
                apvo.setSignatureSha1(vo.getSignatureSha1());
                apvo.setStarRating(vo.getStarRating());
                apvo.setStrImageUrls(vo.getStrImageUrls());
                apvo.setSubCatalog(vo.getSubCatalog());
                apvo.setSupportpad(vo.getSupportpad());
                apvo.setUpdateInfo(vo.getUpdateInfo());
                apvo.setVersion(vo.getVersion());
                apvo.setVersionCode(vo.getVersionCode());
                apvo.setViewCount(vo.getViewCount());
                apvo.setVirusBehaviors(vo.getVirusBehaviors());
                apvo.setVirusKind(vo.getVirusKind());
                apvo.setVirusName(vo.getVirusName());
                setBigPackList(apvo, vo);
                map.put(vo.getId(), apvo);
            }
        }

        List<AppForAllBigGameVo> appvolist = new ArrayList<AppForAllBigGameVo>(map.size());
        Collection<AppForAllBigGameVo> coll = map.values();
        for (AppForAllBigGameVo appForAllBigGameVo : coll) {
            appvolist.add(appForAllBigGameVo);
        }
        map.clear();
        return appvolist;

    }

    private void setBigPackList(AppForAllBigGameVo apvo, AppForBigGamePacksVo vo) {
        Set<BigGamePack> set;
        BigGamePack bigPack;
        set = apvo.getApkPackList();
        bigPack = new BigGamePack();
        bigPack.setBigGamePackId(vo.getBigGamePackId());
        bigPack.setCputype(vo.getCputype());
        bigPack.setFreeSize(vo.getFreeSize());
        bigPack.setMarketUpdateTime(vo.getLastUpdateTime());
        bigPack.setSize(vo.getSize());
        bigPack.setUnsupportPhoneType(vo.getUnsupportPhoneType());
        bigPack.setUrl(vo.getUrl());
        set.add(bigPack);
    }

    @Override
    public List<LatestApp> getLatest(List<Integer> ids) {
        return appRedisMapper.getLatest(ids);
    }

    @Override
    public List<String> getLatestDates(List<Integer> ids) {
        return appRedisMapper.getLatestDates(ids);
    }

    @Override
    public List<App4Summary> getApps4Summary(List<Integer> ids) {
        return appRedisMapper.getApps4Summary(ids);
    }

    @Override
    public List<SimpleRankApp> getSimpleRankApp(List<Integer> ids) {
        return appRedisMapper.getSimpleRankApp(ids);
    }
}
