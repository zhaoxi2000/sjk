package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.Catalog;

public interface CatalogDao extends BaseDao<Catalog> {

    List<Catalog> list();

}
