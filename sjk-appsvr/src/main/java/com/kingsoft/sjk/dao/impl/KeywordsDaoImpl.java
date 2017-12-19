package com.kingsoft.sjk.dao.impl;

import java.io.IOException;
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

import com.kingsoft.sjk.dao.KeywordsDao;
import com.kingsoft.sjk.po.Keywords;
import com.kingsoft.sjk.util.SqlReader;

@Repository
public class KeywordsDaoImpl implements KeywordsDao {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsDaoImpl.class);

    public final String hotKeywords;
    public final BeanPropertyRowMapper<Keywords> keywordsRowMapper = BeanPropertyRowMapper.newInstance(Keywords.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public KeywordsDaoImpl() {
        logger.info("Loading SQL files...\t{}", KeywordsDaoImpl.class);
        String[] tmps = new String[3];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/hot-keywords.sql");
            logger.info("Load SQL files, DONE!\t{}", KeywordsDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        hotKeywords = tmps[0];
        tmps = null;
    }

    @Override
    public List<Keywords> list() {
        PreparedStatementCallback<List<Keywords>> cb = new PreparedStatementCallback<List<Keywords>>() {
            @Override
            public List<Keywords> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    // predicate count.
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<Keywords> list = new ArrayList<Keywords>(count);
                        rs.beforeFirst();
                        Keywords keywords = null;
                        while (rs.next()) {
                            keywords = keywordsRowMapper.mapRow(rs, rs.getRow());
                            list.add(keywords);
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
        return this.jdbcTemplate.execute(hotKeywords, cb);
    }
}
