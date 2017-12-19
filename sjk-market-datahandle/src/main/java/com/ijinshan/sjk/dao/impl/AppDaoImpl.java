/**
 * 
 */
package com.ijinshan.sjk.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.util.HibernateHelper;

/**
 * @author LinZuXiong
 */
@Repository
public class AppDaoImpl implements AppDao {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessions;

    @Override
    public List<App> getLg2AppByPknameMarketName() {
        String queryString = "select new com.ijinshan.sjk.po.App( marketName, pkname) from App group by pkname , marketname  having count(id) > 1 ";
        Query query = sessions.getCurrentSession().createQuery(queryString);
        return HibernateHelper.list(query);
    }

    @Override
    public List<App> getApps(String pkname, String marketname) {
        String queryString = "from App where pkname =:pkname and marketname =:marketname  order by versioncode desc , lastupdatetime desc";
        Query query = sessions.getCurrentSession().createQuery(queryString);
        query.setParameter("pkname", pkname);
        query.setParameter("marketname", marketname);
        List<App> apps = HibernateHelper.list(query);
        return apps;
    }

    @Override
    public void update(App firstApp) {
        Session session = sessions.getCurrentSession();
        session.update(firstApp);
    }

    @Override
    public void delete(App app) {
        Session session = sessions.getCurrentSession();
        session.delete(app);
    }
}
