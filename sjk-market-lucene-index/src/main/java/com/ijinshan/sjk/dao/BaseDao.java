package com.ijinshan.sjk.dao;

import java.io.Serializable;

public interface BaseDao<T> {
    Serializable save(T t);

    void update(T t);

    void saveOrUpdate(T t);

    void delete(T t);

    long count();
}
