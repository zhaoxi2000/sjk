package com.kingsoft.sjk.game.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.App;
import com.kingsoft.sjk.game.dao.IBaseDao;

@Repository
public class AppDaoImpl implements IBaseDao<App> {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public void delete(App entity) {
    }

    @Override
    public boolean isExist(String vid, String pid, String names, String models) {
        return false;
    }

    @Override
    public boolean isExist(String vid, String pid, Integer os_version, Integer os_bit) {
        return false;
    }

    @Override
    public Integer save(App entity) {
        Session session = null;
        Integer pkId = null;
        try {
            session = sessionFactory.openSession();
            pkId = (Integer) session.save(entity);
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.flush();
            session.clear();
            session.close();
        }

        return pkId;
    }

    @Override
    public void update(App entity) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.update(entity);
        } finally {
            session.flush();
            session.clear();
            session.close();
        }
    }

    @Override
    public App find(App entity) {
        return null;
    }

    @Override
    public App findById(Integer id) {
        return null;
    }

}
