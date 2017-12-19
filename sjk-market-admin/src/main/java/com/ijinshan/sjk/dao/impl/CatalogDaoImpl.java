package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
    public long count() {
        Query query = getSession().createQuery("select count(id) from Catalog");
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString());
    }

    @Override
    public List<Catalog> list(Short pid) {
        String hql = "";
        if (pid == null) {
            hql = "from Catalog ";
        } else {
            hql = "from Catalog where pid = ? order by name";
        }
        Query query = getSession().createQuery(hql);
        if (pid != null) {
            query.setParameter(0, pid);
        }
        return HibernateHelper.list(query);
    }

    @Override
    public long countForSearching(short catalog, int subCatalog, String keywords) {
        Criteria cri = this.searchByFilter(catalog, subCatalog, keywords);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public List<Catalog> search(short catalog, int subCatalog, String keywords, int page, int rows, String sort,
            String order) {
        // 查询条件
        Criteria cri = searchByFilter(catalog, subCatalog, keywords);
        // 查询排序
        searchSort(sort, order, cri);
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<Catalog> list = HibernateHelper.list(cri);
        return list;
    }

    // 查询排序
    private void searchSort(String sort, String order, Criteria cri) {
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (StringUtils.isNotBlank(order) && "asc".equals(order.toLowerCase())) {
                cri.addOrder(Order.asc("id"));
            } else {
                cri.addOrder(Order.desc("id"));
            }
        }
    }

    // 查询条件
    private Criteria searchByFilter(short catalog, int subCatalog, String keywords) {
        Criteria cri = getSession().createCriteria(Catalog.class);
        if (subCatalog > 0) {
            cri.add(Restrictions.eq("id", subCatalog));
        }
        if (catalog > 0) {
            cri.add(Restrictions.eq("pid", catalog));
        }
        if (StringUtils.isNotBlank(keywords)) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        return cri;
    }

    private boolean existRelating(int catalogId) {
        Query query = getSession().createSQLQuery(
                "select count(id) from CatalogConvertor where TargetCatalog =:id or TargetSubCatalog =:id");
        query.setParameter("id", catalogId);
        Object o = query.uniqueResult();
        int result = Integer.valueOf(o.toString());
        if (result > 0)
            return true;

        Query queryApp = getSession().createSQLQuery("select count(id) from App where Catalog =:id or SubCatalog =:id");
        queryApp.setParameter("id", catalogId);
        o = queryApp.uniqueResult();
        result = Integer.valueOf(o.toString());
        return result > 0;
    }

    @Override
    public int deleteById(int id) throws Exception {
        if (existRelating(id)) {
            throw new Exception("该类别存在业务关联，不能删除！");
        } else {
            String queryString = "delete Catalog where id= :id";
            Query query = getSession().createQuery(queryString);
            query.setParameter("id", id);
            return query.executeUpdate();
        }
    }
}
