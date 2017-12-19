package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.CatalogDao;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.service.CatalogService;

@Repository
public class CatalogServiceImpl implements CatalogService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

    @Resource(name = "catalogDaoImpl")
    private CatalogDao catalogDao;

    @Override
    public List<Catalog> list(Short pid) {
        return catalogDao.list(pid);
    }

    @Override
    public boolean saveOrUpdate(Catalog entity) {
        if (entity.getId() > 0) {
            catalogDao.saveOrUpdate(entity);
        } else {
            catalogDao.save(entity);
        }
        return true;
    }

    public boolean delete(Catalog entity) {
        catalogDao.delete(entity);
        return true;
    }

    @Override
    public long countForSearching(short catalog, int subCatalog, String keywords) {
        return catalogDao.countForSearching(catalog, subCatalog, keywords);
    }

    @Override
    public List<Catalog> search(short catalog, int subCatalog, String keywords, int page, int rows, String sort,
            String order) {
        return catalogDao.search(catalog, subCatalog, keywords, page, rows, sort, order);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        return catalogDao.deleteById(id) > 0;
    }
}
