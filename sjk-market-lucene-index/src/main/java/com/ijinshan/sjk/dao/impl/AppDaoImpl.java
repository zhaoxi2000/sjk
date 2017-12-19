package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.AbstractBaseDao;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.util.HibernateHelper;

@Repository
public class AppDaoImpl extends AbstractBaseDao<App> implements AppDao {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Override
    public Class<App> getType() {
        return App.class;
    }

    @Override
    public List<App> getApps(int currentPage, int pageSize) {

        Session session = null;
        try {
            session = this.sessions.openSession();

            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("id"));
            proList.add(Projections.property("name"));
            proList.add(Projections.property("catalog"));
            proList.add(Projections.property("subCatalog"));
            proList.add(Projections.property("keywords"));
            proList.add(Projections.property("description"));
            proList.add(Projections.property("downloadRank"));
            /* safe scan */
            proList.add(Projections.property("adPopupTypes"));
            proList.add(Projections.property("signatureSha1"));
            proList.add(Projections.property("officialSigSha1"));
            /* safe scan */
            proList.add(Projections.property("marketName"));
            Criteria cri = session.createCriteria(App.class);
            cri.setProjection(proList);
            cri.add(Restrictions.eq("hidden", false));
            cri.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
            cri.setMaxResults(pageSize);
            List<Object[]> list = HibernateHelper.list(cri);
            List<App> apps = null;
            if (list != null && !list.isEmpty()) {
                apps = new ArrayList<App>(list.size());
                for (Object[] obj : list) {
                    App a = new App();
                    String tmp = null;
                    a.setId((Integer) obj[0]);
                    a.setName(((String) obj[1]).toLowerCase().trim());
                    a.setCatalog((Short) obj[2]);
                    a.setSubCatalog((Integer) obj[3]);
                    if ((tmp = (String) obj[4]) == null) {
                        a.setKeywords("");
                    } else {
                        a.setKeywords(tmp.toLowerCase().trim());
                    }
                    if ((tmp = (String) obj[4]) == null) {
                        a.setDescription("");
                    } else {
                        a.setDescription(tmp.toLowerCase().trim());
                    }
                    a.setDownloadRank((Integer) obj[6]);
                    /* safe scan */
                    a.setAdPopupTypes((String) obj[7]);
                    a.setSignatureSha1((String) obj[8]);
                    a.setOfficialSigSha1((String) obj[9]);
                    /* safe scan */
                    a.setMarketName((String) obj[10]);
                    apps.add(a);
                }
                list.clear();
            }
            return apps;
        } catch (Exception e) {
            logger.error("error:", e);
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.clear();
                session.close();
            }
        }
    }

    @Override
    public List<App> getApps(List<Integer> appIds) {
        String hql = "select id,name,catalog,subCatalog,keywords,description,downloadRank,adPopupTypes,signatureSha1,officialSigSha1,marketName from App app where app.hidden=0 and app.id in(:appIds)";

        Session session = null;
        try {
            session = this.sessions.openSession();
            Query query = session.createQuery(hql);
            query.setParameterList("appIds", appIds);
            List<Object[]> list = HibernateHelper.list(query);

            List<App> apps = null;
            if (list != null && !list.isEmpty()) {
                apps = new ArrayList<App>(list.size());
                for (Object[] obj : list) {
                    App a = new App();
                    String tmp = null;
                    a.setId((Integer) obj[0]);
                    a.setName(((String) obj[1]).toLowerCase().trim());
                    a.setCatalog((Short) obj[2]);
                    a.setSubCatalog((Integer) obj[3]);
                    if ((tmp = (String) obj[4]) == null) {
                        a.setKeywords("");
                    } else {
                        a.setKeywords(tmp.toLowerCase().trim());
                    }
                    if ((tmp = (String) obj[4]) == null) {
                        a.setDescription("");
                    } else {
                        a.setDescription(tmp.toLowerCase().trim());
                    }
                    a.setDownloadRank((Integer) obj[6]);
                    /* safe scan */
                    a.setAdPopupTypes((String) obj[7]);
                    a.setSignatureSha1((String) obj[8]);
                    a.setOfficialSigSha1((String) obj[9]);
                    /* safe scan */
                    a.setMarketName((String) obj[10]);
                    apps.add(a);
                }
                list.clear();
            }
            return apps;
        } catch (Exception e) {
            logger.error("error:", e);
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.clear();
                session.close();
            }
        }
    }
}
