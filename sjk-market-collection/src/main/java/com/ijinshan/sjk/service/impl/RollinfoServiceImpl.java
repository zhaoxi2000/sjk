package com.ijinshan.sjk.service.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.RollinfoDao;
import com.ijinshan.sjk.service.RollinfoService;

@Service
public class RollinfoServiceImpl implements RollinfoService {
    private static final Logger logger = LoggerFactory.getLogger(RollinfoServiceImpl.class);
    @Resource(name = "rollinfoDaoImpl")
    private RollinfoDao dao;

    @Override
    public int deleteByAppId(Session session, int appId) {
        return dao.deleteByAppId(session, appId);
    }

    @Override
    public int deleteByAppId(int appId) {
        return dao.deleteByAppId(appId);
    }
}
