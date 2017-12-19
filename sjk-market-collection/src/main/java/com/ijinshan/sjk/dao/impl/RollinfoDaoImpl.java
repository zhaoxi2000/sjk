package com.ijinshan.sjk.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.RollinfoDao;
import com.ijinshan.sjk.po.Rollinfo;

@Repository
public class RollinfoDaoImpl extends AbstractBaseDao<Rollinfo> implements RollinfoDao {
    private static final Logger logger = LoggerFactory.getLogger(RollinfoDaoImpl.class);

    @Override
    public Class<Rollinfo> getType() {
        return Rollinfo.class;
    }

    @Override
    public int deleteByAppId(Session session, int appId) {
        final String hql = "delete from Rollinfo where appId = :appId";
        Query query = session.createQuery(hql);
        query.setParameter("appId", appId);
        int effected = query.executeUpdate();
        return effected;
    }

    @Override
    public int deleteByAppId(int appId) {
        final String hql = "delete from Rollinfo where appId = :appId";
        Query query = getSession().createQuery(hql);
        query.setParameter("appId", appId);
        return query.executeUpdate();
    }
}
