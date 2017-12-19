package com.kingsoft.sjk.service;

import java.util.List;

import com.kingsoft.sjk.po.AndroidApp;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.AppDetail;
import com.kingsoft.sjk.po.AppType;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.Topic;

public interface AppsService {

    /**
     * @param ids
     * @return
     */
    List<App> findByIds(List<Integer> ids);

    /**
     * 不翻页查询
     * 
     * @param parentId
     * @param subTypeId
     * @return
     */
    List<AndroidApp> list(Integer parentId, Integer subTypeId);

    List<App> list(int parentId, int subTypeId, int pageNumber, int pageSize, int orderByColumnId);

    List<AppType> getAppTypes(int parentId);

    List<App> recommend(int parentId);

    /**
     * 获取应用信息
     * 
     * @param softid
     *            应用ID
     * @return
     */
    AppDetail getAppDetail(final int softid);

    List<ExtendData> getAppExtendDatas(final int softid);

    List<App> getPowerTuiJian(int typeId, int start, int count);

    List<App> getPowerChannelTuiJian(int typeId, int start, int count);

    List<App> getPowerTuiJianTopicApps(int topicid, int start, int count);

    Topic getPowerTuiJianTopic(int topicid);

    List<Topic> getPowerTuiJianTopics(int status, int start, int count);

    boolean gatherStat(int appId, int opKind);

    Topic getPowerChannelTuiJianTopic(int topicid);

    List<App> getPowerChannelTuiJianTopicApps(int topicid, int start, int count);

    List<Topic> getPowerChannelTuiJianTopics(int status, int start, int count);
}
