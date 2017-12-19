package com.kingsoft.sjk.dao.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.dao.StatDao;
import com.kingsoft.sjk.util.SqlReader;

@Repository
public class StatDaoImpl implements StatDao {
    private static final Logger logger = LoggerFactory.getLogger(StatDaoImpl.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private final String download;
    private final String click;

    public StatDaoImpl() {
        logger.info("Loading SQL files...\t{}", StatDaoImpl.class);
        String[] tmps = new String[3];
        try {
            tmps[0] = SqlReader.getSqlFileToString("/sql-file/app-types.sql");
            tmps[1] = SqlReader.getSqlFileToString("/sql-file/apps-filter-types.sql");

            logger.info("Load SQL files, DONE!\t{}", StatDaoImpl.class);
        } catch (IOException e) {
            logger.error("Exception!!!Fatal Error!!!", e);
        }

        download = tmps[0];
        click = tmps[1];
        tmps = null;
    }

    @Override
    public boolean gatherDownloadStat(int appId) {
        return false;
    }

    @Override
    public boolean gatherClickStat(int appId) {
        return false;
    }

}
