package com.kingsoft.sjk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.dao.BasicSessionFactory;
import com.kingsoft.sjk.dao.ExtendDataDao;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.util.HibernateHelper;

@Repository
public class ExtendDataDaoImpl extends BasicSessionFactory implements ExtendDataDao {

    @Override
    public List<ExtendData> getAppExtendData(int softid) {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("from ExtendData as extendData where extendData.softIDs like :softIDs")
                .setString("softIDs", "'%," + softid + ",%'");
        List<ExtendData> list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public List<ExtendData> findAll() {
        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("from ExtendData as  data ");
        List<ExtendData> list = HibernateHelper.list(q);
        return list;
    }

}
