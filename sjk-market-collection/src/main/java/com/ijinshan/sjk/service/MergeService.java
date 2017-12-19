package com.ijinshan.sjk.service;

import java.util.Date;

import org.hibernate.Session;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;

public interface MergeService {

    /**
     * 只处理市场数据的表
     * 
     * @param market
     * @param topMarketApp
     *            最新的应用.市场提供过来的数据,已处理成我们的库中结构的值.
     * @param sess
     */
    void saveOrUpdateMarketApp(Market market, MarketApp topMarketApp, Session sess) throws Exception;

    /**
     * 把MaketApp数据合并入App
     * 
     * @param forceMerge
     *            TODO
     * @param mApp
     * @param session
     * @throws Exception
     */
    void mergeTo1App(boolean forceMerge, MarketApp mApp, Session session) throws Exception;

    /**
     * 批量处理MarketApp 数据入App
     */
    void mergeToApp();

    /**
     * @param mApp
     * @param session
     * @param now
     * @param updateApp
     * @return updateApp
     */
    App updateAppIfNeedUpdate(MarketApp mApp, Market market, Session session, Date now, App updateApp);

    public App createAppFromMarketApp(MarketApp mApp, Session session, Date now) throws Exception;

}
