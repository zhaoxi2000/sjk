package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.MoAppAndTag;
import com.ijinshan.sjk.po.MoViewTagApps;

public interface MoAppAndTagDao extends BaseDao<MoAppAndTag> {

    long countForSearching(Integer tagId, Integer catalog, Short tagType, String keywords);

    public List<MoViewTagApps> search(Integer tagId, Integer catalog, Short tagType, int page, int rows,
            String keywords, String sort, String order);

    long countMoAppAndTag(int tagId);

    List<MoAppAndTag> listTagByApp(List<Integer> appIds);

    int deleteByAppId(Session session, int appId);

    int deleteByMoAppIdAndTagType(List<Integer> appIds, short tagTypeList);

    /* 修改排序 */
    boolean updateSort(int id, int rank);

    MoAppAndTag getMoAppTag(Integer appId, Integer tagId);
}
