package com.ijinshan.sjk.service;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.CatalogConvertor;
import com.ijinshan.sjk.vo.MarketCatalog;

public interface CatalogConvertorService {

    boolean saveOrUpdate(CatalogConvertor entity);

    boolean delete(Integer id);

    boolean deleteByIds(List<Integer> ids);

    CatalogConvertor get(int id);

    CatalogConvertor getByMarketApp(String marketName, short catalog, int subCatalog);

    CatalogConvertor getByMarketApp(Session sess, String marketName, short catalog, int subCatalog);

    // 查询统计条目
    long countForSearching(short catalog, String marketName, String keywords);

    // 分页查询
    List<CatalogConvertor> search(String marketName, short catalog, String keywords, int page, int rows, String sort,
            String order);

    // 根据市场名称查询类对应集合
    List<CatalogConvertor> searchByMarketName(String marketName);

    // 根据市场名称和一级类别查询类对应集合
    List<CatalogConvertor> searchByMarketNameAndCatalog(String marketName, short catalog);

    boolean saveOrEditCatalogConvertors(List<CatalogConvertor> list);

    List<MarketCatalog> searchMarketCatalogs(String marketName);
}
