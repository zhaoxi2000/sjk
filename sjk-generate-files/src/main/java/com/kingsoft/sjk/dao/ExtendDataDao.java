package com.kingsoft.sjk.dao;

import java.util.List;

import com.kingsoft.sjk.po.ExtendData;

public interface ExtendDataDao {
    List<ExtendData> getAppExtendData(int softid);

    List<ExtendData> findAll();
}
