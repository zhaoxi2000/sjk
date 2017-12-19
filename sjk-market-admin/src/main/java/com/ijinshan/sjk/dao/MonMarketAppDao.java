package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.MonApp;
import com.ijinshan.sjk.po.MonChannelApp;

public interface MonMarketAppDao {
    /**
     * 获取被替换的应用列表
     */
    List<MonApp> queryCoverApp(String marketName);

    /**
     * 获取金山手机控推广应用列表
     */
    List<MonChannelApp> queryChannelApp(String marketName);
}
