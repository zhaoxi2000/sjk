package com.ijinshan.sjk.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.AbstractBaseDao;
import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.po.AppHistory4Index;
import com.ijinshan.util.HibernateHelper;

@Repository
public class AppHistory4IndexDaoImpl extends AbstractBaseDao<AppHistory4Index> implements AppHistory4IndexDao {

    private static final Logger logger = LoggerFactory.getLogger(AppHistory4IndexDaoImpl.class);

    @Override
    public List<AppHistory4Index> getApps(int currentPage, int pageSize) {
        String hql = "from AppHistory4Index appIdx where appIdx.indexStatus<>1";

        Session session = null;
        try {
            session = this.sessions.openSession();

            Query q = session.createQuery(hql);
            q.setFirstResult(HibernateHelper.firstResult(currentPage, pageSize));
            q.setMaxResults(pageSize);
            return HibernateHelper.list(q);
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
    public List<AppHistory4Index> getApps() {
        String hql = "from AppHistory4Index appIdx where appIdx.indexStatus<>1";

        Session session = null;
        try {
            session = this.sessions.openSession();
            return HibernateHelper.list(session.createQuery(hql));
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
    public Class<AppHistory4Index> getType() {
        return AppHistory4Index.class;
    }

    /**
     * 更新 状态到数据库,仅针对 appStatus 为 1(add) 2(update) 更新索引 ，
     */
    @Override
    public int updateAppHistory4indeToIndexed(List<Integer> appIds) {
        String hql = "update AppHistory4Index set indexStatus=1,lastIndexTime=:lastIndexTime where (appStatus=1 or appStatus=2) and appId in (:appIds)";

        Session session = null;
        try {
            session = this.sessions.openSession();
            Query query = session.createQuery(hql);
            query.setParameterList("appIds", appIds);
            query.setTimestamp("lastIndexTime", new Date());

            return query.executeUpdate();
        } catch (Exception e) {
            logger.error("error:", e);
            return 0;
        } finally {
            if (session != null && session.isOpen()) {
                session.flush();
                session.clear();
                session.close();
            }
        }

    }

    // appStatus 3(delete)
    @Override
    public int delAppHistory4index(List<Integer> appIds) {

        // String hql =
        // "delete  AppHistory4Index  where appStatus=3 and indexStatus=-1 and appId in (:appIds)";
        // 删除前一天生成索引的数据，避免数据过多
        String hql = "delete  AppHistory4Index  where (indexStatus=-1 and appId in (:appIds) ) or lastIndexTime<:lastIndexTime";
        Session session = null;
        try {
            session = this.sessions.openSession();
            Query query = session.createQuery(hql);
            query.setParameterList("appIds", appIds);
            query.setTimestamp("lastIndexTime", DateUtils.addDays(new Date(), -1));// 删除前一天索引后的数据

            return query.executeUpdate();
        } catch (Exception e) {
            logger.error("error:", e);
            return 0;
        } finally {
            if (session != null && session.isOpen()) {
                session.flush();
                session.clear();
                session.close();
            }
        }

    }
}
