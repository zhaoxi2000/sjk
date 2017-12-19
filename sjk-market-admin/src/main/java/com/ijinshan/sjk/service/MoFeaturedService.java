package com.ijinshan.sjk.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.MoFeatured;

public interface MoFeaturedService {

    void saveOrUpdate(MultipartHttpServletRequest multipartReq, MoFeatured moFeatured) throws MalformedURLException,
            URISyntaxException, IOException;

    List<MoFeatured> search(String type, Boolean hidden, Boolean deleted);

    // 分页统计记录条目
    long countForSearching(Short type, Boolean hidden, Boolean deleted, String keywords);

    // 分页查询搜索
    List<MoFeatured> search(Short type, Boolean hidden, Boolean deleted, String keywords, int page, int rows,
            String sort, String order);

    boolean delete(int appId, String type) throws UnsupportedOperationException;

    boolean updateHide(List<Integer> appIds, String type);

    boolean updateShow(List<Integer> appIds, String type);

    /*
     * 逻辑删除操作， deleted=true 删除操作 deleted=false 还原操作
     */
    boolean updateDeleted(List<Integer> ids, String type, boolean deleted);

    /* 修改排序 */
    boolean updateSort(Integer[] ids, Integer[] ranks);
}
