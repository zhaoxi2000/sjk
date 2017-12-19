package com.ijinshan.sjk.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.CatalogConvertorDao;
import com.ijinshan.sjk.po.CatalogConvertor;

@Repository
public class CatalogConvertorDaoImpl implements CatalogConvertorDao {
    private static final Logger logger = LoggerFactory.getLogger(CatalogConvertorDaoImpl.class);

    @Override
    public CatalogConvertor getByMarketApp(Session session, String marketName, short catalog, int subCatalog) {
        String queryString = "from CatalogConvertor where marketName = :marketName and catalog = :catalog and subCatalog = :subCatalog";
        Query q = session.createQuery(queryString);
        q.setParameter("marketName", marketName);
        q.setParameter("catalog", catalog);
        q.setParameter("subCatalog", subCatalog);

        Object o = q.uniqueResult();
        if (o != null) {
            return (CatalogConvertor) o;
        }
        return null;
    }
}
