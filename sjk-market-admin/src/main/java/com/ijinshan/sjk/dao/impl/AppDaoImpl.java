package com.ijinshan.sjk.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.config.AppStatus;
import com.ijinshan.sjk.config.ChangeOutputImpl;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAdmin;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.Downloads;
import com.ijinshan.sjk.vo.TopAppVo;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.SqlReader;

@Repository
public class AppDaoImpl extends AbstractBaseDao<App> implements AppDao {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ChangeOutputImpl output;

    public final BeanPropertyRowMapper<Downloads> downloadsRowMapper = BeanPropertyRowMapper
            .newInstance(Downloads.class);
    public final BeanPropertyRowMapper<TopAppVo> TopAppVoRowMapper = BeanPropertyRowMapper.newInstance(TopAppVo.class);

    public final String downloadsSql;
    public final String top2000sql;

    public AppDaoImpl() {
        logger.info("Loading SQL files...\t{}", AppDaoImpl.class);
        String[] tmps = new String[10];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/downloads.sql");
            tmps[1] = SqlReader.getSqlFileToString("/sql-file/top2000.sql");
            logger.info("Load SQL files, DONE!\t{}", AppDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        downloadsSql = tmps[0];
        top2000sql = tmps[1];
        tmps = null;
    }

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
    public int updateHide(List<Integer> ids) {
        return updateHide(getSession(), ids);
    }

    @Override
    public int updateShow(List<Integer> ids) {
        String hql = "update App set Hidden = 0 where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public int updateAudit(List<Integer> ids, int status) {
        String hql = "update App set status =:status where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.setInteger("status", status);
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
    public List<AppAdmin> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id,
            String sort, String order, Date startDate, Date endDate, Boolean official, Integer audit) {
        Criteria cri = searchByFilter(catalog, subCatalog, keywords, id, startDate, endDate, official, audit);
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        // cri.addOrder(Order.asc("auditCatalog"));
        if (sort != null && !sort.isEmpty()) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (order != null && "asc".equals(order)) {
                cri.addOrder(Order.asc("downloadRank"));
            } else {
                cri.addOrder(Order.desc("downloadRank"));
            }
        }
        if ("marketName".equals(sort)) {
            cri.addOrder(Order.desc("downloadRank"));
        }
        // cri.addOrder(Order.asc("name"));
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<AppAdmin> list = HibernateHelper.list(cri);
        if (list != null) {
            for (AppAdmin appAdmin : list) {
                output.setAuditStatus(appAdmin);
            }
        }
        return list;
    }

    private Criteria searchByFilter(Short catalog, Integer subCatalog, String keywords, Integer id, Date startDate,
            Date endDate, Boolean official, Integer audit) {
        Criteria cri = getSession().createCriteria(AppAdmin.class);
        if (catalog != null) {
            cri.add(Restrictions.eq("catalog", catalog));
        }
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        if (id != null && id > 0) {
            cri.add(Restrictions.eq("id", id));
        }
        if (keywords != null && !keywords.isEmpty()) {
            // cri.add(Restrictions.like("name", keywords, MatchMode.START));
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        if (official != null) {
            if (official) {
                cri.add(Restrictions.and(Restrictions.isNotNull("signatureSha1"),
                        Restrictions.isNotNull("officialSigSha1")));
                cri.add(Restrictions.eqProperty("signatureSha1", "officialSigSha1"));
            } else {
                cri.add(Restrictions.neProperty("signatureSha1", "officialSigSha1"));
            }
        }
        if (startDate != null && endDate != null) {
            cri.add(Restrictions.between("lastUpdateTime", startDate, endDate));
        }

        if (audit != null && audit >= 0) {
            StringBuilder sbSql = new StringBuilder(20);
            if (audit == 1) {
                sbSql.append(" (Status & ").append(AppStatus.AUDIT_MASK.getVal()).append(") = ");
                sbSql.append(AppStatus.AUDIT_YES.getVal());
            } else if (audit == 2) {
                sbSql.append(" (Status & ").append(AppStatus.AUDIT_MASK.getVal()).append(") = ");
                sbSql.append(AppStatus.AUDIT_NO_NEED.getVal());
            } else {
                sbSql.append(" (Status & ").append(AppStatus.AUDIT_MASK.getVal()).append(") <> ")
                        .append(AppStatus.AUDIT_YES.getVal());
                sbSql.append(" AND  (Status & ").append(AppStatus.AUDIT_MASK.getVal()).append(") <> ")
                        .append(AppStatus.AUDIT_NO_NEED.getVal());
                // sbSql.append(AppStatus.AUDIT_NO.getVal());
            }
            cri.add(Restrictions.sqlRestriction(sbSql.toString()));
        }
        return cri;
    }

    @Override
    public List<App> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id,
            String sort, String order) {
        Criteria cri = searchByFilter(catalog, subCatalog, keywords, id);
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        // cri.addOrder(Order.asc("auditCatalog"));
        if (sort != null && !sort.isEmpty()) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (order != null && "asc".equals(order)) {
                cri.addOrder(Order.asc("downloadRank"));
            } else {
                cri.addOrder(Order.desc("downloadRank"));
            }
        }
        if ("marketName".equals(sort)) {
            cri.addOrder(Order.desc("downloadRank"));
        }
        // cri.addOrder(Order.asc("name"));
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    private Criteria searchByFilter(Short catalog, Integer subCatalog, String keywords, Integer id) {
        Criteria cri = getSession().createCriteria(App.class);

        if (catalog != null) {
            cri.add(Restrictions.eq("catalog", catalog));
        }
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        if (id != null && id > 0) {
            cri.add(Restrictions.eq("id", id));
        }
        if (keywords != null && !keywords.isEmpty()) {
            cri.add(Restrictions.like("name", keywords, MatchMode.START));
        }
        return cri;
    }

