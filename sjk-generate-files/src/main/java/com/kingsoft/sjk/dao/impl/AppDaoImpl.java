package com.kingsoft.sjk.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.dao.AppDao;
import com.kingsoft.sjk.dao.BasicSessionFactory;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.util.HibernateHelper;

@Repository
public class AppDaoImpl extends BasicSessionFactory implements AppDao {

    @Override
    public List<App> findAll() {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("from App   app where app.state =0 and app.isReview = 1 order by id desc ");
        // q.setFirstResult(20);
        // q.setMaxResults(4);
        List<App> list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public List<App> findAll(Date curDate) {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery(
                "from App   app where app.state =0 and app.isReview = 1 and EUpdateDate >=?  order by id desc ")
                .setDate(0, curDate);
        // q.setFirstResult(20);
        // q.setMaxResults(4);
        List<App> list = HibernateHelper.list(q);
        return list;
    }

}
