package com.kingsoft.sjk.dao;

import java.util.Date;
import java.util.List;

import com.kingsoft.sjk.po.App;

public interface AppDao {
    List<App> findAll();

    List<App> findAll(Date curDate);
}
