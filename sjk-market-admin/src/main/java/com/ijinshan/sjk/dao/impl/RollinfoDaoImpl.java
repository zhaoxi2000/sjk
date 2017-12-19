package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.RollinfoDao;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.RollinfoDetail;
import com.ijinshan.util.HibernateHelper;

@Repository
public class RollinfoDaoImpl extends AbstractBaseDao<Rollinfo> implements RollinfoDao {
    private static final Logger logger = LoggerFactory.getLogger(RollinfoDaoImpl.class);

    @Override
    public Class<Rollinfo> getType() {
        return Rollinfo.class;
    }

    @Override
    public List<RollinfoDetail> getRollinfoDetail() {
        StringBuilder queryString = new StringBuilder("select new com.ijinshan.sjk.vo.RollinfoDetail(");
        queryString.append(" rollinfo.appId,rollinfo.recommend , rollinfo.rank,  app.name AS name)");
        queryString.append(" from Rollinfo AS rollinfo left join rollinfo.app AS app");
        Query q = getSession().createQuery(queryString.toString());
        List<RollinfoDetail> list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public long count() {
        Query query = getSession().createQuery("select count(appId) from Rollinfo");
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString());
    }

    @Override
    public int delete(List<Integer> appIds) {
        String hql = "delete Rollinfo where appId in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", appIds);
        int effected = query.executeUpdate();
        return effected;
    }

    @Override
    public int deleteNotExistsApp(Session session) {
        StringBuilder hql = new StringBuilder("delete from Rollinfo where not exists (");
        hql.append(" select 1 from App x");
        hql.append(" where x.id = appId");
        hql.append(")");
        Query query = session.createQuery(hql.toString());
        int effected = query.executeUpdate();
        return effected;
    }

    @Override
    public int deleteNotExistsApp() {
        return deleteNotExistsApp(getSession());
    }

    @Override
    public int updateRecommand(List<Integer> appIds) {
        String queryString = "update Rollinfo set recommend = 1 where appId in (:appIds)";
        Query query = getSession().createQuery(queryString);
        query.setParameterList("appIds", appIds);
        return query.executeUpdate();
    }

    @Override
    public int updateUnRecommand(List<Integer> appIds) {
        final String queryString = "update Rollinfo set recommend = 0 where appId in (:appIds)";
        Query query = getSession().createQuery(queryString);
        query.setParameterList("appIds", appIds);
        return query.executeUpdate();
    }

    @Override
    public int deleteByAppId(Session session, int appId) {
        final String hql = "delete Rollinfo where appId = :appId";
        Query query = session.createQuery(hql);
        query.setParameter("appId", appId);
        int effected = query.executeUpdate();
        return effected;
    }
}
