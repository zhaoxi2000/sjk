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

import com.ijinshan.sjk.dao.MonUserChannelAppDao;
import com.ijinshan.sjk.po.MonUserChannelApp;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MonUserChannelAppDaoImpl extends AbstractBaseDao<MonUserChannelApp> implements MonUserChannelAppDao {
    private static final Logger logger = LoggerFactory.getLogger(MonUserChannelAppDaoImpl.class);

    @Override
    public boolean isExists(String marketName, Integer apkId) {
        Query query = getSession().createQuery(
                "from MonUserChannelApp m where  m.apkId=:apkId  and m.marketName=:marketName ");
        query.setString("marketName", marketName);
        query.setInteger("apkId", apkId);
        List<MonUserChannelApp> list = HibernateHelper.list(query);
        if (list == null || list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public MonUserChannelApp getChannelApp(String marketName, Integer apkId) {
        Query query = getSession().createQuery(
                "from MonUserChannelApp m where  m.apkId=:apkId  and m.marketName=:marketName ");
        query.setString("marketName", marketName);
        query.setInteger("apkId", apkId);
        List<MonUserChannelApp> list = HibernateHelper.list(query);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * @param marketName
     *            ：市场名称
     * @param status
     *            :是否可以 true为可以 ，false不可以禁用，不用再监控了。
     */
    @Override
    public List<MonUserChannelApp> queryList(String marketName, Boolean status) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("from MonUserChannelApp m where  1=1 ");
        if (status != null) {
            sbSql.append(" and  m.status=:status ");
        }
        if (StringUtils.isNotBlank(marketName)) {
            sbSql.append(" and   m.marketName=:marketName  ");
        }
        Query query = getSession().createQuery(sbSql.toString());
        if (status != null) {
            query.setBoolean("status", status);
        }
        if (StringUtils.isNotBlank(marketName)) {
            query.setString("marketName", marketName);
        }
        return HibernateHelper.list(query);
    }

    @Override
    public boolean deleteByIds(List<Integer> ids) {
        String hql = "delete MonUserChannelApp where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate() == ids.size();
    }

    @Override
    public List<MonUserChannelApp> queryList(List<Integer> ids) {
        Query query = getSession().createQuery("from MonUserChannelApp where id in (:ids)");
        query.setParameterList("ids", ids);
        return HibernateHelper.list(query);
    }

    @Override
    public List<MonUserChannelApp> queryList(String keyword, Boolean autCover, int page, int rows, String order,
            String sort) {
        Criteria cri = searchByFilter(keyword, autCover);
        // 查询排序
        searchSort(sort, order, cri);
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<MonUserChannelApp> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long countForSearching(String keyword, Boolean autoCover) {
        Criteria cri = searchByFilter(keyword, autoCover);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    private Criteria searchByFilter(String keyword, Boolean autoCover) {
        Criteria cri = getSession().createCriteria(getType());
        if (autoCover != null) {
            cri.add(Restrictions.eq("autoCover", autoCover));
        }
        if (StringUtils.isNotEmpty(keyword)) {
            cri.add(Restrictions.or(Restrictions.like("appName", keyword, MatchMode.START),
                    Restrictions.like("devName", keyword, MatchMode.START),
                    Restrictions.like("userName", keyword, MatchMode.ANYWHERE),
                    Restrictions.like("marketName", keyword, MatchMode.START)));
        }
        return cri;
    }

    // 查询排序
    private void searchSort(String sort, String order, Criteria cri) {
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (StringUtils.isNotBlank(order) && "desc".equals(order.toLowerCase())) {
                cri.addOrder(Order.desc("id"));
            } else {
                cri.addOrder(Order.desc("autoCover"));
            }
        }
    }

    @Override
    public Class<MonUserChannelApp> getType() {
        return MonUserChannelApp.class;
    }

}
