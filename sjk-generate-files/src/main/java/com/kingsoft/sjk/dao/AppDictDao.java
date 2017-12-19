package com.kingsoft.sjk.dao;

import java.util.List;

import com.kingsoft.sjk.po.AppDict;

public interface AppDictDao {

    List<AppDict> findAll();

    List<AppDict> getAppDicts(int typeId);

    AppDict getAppDict(int dicId);
}
