package com.ijinshan.sjk.dao;

import java.io.Serializable;

import org.hibernate.Session;

/**
 * <ul>
 * <li>sort : column name</li>
 * <li>order : desc or asc</li>
 * </ul>
 * 
 * @author LinZuXiong
 * @param <T>
 */
public interface BaseDao<T> {

    T get(Serializable id);

    Serializable save(Session sess, T t);

    Serializable save(T t);

    void update(Session session, T t);

    void update(T t);

    void saveOrUpdate(Session session, T t);

    void saveOrUpdate(T t);

    void delete(T t);

    long count();

    long count(Session session);

    void delete(Session session, T t);

}
