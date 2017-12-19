package com.ijinshan.sjk.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.po.AppHistory4Index;
import com.ijinshan.util.DefaultDateTime;
import com.ijinshan.util.HibernateHelper;

@Repository
public class AppHistory4IndexDaoImpl extends AbstractBaseDao<AppHistory4Index> implements AppHistory4IndexDao {

    private static final Logger logger = LoggerFactory.getLogger(AppHistory4IndexDaoImpl.class);

    @Override
    public void saveOrUpdate(AppHistory4Index appidx) {
        this.getSession().saveOrUpdate(appidx);
    }

    @Override
    public Class<AppHistory4Index> getType() {
        return AppHistory4Index.class;
    }

    @Override
    public void saveOrUpdate(Integer appId) {

        Session session = null;
        try {
            session = this.sessions.openSession();
            String selectHql = "from AppHistory4Index where appId=:appId";
            Query query = session.createQuery(selectHql);
            query.setInteger("appId", appId);
            // logger.info("will process appId is:{}", appId);
            List<AppHistory4Index> list = HibernateHelper.list(query);
            Date date = new Date();
            if (list == null || list.size() == 0) {
                AppHistory4Index appidx = new AppHistory4Index();
                appidx.setAppId(appId);
                appidx.setAppStatus((byte) 1);// 设置成新增加
                appidx.setCreateTime(date);
                appidx.setIndexStatus((byte) 0);// 需要重索引
                appidx.setLastOpTime(date);
                appidx.setLastIndexTime(DefaultDateTime.getDefaultDateTime());
                session.save(appidx);
                // logger.info("save:{}", appidx.getId());
            } else if (list.size() == 1) {
                AppHistory4Index appidx = list.get(0);
                // logger.info("upate:{}", appidx.getId());
                appidx.setIndexStatus((byte) 0);// 需要重新索引
                appidx.setAppStatus((byte) 2);// 设置状态 为更新
                appidx.setLastOpTime(date);
                session.update(appidx);

            } else {
                logger.error("appid {} is not unique in table AppHistory4index !!!,please check .", appId);
            }

            session.flush();
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.clear();
                session.close();
            }
        }

    }

    /**
     * 设置appStatus 状态 为可删除 状态 3,索引状态 indexStatus为 -1
     */
    @Override
    public int updateAppStatus2Del(List<Integer> ids) {
        Session session = null;

        try {
            session = this.sessions.openSession();

            String hql = "update AppHistory4Index set appStatus=3,indexStatus=-1,lastOpTime=:lastOpTime where appId in (:appIds)";
            Query query = session.createQuery(hql);
            query.setParameterList("appIds", ids);
            query.setTimestamp("lastOpTime", new Date());

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

    @Override
    public int updateAppStatus2Del(Integer id) {
        Session session = null;

        try {
            session = this.sessions.openSession();
            //
            String hql = "update AppHistory4Index set appStatus=3,indexStatus=-1,lastOpTime=:lastOpTime where appId =:appId";
            Query query = session.createQuery(hql);
            query.setInteger("appId", id);
            query.setTimestamp("lastOpTime", new Date());

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

    @Override
    public int update(List<Integer> ids) {
        Session session = null;
        try {
            session = this.sessions.openSession();
            // 设置状态 为更新
            // 需要重新索引
            String hql = "update AppHistory4Index set appStatus=2,indexStatus=0,lastOpTime=:lastOpTime where appId in (:appIds)";
            Query query = session.createQuery(hql);
            query.setParameterList("appIds", ids);
            query.setTimestamp("lastOpTime", new Date());

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
