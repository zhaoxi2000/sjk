package com.ijinshan.sjk.dao;

import org.hibernate.Session;

import com.ijinshan.sjk.po.Rollinfo;

public interface RollinfoDao extends BaseDao<Rollinfo> {

    int deleteByAppId(Session session, int appId);

    int deleteByAppId(int appId);
}
