package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.AppHistory4Index;

public interface AppHistory4IndexDao {
    /**
     * 更新或者 新增加 数据到 AppHistory4Index表中
     * 
     * @param appidx
     */
    void saveOrUpdate(AppHistory4Index appidx);

    void saveOrUpdate(Integer appId);

    int update(List<Integer> ids);

    int updateAppStatus2Del(List<Integer> ids);

    int updateAppStatus2Del(Integer id);
}
