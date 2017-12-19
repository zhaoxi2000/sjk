package com.ijinshan.sjk.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBaseDao<T> implements BaseDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseDao.class);

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    public Session getSession() {
        return sessions.getCurrentSession();
    }

    @Override
    public Serializable save(T t) {
        return getSession().save(t);
    }

    @Override
    public void update(T t) {
        getSession().clear();
        getSession().update(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        getSession().saveOrUpdate(t);
    }

    @Override
    public void delete(T t) {
        getSession().delete(t);
    }

    public SessionFactory getSessions() {
        return sessions;
    }

    public void setSessions(SessionFactory sessions) {
        this.sessions = sessions;
    }

    @Override
    public long count() {
        String queryString = new StringBuilder("select count(id) from ").append(getType().getName()).toString();
        Query query = getSession().createQuery(queryString);
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    public abstract Class<T> getType();
}
