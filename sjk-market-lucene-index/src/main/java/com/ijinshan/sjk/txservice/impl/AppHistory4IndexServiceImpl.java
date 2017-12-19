package com.ijinshan.sjk.txservice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.po.AppHistory4Index;
import com.ijinshan.sjk.txservice.AppHistory4IndexService;

@Service
public class AppHistory4IndexServiceImpl implements AppHistory4IndexService {

    @Resource(name = "appHistory4IndexDaoImpl")
    private AppHistory4IndexDao appHistory4IndexDao;

    @Override
    public List<AppHistory4Index> getApps(int currentPage, int pageSize) {
        return appHistory4IndexDao.getApps(currentPage, pageSize);
    }

    @Override
    public List<AppHistory4Index> getApps() {
        return appHistory4IndexDao.getApps();
    }

    @Override
    public long count() {
        return appHistory4IndexDao.count();
    }

    @Override
    public int updateAppHistory4indeToIndexed(List<Integer> appIds) {
        if (CollectionUtils.isEmpty(appIds)) {
            return 0;
        }
        return appHistory4IndexDao.updateAppHistory4indeToIndexed(appIds);
    }

    @Override
    public int delAppHistory4index(List<Integer> appIds) {
        if (CollectionUtils.isEmpty(appIds)) {
            return 0;
        }
        return appHistory4IndexDao.delAppHistory4index(appIds);
    }

}
