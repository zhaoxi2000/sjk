package com.kingsoft.sjk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.dao.BasicSessionFactory;
import com.kingsoft.sjk.dao.ScreenImageDao;
import com.kingsoft.sjk.po.ScreenImage;
import com.kingsoft.sjk.util.HibernateHelper;

@Repository
public class ScreenImageDaoImpl extends BasicSessionFactory implements ScreenImageDao {

    @Override
    public List<ScreenImage> getAppImages(int softid) {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("from ScreenImage as image where image.softID=:softID").setInteger("softID",
                softid);
        List<ScreenImage> list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public List<ScreenImage> findAll() {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("from ScreenImage as image ");
        List<ScreenImage> list = HibernateHelper.list(q);
        return list;
    }
}
