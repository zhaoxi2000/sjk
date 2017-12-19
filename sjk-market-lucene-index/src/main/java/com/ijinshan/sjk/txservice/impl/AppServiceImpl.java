package com.ijinshan.sjk.txservice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.txservice.AppService;

@Service
public class AppServiceImpl implements AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessions;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    public AppServiceImpl() {
    }

    @Override
    public long count() {
        return appDao.count();
    }

    @Override
    public List<App> getApps(int currentPage, int pageSize) {
        return appDao.getApps(currentPage, pageSize);
    }

    @Override
    public List<App> getApps(List<Integer> appIds) {
        Assert.notEmpty(appIds, "appIds must not be null.");
        return appDao.getApps(appIds);
    }

}
