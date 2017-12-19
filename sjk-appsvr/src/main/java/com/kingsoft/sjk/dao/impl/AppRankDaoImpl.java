package com.kingsoft.sjk.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.config.ChangeOutputImpl;
import com.kingsoft.sjk.dao.AppRankDao;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.util.SqlReader;

@Repository
public class AppRankDaoImpl implements AppRankDao {
    private static final Logger logger = LoggerFactory.getLogger(AppRankDaoImpl.class);
    private final BeanPropertyRowMapper<App> appRowMapper = BeanPropertyRowMapper.newInstance(App.class);
    private static final String QUERY_APP_DEFAULT_TOP_SQL;
    private static final String QUERY_APP_CATEGORY_TOP_SQL;

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "changeOutputImpl")
    private ChangeOutputImpl changeOutputImpl;

    static {
        String[] sqlTemps = new String[20];
        logger.info("Loading SQL files...\t{}", AppRankDaoImpl.class);
        try {
            sqlTemps[0] = SqlReader.getSqlFileToString("/sql-file/app-default-top.sql");
            sqlTemps[1] = SqlReader.getSqlFileToString("/sql-file/app-category-top.sql");
            logger.info("Load SQL files, DONE!\t{}", AppRankDaoImpl.class);
        } catch (Exception e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        QUERY_APP_DEFAULT_TOP_SQL = sqlTemps[0];
        QUERY_APP_CATEGORY_TOP_SQL = sqlTemps[1];

        sqlTemps = null;
    }

    @Override
    public List<App> getAppCategoryRank(final int parentId, final int subCatalog, final int top) {
        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, parentId);
                ps.setInt(2, subCatalog);
                ps.setInt(3, top);
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    }

                } catch (Exception e) {
                    logger.error("SQL data error:", e);
                    return null;
                } finally {
                    if (null != rs)
                        rs.close();
                }
                return null;
            }
        };
        return jdbcTemplate.execute(QUERY_APP_CATEGORY_TOP_SQL, cb);
    }

    @Override
    public List<App> getAppDefultRank(final int typeId, final int top) {
        PreparedStatementCallback<List<App>> cb = new PreparedStatementCallback<List<App>>() {
            @Override
            public List<App> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, typeId);
                ps.setInt(2, top);
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<App> list = new ArrayList<App>(count);
                        rs.beforeFirst();
                        App app = null;
                        while (rs.next()) {
                            app = appRowMapper.mapRow(rs, rs.getRow());
                            changeOutputImpl.setUrls(app);
                            list.add(app);
                        }
                        return list;
                    }

                } catch (Exception e) {
                    logger.error("SQL data error:", e);
                    return null;
                } finally {
                    if (null != rs)
                        rs.close();
                }
                return null;
            }
        };
        return jdbcTemplate.execute(QUERY_APP_DEFAULT_TOP_SQL, cb);
    }

}
