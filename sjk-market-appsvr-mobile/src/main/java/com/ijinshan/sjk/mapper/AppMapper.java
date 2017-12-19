package com.ijinshan.sjk.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

public interface AppMapper {
    App get(int id);

    ScanApp getAppByPackageName(@Param(value = "packagename") String packagename);

    ScanApp getAppByApk(@Param(value = "pkname") String pkname, @Param(value = "signaturesha1") String signaturesha1);

    @Select(value = { "select x.appId, x.recommend, x.catalog", "from Rollinfo x left join App y", "on x.appId = y.id",
            "where y.id is not null", "order by x.rank asc" })
    List<Rollinfo> getRollinfo();

    /**
     * @param catalog
     * @param subCatalog
     * @param startDate
     * @param endDate
     * @param offset
     * @param rowCount
     * @param noAds
     * @param noVirus
     * @param official
     * @return
     */
    List<LatestApp> getLatest(@Param(value = "catalog") Short catalog, @Param(value = "subCatalog") Integer subCatalog,
            @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate,
            @Param(value = "offset") int offset, @Param(value = "rowCount") int rowCount,
            @Param(value = "noAds") Boolean noAds, @Param(value = "noVirus") Boolean noVirus,
            @Param(value = "official") Boolean official);

    List<MobileSearchApp> getByIds(@Param(value = "ids") List<Integer> ids,
            @Param(value = "sortColumn") String sortColumn, @Param(value = "order") String order);

    // List<App> getForIndex(@Param(value = "catalog") Short catalog,
    // @Param(value = "subCatalog") Integer subCatalog,
    // @Param(value = "offset") Integer offset, @Param(value = "rowCount")
    // Integer rowCount);

    // List<App> getForQuickTipsIndex(Session session, Integer currentPage,
    // Integer pageSize);

    // List<String> getName(Session session ,@Param(value = "catalog") short
    // catalog, @Param(value = "subCatalog") Integer subCatalog,
    // @Param(value = "offset") Integer offset, @Param(value = "rowCount")
    // Integer rowCount);

    long count(@Param(value = "catalog") Short catalog);

    /**
     * 最新日期排序下的App个数
     * 
     * @param catalog
     * @param subCatalog
     * @param startDate
     * @param endDate
     * @param offset
     * @param rowCount
     * @param noAds
     * @param noVirus
     * @param official
     * @return
     */
    long getLatestCount(@Param(value = "catalog") Short catalog, @Param(value = "subCatalog") Integer subCatalog,
            @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    List<App> list(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") Integer subCatalog,
            @Param(value = "sortColumn") String sortColumn, @Param(value = "order") String order,
            @Param(value = "offset") int offset, @Param(value = "rowCount") int rowCount,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    long countByFilters(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") Integer subCatalog,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    /**
     * 单个分类下的日期列表.
     * 
     * @param catalog
     * @param subCatalog
     * @param offset
     * @param rowCount
     * @param noAds
     *            TODO
     * @param official
     *            TODO
     * @return
     */
    List<String> getLatestDates(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") int subCatalog,
            @Param(value = "offset") int offset, @Param(value = "rowCount") int rowCount,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    /**
     * 日期的个数
     * 
     * @param catalog
     * @param subCatalog
     * @param noAds
     * @param official
     * @return
     */
    long getLatestDatesCount(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") int subCatalog,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    /**
     * 日期列表
     * 
     * @param noAds
     * @param official
     * @return
     */
    List<LatestDate> getLatestDate(@Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    @Select("select count( distinct name) from App where hidden = 0")
    long countName();

    int updateIncrementDownload(@Param(value = "id") int id, @Param(value = "delta") int delta);

    List<App> getScanTop();

    List<App> getMaxDownloadOfDay();

    List<App> getMaxDownloadOfWeek();

    List<App> getByPackageName(@Param(value = "pkname") String pkname);

    List<AppAndBigGamesVo> getAppListByCatalog(@Param(value = "catalog") short catalog,
            @Param(value = "subCatalog") Integer subCatalog);

    List<MobileSearchApp> getSearchAppListByParams(@Param(value = "catalog") short catalog,
            @Param(value = "subCatalog") Integer subCatalog, @Param(value = "offset") int offset,
            @Param(value = "rowCount") int rows, @Param(value = "sort") String sort);

    long getSearchAppListByParamsCount(@Param(value = "catalog") short catalog,
            @Param(value = "subCatalog") Integer subCatalog, @Param(value = "sort") String sort);

    AppVo getAppVoById(@Param(value = "id") int id);

    List<ScanApp> getApks(@Param(value = "pkname") String pkname, @Param(value = "versionCode") long versionCode);

    List<ScanApks> getScanAppTopN();

    List<MobileSearchApp> getSearchAppByIds(@Param(value = "ids") List<Integer> ids);

    /**
     * 手机版-获取上升最快的应用分页总记录
     */
    long getIncrementCount(@Param(value = "catalog") Short catalog, @Param(value = "date") Date date);

    /**
     * 手机版-获取上升最快的应用
     */
    List<App4Summary> getIncrement(@Param(value = "offset") int page, @Param(value = "rowCount") int rows,
            @Param(value = "catalog") Short catalog, @Param(value = "date") Date date);
}
