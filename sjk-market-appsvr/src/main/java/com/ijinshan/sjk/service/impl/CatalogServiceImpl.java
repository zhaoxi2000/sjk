package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ijinshan.sjk.mapper.CatalogMapper;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.CatalogInfo;
import com.ijinshan.sjk.service.CatalogService;

@Repository
public class CatalogServiceImpl implements CatalogService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

    @Resource(name = "catalogMapper")
    private CatalogMapper catalogMapper;

    @Override
    public List<Catalog> list(int catalog) {
        Assert.isTrue(catalog > 0, "invalid pid.");
        return catalogMapper.listByCatalog(catalog);
    }

    @Override
    public List<CatalogInfo> listCatalogInfo(int catalog, Integer noAds, Integer official) {
        Assert.isTrue(catalog > 0, "invalid pid.");
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        return catalogMapper.listCatalogInfoByFilters(catalog, bNoAds, bOfficial);
    }

    @Override
    public List<CatalogInfo> listCatalogInfo() {
        return catalogMapper.listCatalogInfo();
    }
}
