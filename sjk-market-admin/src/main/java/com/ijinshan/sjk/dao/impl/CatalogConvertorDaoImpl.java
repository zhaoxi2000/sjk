package com.ijinshan.sjk.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.CatalogConvertorDao;
import com.ijinshan.sjk.po.CatalogConvertor;
import com.ijinshan.sjk.vo.MarketCatalog;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.SqlReader;

@Repository
public class CatalogConvertorDaoImpl extends AbstractBaseDao<CatalogConvertor> implements CatalogConvertorDao {
    private static final Logger logger = LoggerFactory.getLogger(CatalogConvertorDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public final BeanPropertyRowMapper<MarketCatalog> marketCatalogRowMapper = BeanPropertyRowMapper
            .newInstance(MarketCatalog.class);

    public final String marketCatalogSql;

    public CatalogConvertorDaoImpl() {
        logger.info("Loading SQL files...\t{}", AppDaoImpl.class);
        String[] tmps = new String[10];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/market-catalog.sql");
            logger.info("Load SQL files, DONE!\t{}", AppDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        marketCatalogSql = tmps[0];
        tmps = null;
    }

    @Override
    public Class<CatalogConvertor> getType() {
        return CatalogConvertor.class;
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        String hql = "delete CatalogConvertor where id in (:ids)";
        Query query = getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public long count() {
        Query query = getSession().createQuery("select count(id) from CatalogConvertor");
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString());
    }

    @Override
    public CatalogConvertor getByMarketApp(String marketName, short catalog, int subCatalog) {
        return getByMarketApp(getSession(), marketName, catalog, subCatalog);
    }

    @Override
    public CatalogConvertor getByMarketApp(Session sess, String marketName, short catalog, int subCatalog) {
        Criteria cri = sess.createCriteria(CatalogConvertor.class);
        CatalogConvertor catalogConvertor = new CatalogConvertor();
        catalogConvertor.setMarketName(marketName);
        catalogConvertor.setCatalog(catalog);
        catalogConvertor.setSubCatalog(subCatalog);
        Example example = Example.create(catalogConvertor);
        example.excludeZeroes();
        cri.add(example);

        Object o = cri.uniqueResult();
        if (o != null) {
            catalogConvertor = (CatalogConvertor) o;
        }
        return catalogConvertor;
    }

    @Override
    public boolean isExists(String marketName, short catalog, int subCatalog) {
        Query query = getSession()
                .createQuery(
                        "select count(id) from CatalogConvertor where   catalog= :catalog and marketName= :marketName and subCatalog =:subCatalog")
                .setInteger("catalog", catalog)
                .setString("marketName", StringUtils.defaultIfEmpty(marketName, "").trim())
                .setInteger("subCatalog", subCatalog);
        Object o = query.uniqueResult();
        return Integer.valueOf(o.toString()) > 0;
    }

    @Override
    public List<CatalogConvertor> searchByMarketName(String marketName) {
        Criteria cri = getSession().createCriteria(CatalogConvertor.class);
        cri.add(Restrictions.eq("marketName", marketName));
        List<CatalogConvertor> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public List<CatalogConvertor> searchByMarketNameAndCatalog(String marketName, short catalog) {
        Criteria cri = getSession().createCriteria(CatalogConvertor.class);
        cri.add(Restrictions.eq("marketName", marketName));
        cri.add(Restrictions.eq("catalog", catalog));
        List<CatalogConvertor> list = HibernateHelper.list(cri);
        return list;
    }

    @Override
    public long countForSearching(short catalog, String marketName, String keywords) {
        Criteria cri = this.searchByFilter(marketName, catalog, keywords);
        cri.setProjection(Projections.count("id"));
        return ((Long) cri.uniqueResult()).longValue();
    }

    @Override
    public List<CatalogConvertor> search(String marketName, short catalog, String keywords, int page, int rows,
            String sort, String order) {
        // 查询条件
        Criteria cri = searchByFilter(marketName, catalog, keywords);
        // 查询排序
        searchSort(sort, order, cri);
        cri.setMaxResults(rows);
        cri.setFirstResult(HibernateHelper.firstResult(page, rows));
        List<CatalogConvertor> list = HibernateHelper.list(cri);
        return list;
    }

    // 根据市场名称，在市场应用中获取该市场下的所有【类别】
    @Override
    public List<MarketCatalog> searchMarketCatalogs(String marketName) {
        return this.jdbcTemplate.query(this.marketCatalogSql, this.marketCatalogRowMapper, marketName);
    }

    // 查询排序
    private void searchSort(String sort, String order, Criteria cri) {
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            HibernateHelper.addOrder(cri, sort, order);
        } else {
            if (StringUtils.isNotBlank(order) && "asc".equals(order.toLowerCase())) {
                cri.addOrder(Order.asc("marketName"));
            } else {
                cri.addOrder(Order.desc("marketName"));
            }
        }
    }

    // 查询条件
    private Criteria searchByFilter(String marketName, short catalog, String keywords) {
        Criteria cri = getSession().createCriteria(CatalogConvertor.class);
        if (StringUtils.isNotBlank(marketName)) {
            cri.add(Restrictions.eq("marketName", marketName));
        }
        if (catalog > 0) {
            cri.add(Restrictions.eq("targetCatalog", catalog));
        }
        if (StringUtils.isNotBlank(keywords)) {
            cri.add(Restrictions.like("subCatalogName", keywords, MatchMode.ANYWHERE));
        }
        return cri;
    }

}
