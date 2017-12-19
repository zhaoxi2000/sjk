package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.App;

public interface AppDao {

    List<App> getLg2AppByPknameMarketName();

    List<App> getApps(String pkname, String marketname);

    void update(App firstApp);

    void delete(App app);

}
