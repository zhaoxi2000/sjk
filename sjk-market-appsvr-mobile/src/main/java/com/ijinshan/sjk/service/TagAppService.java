package com.ijinshan.sjk.service;

import java.util.List;
import java.util.Set;

import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.vo.AppTopic;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.TagMobileVo;
import com.ijinshan.sjk.vo.mobile.MoTagVo;
import com.ijinshan.sjk.vo.mobile.TagListVo;
import com.ijinshan.util.Pager;

public interface TagAppService {
    List<AppAndTag> getTags(int tagId, short catalog);

    Set<AppTopic> getAppTopic(int tagId, short catalog);

    Set<AppTopic> getMobileAppTopic(int tagId, int page, int rows);

    List<MobileSearchApp> getTopicList(int tagId);

    Pager<MobileSearchApp> getMobileAppTopicList(int page, int rows);

    Pager<MobileSearchApp> getMobileTagList(int tagId, int page, int rows);

    TagMobileVo getTagByTagId(int tagId);

    Pager<TagListVo> getTagListPage(Integer tagType, Integer page, Integer rows);

    long getMobileAppTopic();
}
