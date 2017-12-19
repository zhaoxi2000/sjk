package com.ijinshan.sjk.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.PhoneRelationsDao;
import com.ijinshan.sjk.po.PhoneRelations;
import com.ijinshan.sjk.vo.Downloads;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.SqlReader;

@Repository
public class PhoneRelationsDaoImpl extends AbstractBaseDao<PhoneRelations> implements PhoneRelationsDao {
    private static final Logger logger = LoggerFactory.getLogger(PhoneRelationsDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public final BeanPropertyRowMapper<Downloads> downloadsRowMapper = BeanPropertyRowMapper
            .newInstance(Downloads.class);
    public final String downloadsSql;

    public PhoneRelationsDaoImpl() {
        logger.info("Loading SQL files...\t{}", PhoneRelationsDaoImpl.class);
        String[] tmps = new String[10];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/downloads.sql");
            logger.info("Load SQL files, DONE!\t{}", PhoneRelationsDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        downloadsSql = tmps[0];
        tmps = null;
    }

    @Override
    public Class<PhoneRelations> getType() {
        return PhoneRelations.class;
    }

    @Override
    public List<PhoneRelations> findPhoneRelationsByParam(String productId, Integer phoneId) {
        Query query = getSession().createQuery("from PhoneRelations where productId =:productId and phoneId=:phoneId");
        query.setParameter("productId", productId);
        query.setParameter("phoneId", phoneId);
        return HibernateHelper.list(query);
    }

}
