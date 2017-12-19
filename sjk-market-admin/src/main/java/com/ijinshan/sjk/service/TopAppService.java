package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.TopApp;

public interface TopAppService {

    List<TopApp> findTopAppList(String keywords, Integer id, int page, int rows);

    List<TopApp> getAllTopAppList();

    boolean delete(List<Integer> list);

    TopApp get(int id);

    void saveOrUpdate(TopApp topApp);

    long getCounts();

    int updateState(List<Integer> ids,byte state);

}
