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
    public int getAppsRollRecommendNum() {
        Query query = getSession().createQuery("select intValue from SysDictionary where name = 'recommendCount'");
        Object o = query.uniqueResult();
        if (o != null) {
            return ((Integer) o).intValue();
        }
        return 0;
    }
}
