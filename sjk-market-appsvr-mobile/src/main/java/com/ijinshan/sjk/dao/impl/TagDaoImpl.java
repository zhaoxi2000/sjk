package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.TagDao;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.util.HibernateHelper;

@Repository
public class TagDaoImpl extends AbstractBaseDao<Tag> implements TagDao {
    private static final Logger logger = LoggerFactory.getLogger(TagDaoImpl.class);

    @Override
    public Class<Tag> getType() {
        return Tag.class;
    }

    @Override
    public List<Tag> getList(Session session) {
        String hql = "from Tag";
        Query query = session.createQuery(hql);
        return HibernateHelper.list(query);
    }

    @Override
    public List<Integer> getPkByAppId(Session session, Integer appId) {
        return null;
    }

}
