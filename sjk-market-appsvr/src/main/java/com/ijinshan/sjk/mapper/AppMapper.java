package com.ijinshan.sjk.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.AppAndBigGamesVo;
import com.ijinshan.sjk.vo.AppForBigGamePacksVo;
import com.ijinshan.sjk.vo.AppForBigGamesVo;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.ScanApks;
import com.ijinshan.sjk.vo.pc.ScanApp;
import com.ijinshan.sjk.vo.pc.SearchApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;

public interface AppMapper {
    App get(int id);

    ScanApp getAppByPackageName(@Param(value = "packagename") String packagename);

    ScanApp getAppByApk(@Param(value = "pkname") String pkname, @Param(value = "signaturesha1") String signaturesha1);

    List<ScanApp> getApks(@Param(value = "pkname") String pkname, @Param(value = "versionCode") long versionCode);

    @Select(value = { "select x.appId, x.recommend, x.catalog from Rollinfo x INNER join App y on x.appId = y.id" })
    List<Rollinfo> getRollinfo();

    // select x.appId, x.recommend, x.catalog from Rollinfo x INNER join App y
    // on x.appId = y.id order by x.rank;

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
    List<LatestApp> getLatest(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") Integer subCatalog,
            @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate,
            @Param(value = "offset") int offset, @Param(value = "rowCount") int rowCount,
            @Param(value = "noAds") Boolean noAds, @Param(value = "noVirus") Boolean noVirus,
            @Param(value = "official") Boolean official);

    List<App> getByIds(@Param(value = "ids") List<Integer> ids, @Param(value = "sortColumn") String sortColumn,
            @Param(value = "order") String order);

    List<SearchApp> getSearchAppByIds(@Param(value = "ids") List<Integer> ids);

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
    long getLatestCount(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") Integer subCatalog,
            @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    List<SimpleRankApp> getSimpleRankApp(@Param(value = "catalog") Integer catalog,
            @Param(value = "subCatalog") Integer subCatalog, @Param(value = "sortColumn") String sortColumn,
            @Param(value = "order") String order, @Param(value = "offset") int offset,
            @Param(value = "rowCount") int rowCount, @Param(value = "noAds") Boolean noAds,
            @Param(value = "official") Boolean official);

    List<App4Summary> getApps4Summary(@Param(value = "catalog") short catalog,
            @Param(value = "subCatalog") Integer subCatalog, @Param(value = "sortColumn") String sortColumn,
            @Param(value = "order") String order, @Param(value = "offset") int offset,
            @Param(value = "rowCount") int rowCount, @Param(value = "noAds") Boolean noAds,
            @Param(value = "official") Boolean official);

    long countByFilters(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") Integer subCatalog,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    /**
     * 日期列表.
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
    List<String> getLatestDates(@Param(value = "catalog") short catalog,
            @Param(value = "subCatalog") Integer subCatalog, @Param(value = "offset") int offset,
            @Param(value = "rowCount") int rowCount, @Param(value = "noAds") Boolean noAds,
            @Param(value = "official") Boolean official);

    /**
     * 日期的个数
     * 
     * @param catalog
     * @param subCatalog
     * @param noAds
     * @param official
     * @return
     */
    long getLatestDatesCount(@Param(value = "catalog") short catalog, @Param(value = "subCatalog") Integer subCatalog,
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

    List<AppAndBigGamesVo> getApps4BigGameByCatalog(@Param(value = "catalog") short catalog,
            @Param(value = "subCatalog") Integer subCatalog);

    List<AppForBigGamesVo> getApps4BigGameByCpuType(@Param(value = "cputype") Integer cputype,
            @Param(value = "subCatalog") String subCatalog);

    List<AppForBigGamePacksVo> getApps4BigGameByCpuTypeOrCatalog(@Param(value = "cputype") Integer cputype,
            @Param(value = "subCatalog") String subCatalog);

    List<AppForBigGamesVo> getApps4BigGame(@Param(value = "cputype") Integer cputype);

    List<ScanApks> getScanAppTopN();

    List<App4Summary> getIncrement(@Param(value = "catalog") Short catalog, @Param(value = "date") Date date);
}
