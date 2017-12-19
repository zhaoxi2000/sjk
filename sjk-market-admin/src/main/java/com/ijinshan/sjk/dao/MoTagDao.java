package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.po.MoTag;
import com.ijinshan.sjk.po.marketmerge.MoTagTopic;

public interface MoTagDao extends BaseDao<MoTag> {
    List<MoTag> list(TagType tagType);

    int deleteByIds(List<Integer> ids);

    // 分页统计记录条目
    long countForSearching(Short pId, Short tagType, String keywords);

    // 分页查询搜索
    List<MoTag> search(Short pId, String keywords, int page, int rows, String sort, String order);

    // List<MTag> listTopic(Integer pid, String keywords);
    // 分页查询搜索
    List<MoTagTopic> searchTagList(Short pid, Short tagType, String keywords, int page, int rows, String sort,
            String order);
}
