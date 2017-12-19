package com.kingsoft.sjk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.dao.AppDictDao;
import com.kingsoft.sjk.dao.BasicSessionFactory;
import com.kingsoft.sjk.po.AppDict;
import com.kingsoft.sjk.util.HibernateHelper;

@Repository
public class AppDictDaoImpl extends BasicSessionFactory implements AppDictDao {

    @Override
    public List<AppDict> findAll() {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("from AppDict as dict");
        List<AppDict> list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public List<AppDict> getAppDicts(int typeId) {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("from AppDict   dict where dict.typeID=:typeID").setInteger("typeID", typeId);
        List<AppDict> list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public AppDict getAppDict(int dicId) {
        Session session = this.sessionFactory.getCurrentSession();
        AppDict dict = (AppDict) session.createQuery("from AppDict dict where dict.id = ?").setInteger(0, dicId)
                .uniqueResult();
        return dict;
    }

}
