package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
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
import com.ijinshan.sjk.vo.CatalogListVo;
import com.ijinshan.sjk.vo.CatalogVo;

@Repository
public class CatalogServiceImpl implements CatalogService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

    // @Resource(name = "catalogDaoImpl")
    // private CatalogDao catalogDao;

    @Resource(name = "catalogMapper")
    private CatalogMapper catalogMapper;

    @Override
    public List<Catalog> list(int catalog) {
        Assert.isTrue(catalog > 0, "invalid pid.");
        // return catalogDao.list(catalog);
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
        // return catalogDao.listCatalogInfo(catalog, bNoAds, bOfficial);
        return catalogMapper.listCatalogInfoByFilters(catalog, bNoAds, bOfficial);
    }

    @Override
    public List<CatalogInfo> listCatalogInfo() {
        // return catalogDao.listCatalogInfo();
        return catalogMapper.listCatalogInfo();
    }

    @Override
    public List<CatalogVo> getCatalogList(Integer catalog) {
        return catalogMapper.getCatalogList(catalog);
    }

    @Override
    public List<CatalogListVo> getCatalogListNoParams() {
        List<CatalogListVo> voList = new ArrayList<CatalogListVo>();
        List<CatalogVo> catalogVos1 = new ArrayList<CatalogVo>();
        List<CatalogVo> catalogVos2 = new ArrayList<CatalogVo>();
        CatalogListVo cataVo1 = null;
        CatalogListVo cataVo2 = null;
        List<CatalogVo> catalogList = catalogMapper.getCatalogList(null);
        Assert.isTrue(catalogList.size() > 0, "数据为空");
        for (CatalogVo catalogVo : catalogList) {
            if (catalogVo.getpId() == 1) {
                cataVo1 = getData(catalogVos1, catalogVo);
            } else if (catalogVo.getpId() == 2) {
                cataVo2 = getData(catalogVos2, catalogVo);
            }
        }
        voList.add(cataVo1);
        voList.add(cataVo2);
        return voList;
    }

    // 封装数据
    private CatalogListVo getData(List<CatalogVo> catalogVos, CatalogVo catalogVo) {
        CatalogListVo cataVo;
        cataVo = new CatalogListVo();
        cataVo.setPid(catalogVo.getpId());
        catalogVos.add(catalogVo);
        cataVo.setCatalog(catalogVos);
        return cataVo;
    }

}
