package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.App;

public interface AppDao extends BaseDao<App> {

    List<App> getApps(int currentPage, int pageSize);

    List<App> getApps(List<Integer> appIds);

}
