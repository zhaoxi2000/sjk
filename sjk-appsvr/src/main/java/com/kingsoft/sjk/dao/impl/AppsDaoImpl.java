package com.kingsoft.sjk.dao.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.config.ChangeOutputImpl;
import com.kingsoft.sjk.dao.AppsDao;
import com.kingsoft.sjk.po.AndroidApp;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.AppDetail;
import com.kingsoft.sjk.po.AppType;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.ScreenImage;
import com.kingsoft.sjk.po.Topic;
import com.kingsoft.sjk.util.HibernateHelper;
import com.kingsoft.sjk.util.SqlReader;

@Repository
public class AppsDaoImpl implements AppsDao {
    private static final Logger logger = LoggerFactory.getLogger(AppsDaoImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "changeOutputImpl")
    private ChangeOutputImpl changeOutputImpl;

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "namedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource(name = "sessionFactory")
    private SessionFactory sessions;

    private final String appTypes;
    private final String apps;
    private final String recommendApps;
    private final String appDetail;
    private final String appScreenImage;
    private final String appExtendData;
    private final String appsByIds;
    private final String appsPowerTuiJian;
    private final String appsPowerChannelTuiJian;
    private final String topicPowerTuiJian;
    private final String topicsPowerTuiJian;
    private final String appsPowerTuiJianTopic;
    private final String topicPowerChannelTuiJian;
    private final String topicsPowerChannelTuiJian;
    private final String appsPowerChannelTuiJianTopic;

    public final BeanPropertyRowMapper<AppType> appTypeRowMapper = BeanPropertyRowMapper.newInstance(AppType.class);
    public final BeanPropertyRowMapper<App> appsRowMapper = BeanPropertyRowMapper.newInstance(App.class);
    public final BeanPropertyRowMapper<AppDetail> appDetailRowMapper = BeanPropertyRowMapper
            .newInstance(AppDetail.class);
    public final BeanPropertyRowMapper<ScreenImage> screenImageRowMapper = BeanPropertyRowMapper
            .newInstance(ScreenImage.class);
    public final BeanPropertyRowMapper<ExtendData> extendDataRowMapper = BeanPropertyRowMapper
            .newInstance(ExtendData.class);

    public final BeanPropertyRowMapper<Topic> topicRowMapper = BeanPropertyRowMapper.newInstance(Topic.class);

    public AppsDaoImpl() {
        logger.info("Loading SQL files...\t{}", AppsDaoImpl.class);
        String[] tmps = new String[16];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/app-types.sql");
            tmps[1] = SqlReader.getSqlFileToString("/sql-file/apps-filter-types.sql");
            tmps[2] = SqlReader.getSqlFileToString("/sql-file/recommend-apps.sql");
            tmps[3] = SqlReader.getSqlFileToString("/sql-file/app-detail.sql");
            tmps[4] = SqlReader.getSqlFileToString("/sql-file/app-detail-screen-image.sql");
            tmps[5] = SqlReader.getSqlFileToString("/sql-file/app-detail-extend-data.sql");
            tmps[6] = SqlReader.getSqlFileToString("/sql-file/apps-by-ids.sql");

            tmps[7] = SqlReader.getSqlFileToString("/sql-file/apps-power-tuijian.sql");
            tmps[8] = SqlReader.getSqlFileToString("/sql-file/apps-power-channel-tuijian.sql");
            tmps[9] = SqlReader.getSqlFileToString("/sql-file/topic-power-tuijian.sql");
            tmps[10] = SqlReader.getSqlFileToString("/sql-file/topics-power-tuijian.sql");
            tmps[11] = SqlReader.getSqlFileToString("/sql-file/apps-power-tuijian-topic.sql");
            tmps[12] = SqlReader.getSqlFileToString("/sql-file/topic-power-channel-tuijian.sql");
            tmps[13] = SqlReader.getSqlFileToString("/sql-file/topics-power-channel-tuijian.sql");
            tmps[14] = SqlReader.getSqlFileToString("/sql-file/apps-power-channel-tuijian-topic.sql");
            logger.info("Load SQL files, DONE!\t{}", AppsDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        appTypes = tmps[0];
        apps = tmps[1];
        recommendApps = tmps[2];
        appDetail = tmps[3];
        appScreenImage = tmps[4];
        appExtendData = tmps[5];
        appsByIds = tmps[6];

        appsPowerTuiJian = tmps[7];
        appsPowerChannelTuiJian = tmps[8];
        topicPowerTuiJian = tmps[9];
        topicsPowerTuiJian = tmps[10];
        appsPowerTuiJianTopic = tmps[11];

        topicPowerChannelTuiJian = tmps[12];
        topicsPowerChannelTuiJian = tmps[13];
        appsPowerChannelTuiJianTopic = tmps[14];
        tmps = null;
    }

    @Override
    public List<AppType> getAppTypes(final int parentId) {
        PreparedStatementCallback<List<AppType>> cb = new PreparedStatementCallback<List<AppType>>() {
            @Override
            public List<AppType> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, parentId);
                    ps.setInt(2, parentId);
                    rs = ps.executeQuery();
                    // predicate count.
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<AppType> list = new ArrayList<AppType>(count);
                        rs.beforeFirst();
                        AppType appType = null;
                        while (rs.next()) {
                            appType = appTypeRowMapper.mapRow(rs, rs.getRow());
                            list.add(appType);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }
        };
        return this.jdbcTemplate.execute(appTypes, cb);
    }

