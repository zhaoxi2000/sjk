package com.kingsoft.sjk.dao;

import java.util.List;

import com.kingsoft.sjk.po.AndroidApp;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.AppDetail;
import com.kingsoft.sjk.po.AppType;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.Topic;

public interface AppsDao {

    List<AppType> getAppTypes(final int parentId);

    /**
     * 不翻页查询
     * 
     * @param parentId
     * @param subTypeId
     * @return
     */
    List<AndroidApp> list(final Integer parentId, final Integer subTypeId);

    List<App> list(final int typeId, final int subTypeId, final int pageNumber, final int pageSize,
            final int orderByColumnId);

    List<App> recommend(final int parentId);

    /**
     * 获取应用信息
     * 
     * @param softid
     *            应用ID
     * @return
     */
    AppDetail getAppDetail(final int softid);

    List<App> findByIds(List<Integer> ids);

    List<ExtendData> getAppExtendDatas(final int softid);

    List<App> getPowerTuiJian(final int typeId, final int start, final int count);

    List<App> getPowerChannelTuiJian(final int typeId, final int start, final int count);

    List<App> getPowerTuiJianTopicApps(final int topicid, final int start, final int count);

    List<Topic> getPowerTuiJianTopics(final int status, final int start, final int count);

    Topic getPowerTuiJianTopic(final int topicid);

    Topic getPowerChannelTuiJianTopic(final int topicid);

    List<Topic> getPowerChannelTuiJianTopics(final int status, final int start, final int count);

    List<App> getPowerChannelTuiJianTopicApps(final int topicid, final int start, final int count);
}
