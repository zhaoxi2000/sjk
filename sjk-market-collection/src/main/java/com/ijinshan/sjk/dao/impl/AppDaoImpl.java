package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.config.EnumCatalog;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.util.HibernateHelper;

@Repository
public class AppDaoImpl extends AbstractBaseDao<App> implements AppDao {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Resource(name = "appHistory4IndexDaoImpl")
    private AppHistory4IndexDao appHistory4IndexDaoImpl;

    @Override
    public Class<App> getType() {
        return App.class;
    }

    @Override
    public App getAppByPackageName(String packagename) {
        return getAppByPackageName(getSession(), packagename);
    }

    @Override
    public Integer getPkByIdOfMarketApp(int foreignKey) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("marketAppId", foreignKey));
        cri.setProjection(Projections.property("id"));
        try {
            Object o = cri.uniqueResult();
            if (o != null) {
                return (Integer) o;
            }
            return null;
        } catch (org.hibernate.AssertionFailure e) {
            return null;
        }
    }

    @Override
    public long count() {
        Query query = getSession().createQuery("select count(id) from App");
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public Integer getPKByPkname(String pkname) {
        Criteria cri = getSession().createCriteria(MarketApp.class);
        cri.setProjection(Projections.property("id"));
        cri.add(Restrictions.eq("pkname", pkname));
        try {
            Object o = cri.uniqueResult();
            if (o != null) {
                return (Integer) o;
            }
            return null;
        } catch (org.hibernate.AssertionFailure e) {
            return null;
        }
    }

    @Override
    public int editCatalog(List<Integer> ids, short catalog, int subCatalog) {
        String hql = "update App set catalog = :catalog , subCatalog = :subCatalog , auditCatalog = 1 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.setParameter("catalog", catalog);
        query.setParameter("subCatalog", subCatalog);
        return query.executeUpdate();
    }

    @Override
    public List<App> listForBase(short catalog, Integer subCatalog, int currentPage, int pageSize) {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("id"));
        proList.add(Projections.property("name"));
        proList.add(Projections.property("subCatalog"));
        proList.add(Projections.property("auditCatalog"));

        Criteria cri = getSession().createCriteria(App.class);
        cri.setProjection(proList);
        cri.addOrder(Order.asc("auditCatalog"));
        cri.addOrder(Order.asc("name"));
        cri.add(Restrictions.eq("catalog", catalog));
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        cri.setMaxResults(pageSize);
        cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
        List<Object[]> list = HibernateHelper.list(cri);
        List<App> apps = null;
        if (list != null) {
            apps = new ArrayList<App>(list.size());
            for (Object[] obj : list) {
                App e = new App((Integer) obj[0], (String) obj[1]);
                e.setSubCatalog((Integer) obj[2]);
                e.setAuditCatalog((Boolean) obj[3]);
                apps.add(e);
            }
            list.clear();
        }
        return apps;
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        String hql = "delete App where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public long count(short catalog, Integer subCatalog) {
        StringBuilder sb = new StringBuilder("select count(id) from App where catalog = :catalog ");
        if (subCatalog != null) {
            sb.append(" and subCatalog= :subCatalog");
        }
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("catalog", catalog);
        if (subCatalog != null) {
            query.setParameter("subCatalog", subCatalog);
        }
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public int deleteByMarketApp(int foreignKey) {
        return deleteByMarketApp(getSession(), foreignKey);
    }

    @Override
    public int deleteByMarketApp(Session sess, int foreignKey) {
        Query query = sess.createQuery("delete from App where marketAppId=:marketAppId");
        query.setInteger("marketAppId", foreignKey);
        int rows = query.executeUpdate();
        return rows;
    }

    @Override
    public App getAppByPackageName(Session sess, String pkname) {
        Criteria cri = sess.createCriteria(App.class);
        cri.add(Restrictions.eq("pkname", pkname));
        Object o = cri.uniqueResult();
        if (o != null) {
            return (App) o;
        }
        return null;
    }

    @Override
    public List<App> getByMarketApp(Session sess, String marketName, int apkId, String pkName) {
        String hql = "from App where apkId = ? and marketName = ? and pkname = ?";
        Query query = sess.createQuery(hql);
        query.setParameter(0, apkId);
        query.setParameter(1, marketName);
        query.setParameter(2, pkName);
        return HibernateHelper.list(query);
    }

    @Override
    public List<App> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, String sort,
            String order) {
        Criteria cri = searchByFilter(catalog, subCatalog, keywords);
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        // cri.addOrder(Order.asc("auditCatalog"));
        if (sort != null && !sort.isEmpty()) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (order != null && "asc".equals(order)) {
                cri.addOrder(Order.asc("downloads"));
            } else {
                cri.addOrder(Order.desc("downloads"));
            }
        }
        if ("marketName".equals(sort)) {
            cri.addOrder(Order.desc("downloads"));
        }
        // cri.addOrder(Order.asc("name"));
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    private Criteria searchByFilter(Short catalog, Integer subCatalog, String keywords) {
        Criteria cri = getSession().createCriteria(App.class);
        if (catalog != null) {
            cri.add(Restrictions.eq("catalog", catalog));
        }
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        if (keywords != null && !keywords.isEmpty()) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        return cri;
    }

    @Override
    public long countForSearching(Short catalog, Integer subCatalog, String keywords) {
        Criteria cri = searchByFilter(catalog, subCatalog, keywords);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public List<App> getAuditedCatalog() {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("auditCatalog", true));
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public int deleteByMarketApps(Session session, List<Integer> marketAppIds) {
        String hql = "delete App where marketAppId in (:marketAppIds)";
        Query query = session.createQuery(hql);
        query.setParameterList("marketAppIds", marketAppIds);
        return query.executeUpdate();
    }

    @Override
    public App get(int id) {
        return (App) getSession().get(App.class, id);
    }

    @Override
    public List<Rollinfo> searchForRolling(Short catalog, Integer subCatalog, int page, int rows, String keywords,
            String sort, String order) {
        Criteria cri = getSession().createCriteria(Rollinfo.class);
        Criteria appCriteria = cri.createCriteria("app", JoinType.LEFT_OUTER_JOIN);
        if (catalog != null) {
            appCriteria.add(Restrictions.eq("catalog", catalog));
        }
        if (subCatalog != null) {
            appCriteria.add(Restrictions.eq("subCatalog", subCatalog));
        }

        if (keywords != null && !keywords.isEmpty()) {
            appCriteria.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }

        if (sort != null && !sort.isEmpty()) {
            HibernateHelper.addOrder(appCriteria, sort, order);
        }
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<Rollinfo> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public List<App> listForDownloads(Session session, int currentPage, int pageSize) {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("id"));
        proList.add(Projections.property("pkname"));
        proList.add(Projections.property("marketName"));

        Criteria cri = session.createCriteria(App.class);
        cri.setProjection(proList);
        cri.setMaxResults(pageSize);
        cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
        // 先全部更新.
        // cri.add(Restrictions.or(Restrictions.ltProperty("realDownload",
        // "lastDayDownload"),
        // Restrictions.ne("deltaDownload", 0)));
        List<Object[]> list = HibernateHelper.list(cri);
        List<App> apps = null;
        if (list != null) {
            apps = new ArrayList<App>(list.size());
            for (Object[] obj : list) {
                App e = new App();
                e.setId((Integer) obj[0]);
                e.setPkname((String) obj[1]);
                e.setMarketName((String) obj[2]);
                apps.add(e);
            }
            list.clear();
        }
        return apps;
    }

    @Override
    public int updateDownloads(Session session, int id, int downloadRank, int downloadsOfApp) {
        String hql = "update App set downloadRank = :downloadRank, downloads = :downloadsOfApp where id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("downloadRank", downloadRank);
        query.setParameter("downloadsOfApp", downloadsOfApp);
        return query.executeUpdate();
    }

    @Override
    public int updateDayDownload(Session session, int id) {
        String hql = "update App set LastDayDelta =RealDownload - LastDayDownload , LastDayDownload = RealDownload where id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public int updateWeekDownload(Session session, int id) {
        String hql = "update App set LastWeekDelta =RealDownload - LastWeekDownload , LastWeekDownload = RealDownload where id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public int updateDownloadRank(Session session, int id) {
        String hql = "update App set DownloadRank = RealDownload + DeltaDownload where id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public int updateDownload(List<Integer> ids, Integer realDownload, int deltaDownload) {
        String hql = null;
        if (realDownload != null) {
            hql = "update App set realDownload = :realDownload , deltaDownload = :deltaDownload, downloadRank = :downloadRank where id in (:id)";
        } else {
            hql = "update App set deltaDownload = :deltaDownload, downloadRank = realDownload + :deltaDownload where id in (:id)";
        }
        Query query = getSession().createQuery(hql);
        if (realDownload != null) {
            query.setParameter("realDownload", realDownload);
            query.setParameter("downloadRank", realDownload.intValue() + deltaDownload);
        }
        query.setParameter("deltaDownload", deltaDownload);
        query.setParameterList("id", ids);
        return query.executeUpdate();
    }

    @Override
    public long countForSearchingRolling(Short catalog, Integer subCatalog, String keywords) {
        Criteria cri = getSession().createCriteria(Rollinfo.class);
        Criteria appCriteria = cri.createCriteria("app", JoinType.LEFT_OUTER_JOIN);

        if (catalog != null) {
            appCriteria.add(Restrictions.eq("catalog", catalog));
        }
        if (subCatalog != null) {
            appCriteria.add(Restrictions.eq("subCatalog", subCatalog));
        }

        if (keywords != null && !keywords.isEmpty()) {
            appCriteria.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        cri.setProjection(Projections.rowCount());
        List<Long> list = HibernateHelper.list(cri);
        return list.get(0);
    }

    @Override
    public List<App> getByPackageName(Session session, String pkname) {
        String queryString = "from App where pkname = :pkname";
        Query q = session.createQuery(queryString);
        q.setParameter("pkname", pkname);
        return HibernateHelper.list(q);
    }

    @Override
    public List<App> getApps(Session session, String pkname) {
        String queryString = "from App where pkname = :pkname";
        Query q = session.createQuery(queryString);
        q.setParameter("pkname", pkname);
        return HibernateHelper.list(q);
    }

    @Override
    public List<App> getApps(List<Integer> ids) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.in("id", ids));
        return HibernateHelper.list(cri);
    }

    @Override
    public App getNotBigGame(Session session, String pkname, String signatureSha1) {
        String queryString = "from App where pkname = :pkname and signatureSha1 = :signatureSha1 and catalog <> :catalog";
        Query q = session.createQuery(queryString);
        q.setParameter("pkname", pkname);
        q.setParameter("signatureSha1", signatureSha1);
        q.setParameter("catalog", EnumCatalog.BIGGAME.getCatalog());
        Object o = q.uniqueResult();
        if (o != null) {
            return (App) o;
        }
        return null;
    }

    @Override
    public App getNullSignature(Session session, String pkname) {
        String queryString = "from App where pkname = :pkname and signatureSha1 is null";
        Query q = session.createQuery(queryString);
        q.setParameter("pkname", pkname);
        Object o = q.uniqueResult();
        if (o != null) {
            return (App) o;
        }
        return null;
    }

    @Override
    public String getOfficialSigSha1(Session session, String pkname) {
        Projection officialSigSha1 = Projections.property("officialSigSha1");
        Criteria cri = session.createCriteria(App.class);
        cri.setProjection(officialSigSha1);
        cri.add(Restrictions.eq("pkname", pkname));
        cri.add(Restrictions.isNotNull("officialSigSha1"));
        List<String> list = HibernateHelper.list(cri);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<App> getAppsOfDropMarket(Session session, String marketName, Integer currentPage, Integer pageSize) {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("pkname"));
        proList.add(Projections.property("signatureSha1"));
        Criteria cri = session.createCriteria(App.class);
        cri.setProjection(proList);
        cri.setMaxResults(pageSize);
        cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
        List<Object[]> list = HibernateHelper.list(cri);
        List<App> apps = null;
        if (list != null) {
            apps = new ArrayList<App>(list.size());
            for (Object[] obj : list) {
                App e = new App();
                e.setPkname((String) obj[0]);
                e.setSignatureSha1((String) obj[1]);
                apps.add(e);
            }
            list.clear();
        }
        return apps;
    }

    @Override
    public long countAppsOfDropMarket(Session session, String marketName) {
        Query query = session.createQuery("select count(id) from App  where marketName = :marketName");
        query.setParameter("marketName", marketName);
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public int updateToHide(Session session, String marketName) {
        String hql = "update App set hidden = 1 where marketName = :marketName";
        Query query = session.createQuery(hql);
        query.setParameter("marketName", marketName);
        return query.executeUpdate();
    }

    @Override
    public int updateToShow(Session session, String marketName) {
        String hql = "update App set hidden = 0 where marketName = :marketName";
        Query query = session.createQuery(hql);
        query.setParameter("marketName", marketName);
        return query.executeUpdate();
    }

    @Override
    public List<App> queryVirtualApp() {
        Criteria cri = getSession().createCriteria(App.class);
        // cri.add(Restrictions.or(Restrictions.eq("catalog", (short) 100),
        // Restrictions.eq("audit", true)));
        // audit
        cri.add(Restrictions.eq("catalog", (short) 100));
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public int updateHide(Session sess, List<Integer> ids) {
        String hql = "update App set Hidden = 1 where id in (:ids)";
        Query query = sess.createQuery(hql);
        query.setParameterList("ids", ids);
        int num = query.executeUpdate();
        if (ids != null && ids.size() == num) {
            appHistory4IndexDaoImpl.updateAppStatus2Del(ids);
        }
        return num;
    }

    @Override
    public App getByMarket(Session session, String pkname, String marketName) {
        String queryString = "from App where pkname = :pkname and marketName = :marketName";
        Query q = session.createQuery(queryString);
        q.setParameter("pkname", pkname);
        q.setParameter("marketName", marketName);
        Object o = q.uniqueResult();
        if (o != null) {
            return (App) o;
        }
        return null;
    }

    @Override
    public App getByFKMarketAppId(Session session, int marketAppId, short catalog) {
        final String queryString = "from App where marketAppId = :marketAppId and catalog = :catalog";
        final Query q = session.createQuery(queryString);
        q.setParameter("marketAppId", marketAppId);
        q.setParameter("catalog", catalog);
        Object o = q.uniqueResult();
        if (o != null) {
            return (App) o;
        }
        return null;
    }

    @Override
    public void updateHide(Session session, App app) {
        session.update(app);
    }
}
