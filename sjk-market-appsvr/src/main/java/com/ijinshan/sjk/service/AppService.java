package com.ijinshan.sjk.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.AppAndBigGamesVo;
import com.ijinshan.sjk.vo.AppForAllBigGameVo;
import com.ijinshan.sjk.vo.AppForBigGamesVo;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.ScanApks;
import com.ijinshan.sjk.vo.pc.ScanApp;
import com.ijinshan.sjk.vo.pc.SearchApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;

public interface AppService {
    List<Rollinfo> getRollinfo();

    App get(int id);

    List<LatestApp> getLatest(short catalog, Integer subCatalog, Long date, int currentPage, int pageSize,
            Integer noAds, Integer noVirus, Integer official);

    long getLatestCount(short catalog, Integer subCatalog, Long date, Integer noAds, Integer noVirus, Integer official);

    long getHotDownloadCount(short catalog, Integer subCatalog, Integer noAds, Integer noVirus, Integer official);

    List<App4Summary> getHotDownload(short catalog, Integer subCatalog, int currentPage, int pageSize,
            Integer orderType, Integer noAds, Integer noVirus, Integer official);

    List<App4Summary> getIncrement(Short catalog);

    /**
     * 单个分类下的日期列表.
     * 
     * @param catalog
     * @param subCatalog
     * @param currentPage
     * @param pageSize
     * @param noAds
     *            TODO
     * @param official
     *            TODO
     * @return
     */
    List<String> getLatestDates(short catalog, Integer subCatalog, int currentPage, int pageSize, Integer noAds,
            Integer official);

    long getLatestDatesCount(short catalog, Integer subCatalog, Integer noAds, Integer official);

    /**
     * 取出所有分类的最近一天日期
     * 
     * @param noAds
     *            TODO
     * @param official
     *            TODO
     * @return
     */
    List<LatestDate> getLatestDate(Integer noAds, Integer official);

    void postHandleInScan(HttpServletResponse resp, App app, List<App> apps);

    List<AppAndBigGamesVo> getAppListByCatalog(short catalog, Integer subCatalog);

    List<AppForBigGamesVo> getAppList(Integer cputype);

    List<AppForAllBigGameVo> getApplistByParams(Integer cputype, String phoneId, String subCatalog);

    ScanApp getAppByPkame(String pkname);

    ScanApp getAppByApk(String pkname, String signaturesha1);

    List<ScanApp> getApks(String pkname, long versionCode);

    List<SearchApp> getSearchAppByIds(List<Integer> ids);

    List<ScanApks> getScanAppTopN();

    List<SimpleRankApp> getSimpleRankApp(Integer catalog, Integer subCatalog, int page, int rows, Integer sortType,
            Integer noAds, Integer noVirus, Integer official);

    List<AppForAllBigGameVo> getAllAppList();

    List<LatestApp> getLatest(List<Integer> ids);


    List<String> getLatestDates(List<Integer> ids);

    List<App4Summary> getApps4Summary(List<Integer> ids);
    
    List<SimpleRankApp> getSimpleRankApp(List<Integer> ids);
}
