package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.KeywordDao;
import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.util.HibernateHelper;

@Repository
public class KeywordDaoImpl extends AbstractBaseDao<Keyword> implements KeywordDao {
    private static final Logger logger = LoggerFactory.getLogger(KeywordDaoImpl.class);

    @Override
    public Class<Keyword> getType() {
        return Keyword.class;
    }

    @Override
    public List<Keyword> search(int page, int rows, String q, String sort, String order) {
        Criteria cri = getSession().createCriteria(Keyword.class);
        if (q != null && !q.isEmpty()) {
            cri.add(Restrictions.like("name", q, MatchMode.ANYWHERE));
        }
        if (sort != null && !sort.isEmpty()) {
            HibernateHelper.addOrder(cri, sort, order);
        }
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<Keyword> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long countForSearching(String q) {
        Criteria cri = getSession().createCriteria(Keyword.class);
        cri.setProjection(Projections.count("id"));
        if (q != null && !q.isEmpty()) {
            cri.add(Restrictions.like("name", q, MatchMode.END));
        }
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public long deleteByIds(List<Integer> ids) {
        String hql = "delete Keyword k  where k.id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

}
