package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.Tag;

public interface TagDao extends BaseDao<Tag> {

    List<Tag> getList(Session session);

    List<Integer> getPkByAppId(Session session, Integer appId);

}
