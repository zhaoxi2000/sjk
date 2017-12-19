package com.ijinshan.sjk.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAdmin;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.Downloads;
import com.ijinshan.sjk.vo.TopAppVo;

public interface AppDao extends BaseDao<App> {

    App getAppByPackageName(String packagename);

    Integer getPKByPkname(String pkname);

    Integer getPkByIdOfMarketApp(int foreignKey);

    int editCatalog(List<Integer> ids, short catalog, int subCatalog);

    List<App> listForBase(short catalog, Integer subCatalog, int currentPage, int pageSize);

    int updateHide(List<Integer> ids);

    int updateShow(List<Integer> ids);

    int updateAudit(List<Integer> ids, int status);

    long count(short catalog, Integer subCatalog);

    List<App> getByMarketApp(Session sess, String marketName, int apkIdFromMarketData, String pkName);

    App getAppByPackageName(Session sess, String pkname);

    List<App> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id, String sort,
            String order);

    List<AppAdmin> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id,
            String sort, String order, Date startDate, Date endDate, Boolean official, Integer audit);

    long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id);

    long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id, Date startDate,
            Date endDate, Boolean official, Integer audit);

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

    List<Downloads> getDownloads(String pkname);

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
     * 查找包名和此签名.
     * 
     * @param session
     * @param pkname
     * @param signatureSha1
     * @return
     */
    App get(Session session, String pkname, String signatureSha1);

    /**
     * 查找有包名,同时无此签名.
     * 
     * @param session
     * @param pkname
     * @return
     */
    App getNullSignature(Session session, String pkname);

    String getOfficialSigSha1(String pkname);

    List<App> getAppsOfDropMarket(Session session, String marketName, Integer currentPage, Integer pageSize);

    long countAppsOfDropMarket(Session session, String marketName);

    int updateHide(Session sess, List<Integer> ids);

    int updateToHide(Session session, String marketName);

    int updateToShow(Session session, String marketName);

    /* 要被虚拟市场数据 */
    List<App> queryVirtualApp();

    List<App> getApps(List<Integer> ids);

    // List<App> getAppList(List<Integer> ids);

    List<TopAppVo> getAppList(Set<String> pnames);
}
