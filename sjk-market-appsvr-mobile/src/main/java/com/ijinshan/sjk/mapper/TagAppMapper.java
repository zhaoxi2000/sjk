package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.MoAppAndTag;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.TagMobileVo;
import com.ijinshan.sjk.vo.mobile.MoTagVo;
import com.ijinshan.sjk.vo.mobile.TagListVo;

public interface TagAppMapper {
    public List<AppAndTag> getTags(@Param(value = "tagId") int tagId, @Param(value = "catalog") int catalog);

    public List<MoAppAndTag> getTagsByTagId(@Param(value = "tagId") int tagId, @Param(value = "offset") int offset,
            @Param(value = "pageSize") int pageSize);

    public List<MobileSearchApp> getMobileAppTopicList(@Param(value = "offset") int offset,
            @Param(value = "pageSize") int pageSize);

    public List<MobileSearchApp> getMobileTagAppList(@Param(value = "tagId") int tagId,
            @Param(value = "offset") int offset, @Param(value = "pageSize") int pageSize);

    public TagMobileVo getTagByTagId(@Param(value = "tagId") int tagId);

    public List<MoAppAndTag> getTagsByTagIds(int tagId);

    public List<TagListVo> getTagListPage(@Param(value = "tagType") int tagType, @Param(value = "offset") int offset,
            @Param(value = "pageSize") int pageSize);

    public List<MoTagVo> getTagAllList(@Param(value = "tagType") int tagType, @Param(value = "offset") int offset,
            @Param(value = "pageSize") int pageSize);

    public long getCountMobileAppTopic();

    public long getCountTagListPage(@Param(value = "tagType") int tagType);

}
