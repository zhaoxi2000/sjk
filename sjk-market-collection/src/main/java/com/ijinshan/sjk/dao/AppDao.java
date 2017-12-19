package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;

public interface AppDao extends BaseDao<App> {

    App getAppByPackageName(String packagename);

    Integer getPKByPkname(String pkname);

    Integer getPkByIdOfMarketApp(int foreignKey);

    int editCatalog(List<Integer> ids, short catalog, int subCatalog);

    List<App> listForBase(short catalog, Integer subCatalog, int currentPage, int pageSize);

    long count(short catalog, Integer subCatalog);

    List<App> getByMarketApp(Session sess, String marketName, int apkIdFromMarketData, String pkName);

    App getAppByPackageName(Session sess, String pkname);

    List<App> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, String sort, String order);

    long countForSearching(Short catalog, Integer subCatalog, String keywords);

    List<App> getAuditedCatalog();

    int deleteByIds(List<Integer> ids);

    int deleteByMarketApp(int foreignKey);

    int deleteByMarketApp(Session sess, int foreignKey);

    int deleteByMarketApps(Session session, List<Integer> foreignKey);

    App get(int id);

    List<Rollinfo> searchForRolling(Short catalog, Integer subCatalog, int page, int rows, String keywords,
            String sort, String order);

    /**
     * deltaDownload <> 0.
     * 
     * @param session
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<App> listForDownloads(Session session, int currentPage, int pageSize);

    int updateDownloads(Session session, int id, int downloadRank, int downloadsOfApp);

    int updateDayDownload(Session session, int id);

    int updateWeekDownload(Session session, int id);

    /**
     * 先期根据市场上的总下载量,来做下载排序. <br />
     * 后期按我们的真实下载量,来做下载排序. 注意:需求变化时修改代码.
     * 
     * @param session
     * @param id
     * @return
     */
    int updateDownloadRank(Session session, int id);

    int updateDownload(List<Integer> ids, Integer realDownload, int deltaDownload);

    long countForSearchingRolling(Short catalog, Integer subCatalog, String keywords);

    List<App> getByPackageName(Session session, String pkname);

    List<App> getApps(Session session, String pkname);

    /**
     * 非大游戏的. 查找包名和此签名.
     * 
     * @param session
     * @param pkname
     * @param signatureSha1
     *            TODO
     * @return
     */
    App getNotBigGame(Session session, String pkname, String signatureSha1);

    App getByMarket(Session session, String pkname, String marketName);

    /**
     * 查找有包名,同时无此签名.
     * 
     * @param session
     * @param pkname
     * @return
     */
    App getNullSignature(Session session, String pkname);

    String getOfficialSigSha1(Session session, String pkname);

    List<App> getAppsOfDropMarket(Session session, String marketName, Integer currentPage, Integer pageSize);

    long countAppsOfDropMarket(Session session, String marketName);

    int updateHide(Session sess, List<Integer> ids);

    int updateToHide(Session session, String marketName);

    int updateToShow(Session session, String marketName);

    /* 要被虚拟市场数据 */
    List<App> queryVirtualApp();

    List<App> getApps(List<Integer> ids);

    App getByFKMarketAppId(Session session, int marketAppId, short catalog);

    void updateHide(Session session, App app);

}
