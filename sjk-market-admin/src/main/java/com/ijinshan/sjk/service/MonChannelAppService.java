package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.MonUserChannelApp;

public interface MonChannelAppService {
    MonUserChannelApp get(int id);

    /** 保存修改 */
    boolean saveAndUpdate(MonUserChannelApp app, MonUserChannelApp oleEntity);

    /** 是否存在 */
    boolean isExists(String marketName, Integer apkId);

    /** 批量删除 */
    boolean deleteByIds(List<Integer> ids);

    /** 分页查询 */
    List<MonUserChannelApp> queryList(String keyword, Boolean autoCover, int page, int rows, String order, String sort);

    /** 分页记录数 */
    long countForSearching(String keyword, Boolean autoCover);

    boolean updateSmsUserChannelUpdate(List<MonUserChannelApp> list);

    /**
     * 更加Ids获取列表
     */
    List<MonUserChannelApp> queryList(List<Integer> ids);

}
