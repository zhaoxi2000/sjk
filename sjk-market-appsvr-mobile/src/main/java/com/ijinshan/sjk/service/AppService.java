package com.ijinshan.sjk.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.AppAndBigGamesVo;
import com.ijinshan.sjk.vo.AppVo;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.ScanApp;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.ScanApks;
import com.ijinshan.util.Pager;

public interface AppService {
    List<Rollinfo> getRollinfo();

    App get(int id);

    List<LatestApp> getLatest(Short catalog, Integer subCatalog, Long date, int currentPage, int pageSize,
            Integer noAds, Integer noVirus, Integer official);

    long getLatestCount(Short catalog, Integer subCatalog, Long date, Integer noAds, Integer noVirus, Integer official);

    long getHotDownloadCount(short catalog, Integer subCatalog, Integer noAds, Integer noVirus, Integer official);

    List<App> getHotDownload(short catalog, Integer subCatalog, int currentPage, int pageSize, Integer orderType,
            Integer noAds, Integer noVirus, Integer official);

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
    List<String> getLatestDates(short catalog, int subCatalog, int currentPage, int pageSize, Integer noAds,
            Integer official);

    long getLatestDatesCount(short catalog, int subCatalog, Integer noAds, Integer official);

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

    ScanApp getAppByPackageName(String packagename);

    List<App> getByPackageName(String pkname);

    List<App> getScanTop();

    List<App> getMaxDownloadOfDay();

    List<App> getMaxDownloadOfWeek();

    ScanApp getAppByApk(String pkname, String signaturesha1);

    void postHandleInScan(HttpServletResponse resp, App app, List<App> apps);

    List<AppAndBigGamesVo> getAppListByCatalog(short catalog, Integer subCatalog);

    Pager<MobileSearchApp> getSearchAppListByParams(short catalog, Integer subCatalog, int page, int rows,
            Integer sortType);

    AppVo getAppVoById(int id);

    List<ScanApp> getApks(String pkname, long versionCode);

    List<ScanApks> getScanAppTopN();

    List<MobileSearchApp> getSearchAppByIds(List<Integer> ids);

    /**
     * 手机版-获取上升最快的应用
     */
    Pager<App4Summary> getIncrement(int page, int rows, Short catalog);

}
