package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.Catalog;

public interface CatalogDao extends BaseDao<Catalog> {

    List<Catalog> list(Short pid);

    // 分页统计记录条目
    long countForSearching(short catalog, int subCatalog, String keywords);

    // 分页查询搜索
    List<Catalog> search(short catalog, int subCatalog, String keywords, int page, int rows, String sort, String order);

    int deleteById(int id) throws Exception;
}
