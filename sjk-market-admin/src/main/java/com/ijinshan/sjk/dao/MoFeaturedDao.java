package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.MoFeatured;

public interface MoFeaturedDao extends BaseDao<MoFeatured> {

    List<MoFeatured> search(String type, Boolean hidden, Boolean deleted);

    long count(String type);

    // 分页统计记录条目
    long countForSearching(Short type, Boolean hidden, Boolean deleted, String keywords);

    // 分页查询搜索
    List<MoFeatured> search(Short type, Boolean hidden, Boolean deleted, String keywords, int page, int rows,
            String sort, String order);

    int delete(int appId);

    int updateHide(List<Integer> appIds);

    int updateShow(List<Integer> appIds);

    /* 修改排序 */
    boolean updateSort(int id, int rank);

    /*
     * 逻辑删除操作， deleted=true 删除操作 deleted=false 还原操作
     */
    Boolean updateDeleted(List<Integer> ids, boolean deleted);

}
