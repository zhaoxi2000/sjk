package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.CatalogInfo;
import com.ijinshan.sjk.vo.CatalogListVo;
import com.ijinshan.sjk.vo.CatalogVo;

public interface CatalogService {
    List<Catalog> list(int catalog);

    List<CatalogInfo> listCatalogInfo(int catalog, Integer noAds, Integer official);

    List<CatalogInfo> listCatalogInfo();

    List<CatalogVo> getCatalogList(Integer catalog);

    List<CatalogListVo> getCatalogListNoParams();

}
