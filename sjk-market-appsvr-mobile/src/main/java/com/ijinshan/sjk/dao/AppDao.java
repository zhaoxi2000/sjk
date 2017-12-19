package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;

public interface AppDao extends BaseDao<App> {

    App getAppByPackageName(String packagename);

    App getAppByApk(String pkname, String signaturesha1);

    int deleteByMarketApp(int foreignKey);

    Integer getPkByMarketAppId(int marketAppId);

    List<Rollinfo> rollinfo();

    List<App> getLatest(short catalog, Integer subCatalog, Long date, int currentPage, int pageSize, Boolean noAds,
            Boolean noVirus, Boolean official);

    List<App> getMaxDownloadOfDay();

    List<App> get(List<Integer> ids, String sort, String order);

    List<App> getForIndex(Session session, Short catalog, Integer subCatalog, Integer currentPage, Integer pageSize);

    long count(Session session, short catalog);

    long getLatestCount(short catalog, Integer subCatalog, Long date, Boolean noAds, Boolean noVirus, Boolean official);

    List<App> list(short catalog, Integer subCatalog, String sort, String order, int currentPage, int pageSize,
            Boolean noAds, Boolean noVirus, Boolean official);

    long count(short catalog, Integer subCatalog, Boolean noAds, Boolean noVirus, Boolean official);

    List<String> getName(Session session, short catalog, Integer subCatalog, Integer currentPage, Integer pageSize);

    long count(Session session);

    List<App> getForQuickTipsIndex(Session session, Integer currentPage, Integer pageSize);

    long countName(Session session);

    List<App> getScanTop();

    int updateIncrementDownload(Session session, int id, int delta);

    List<App> getMaxDownloadOfWeek();

    List<App> getByPackageName(String pkname);

}
