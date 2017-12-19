package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.CatalogInfo;

public interface CatalogService {
    List<Catalog> list(int catalog);

    List<CatalogInfo> listCatalogInfo(int catalog, Integer noAds, Integer official);

    List<CatalogInfo> listCatalogInfo();
}
