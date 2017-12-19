package com.ijinshan.sjk.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.SessionScope;

import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.Viapp;
import com.ijinshan.util.HibernateHelper;

@Repository
public class AppDaoImpl implements AppDao {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    @Override
    public List<Viapp> getAllApp() {
        List<Viapp> list = null;
        final Session session = sessions.openSession();
        try {
            Criteria cri = session.createCriteria(Viapp.class);
            cri.setCacheable(true);
            list = HibernateHelper.list(cri);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            if (null != session) {
                session.close();
            }
        }
        return list;
    }

}
