package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.VirusKind;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.HighAndLowDate;

@Repository
public class AppDaoImpl extends AbstractBaseDao<App> implements AppDao {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public final BeanPropertyRowMapper<App> appRowMapper = BeanPropertyRowMapper.newInstance(App.class);

    public final BeanPropertyRowMapper<LatestDate> latestDateRowMapper = BeanPropertyRowMapper
            .newInstance(LatestDate.class);

    public AppDaoImpl() {
    }

    @Override
    public List<App> getByPackageName(String pkname) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("pkname", pkname));
        cri.addOrder(Order.desc("versionCode"));
        List<App> apps = HibernateHelper.list(cri);
        return apps;
    }

    @Override
    public App getAppByPackageName(String packagename) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("pkname", packagename));
        cri.addOrder(Order.desc("realDownload"));
        cri.setMaxResults(1);
        List<App> apps = HibernateHelper.list(cri);
        if (apps != null && !apps.isEmpty()) {
            return apps.get(0);
        }
        return null;
    }

    @Override
    public App getAppByApk(String pkname, String signaturesha1) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("pkname", pkname));
        cri.add(Restrictions.eq("signatureSha1", signaturesha1));
        Object o = cri.uniqueResult();
        if (o != null) {
            return (App) o;
        }
        return null;
    }

    @Override
    public int deleteByMarketApp(int foreignKey) {
        Query query = getSession().createQuery("delete from App where marketAppId=:marketAppId");
        query.setInteger("marketAppId", foreignKey);
        int rows = query.executeUpdate();
        return rows;
    }

    @Override
    public Integer getPkByMarketAppId(int marketAppId) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("marketAppId", marketAppId));
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
        Query query = getSession().createQuery("select count(id) from App where hidden = 0");
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public List<Rollinfo> rollinfo() {
        // Query query =
        // getSession().createQuery("from Rollinfo x left join fetch x.app where x.app.id is not null");
        Query query = getSession().createQuery("from Rollinfo x");
        return HibernateHelper.list(query);
    }

    @Override
    public List<App> get(List<Integer> ids, String sort, String order) {
        List<Integer> sorted = new ArrayList<Integer>(ids);
        Collections.sort(sorted);
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.in("id", sorted));
        cri.add(Restrictions.eq("hidden", false));
        HibernateHelper.addOrder(cri, sort, order);
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public List<App> getForIndex(Session session, Short catalog, Integer subCatalog, Integer currentPage,
            Integer pageSize) {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("id"));
        proList.add(Projections.property("name"));
        proList.add(Projections.property("description"));
        proList.add(Projections.property("keywords"));
        proList.add(Projections.property("subCatalog"));
        proList.add(Projections.property("downloadRank"));
        proList.add(Projections.property("catalog"));

        proList.add(Projections.property("adPopupTypes"));
        proList.add(Projections.property("signatureSha1"));
        proList.add(Projections.property("officialSigSha1"));

        Criteria cri = session.createCriteria(App.class);
        cri.setProjection(proList);
        cri.add(Restrictions.eq("hidden", false));
        if (catalog != null) {
            cri.add(Restrictions.eq("catalog", catalog));
        }
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        if (currentPage != null && pageSize != null) {
            cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
            cri.setMaxResults(pageSize);
        }
        List<Object[]> list = HibernateHelper.list(cri);
        List<App> apps = null;
        if (list != null) {
            apps = new ArrayList<App>(list.size());
            for (Object[] obj : list) {
                App a = new App();
                a.setId((Integer) obj[0]);
                a.setName((String) obj[1]);
                a.setDescription((String) obj[2]);
                a.setKeywords((String) obj[3]);
                a.setSubCatalog((Integer) obj[4]);
                a.setDownloadRank((Integer) obj[5]);
                a.setCatalog((Short) obj[6]);
                a.setAdPopupTypes((String) obj[7]);
                a.setSignatureSha1((String) obj[8]);
                a.setOfficialSigSha1((String) obj[9]);
                apps.add(a);
            }
            list.clear();
        }
        return apps;
    }

    @Override
    public long count(Session session, short catalog) {
        Query query = session.createQuery("select count(id) from App where catalog = ? and hidden = 0");
        query.setParameter(0, catalog);
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString());
    }

    @Override
    public List<App> getLatest(short catalog, Integer subCatalog, Long date, int currentPage, int pageSize,
            Boolean noAds, Boolean noVirus, Boolean official) {
        Criteria cri = getSession().createCriteria(App.class);
        getLastestByFilter(catalog, subCatalog, date, cri);
        cri.add(Restrictions.eq("hidden", false));
        filterAdVirusOfficial(noAds, noVirus, official, cri);

        if (date != null) {
            cri.addOrder(Order.desc("viewCount"));
        } else {
            // 不查询日期,按日期先排序.
            cri.addOrder(Order.desc("lastUpdateTime"));
        }
        cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
        cri.setMaxResults(pageSize);
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long getLatestCount(short catalog, Integer subCatalog, Long date, Boolean noAds, Boolean noVirus,
            Boolean official) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.setProjection(Projections.count("id"));
        cri.add(Restrictions.eq("hidden", false));

        filterAdVirusOfficial(noAds, noVirus, official, cri);

        getLastestByFilter(catalog, subCatalog, date, cri);
        Object o = cri.uniqueResult();
        return ((Long) o).longValue();
    }

    private void getLastestByFilter(short catalog, Integer subCatalog, Long date, Criteria cri) {
        cri.add(Restrictions.eq("catalog", catalog));
        cri.add(Restrictions.eq("hidden", false));
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        if (date != null) {
            HighAndLowDate hld = new HighAndLowDate(date);
            cri.add(Restrictions.between("lastUpdateTime", hld.getLow(), hld.getHigh()));
        }
    }

    @Override
    public List<App> list(short catalog, Integer subCatalog, String sort, String order, int currentPage, int pageSize,
            Boolean noAds, Boolean noVirus, Boolean official) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("catalog", catalog));
        cri.add(Restrictions.eq("hidden", false));
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        filterAdVirusOfficial(noAds, noVirus, official, cri);
        HibernateHelper.addOrder(cri, sort, order);
        cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
        cri.setMaxResults(pageSize);
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long count(short catalog, Integer subCatalog, Boolean noAds, Boolean noVirus, Boolean official) {
        Criteria cri = getSession().createCriteria(App.class);
        cri.setProjection(Projections.count("id"));
        cri.add(Restrictions.eq("catalog", catalog));
        cri.add(Restrictions.eq("hidden", false));
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        filterAdVirusOfficial(noAds, noVirus, official, cri);
        Object o = cri.uniqueResult();
        return ((Long) o).longValue();
    }

    @Override
    public List<String> getName(Session session, short catalog, Integer subCatalog, Integer currentPage,
            Integer pageSize) {
        Criteria cri = session.createCriteria(App.class);
        cri.setProjection(Projections.distinct(Projections.property("name")));
        cri.add(Restrictions.eq("catalog", catalog));
        cri.add(Restrictions.eq("hidden", false));
        if (subCatalog != null) {
            cri.add(Restrictions.eq("subCatalog", subCatalog));
        }
        cri.addOrder(Order.asc("name"));
        if (currentPage != null && pageSize != null) {
            cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
            cri.setMaxResults(pageSize);
        }
        List<String> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long count(Session session) {
        Criteria cri = session.createCriteria(App.class);
        cri.add(Restrictions.eq("hidden", false));
        cri.setProjection(Projections.count("id"));
        Object o = cri.uniqueResult();
        return ((Long) o).longValue();
    }

    @Override
    public List<App> getForQuickTipsIndex(Session session, Integer currentPage, Integer pageSize) {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("name"));
        proList.add(Projections.property("downloadRank"));
        proList.add(Projections.property("catalog"));
        proList.add(Projections.property("id"));
        Criteria cri = session.createCriteria(App.class);
        cri.setProjection(proList);
        cri.add(Restrictions.eq("hidden", false));
        cri.addOrder(Order.asc("downloadRank"));
        if (currentPage != null && pageSize != null) {
            cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
            cri.setMaxResults(pageSize);
        }
        List<Object[]> list = HibernateHelper.list(cri);
        List<App> apps = null;
        if (list != null) {
            apps = new ArrayList<App>(list.size());
            for (Object[] obj : list) {
                App e = new App();
                e.setName((String) obj[0]);
                e.setDownloadRank((Integer) obj[1]);
                e.setCatalog((Short) obj[2]);
                e.setId((Integer) obj[3]);
                apps.add(e);
            }
            list.clear();
        }
        return apps;
    }

    @Override
    public long countName(Session session) {
        Query query = getSession().createQuery("select count(name) from App where hidden = 0");
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public List<App> getScanTop() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("id"));
        proList.add(Projections.property("catalog"));
        proList.add(Projections.property("pkname"));
        proList.add(Projections.property("name"));
        proList.add(Projections.property("downloadUrl"));
        proList.add(Projections.property("description"));
        proList.add(Projections.property("logoUrl"));
        proList.add(Projections.property("size"));
        proList.add(Projections.property("version"));
        proList.add(Projections.property("versionCode"));
        proList.add(Projections.property("pageUrl"));
        proList.add(Projections.property("updateInfo"));
        proList.add(Projections.property("pathStatus"));

        Criteria cri = getSession().createCriteria(App.class);
        cri.setProjection(proList);
        cri.add(Restrictions.eq("hidden", false));
        cri.setMaxResults(300);
        cri.addOrder(Order.desc("downloadRank"));
        List<Object[]> list = HibernateHelper.list(cri);
        List<App> apps = null;
        if (list != null) {
            apps = new ArrayList<App>(list.size());
            for (Object[] obj : list) {
                App e = new App();
                e.setId((Integer) obj[0]);
                e.setCatalog((Short) obj[1]);
                e.setPkname((String) obj[2]);
                e.setName((String) obj[3]);
                e.setDownloadUrl((String) obj[4]);
                e.setDescription((String) obj[5]);
                e.setLogoUrl((String) obj[6]);
                e.setSize((Integer) obj[7]);
                e.setVersion((String) obj[8]);
                e.setVersionCode((Long) obj[9]);
                e.setPageUrl((String) obj[10]);
                e.setUpdateInfo((String) obj[11]);
                e.setPathStatus((Byte) obj[12]);
                apps.add(e);
            }
            list.clear();
        }
        return apps;
    }

    @Override
    public Class<App> getType() {
        return App.class;
    }

    @Override
    public int updateIncrementDownload(Session session, int id, int delta) {
        String queryString = "update App set realDownload = realDownload + :delta , downloadrank = realDownload +deltaDownload where id = :id";
        Query q = session.createQuery(queryString);
        q.setParameter("id", id);
        q.setParameter("delta", delta);
        return q.executeUpdate();
    }

    @Override
    public List<App> getMaxDownloadOfDay() {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("hidden", false));
        cri.addOrder(Order.desc("lastDayDelta"));
        cri.setMaxResults(AppConfig.BATCH_SIZE);
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public List<App> getMaxDownloadOfWeek() {
        Criteria cri = getSession().createCriteria(App.class);
        cri.add(Restrictions.eq("hidden", false));
        cri.addOrder(Order.desc("lastWeekDelta"));
        cri.setMaxResults(AppConfig.BATCH_SIZE);
        List<App> list = HibernateHelper.list(cri);
        return list;
    }

    private void filterAdVirusOfficial(Boolean noAds, Boolean noVirus, Boolean official, Criteria cri) {
        if (noAds != null && noAds.booleanValue()) {
            cri.add(Restrictions.or(Restrictions.isNull("adPopupTypes"), Restrictions.eq("adPopupTypes", "")));
            cri.add(Restrictions.or(Restrictions.isNull("adActionTypes"), Restrictions.eq("adActionTypes", "")));
        }
        if (noVirus != null && noVirus.booleanValue()) {
            cri.add(Restrictions.ne("virusKind", VirusKind.BLACK.getVal()));
        }
        if (official != null && official.booleanValue()) {
            cri.add(Restrictions.isNotNull("signatureSha1"));
            cri.add(Restrictions.sqlRestriction("signatureSha1 = officialSigSha1"));
        }
    }
}
