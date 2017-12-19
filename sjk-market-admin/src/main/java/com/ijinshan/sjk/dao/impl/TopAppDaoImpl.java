package com.ijinshan.sjk.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.config.ChangeOutputImpl;
import com.ijinshan.sjk.dao.TopAppDao;
import com.ijinshan.sjk.po.TopApp;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.SqlReader;

@Repository
public class TopAppDaoImpl extends AbstractBaseDao<TopApp> implements TopAppDao {
    private static final Logger logger = LoggerFactory.getLogger(TopAppDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ChangeOutputImpl output;

    public final BeanPropertyRowMapper<TopApp> topAppRowMapper = BeanPropertyRowMapper.newInstance(TopApp.class);

    public final String topAppSql;

    public TopAppDaoImpl() {
        logger.info("Loading SQL files...\t{}", TopAppDaoImpl.class);
        String[] tmps = new String[10];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/topapp.sql");
            logger.info("Load SQL files, DONE!\t{}", TopAppDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        topAppSql = tmps[0];
        tmps = null;
    }

    @Override
    public Class<TopApp> getType() {
        return TopApp.class;
    }

    @Override
    public List<TopApp> findTopAppList(String keywords, Integer id, int page, int rows) {
        Criteria cri = getSession().createCriteria(TopApp.class);
        if (id != null && id > 0) {
            cri.add(Restrictions.eq("id", id));
        }
        if (keywords != null && !keywords.isEmpty()) {
            cri.add(Restrictions.like("name", keywords, MatchMode.ANYWHERE));
        }
        cri.add(Restrictions.and(Restrictions.ne("name",""), Restrictions.isNotNull("name")));
        
        cri.addOrder(Order.desc("state"));
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<TopApp> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public List<TopApp> getAllTopAppList() {
        return this.jdbcTemplate.query(topAppSql, topAppRowMapper);
    }

    @Override
    public boolean deleteByIds(List<Integer> ids) {
        String hql = "delete TopApp where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate() == ids.size();
    }

    @Override
    public long getCounts() {
        Query query = getSession().createQuery("select count(id) from TopApp");
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    @Override
    public int updateState(List<Integer> ids,byte state) {
        String hql = "update TopApp set state = :state  where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.setParameter("state", state);
        return  query.executeUpdate();
    }
}
