package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.MonMarketAppDao;
import com.ijinshan.sjk.po.MonApp;
import com.ijinshan.sjk.po.MonChannelApp;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MonMarketAppDaoImpl implements MonMarketAppDao {
    private static final Logger logger = LoggerFactory.getLogger(MonMarketAppDaoImpl.class);
    @Resource(name = "sessionFactory")
    protected SessionFactory sessions;

    public Session getSession() {
        return sessions.getCurrentSession();
    }

    @Override
    public List<MonApp> queryCoverApp(String marketName) {
        List<MonApp> list = new ArrayList<MonApp>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT");
        sbSql.append("  new.id,new.MarketAppId,");
        sbSql.append("  new.MarketName as NewMarketName, ");
        sbSql.append("  old.MarketName,");
        sbSql.append("  old.ApkId,");
        sbSql.append("  old.`Name`,");
        sbSql.append("  new.NAME AS NewName,");
        sbSql.append("  new.version as Newversion,");
        sbSql.append("  old.version,");
        sbSql.append("  new.versionCode as NewVersionCode,");
        sbSql.append("  old.versionCode,");
        sbSql.append("  old.pkname,");
        sbSql.append("  old.downloadUrl,");
        sbSql.append("  old.SignatureSHA1,");
        sbSql.append("  old.MarketApkScanTime,");
        sbSql.append("  new.LastFetchTime AS AppFetchTime ");
        sbSql.append(" FROM ");
        sbSql.append("      MarketApp old LEFT JOIN  App new ");
        sbSql.append("    ON old .Pkname = new.Pkname AND new.SignatureSHA1 = old .SignatureSHA1  AND new.MarketName<> old.MarketName ");
        sbSql.append(" WHERE  ");
        sbSql.append(" new.versionCode > old .versionCode");
        if (StringUtils.isNotEmpty(marketName)) {
            sbSql.append(" AND old.MarketName =:marketName");
        }
        logger.debug(sbSql.toString());
        Session sessionQuery = this.sessions.openSession();
        Query q = sessionQuery.createSQLQuery(sbSql.toString()).addEntity(MonApp.class);
        if (StringUtils.isNotEmpty(marketName)) {
            q.setString("marketName", marketName);
        }
        list = HibernateHelper.list(q);
        sessionQuery.close();
        return list;
    }

    @Override
    public List<MonChannelApp> queryChannelApp(String marketName) {
        List<MonChannelApp> list = new ArrayList<MonChannelApp>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT");
        sbSql.append("  id,");
        sbSql.append("  version,");
        sbSql.append("  AppId,");
        sbSql.append("  ApkId,");
        sbSql.append("  MarketName,");
        sbSql.append("  NAME,");
        sbSql.append("  DownloadUrl,");
        sbSql.append("  publisherShortName,");
        sbSql.append("  Minsdkversion,");
        sbSql.append("  VersionCode,");
        sbSql.append("  Pkname,");
        sbSql.append("  LastUpdateTime,");
        sbSql.append("  MarketUpdateTime,");
        sbSql.append("  MarketApkScanTime,");
        sbSql.append("  AppFetchTime");
        sbSql.append(" FROM ");
        sbSql.append("  MarketApp ");
        sbSql.append(" WHERE ");
        if (StringUtils.isNotEmpty(marketName)) {
            sbSql.append("   MarketName =:marketName ");
        }
        sbSql.append(" ORDER BY");
        sbSql.append("  ApkId ");
        Session sessionQuery = this.sessions.openSession();
        Query q = sessionQuery.createSQLQuery(sbSql.toString()).addEntity(MonChannelApp.class);
        if (StringUtils.isNotEmpty(marketName)) {
            q.setString("marketName", marketName);
        }
        list = HibernateHelper.list(q);
        sessionQuery.close();
        return list;
    }
}
