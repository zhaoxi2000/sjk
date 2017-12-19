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

import com.ijinshan.sjk.dao.MoMixFeaturedDao;
import com.ijinshan.sjk.po.MoMixFeatured;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MoMixFeaturedDaoImpl extends AbstractBaseDao<MoMixFeatured> implements MoMixFeaturedDao {
    private static final Logger logger = LoggerFactory.getLogger(MoMixFeaturedDaoImpl.class);

    @Override
    public Class<MoMixFeatured> getType() {
        return MoMixFeatured.class;
    }

    @Override
    public List<MoMixFeatured> search(String type, Boolean hidden) {
        Criteria cri = getSession().createCriteria(MoMixFeatured.class);
        if (type != null && StringUtils.isNotEmpty(type)) {
            cri.add(Restrictions.eq("type", type));
        }
        if (hidden != null) {
            cri.add(Restrictions.eq("hidden", hidden));
        }
        List<MoMixFeatured> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long count(String type) {
        StringBuilder queryString = new StringBuilder("select count(id) from ").append(getType().getName());
        queryString.append(" where type = :type and hidden = 0");
        Query query = getSession().createQuery(queryString.toString());
        query.setParameter("type", type);
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public long countForSearching(Short type, Short picType, Boolean hidden, String keywords) {
        Criteria cri = searchByFilter(type, picType, hidden, keywords);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    // 查询条件
    private Criteria searchByFilter(Short type, Short picType, Boolean hidden, String keywords) {
        Criteria cri = getSession().createCriteria(MoMixFeatured.class);
        if (type != null) {
            cri.add(Restrictions.eq("type", type));
        }
        if (picType != null) {
            cri.add(Restrictions.eq("picType", picType));
        }
        if (hidden != null) {
            cri.add(Restrictions.eq("hidden", hidden));
        }
        if (StringUtils.isNotBlank(keywords)) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        return cri;
    }

    // 查询排序
    private void searchSort(String sort, String order, Criteria cri) {
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (StringUtils.isNotBlank(order) && "desc".equals(order.toLowerCase())) {
                cri.addOrder(Order.desc("rank"));
            } else {
                cri.addOrder(Order.asc("rank"));
            }
        }
    }

    @Override
    public List<MoMixFeatured> search(Short type, Short picType, Boolean hidden, String keywords, int page, int rows,
            String sort, String order) {
        // 查询条件
        Criteria cri = searchByFilter(type, picType, hidden, keywords);
        // 查询排序
        searchSort(sort, order, cri);
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<MoMixFeatured> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public int delete(int id) {
        Query query = getSession().createQuery("delete from MoMixFeatured where id=:id");
        query.setInteger("id", id);
        int rows = query.executeUpdate();
        return rows;
    }

    @Override
    public int updateHide(List<Integer> ids) {
        String hql = "update MoMixFeatured set Hidden = 1 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public int updateShow(List<Integer> ids) {
        String hql = "update MoMixFeatured set Hidden = 0 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public boolean updateSort(int id, int rank) {
        final String hql = "update MoMixFeatured set rank = :rank   where id = :id ";
        Query query = getSession().createQuery(hql);
        query.setParameter("rank", rank);
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }
}