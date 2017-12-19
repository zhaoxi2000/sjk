package com.ijinshan.sjk.txservice;

import java.util.List;

import com.ijinshan.sjk.po.AppHistory4Index;

public interface AppHistory4IndexService {

    /**
     * 分页输入
     * 
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<AppHistory4Index> getApps(int currentPage, int pageSize);

    /**
     * 非分页分出
     * 
     * @return
     */
    List<AppHistory4Index> getApps();

    long count();

    /**
     * 更新这些数据状态为已经索引
     * 
     * @param appIds
     * @return TODO
     */
    int updateAppHistory4indeToIndexed(List<Integer> appIds);

    /**
     * 删除 指定id 的数据
     * 
     * @param appIds
     * @return TODO
     */
    int delAppHistory4index(List<Integer> appIds);
}
