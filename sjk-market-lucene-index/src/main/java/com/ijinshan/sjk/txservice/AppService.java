package com.ijinshan.sjk.txservice;

import java.util.List;

import com.ijinshan.sjk.po.App;

public interface AppService {

    long count();

    List<App> getApps(int currentPage, int pageSize);

    List<App> getApps(List<Integer> appIds);

}
