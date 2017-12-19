package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.dao.MarketAppDao;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MarketAppDaoImpl extends AbstractBaseDao<MarketApp> implements MarketAppDao {
    private static final Logger logger = LoggerFactory.getLogger(MarketAppDao.class);

    @Override
    public Class<MarketApp> getType() {
        return MarketApp.class;
    }

    @Override
    public Integer getPK(String marketName, int appId) {
        return getPK(getSession(), marketName, appId);
    }

    @Override
    public int deleteByPK(Session sess, int id) {
        Query query = sess.createQuery("delete from MarketApp where id=:id");
        query.setInteger("id", id);
        int rows = query.executeUpdate();
        return rows;
    }

    @Override
    public int deleteByPK(int id) {
        return deleteByPK(getSession(), id);
    }

    @Override
    public long count() {
        Query query = getSession().createQuery("select count(id) from MarketApp");
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString());
    }

    @Override
    public List<Integer> getPKByMarketApp(String marketName, List<Integer> appIdsOfMarketApp) {
        return getPkByMarketApp(sessions.getCurrentSession(), marketName, appIdsOfMarketApp);
    }

    @Override
    public Integer getPK(Session sess, String marketName, int appIdOfMarketApp) {
        Criteria cri = sess.createCriteria(MarketApp.class);
        cri.setProjection(Projections.property("id"));
        cri.add(Restrictions.eq("appId", appIdOfMarketApp));
        cri.add(Restrictions.eq("marketName", marketName));
        cri.setCacheable(false);
        Object o = cri.uniqueResult();
        if (o != null) {
            return (Integer) o;
        }
        return null;
    }

    @Override
    public List<Integer> getPkByMarketApp(Session session, String marketName, List<Integer> appIdsOfMarket) {
        Criteria cri = session.createCriteria(MarketApp.class);
        cri.setProjection(Projections.property("id"));
        cri.add(Restrictions.in("appId", appIdsOfMarket));
        cri.add(Restrictions.eq("marketName", marketName));
        return HibernateHelper.list(cri);
    }

    @Override
    public int deleteByMarketApp(Session session, String marketName, List<Integer> appIdsOfMarket) {
        Query query = session
                .createQuery("delete from MarketApp where appId in ( :appIdsOfMarket) and marketName=:marketName");
        query.setParameterList("appIdsOfMarket", appIdsOfMarket);
        query.setString("marketName", marketName);
        int rows = query.executeUpdate();
        return rows;
    }

    @Override
    public List<MarketApp> getByPackagename(String pkname) {
        return getByPackagename(getSession(), pkname);
    }

    @Override
    public int countDownloads(String pkname, int id) {
        Query query = getSession().createQuery(
                "select sum(downloads) from MarketApp where pkname =:pkname and id <> :id");
        query.setParameter("id", id);
        query.setParameter("pkname", pkname);
        Object o = query.uniqueResult();
        if (o != null) {
            return Integer.valueOf(o.toString());
        }
        return 0;
    }

    @Override
    public long countDownloads(String marketName) {
        return countDownloads(getSession(), marketName);
    }

    @Override
    public long countDownloads(Session session, String marketName) {
        Query query = session.createQuery("select sum(downloads) from MarketApp where marketName =:marketName");
        query.setParameter("marketName", marketName);
        Object o = query.uniqueResult();
        if (o != null) {
            return Long.valueOf(o.toString());
        }
        return 0;
    }

    @Override
    public List<MarketApp> getByPackagename(Session sess, String pkname) {
        Criteria cri = sess.createCriteria(MarketApp.class);
        cri.add(Restrictions.eq("pkname", pkname));
        List<MarketApp> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public List<MarketApp> getByApk(Session sess, String pkname, String signatureSha1) {
        Criteria cri = sess.createCriteria(MarketApp.class);
        cri.add(Restrictions.eq("pkname", pkname));
        cri.add(Restrictions.eq("signatureSha1", signatureSha1));
        List<MarketApp> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public MarketApp getByManyId(Session sess, String marketName, Integer appIdOfMarket, int apkIdOfMarket) {
        Criteria cri = sess.createCriteria(MarketApp.class);
        if (appIdOfMarket != null && appIdOfMarket.intValue() > 0) {
            cri.add(Restrictions.eq("appId", appIdOfMarket));
        }
        cri.add(Restrictions.eq("apkId", apkIdOfMarket));
        cri.add(Restrictions.eq("marketName", marketName));
        cri.setCacheable(false);
        Object o = cri.uniqueResult();
        if (o != null) {
            return (MarketApp) o;
        }
        return null;
    }

    @Override
    public Integer getPK(Session sess, String marketName, String pkname) {
        Criteria cri = sess.createCriteria(MarketApp.class);
        cri.setProjection(Projections.property("id"));
        cri.add(Restrictions.eq("pkname", pkname));
        cri.add(Restrictions.eq("marketName", marketName));
        cri.setCacheable(false);
        Object o = cri.uniqueResult();
        if (o != null) {
            return (Integer) o;
        }
        return null;
    }

    @Override
    public MarketApp get(Session session, String marketName, String pkname) {
        Criteria cri = session.createCriteria(MarketApp.class);
        cri.add(Restrictions.eq("pkname", pkname));
        cri.add(Restrictions.eq("marketName", marketName));
        cri.setCacheable(false);
        Object o = cri.uniqueResult();
        if (o != null) {
            return (MarketApp) o;
        }
        return null;
    }

    @Override
    public List<MarketApp> getByIds(Session session, List<Integer> ids) {
        StringBuilder queryString = new StringBuilder("from MarketApp where id in (:ids)");
        Query q = session.createQuery(queryString.toString());
        q.setParameterList("ids", ids);
        List<MarketApp> list = HibernateHelper.list(q);
        return list;
    }

    @Override
    public List<MarketApp> search(EnumMarket enumMarket, Short catalog, Integer subCatalog, int page, int rows,
            String keywords, Integer id, Integer cputype, String sort, String order, Date startDate, Date endDate) {
        List<MarketApp> list = null;
        // cpu的类型为空的时候我们只需要到MarketApp中查找
        if (cputype == null)
            cputype = 0;
        if (cputype.byteValue() == 0) {
            list = findMarketAppList(enumMarket, catalog, subCatalog, page, rows, keywords, id, sort, order, startDate,
                    endDate);
        } else { // cpu的类型为空的时候我们需要先到BigGamePack中查询一下然后再到MarketApp中查找
            list = new ArrayList<MarketApp>();
            List<MarketApp> marketList = findMarketAppList(enumMarket, catalog, subCatalog, page, rows, keywords, id,
                    sort, order, startDate, endDate);
            String hql = "from BigGamePack where cputype =:cputype group by MarketAppId";
            Query query = getSession().createQuery(hql);
            query.setByte("cputype", cputype.byteValue());
            List<BigGamePack> bgList = HibernateHelper.list(query);
            if (null != bgList && bgList.size() > 0 && null != marketList && marketList.size() > 0) {
                for (BigGamePack bigGamePack : bgList) {
                    for (MarketApp mp : marketList) {
                        if (mp.getId() == bigGamePack.getMarketAppId()) {
                            list.add(mp);
                        }
                    }
                }
            }
        }
        return list;
    }

    private List<MarketApp> findMarketAppList(EnumMarket enumMarket, Short catalog, Integer subCatalog, int page,
            int rows, String keywords, Integer id, String sort, String order, Date startDate, Date endDate) {
        List<MarketApp> list;
        Criteria cri = searchByFilter(enumMarket, catalog, subCatalog, keywords, id, startDate, endDate);
        if (sort != null && !sort.isEmpty()) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (order != null && "asc".equals(order)) {
                cri.addOrder(Order.asc("id"));
            } else {
                cri.addOrder(Order.desc("id"));
            }
        }
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long countForSearching(EnumMarket enumMarket, Short catalog, Integer subCatalog, String keywords,
            Integer id, Date startDate, Date endDate) {
        Criteria cri = searchByFilter(enumMarket, catalog, subCatalog, keywords, id, startDate, endDate);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    private Criteria searchByFilter(EnumMarket enumMarket, Short catalog, Integer subCatalog, String keywords,
            Integer id, Date startDate, Date endDate) {
        Criteria cri = getSession().createCriteria(MarketApp.class);
        if (enumMarket != null) {
            cri.add(Restrictions.eq("marketName", enumMarket.getName()));
        }
        if (catalog != null) {
            cri.add(Restrictions.eq("catalog", catalog));
        }
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        if (id != null && id > 0) {
            cri.add(Restrictions.eq("id", id));
        }
        if (startDate != null && endDate != null) {
            cri.add(Restrictions.between("lastUpdateTime", startDate, endDate));
        }
        if (keywords != null && !keywords.isEmpty()) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        return cri;
    }

    @Override
    public MarketApp getPKName(EnumMarket enumMarket, String pkname) {
        Criteria cri = getSession().createCriteria(MarketApp.class);
        cri.add(Restrictions.eq("marketName", enumMarket.getName()));
        cri.add(Restrictions.eq("pkname", pkname));
        cri.setCacheable(false);
        Object o = cri.uniqueResult();
        if (o != null) {
            return (MarketApp) o;
        }
        return null;
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        String hql = "delete MarketApp where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public int editCatalog(List<Integer> ids, short catalog, int subCatalog, String subCatalogName) {
        String hql = "update MarketApp set catalog = :catalog , subCatalog = :subCatalog , subCatalogName = :subCatalogName where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.setParameter("catalog", catalog);
        query.setParameter("subCatalog", subCatalog);
        query.setParameter("subCatalogName", subCatalogName);
        return query.executeUpdate();
    }

}
