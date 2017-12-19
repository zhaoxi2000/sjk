package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.po.marketmerge.TagTopic;

public interface TagDao extends BaseDao<Tag> {
    List<Tag> list(TagType tagType);

    int deleteByIds(List<Integer> ids);

    List<TagTopic> searchTopicList(Integer pid, String keywords, int page, int rows, String sort, String order);

    long countForSearching(Integer pid, TagType tagType, String keywords);
}
