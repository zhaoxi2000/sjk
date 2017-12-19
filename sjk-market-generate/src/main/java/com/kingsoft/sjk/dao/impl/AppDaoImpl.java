package com.kingsoft.sjk.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Permissions;
import com.kingsoft.sjk.config.TagType;
import com.kingsoft.sjk.dao.AppDao;
import com.kingsoft.sjk.dao.BasicSessionFactory;
import com.kingsoft.sjk.po.AppTags;
import com.kingsoft.sjk.po.Catalog;
import com.kingsoft.sjk.util.HibernateHelper;
import com.kingsoft.sjk.util.Pager;

@Repository
public class AppDaoImpl extends BasicSessionFactory implements AppDao {

    private static final Logger logger = LoggerFactory.getLogger(AppDaoImpl.class);

    @Override
    public List<App> findData() {
        Session session = null;
        List<App> list = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from App app where app.hidden = false");
            list = HibernateHelper.list(q);
            // if (list != null) {
            // logger.debug("in findData all .{} ", list.size());
            // }
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List<App> findData(Date curDate) {
        Session session = null;
        List<App> list = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session
                    .createQuery(
                            "from App app where ( app.lastFetchTime >=:lastFetchTime or app.lastUpdateTime >=:lastUpdateTime ) and hidden =false")
                    .setDate("lastFetchTime", curDate).setDate("lastUpdateTime", curDate);
            list = HibernateHelper.list(q);
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public int updatePageUrl(App app) {
        Session session = null;
        // List<App> list = null;
        // int updateInt = 0;
        // Transaction tx = null;
        try {
            session = this.sessionFactory.openSession();
            // tx = session.beginTransaction();
            session.createQuery("update App set pageUrl=? where id=?").setString(0, app.getPageUrl())
                    .setInteger(1, app.getId()).executeUpdate();
            // logger.debug("updateInt : {}", updateInt);
            // tx.commit();
            // } catch (Exception e) {
            // tx.rollback();
            // logger.error("error:", e);

        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.flush();
            session.clear();
            session.close();
        }
        return 0;
    }

    @Override
    public Map<Integer, com.kingsoft.sjk.po.Catalog> initCatalog() {
        Session session = null;
        List<Catalog> list = null;
        int updateInt = 0;
        Map<Integer, Catalog> catalogs = new HashMap<Integer, Catalog>();
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from Catalog");
            list = HibernateHelper.list(q);
            for (Catalog catalog : list) {
                catalogs.put(catalog.getId(), catalog);
            }

        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            list.clear();
            session.close();
        }

        return catalogs;
    }

    @Override
    public Map<Integer, List<AppTags>> initAppTags(TagType tagType) {
        List<AppTags> list = getAppTags(tagType);
        Map<Integer, List<AppTags>> maps = new HashMap<Integer, List<AppTags>>(list == null ? 0 : list.size());
        for (AppTags tag : list) {
            if (tag != null) {
                List<AppTags> mapList = maps.get(tag.getAppId());
                if (mapList != null) {
                    mapList.add(tag);
                } else {
                    mapList = new ArrayList<AppTags>();
                    mapList.add(tag);
                }
                maps.put(tag.getAppId(), mapList);
            }
        }
        list.clear();

        return maps;
    }

    @Override
    public List<AppTags> getAppTags(TagType tagType) {
        Session session = null;
        List<AppTags> list = null;
        try {

            session = this.sessionFactory.openSession();
            Query q = session
                    .createSQLQuery("select Id,AppId,TagId,Rank,TagName,TagType from AppAndTag where tagType=:tagType")
                    .addEntity(AppTags.class).setShort("tagType", tagType.getVal());
            list = HibernateHelper.list(q);

        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Map<Integer, Permissions> initPermissions() {
        Session session = null;
        List<Permissions> list = null;
        int updateInt = 0;
        Map<Integer, Permissions> permissionss = new HashMap<Integer, Permissions>();
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from Permissions");
            list = HibernateHelper.list(q);
            for (Permissions permissions : list) {
                permissionss.put(permissions.getId(), permissions);
            }

        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            list.clear();
            session.close();
        }

        return permissionss;
    }

    @Override
    public List<App> queryPager(long total, int page, int rows, Date curDate) {
        Pager<App> pager = new Pager<App>();
        pager.setTotalCount(total);
        pager.setPageSize(rows);
        pager.setPageNo(page);

        Session session = null;
        List<App> list = null;
        Query q = null;
        try {
            session = this.sessionFactory.openSession();
            if (null == curDate) {
                q = session.createQuery("from App app where app.hidden = false");
            } else {
                session.createQuery("from App  app where app.hidden = false and LastFetchTime >=?  ").setDate(0,
                        curDate);
            }

            q.setFirstResult(pager.getFirst());
            q.setMaxResults(pager.getPageSize());

            list = HibernateHelper.list(q);
            // if (list != null) {
            // logger.debug("in findData all .{} ", list.size());
            // }
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
            pager = null;
        }
        return list;
    }

    @Override
    public int count() {
        Session session = null;
        // Query q = null;
        try {
            session = this.sessionFactory.openSession();
            return ((Number) session.createQuery("select count(id) from App  app where app.hidden = false ")
                    .uniqueResult()).intValue();
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
        }
        return 0;
    }

    @Override
    public App findById(int id) {

        Session session = null;
        // Query q = null;
        try {
            session = this.sessionFactory.openSession();
            App app = (App) session.get(App.class, id);
            return app;
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<App> findAppListByCatalog(Integer catalog) {
        List<App> appList = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            appList = session.createQuery("from App app where  catalog =:catalog")
                    .setParameter("catalog", catalog.shortValue()).list();
            if (appList.size() <= 0) {
                appList = new ArrayList<App>();
            }
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.close();
        }
        return appList;
    }

}
