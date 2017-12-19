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

import com.ijinshan.sjk.dao.MoFeaturedDao;
import com.ijinshan.sjk.po.MoFeatured;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MoFeaturedDaoImpl extends AbstractBaseDao<MoFeatured> implements MoFeaturedDao {
    private static final Logger logger = LoggerFactory.getLogger(MoFeaturedDaoImpl.class);

    @Override
    public Class<MoFeatured> getType() {
        return MoFeatured.class;
    }

    @Override
    public List<MoFeatured> search(String type, Boolean hidden, Boolean deleted) {
        Criteria cri = getSession().createCriteria(MoFeatured.class);
        if (type != null && StringUtils.isNotEmpty(type)) {
            cri.add(Restrictions.eq("type", type));
        }
        if (hidden != null) {
            cri.add(Restrictions.eq("hidden", hidden));
        }
        if (deleted != null) {
            cri.add(Restrictions.eq("deleted", deleted));
        }
        List<MoFeatured> list = HibernateHelper.list(cri);
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
    public int delete(int id) {
        Query query = getSession().createQuery("delete from MoFeatured where id=:id");
        query.setInteger("id", id);
        int rows = query.executeUpdate();
        return rows;
    }

    @Override
    public Boolean updateDeleted(List<Integer> ids, boolean deleted) {
        String hql = "update MoFeatured set Deleted =:deleted where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setBoolean("deleted", deleted);
        query.setParameterList("ids", ids);
        return query.executeUpdate() > 0;
    }

    @Override
    public int updateHide(List<Integer> ids) {
        String hql = "update MoFeatured set Hidden = 1 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public int updateShow(List<Integer> ids) {
        String hql = "update MoFeatured set Hidden = 0 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    /* 修改排序 */
    @Override
    public boolean updateSort(int id, int rank) {
        final String hql = "update MoFeatured set rank = :rank   where id = :id ";
        Query query = getSession().createQuery(hql);
        query.setParameter("rank", rank);
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public long countForSearching(Short type, Boolean hidden, Boolean deleted, String keywords) {
        Criteria cri = searchByFilter(type, hidden, deleted, keywords);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public List<MoFeatured> search(Short type, Boolean hidden, Boolean deleted, String keywords, int page, int rows,
            String sort, String order) {
        // 查询条件
        Criteria cri = searchByFilter(type, hidden, deleted, keywords);
        // 查询排序
        searchSort(sort, order, cri);
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<MoFeatured> list = HibernateHelper.list(cri);
        return list;
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

    // 查询条件
    private Criteria searchByFilter(Short type, Boolean hidden, Boolean deleted, String keywords) {
        Criteria cri = getSession().createCriteria(MoFeatured.class);
        if (type != null) {
            cri.add(Restrictions.eq("type", type));
        }
        if (hidden != null) {
            cri.add(Restrictions.eq("hidden", hidden));
        }
        if (deleted != null) {
            cri.add(Restrictions.eq("deleted", deleted));
        }
        if (StringUtils.isNotBlank(keywords)) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        return cri;
    }

}
