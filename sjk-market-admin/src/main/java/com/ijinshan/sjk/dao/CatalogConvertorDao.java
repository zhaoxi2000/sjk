package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.CatalogConvertor;
import com.ijinshan.sjk.vo.MarketCatalog;

public interface CatalogConvertorDao extends BaseDao<CatalogConvertor> {

    CatalogConvertor getByMarketApp(String marketName, short catalog, int subCatalog);

    CatalogConvertor getByMarketApp(Session sess, String marketName, short catalog, int subCatalog);

    // 删除操作
    int deleteByIds(List<Integer> ids);

    // 查询统计条目
    long countForSearching(short catalog, String marketName, String keywords);

    // 分页查询
    List<CatalogConvertor> search(String marketName, short catalog, String keywords, int page, int rows, String sort,
            String order);

    // 根据市场名称查询类对应集合
    List<CatalogConvertor> searchByMarketName(String marketName);

    // 根据市场名称和一级类别查询类对应集合
    List<CatalogConvertor> searchByMarketNameAndCatalog(String marketName, short catalog);

    // 根据市场名称，在市场应用中获取该市场下的所有【类别】
    List<MarketCatalog> searchMarketCatalogs(String marketName);

    /*
     * 是否存在重复
     */
    boolean isExists(String marketName, short catalog, int subCatalog);
}
