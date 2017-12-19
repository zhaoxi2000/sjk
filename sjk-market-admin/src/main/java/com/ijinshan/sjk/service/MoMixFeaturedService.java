package com.ijinshan.sjk.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.MoMixFeatured;

public interface MoMixFeaturedService {
    /**
     * 保存和修改
     */
    void saveOrUpdate(MultipartHttpServletRequest multipartReq, MoMixFeatured t);

    /**
     * 查询接口
     */
    List<MoMixFeatured> search(String type, Boolean hidden);

    /**
     * 查询接口统计记录条目
     */
    long count(String type);

    /** 分页统计记录条目 */
    long countForSearching(Short type, Short picType, Boolean hidden, String keywords);

    /** 分页查询搜索 */
    List<MoMixFeatured> search(Short type, Short picType, Boolean hidden, String keywords, int page, int rows,
            String sort, String order);

    /** 刪除 */
    boolean delete(int appId);

    /** 隱藏 */
    boolean updateHide(List<Integer> appIds);

    /** 显示 */
    boolean updateShow(List<Integer> appIds);

    /** 修改排序 */
    boolean updateSort(Integer[] ids, Integer[] ranks);
}
