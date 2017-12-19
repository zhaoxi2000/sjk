package com.kingsoft.sjk.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Permissions;
import com.kingsoft.sjk.config.TagType;
import com.kingsoft.sjk.po.AppTags;
import com.kingsoft.sjk.po.Catalog;

public interface AppDao {
    List<App> findData();

    List<App> findData(Date curDate);

    int updatePageUrl(App app);

    Map<Integer, Catalog> initCatalog();

    Map<Integer, Permissions> initPermissions();

    Map<Integer, List<AppTags>> initAppTags(TagType tagType);

    List<AppTags> getAppTags(TagType tagType);

    List<App> queryPager(long total, int page, int rows, Date curDate);

    int count();

    App findById(int id);

    List<App> findAppListByCatalog(Integer catalog);
}
