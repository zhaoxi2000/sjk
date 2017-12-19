package com.ijinshan.sjk.service;

import java.util.List;
import java.util.Set;

import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.vo.AppTopic;
import com.ijinshan.sjk.vo.pc.abstracttag.TagApps;

public interface TagAppService {
    List<AppAndTag> getTags(int tagId, short catalog);

    Set<AppTopic> getAppTopic(int tagId, short catalog);

    /**
     * 新版接口.
     * 
     * @param tagId
     * @return
     */
    List<TagApps> getApps4Topic(int tagId);
}
