package com.ijinshan.sjk.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.AppAndTagDao;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.TopAppDao;
import com.ijinshan.sjk.po.TopApp;
import com.ijinshan.sjk.service.TopAppService;

@Service
public class TopAppServiceImpl implements TopAppService {
    private static final Logger logger = LoggerFactory.getLogger(TopAppServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    @Resource(name = "appAndTagDaoImpl")
    private AppAndTagDao appTagDao;

    @Resource(name = "topAppDaoImpl")
    private TopAppDao topAppDao;

    @Override
    public List<TopApp> findTopAppList(String keywords, Integer id, int page, int rows) {

        return topAppDao.findTopAppList(keywords, id, page, rows);
    }

    @Override
    public List<TopApp> getAllTopAppList() {
        return topAppDao.getAllTopAppList();
    }

    @Override
    public boolean delete(List<Integer> ids) {
        return topAppDao.deleteByIds(ids);
    }

    @Override
    public TopApp get(int id) {
        return topAppDao.get(id);
    }

    @Override
    public void saveOrUpdate(TopApp topApp) {
        topAppDao.saveOrUpdate(topApp);
    }

    @Override
    public long getCounts() {
        return topAppDao.getCounts();
    }

    @Override
    public int updateState(List<Integer> ids,byte state) {
        
        return topAppDao.updateState(ids,state);
    }

}