    @Override
    public List<App> list(final int typeId, final int subTypeId, final int pageNumber, final int pageSize,
            int orderByColumnId) {
        final int offset = (pageNumber - 1) * pageSize;
        StringBuilder realSql = new StringBuilder(apps.length() + 100);
        realSql.append(apps);
        realSql.append(" ORDER BY UpdateDate DESC");
        realSql.append(" LIMIT ").append(offset).append(", ").append(pageSize);

        Map<String, Object> params = new HashMap<String, Object>(5 * 4 / 3 + 1);
        params.put("typeId", Integer.valueOf(typeId));
        params.put("subTypeId", Integer.valueOf(subTypeId));

        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appsRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }
        };
        return this.namedParameterJdbcTemplate.execute(realSql.toString(), params, cb);
    }

    @Override
    public List<App> recommend(final int parentId) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, parentId);
            }
        };
        return this.jdbcTemplate.query(recommendApps, pss, appsRowMapper);
    }

    @Override
    public AppDetail getAppDetail(final int softid) {
        PreparedStatementCallback<AppDetail> cb = new PreparedStatementCallback<AppDetail>() {
            @Override
            public AppDetail doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, softid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        return null;
                    }
                    AppDetail app = appDetailRowMapper.mapRow(rs, 1);
                    changeOutputImpl.setUrls(app);
                    return app;
                } finally {
                    if (null != rs)
                        rs.close();
                }
            }
        };
        AppDetail app = this.jdbcTemplate.execute(appDetail, cb);
        List<ScreenImage> list = getAppScreenImages(softid);
        app.setScreenImages(list);
        app.setExtendDataList(getAppExtendDatas(softid));
        return app;
    }

    /**
     * 根据软件编号获取截图数据
     * 
     * @param softid
     *            软件编号
     * @return
     */
    private List<ScreenImage> getAppScreenImages(final int softid) {
        PreparedStatementCallback<List<ScreenImage>> cb = new PreparedStatementCallback<List<ScreenImage>>() {
            @Override
            public List<ScreenImage> doInPreparedStatement(PreparedStatement ps) throws SQLException,
                    DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, softid);
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<ScreenImage> list = new ArrayList<ScreenImage>(count);
                        rs.beforeFirst();
                        ScreenImage appScreenImage = null;
                        while (rs.next()) {
                            appScreenImage = screenImageRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setAppScreenUrl(appScreenImage);
                            list.add(appScreenImage);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs)
                        rs.close();
                }
            }
        };
        return this.jdbcTemplate.execute(appScreenImage, cb);
    }

    /**
     * 根据软件编号数据包（主要是游戏数据包）
     * 
     * @param softid
     *            软件编号
     * @return
     */
    @Override
    public List<ExtendData> getAppExtendDatas(final int softid) {
        String sql = new StringBuilder(appExtendData).append(" '%,").append(softid).append(",%'").toString();
        PreparedStatementCallback<List<ExtendData>> cb = new PreparedStatementCallback<List<ExtendData>>() {
            @Override
            public List<ExtendData> doInPreparedStatement(PreparedStatement ps) throws SQLException,
                    DataAccessException {
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<ExtendData> list = new ArrayList<ExtendData>(count);
                        rs.beforeFirst();
                        ExtendData appExtendData = null;
                        while (rs.next()) {
                            appExtendData = extendDataRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setAppExtendDataUrl(appExtendData);
                            list.add(appExtendData);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs)
                        rs.close();
                }
            }
        };
        return this.jdbcTemplate.execute(sql, cb);
    }

    @Override
    public List<AndroidApp> list(Integer parentId, Integer subTypeId) {
        Session session = sessions.getCurrentSession();
        Query query = null;
        StringBuilder realHQL = new StringBuilder("from AndroidApp a");
        if (parentId != null && subTypeId != null) {
            realHQL.append(" where a.catalog = :catalog and a.subCatalog = :subCatalog");
            query = session.createQuery(realHQL.toString());
            query.setInteger("catalog", parentId);
            query.setInteger("subCatalog", subTypeId);
        } else if (parentId != null) {
            realHQL.append(" where a.catalog = :catalog");
            query = session.createQuery(realHQL.toString());
            query.setInteger("catalog", parentId);
        } else if (subTypeId != null) {
            realHQL.append(" where a.subCatalog = :subCatalog");
            query = session.createQuery(realHQL.toString());
            query.setInteger("subCatalog", subTypeId);
        } else {
            query = session.createQuery(realHQL.toString());
        }
        List<AndroidApp> list = HibernateHelper.list(query);
        return list;
    }

    @Override
    public List<App> findByIds(List<Integer> ids) {
        // Session session = sessions.getCurrentSession();
        // Query query = null;
        // query =
        // session.createQuery("select a.id, a.name, a.downLoadLink, a.logo from App a where id in (:ids)").setParameterList("ids",
        // ids);
        // List<App> list = HibernateHelper.list(query);
        // for (App app : list) {
        // changeOutputImpl.setUrls(app);
        // }
        StringBuilder tmp = new StringBuilder(ids.size() * 6);
        for (Integer id : ids) {
            tmp.append(id).append(',');
        }
        tmp.deleteCharAt(tmp.length() - 1);
        String sql = appsByIds.replace("?", tmp.toString());

        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appsRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }

        };
        return this.jdbcTemplate.execute(sql, cb);
    }

    @Override
    public List<App> getPowerTuiJian(final int typeId, final int start, final int count) {
        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, typeId);
                    ps.setInt(2, start);
                    ps.setInt(3, count);
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appsRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }
        };
        return this.jdbcTemplate.execute(appsPowerTuiJian, cb);
    }

    @Override
    public List<App> getPowerChannelTuiJian(final int typeId, final int start, final int count) {
        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, typeId);
                    ps.setInt(2, start);
                    ps.setInt(3, count);
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appsRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }
        };
        return this.jdbcTemplate.execute(appsPowerChannelTuiJian, cb);
    }

    @Override
    public List<Topic> getPowerTuiJianTopics(final int status, final int start, final int count) {

        PreparedStatementCallback<List<Topic>> cb = new PreparedStatementCallback<List<Topic>>() {

            @Override
            public List<Topic> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, status);
                    ps.setInt(2, start);
                    ps.setInt(3, count);
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<Topic> list = new ArrayList<Topic>(count);
                        rs.beforeFirst();
                        Topic topic = null;
                        while (rs.next()) {
                            topic = topicRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(topic);
                            list.add(topic);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }

        };

        return this.jdbcTemplate.execute(topicsPowerTuiJian, cb);
    }

    @Override
    public Topic getPowerTuiJianTopic(final int topicid) {
        PreparedStatementCallback<Topic> cb = new PreparedStatementCallback<Topic>() {
            @Override
            public Topic doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, topicid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        return null;
                    }
                    Topic topic = topicRowMapper.mapRow(rs, 1);
                    changeOutputImpl.setUrls(topic);
                    return topic;
                } finally {
                    if (null != rs)
                        rs.close();
                }
            }
        };
        return jdbcTemplate.execute(topicPowerTuiJian, cb);
    }

    @Override
    public List<App> getPowerTuiJianTopicApps(final int topicid, final int start, final int count) {

        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, topicid);
                    ps.setInt(2, start);
                    ps.setInt(3, count);
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appsRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }
        };
        return this.jdbcTemplate.execute(appsPowerTuiJianTopic, cb);

    }

    @Override
    public Topic getPowerChannelTuiJianTopic(final int topicid) {
        PreparedStatementCallback<Topic> cb = new PreparedStatementCallback<Topic>() {
            @Override
            public Topic doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, topicid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        return null;
                    }
                    Topic topic = topicRowMapper.mapRow(rs, 1);
                    changeOutputImpl.setUrls(topic);
                    return topic;
                } finally {
                    if (null != rs)
                        rs.close();
                }
            }
        };
        return jdbcTemplate.execute(topicPowerChannelTuiJian, cb);
    }

    @Override
    public List<Topic> getPowerChannelTuiJianTopics(final int status, final int start, final int count) {

        PreparedStatementCallback<List<Topic>> cb = new PreparedStatementCallback<List<Topic>>() {

            @Override
            public List<Topic> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, status);
                    ps.setInt(2, start);
                    ps.setInt(3, count);
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<Topic> list = new ArrayList<Topic>(count);
                        rs.beforeFirst();
                        Topic topic = null;
                        while (rs.next()) {
                            topic = topicRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(topic);
                            list.add(topic);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }

        };

        return this.jdbcTemplate.execute(topicsPowerChannelTuiJian, cb);
    }

    @Override
    public List<App> getPowerChannelTuiJianTopicApps(final int topicid, final int start, final int count) {

        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    ps.setInt(1, topicid);
                    ps.setInt(2, start);
                    ps.setInt(3, count);
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appsRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    } else {
                        return null;
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            }
        };
        return this.jdbcTemplate.execute(appsPowerChannelTuiJianTopic, cb);

    }
}
