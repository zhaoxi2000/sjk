package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.MoMixFeatured;

public interface MoMixFeaturedDao extends BaseDao<MoMixFeatured> {
    List<MoMixFeatured> search(String type, Boolean hidden);

    long count(String type);

    /** 分页统计记录条目 */
    long countForSearching(Short type, Short picType, Boolean hidden, String keywords);

    /** 分页查询搜索 */
    List<MoMixFeatured> search(Short type, Short picType, Boolean hidden, String keywords, int page, int rows,
            String sort, String order);

    /** 刪除 */
    int delete(int id);

    /** 隱藏 */
    int updateHide(List<Integer> ids);

    /** 显示 */
    int updateShow(List<Integer> ids);

    /** 修改排序 */
    boolean updateSort(int id, int rank);
}
