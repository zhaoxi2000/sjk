package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.MoAppAndTagDao;
import com.ijinshan.sjk.po.MoAppAndTag;
import com.ijinshan.sjk.po.MoViewTagApps;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MoAppAndTagDaoImpl extends AbstractBaseDao<MoAppAndTag> implements MoAppAndTagDao {
    private static final Logger logger = LoggerFactory.getLogger(MoAppAndTagDaoImpl.class);

    @Override
    public long count() {
        Query query = getSession().createQuery("select count(id) from MoAppAndTag");
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString());
    }

    @Override
    public long countMoAppAndTag(int tagId) {
        Query query = getSession().createQuery("select count(id) from MoAppAndTag where tagId = ?");
        query.setParameter(0, tagId);
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString());
    }

    @Override
    public Class<MoAppAndTag> getType() {
        return MoAppAndTag.class;
    }

    @Override
    public List<MoAppAndTag> listTagByApp(List<Integer> appIds) {
        Query query = getSession().createQuery("from MoAppAndTag re left join fetch re.tag where appId in (:appIds)");
        query.setParameterList("appIds", appIds);
        List<MoAppAndTag> list = HibernateHelper.list(query);
        return list;
    }

    @Override
    public int deleteByMoAppIdAndTagType(List<Integer> appIds, short tagType) {
        String queryString = "delete MoAppAndTag where appId in (:appIds) and tagType = :tagType";
        Query query = getSession().createQuery(queryString);
        query.setParameterList("appIds", appIds);
        query.setParameter("tagType", tagType);
        return query.executeUpdate();
    }

    @Override
    public List<MoViewTagApps> search(Integer tagId, Integer catalog, Short tagType, int page, int rows,
            String keywords, String sort, String order) {
        Criteria cri = searchByFilter(tagId, catalog, tagType, keywords);
        if (sort != null && !sort.isEmpty()) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (order != null && "asc".equals(order)) {
                cri.addOrder(Order.asc("id"));
            } else {
                cri.addOrder(Order.desc("id"));
            }
        }
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<MoViewTagApps> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long countForSearching(Integer tagId, Integer catalog, Short tagType, String keywords) {
        Criteria cri = searchByFilter(tagId, catalog, tagType, keywords);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    private Criteria searchByFilter(Integer tagId, Integer catalog, Short tagType, String keywords) {
        Criteria cri = getSession().createCriteria(MoViewTagApps.class);
        if (tagType != null) {
            cri.add(Restrictions.eq("tagType", tagType));
        }
        if (tagId != null && tagId > 0) {
            cri.add(Restrictions.eq("tagId", tagId));
        }
        if (catalog != null && catalog > 0) {
            cri.add(Restrictions.eq("catalog", catalog));
        }
        if (keywords != null && !keywords.isEmpty()) {
            cri.add(Restrictions.or(Restrictions.like("appName", keywords, MatchMode.ANYWHERE),
                    Restrictions.like("marketName", keywords, MatchMode.ANYWHERE),
                    Restrictions.like("tagName", keywords, MatchMode.ANYWHERE)));
        }
        return cri;
    }

    /* 修改排序 */
    @Override
    public boolean updateSort(int id, int rank) {
        final String hql = "update MoAppAndTag set rank = :rank   where id = :id ";
        Query query = getSession().createQuery(hql);
        query.setParameter("rank", rank);
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public int deleteByAppId(Session session, int appId) {
        final String queryString = "delete MoAppAndTag where appId = :appId";
        Query query = session.createQuery(queryString);
        query.setParameter("appId", appId);
        return query.executeUpdate();
    }

    @Override
    public MoAppAndTag getMoAppTag(Integer appId, Integer tagId) {
        Criteria cri = getSession().createCriteria(MoAppAndTag.class);
        cri.add(Restrictions.eq("appId", appId));
        cri.add(Restrictions.eq("tagId", tagId));
        List<MoAppAndTag> list = HibernateHelper.list(cri);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
