package com.kingsoft.sjk.service;

import java.util.List;

import com.kingsoft.sjk.po.App;

public interface AppService {
    List<App> FindAll();

    App AppDetail(int softid);
}
