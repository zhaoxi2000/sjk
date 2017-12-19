package com.ijinshan.sjk.dao.impl;

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

import com.ijinshan.sjk.dao.MetroDao;
import com.ijinshan.sjk.po.Metro;
import com.ijinshan.sjk.vo.MetroVO;
import com.ijinshan.util.SqlReader;

@Repository
public class MetroDaoImpl extends AbstractBaseDao<Metro> implements MetroDao {
    private static final Logger logger = LoggerFactory.getLogger(MetroDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public final BeanPropertyRowMapper<MetroVO> metroVORowMapper = BeanPropertyRowMapper.newInstance(MetroVO.class);
    public final String metroVOSql;

    public MetroDaoImpl() {
        logger.info("Loading SQL files...\t{}", MetroDaoImpl.class);
        String[] tmps = new String[10];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/metro.sql");
            logger.info("Load SQL files, DONE!\t{}", MetroDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }
        metroVOSql = tmps[0];
        tmps = null;
    }

    @Override
    public Class<Metro> getType() {
        return Metro.class;
    }

    @Override
    public List<MetroVO> list() {
        PreparedStatementCallback<List<MetroVO>> cb = new PreparedStatementCallback<List<MetroVO>>() {
            @Override
            public List<MetroVO> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    // predicate count.
                    if (rs.last()) {
                        int count = rs.getRow();
                        List<MetroVO> list = new ArrayList<MetroVO>(count);
                        rs.beforeFirst();
                        MetroVO e = null;
                        while (rs.next()) {
                            e = metroVORowMapper.mapRow(rs, rs.getRow());
                            String[] picArray = null;
                            String pics = rs.getString("pics");
                            if (pics != null && !pics.isEmpty()) {
                                picArray = pics.split(",");
                                if (picArray != null) {
                                    e.setPic(picArray[0]);
                                    if (picArray.length > 1) {
                                        e.setPic1(picArray[1]);
                                    }
                                }
                            }
                            list.add(e);
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
        return this.jdbcTemplate.execute(metroVOSql, cb);
    }
}
