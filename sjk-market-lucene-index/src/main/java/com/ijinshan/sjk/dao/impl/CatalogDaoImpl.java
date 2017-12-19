package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.AbstractBaseDao;
import com.ijinshan.sjk.dao.CatalogDao;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.util.HibernateHelper;

@Repository
public class CatalogDaoImpl extends AbstractBaseDao<Catalog> implements CatalogDao {
    private static final Logger logger = LoggerFactory.getLogger(CatalogDaoImpl.class);

    @Override
    public Class<Catalog> getType() {
        return Catalog.class;
    }

    @Override
    public List<Catalog> list() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("id"));
        proList.add(Projections.property("pid"));
        proList.add(Projections.property("name"));

        Criteria cri = getSession().createCriteria(Catalog.class);
        cri.setProjection(proList);
        List<Object[]> list = HibernateHelper.list(cri);
        List<Catalog> catalogs = null;
        if (list != null && !list.isEmpty()) {
            catalogs = new ArrayList<Catalog>(list.size());
            for (Object[] obj : list) {
                Catalog c = new Catalog();
                c.setId((Integer) obj[0]);
                c.setPid((Short) obj[1]);
                c.setName(((String) obj[2]).toLowerCase());
                catalogs.add(c);
            }
            return catalogs;
        }
        return null;
    }

}
