package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ijinshan.sjk.po.AppAndTag;

public interface TagAppMapper {
    public List<AppAndTag> getTags(@Param(value = "tagId") int tagId, @Param(value = "catalog") int catalog);

    public List<com.ijinshan.sjk.vo.pc.abstracttag.TagApps> getApps4Topic(@Param(value = "tagId") int tagId);
}
