package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.MonUserChannelApp;

public interface MonUserChannelAppDao extends BaseDao<MonUserChannelApp> {
    /** 获取实体 */
    MonUserChannelApp getChannelApp(String marketName, Integer apkId);

    /** 是否存在 */
    boolean isExists(String marketName, Integer apkId);

    /** 批量删除 */
    boolean deleteByIds(List<Integer> ids);

    /** 分页查询 */
    List<MonUserChannelApp> queryList(String keyword, Boolean autoCover, int page, int rows, String order, String sort);

    /** 分页记录数 */
    long countForSearching(String keyword, Boolean autoCover);

    /**
     * 获取监控用户渠道包列表
     * 
     * @param marketName
     *            ：市场名称
     * @param status
     *            :是否可以 true为可以 ，false不可以禁用，不用再监控了。
     */
    List<MonUserChannelApp> queryList(String marketName, Boolean status);

    /**
     * 更加Ids获取列表
     */
    List<MonUserChannelApp> queryList(List<Integer> ids);
}
