package com.ijinshan.sjk.service;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.jsonpo.BrokenApp;

public interface OffAppService {
    void deleteByBrokenApp(String marketName, List<BrokenApp> data);

    /**
     * 根据apkid删除应用.
     * 
     * @param marketName
     * @param apkIdsOfMarket
     */
    void deleteBatchByApkIdsOfMarketApp(String marketName, List<Integer> apkIdsOfMarket);

    void deleteTransactionByManyId(String marketName, Integer appIdOfMarket, int apkIdOfMarket);

    /**
     * 删除其它关联表中的数据.
     * 
     * @param sess
     * @param marketName
     * @param apkIdOfMarket
     */
    void deleteOtherTransactionsByAppId(Session session, int appId);

}
