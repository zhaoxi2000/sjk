package com.ijinshan.sjk.dao.impl;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.SysDictionaryDao;
import com.ijinshan.sjk.po.SysDictionary;

@Repository
public class SysDictionaryDaoImpl extends AbstractBaseDao<SysDictionary> implements SysDictionaryDao {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryDaoImpl.class);

    @Override
    public Class<SysDictionary> getType() {
        return SysDictionary.class;
    }

    @Override
    public int updateDicValue(Integer id, Integer intValue) {
        String hql = "update SysDictionary set IntValue =:intValue where id =:id";
        Query query = getSession().createQuery(hql);
        query.setInteger("intValue", intValue);
        query.setInteger("id", id);
        return query.executeUpdate();
    }

    @Override
    public int updateDicValue(String name, Integer intValue) {
        String hql = "update SysDictionary set Name =:name where id =:id";
        Query query = getSession().createQuery(hql);
        query.setInteger("intValue", intValue);
        query.setString("name", name);
        return query.executeUpdate();
    }
}
