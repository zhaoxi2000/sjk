package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.TopApp;

public interface TopAppDao extends BaseDao<TopApp> {

    List<TopApp> findTopAppList(String keywords, Integer id, int page, int rows);

    List<TopApp> getAllTopAppList();

    boolean deleteByIds(List<Integer> ids);

    long getCounts();

    int updateState(List<Integer> ids,byte state);

}
