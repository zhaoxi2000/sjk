package com.kingsoft.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.kingsoft.sjk.dao.AppsDao;
import com.kingsoft.sjk.dao.StatDao;
import com.kingsoft.sjk.po.AndroidApp;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.AppDetail;
import com.kingsoft.sjk.po.AppType;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.Topic;
import com.kingsoft.sjk.service.AppsService;

@Service
public class AppsServiceImpl implements AppsService {
    private static final Logger logger = LoggerFactory.getLogger(AppsServiceImpl.class);

    @Resource(name = "appsDaoImpl")
    private AppsDao appsDao;

    @Resource(name = "statDaoImpl")
    private StatDao statDao;

    @Override
    public List<App> list(int typeId, int subTypeId, int pageNumber, int pageSize, int orderByColumnId) {
        Assert.isTrue(typeId > -1 && subTypeId > -1, "Illegal params!");
        Assert.isTrue(pageNumber > 0 && pageSize > 0, "Illegal params!");
        return appsDao.list(typeId, subTypeId, pageNumber, pageSize, orderByColumnId);
    }

    @Override
    public List<AppType> getAppTypes(int parentId) {
        Assert.isTrue(parentId > -1, "Illegal params!");
        return appsDao.getAppTypes(parentId);
    }

    @Override
    public List<App> recommend(int parentId) {
        Assert.isTrue(parentId > -1, "Illegal params!");
        return appsDao.recommend(parentId);
    }

    @Override
    public AppDetail getAppDetail(int softid) {
        Assert.isTrue(softid > -1, "Illegal params!");
        return appsDao.getAppDetail(softid);
    }

    @Override
    public List<AndroidApp> list(Integer parentId, Integer subTypeId) {
        if (parentId != null) {
            Assert.isTrue(parentId > -1, "Illegal params!");
        }
        if (subTypeId != null) {
            Assert.isTrue(subTypeId > -1, "Illegal params!");
        }
        return appsDao.list(parentId, subTypeId);
    }

    @Override
    public List<App> findByIds(List<Integer> ids) {
        Assert.isTrue(ids != null && !ids.isEmpty(), "Illegal params!");
        return appsDao.findByIds(ids);
    }

    @Override
    public List<ExtendData> getAppExtendDatas(int softid) {
        Assert.isTrue(softid > -1, "Illegal params!");
        return appsDao.getAppExtendDatas(softid);
    }

    @Override
    public List<App> getPowerTuiJian(int typeId, int start, int count) {
        Assert.isTrue(typeId > -1, "Illegal params!");
        Assert.isTrue(start > -1, "Illegal params!start!");
        return appsDao.getPowerTuiJian(typeId, start, count);
    }

    @Override
    public List<App> getPowerChannelTuiJian(int typeId, int start, int count) {
        Assert.isTrue(typeId > -1, "Illegal params!");
        Assert.isTrue(start > -1, "Illegal params!start!");
        return appsDao.getPowerChannelTuiJian(typeId, start, count);
    }

    @Override
    public boolean gatherStat(int appId, int opKind) {
        Assert.isTrue(appId > -1, "Illegal params!");
        if (opKind == 1) {
            return statDao.gatherDownloadStat(appId);

        } else if (opKind == 2) {
            return statDao.gatherClickStat(appId);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Topic getPowerTuiJianTopic(int topicid) {
        Assert.isTrue(topicid > -1, "Illegal params!topicid!");
        return appsDao.getPowerTuiJianTopic(topicid);
    }

    @Override
    public List<Topic> getPowerTuiJianTopics(int status, int start, int count) {
        Assert.isTrue(status > -1, "Illegal params!status!");
        Assert.isTrue(start > -1, "Illegal params!start!");
        Assert.isTrue(count > -1, "Illegal params!count!");
        return appsDao.getPowerTuiJianTopics(status, start, count);
    }

    @Override
    public List<App> getPowerTuiJianTopicApps(int topicid, int start, int count) {
        Assert.isTrue(topicid > -1, "Illegal params!topicid!");
        Assert.isTrue(start > -1, "Illegal params!start!");
        Assert.isTrue(count > -1, "Illegal params!count!");
        return appsDao.getPowerTuiJianTopicApps(topicid, start, count);
    }

    @Override
    public Topic getPowerChannelTuiJianTopic(int topicid) {
        Assert.isTrue(topicid > -1, "Illegal params!topicid!");
        return appsDao.getPowerChannelTuiJianTopic(topicid);
    }

    @Override
    public List<Topic> getPowerChannelTuiJianTopics(int status, int start, int count) {
        Assert.isTrue(status > -1, "Illegal params!status!");
        Assert.isTrue(start > -1, "Illegal params!start!");
        Assert.isTrue(count > -1, "Illegal params!count!");
        return appsDao.getPowerChannelTuiJianTopics(status, start, count);
    }

    @Override
    public List<App> getPowerChannelTuiJianTopicApps(int topicid, int start, int count) {
        Assert.isTrue(topicid > -1, "Illegal params!topicid!");
        Assert.isTrue(start > -1, "Illegal params!start!");
        Assert.isTrue(count > -1, "Illegal params!count!");
        return appsDao.getPowerChannelTuiJianTopicApps(topicid, start, count);
    }

}
