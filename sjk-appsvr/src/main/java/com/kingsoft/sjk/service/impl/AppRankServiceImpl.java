package com.kingsoft.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.kingsoft.sjk.dao.AppRankDao;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.service.AppRankService;

@Service
public class AppRankServiceImpl implements AppRankService {

    @Resource(name = "appRankDaoImpl")
    private AppRankDao dao;

    @Override
    public List<App> getAppCategoryRank(int parentId, int subCatalog, int top) {
        Assert.isTrue(parentId > -1 && subCatalog > -1, "Illegal params!");
        return dao.getAppCategoryRank(parentId, subCatalog, top);
    }

    @Override
    public List<App> getAppDefultRank(int typeId, int top) {
        Assert.isTrue(typeId > -1, "Illegal params!");
        return dao.getAppDefultRank(typeId, top);
    }

}
