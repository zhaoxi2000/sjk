package com.ijinshan.sjk.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.po.MarketApp;

public interface MarketAppDao extends BaseDao<MarketApp> {

    Integer getPK(Session sess, String marketName, int appIdOfMarketApp);

    Integer getPK(Session sess, String marketName, String pkname);

    Integer getPK(String marketName, int appIdOfMarketApp);

    int deleteByPK(Session sess, int id);

    int deleteByPK(int id);

    List<Integer> getPKByMarketApp(String marketName, List<Integer> appIdsOfMarketApp);

    List<Integer> getPkByMarketApp(Session session, String marketName, List<Integer> appIdsOfMarketApp);

    int deleteByMarketApp(Session session, String marketName, List<Integer> appIdsOfMarketApp);

    List<MarketApp> getByPackagename(String pkname);

    /**
     * 按pkname,并且排除id后求和的下载量
     * 
     * @param pkname
     * @param id
     * @return
     */
    int countDownloads(String pkname, int id);

    long countDownloads(String marketName);

    long countDownloads(Session session, String marketName);

    List<MarketApp> getByPackagename(Session sess, String pkname);

    List<MarketApp> getByApk(Session sess, String pkname, String signatureSha1);

    MarketApp getByManyId(Session sess, String marketName, Integer appIdOfMarket, int apkIdOfMarket);

    MarketApp get(Session sessionForSelect, String marketName, String pkname);

    /**
     * 已从市场更新过的MarketApp
     * 
     * @param session
     * @return
     */
    // long countMarketUpdatedNotScan(Session session);
    //
    // List<MarketApp> get(Session session, int currentPage, int pageSize);

    List<MarketApp> getByIds(Session session, List<Integer> pagination);

    List<MarketApp> search(EnumMarket enumMarket, Short catalog, Integer subCatalog, int page, int rows,
            String keywords, Integer id, Integer cputype, String sort, String order, Date startDate, Date endDate);

    long countForSearching(EnumMarket enumMarket, Short catalog, Integer subCatalog, String keywords, Integer id,
            Date startDate, Date endDate);

    MarketApp getPKName(EnumMarket enumMarket, String pkname);

    int deleteByIds(List<Integer> ids);

    int editCatalog(List<Integer> ids, short catalog, int subCatalog, String subCatalogName);
}
