package com.ijinshan.sjk.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;

public interface BaseMarketService {
    /**
     * Save one pagination of marketapp
     * 
     * @param session
     * @param market
     * @param marketApps
     * @param offMarketApps
     *            TODO
     */
    void savePaginationMarketApp(Session session, Market market, List<MarketApp> marketApps,
            List<MarketApp> offMarketApps);

    void importFull();

    void importIncrement();

    void resetMarketForIncrement(Date lastReqDate);

    void resetMarketForFull(Date lastReqDate);

    Market getMarket(Session session);

    void deleteMarketAppsTransaction(List<MarketApp> mApps);

    void deleteMarketAppTransaction(MarketApp mApp);

    void saveOrUpdateMarketApp(Market market, MarketApp mApp, Session session) throws Exception;

}
