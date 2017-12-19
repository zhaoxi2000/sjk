package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.ViewTagApps;

public interface AppAndTagDao extends BaseDao<AppAndTag> {

    /* 分页返回行数 */
    long countForSearching(Integer tagId, Integer catalog, Short tagType, String keywords);

    long countAppAndTag(int tagId);

    /* 分页查询 */
    List<ViewTagApps> search(Integer tagId, Integer catalog, Short tagType, int page, int rows, String keywords,
            String sort, String order);

    List<AppAndTag> listTagByApp(List<Integer> appIds);

    int deleteByAppId(Session session, int appId);

    int deleteByAppIdAndTagType(List<Integer> appIds, short tagTypeList);

    /* 修改排序 */
    boolean updateSort(int id, int rank);

    AppAndTag getAppTag(Integer appId, Integer tagId);
}
