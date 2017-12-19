package com.ijinshan.sjk.dao.impl;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.KeywordDao;
import com.ijinshan.sjk.po.Keyword;

@Repository
public class KeywordDaoImpl extends AbstractBaseDao<Keyword> implements KeywordDao {
    private static final Logger logger = LoggerFactory.getLogger(KeywordDaoImpl.class);

    @Override
    public Class<Keyword> getType() {
        return Keyword.class;
    }

    @Override
    public boolean exists(String name) {
        String queryString = "select count(id) from Keyword where name = :name ";
        Query q = getSession().createQuery(queryString);
        q.setParameter("name", name);
        Long count = (Long) q.uniqueResult();
        if (count != null && count.longValue() > 0L) {
            return true;
        }
        return false;
    }

    @Override
    public Keyword get(String name) {
        String queryString = "from Keyword where name = :name ";
        Query q = getSession().createQuery(queryString);
        q.setParameter("name", name);
        Keyword keyword = (Keyword) q.uniqueResult();
        return keyword;
    }
}
