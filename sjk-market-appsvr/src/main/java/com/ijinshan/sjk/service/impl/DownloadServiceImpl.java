package com.ijinshan.sjk.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.service.DownloadService;

@Service
public class DownloadServiceImpl implements DownloadService {
    private static final Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

    @Resource(name = "writeJdbcTemplate")
    private JdbcTemplate writeJdbcTemplate;

    @Override
    public int update(int id, int delta) {
        final String sql = new StringBuilder(200).append(" update App  set  realDownload = realDownload + ")
                .append(delta).append(" , downloadrank  =  realDownload  +  deltaDownload ").append(" where id = ")
                .append(id).toString();
        return this.writeJdbcTemplate.update(sql);
    }

}