    @Override
    public long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id) {
        Criteria cri = searchByFilter(catalog, subCatalog, keywords, id);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id, Date startDate,
            Date endDate, Boolean official, Integer audit) {
        Criteria cri = searchByFilter(catalog, subCatalog, keywords, id, startDate, endDate, official, audit);
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
            appCriteria.add(Restrictions.like("name", keywords, MatchMode.START));
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
    public List<Downloads> getDownloads(String pkname) {
        return this.jdbcTemplate.query(this.downloadsSql, this.downloadsRowMapper, pkname);
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
            appCriteria.add(Restrictions.like("name", keywords, MatchMode.START));
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
    public App get(Session session, String pkname, String signatureSha1) {
        String queryString = "from App where pkname = :pkname and signatureSha1 = :signatureSha1";
        Query q = session.createQuery(queryString);
        q.setParameter("pkname", pkname);
        q.setParameter("signatureSha1", signatureSha1);
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
    public String getOfficialSigSha1(String pkname) {
        Projection officialSigSha1 = Projections.property("officialSigSha1");

        Criteria cri = getSession().createCriteria(App.class);
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

    // @Override
    // public List<App> getAppList(List<Integer> ids) {
    // // /*ProjectionList prolist = Projections.projectionList();
    // // prolist.add(Projections.property("id"));
    // // prolist.add(Projections.property("name"));
    // // prolist.add(Projections.property("versionCode"));
    // // prolist.add(Projections.property("pkname"));
    // // prolist.add(Projections.property("signatureSha1"));
    // //
    // // Criteria cri = getSession().createCriteria(App.class);
    // // cri.add(Restrictions.);
    // //
    // // return null;*/
    // }

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
        return query.executeUpdate();
    }

    @Override
    public List<TopAppVo> getAppList(Set<String> pnames) {

        // String names = "";
        // StringBuilder sb = new StringBuilder();
        // for (String name : pnames) {
        // sb.append("'" + name + "'" + ",");
        // }
        // names = sb.toString().substring(0, sb.toString().length() - 1);
        // names = "'com.tencent.mobileqq'";
        // // System.out.println("--> " + names);
        // return this.jdbcTemplate.query(this.top2000sql,
        // this.TopAppVoRowMapper, names);

        Query query = getSession().createSQLQuery(this.top2000sql);
        query.setParameterList("ids", pnames);
        List<Object[]> lists = HibernateHelper.list(query);
        List<TopAppVo> volist = new ArrayList<TopAppVo>();
        for (Object[] obj : lists) {
            TopAppVo vo = new TopAppVo();
            vo.setId((Integer) obj[0]);
            vo.setName((String) obj[1]);
            vo.setPkname((String) obj[2]);
            vo.setLastUpdateTime((Date)obj[3]);
            volist.add(vo);

        }
        return volist;

    }

}
