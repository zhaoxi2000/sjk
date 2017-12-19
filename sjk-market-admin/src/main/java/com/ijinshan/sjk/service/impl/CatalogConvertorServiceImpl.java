package com.ijinshan.sjk.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.CatalogConvertorDao;
import com.ijinshan.sjk.po.CatalogConvertor;
import com.ijinshan.sjk.service.CatalogConvertorService;
import com.ijinshan.sjk.vo.MarketCatalog;

@Service
public class CatalogConvertorServiceImpl implements CatalogConvertorService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogConvertorServiceImpl.class);

    @Autowired
    CatalogConvertorDao dao;

    @Override
    public boolean saveOrUpdate(CatalogConvertor entity) {
        dao.saveOrUpdate(entity);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        CatalogConvertor entity = this.get(id);
        if (entity == null)
            return true;
        dao.delete(entity);
        return true;
    }

    @Override
    public CatalogConvertor get(int id) {
        return dao.get(id);
    }

    @Override
    public CatalogConvertor getByMarketApp(String marketName, short catalog, int subCatalog) {
        return dao.getByMarketApp(marketName, catalog, subCatalog);
    }

    @Override
    public CatalogConvertor getByMarketApp(Session sess, String marketName, short catalog, int subCatalog) {
        return dao.getByMarketApp(sess, marketName, catalog, subCatalog);
    }

    @Override
    public List<CatalogConvertor> search(String marketName, short catalog, String keywords, int page, int rows,
            String sort, String order) {
        return dao.search(marketName, catalog, keywords, page, rows, sort, order);
    }

    @Override
    public List<CatalogConvertor> searchByMarketName(String marketName) {
        return dao.searchByMarketName(marketName);
    }

    @Override
    public List<CatalogConvertor> searchByMarketNameAndCatalog(String marketName, short catalog) {
        return dao.searchByMarketNameAndCatalog(marketName, catalog);
    }

    @Override
    public boolean deleteByIds(List<Integer> ids) {
        dao.deleteByIds(ids);
        return true;
    }

    @Override
    public long countForSearching(short catalog, String marketName, String keywords) {
        return dao.countForSearching(catalog, marketName, keywords);
    }

    @Override
    public boolean saveOrEditCatalogConvertors(List<CatalogConvertor> list) {
        try {
            if (list != null) {
                for (CatalogConvertor catalog : list) {
                    if (catalog.getId() > 0) {
                        dao.update(catalog);
                    } else {
                        if (!dao.isExists(catalog.getMarketName(), catalog.getCatalog(), catalog.getSubCatalog())) {
                            dao.save(catalog);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("saveOreditCatalogConvertors err", e);
            return false;
        }
        return true;
    }

    @Override
    public List<MarketCatalog> searchMarketCatalogs(String marketName) {
        List<CatalogConvertor> list = dao.searchByMarketName(marketName);
        List<MarketCatalog> marketCatalogs = dao.searchMarketCatalogs(marketName);
        for (MarketCatalog marketCatalog : marketCatalogs) {
            marketCatalog.setIsConvertor(isCatalogConvertor(marketCatalog, list));
        }
        // Collections.sort(marketCatalogs, new MarketCatalogComparator());
        return marketCatalogs;
    }

    private boolean isCatalogConvertor(MarketCatalog marketCatalog, List<CatalogConvertor> list) {
        for (CatalogConvertor catalog : list) {
            if (catalog.getMarketName().equals(marketCatalog.getMarketName())
                    && catalog.getCatalog() == marketCatalog.getCatalog()
                    && catalog.getSubCatalog() == marketCatalog.getSubCatalog()) {
                marketCatalog.setId(catalog.getId());
                marketCatalog.setTargetCatalog(catalog.getTargetCatalog());
                marketCatalog.setTargetSubCatalog(catalog.getTargetSubCatalog());
                marketCatalog.setSubCatalogName(catalog.getSubCatalogName());
                return true;
            }
        }
        return false;
    }

}
