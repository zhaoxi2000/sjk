package com.ijinshan.sjk.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.dao.BaseDao;

public abstract class AbstractBaseDao<T> implements BaseDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseDao.class);

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Override
    @SuppressWarnings("unchecked")
    public T get(Serializable id) {
        return (T) getSession().get(getType(), id);
    }

    public Session getSession() {
        return sessions.getCurrentSession();
    }

    @Override
    public Serializable save(Session sess, T t) {
        return sess.save(t);
    }

    @Override
    public Serializable save(T t) {
        return getSession().save(t);
    }

    @Override
    public void update(Session session, T t) {
        session.update(t);
    }

    @Override
    public void update(T t) {
        getSession().update(t);
    }

    @Override
    public void saveOrUpdate(Session sess, T t) {
        sess.saveOrUpdate(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        getSession().saveOrUpdate(t);
    }

    @Override
    public void delete(T t) {
        delete(getSession(), t);
    }

    @Override
    public void delete(Session session, T t) {
        session.delete(t);
    }

    public SessionFactory getSessions() {
        return sessions;
    }

    public void setSessions(SessionFactory sessions) {
        this.sessions = sessions;
    }

    @Override
    public long count() {
        return count(getSession());
    }

    @Override
    public long count(Session session) {
        String queryString = new StringBuilder("select count(id) from ").append(getType().getName()).toString();
        Query query = session.createQuery(queryString);
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    public abstract Class<T> getType();

}
