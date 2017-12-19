package com.ijinshan.sjk.dao;

import org.hibernate.Session;

import com.ijinshan.sjk.po.CatalogConvertor;

public interface CatalogConvertorDao {

    CatalogConvertor getByMarketApp(Session sess, String marketName, short catalog, int subCatalog);

}
