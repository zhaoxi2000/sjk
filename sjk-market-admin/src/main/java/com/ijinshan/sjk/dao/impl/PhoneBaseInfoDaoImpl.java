package com.ijinshan.sjk.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.PhoneBaseInfoDao;
import com.ijinshan.sjk.po.PhoneBasicInfo;
import com.ijinshan.sjk.vo.Downloads;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.SqlReader;

@Repository
public class PhoneBaseInfoDaoImpl extends AbstractBaseDao<PhoneBasicInfo> implements PhoneBaseInfoDao {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBaseInfoDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public final BeanPropertyRowMapper<Downloads> downloadsRowMapper = BeanPropertyRowMapper
            .newInstance(Downloads.class);
    public final String downloadsSql;

    public PhoneBaseInfoDaoImpl() {
        logger.info("Loading SQL files...\t{}", PhoneBaseInfoDaoImpl.class);
        String[] tmps = new String[10];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/downloads.sql");
            logger.info("Load SQL files, DONE!\t{}", PhoneBaseInfoDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        downloadsSql = tmps[0];
        tmps = null;
    }

    @Override
    public Class<PhoneBasicInfo> getType() {
        return PhoneBasicInfo.class;
    }

    @Override
    public List<String> findAllPhoneBrand() {
        Query query = getSession().createQuery("select distinct(brand) from PhoneBasicInfo where Brand is not null");
        return HibernateHelper.list(query);
    }

    @Override
    public List<PhoneBasicInfo> findPhoneListByBrand(String brand) {
        Query query = getSession().createQuery(" from PhoneBasicInfo where brand = :brand");
        query.setParameter("brand", brand);
        return HibernateHelper.list(query);
    }

    @Override
    public List<PhoneBasicInfo> findPhoneListByParams(String brand, String product) {
        Query query = null;
        if (StringUtils.isEmpty(product)) {
            query = getSession().createQuery(" from PhoneBasicInfo where brand = :brand and Product <> 'Android设备'");
            query.setParameter("brand", brand);
        } else {
            query = getSession().createQuery(
                    " from PhoneBasicInfo where brand = :brand and product =:product and Product <> 'Android设备'");
            query.setParameter("brand", brand);
            query.setParameter("product", product);
        }
        return HibernateHelper.list(query);
    }

    @Override
    public List<PhoneBasicInfo> getPhoneBaseInfoListByIds(List<Integer> ids) {
        Query query = getSession().createQuery(" from PhoneBasicInfo where id in (:ids)");
        query.setParameterList("ids", ids);
        return HibernateHelper.list(query);
    }

}
