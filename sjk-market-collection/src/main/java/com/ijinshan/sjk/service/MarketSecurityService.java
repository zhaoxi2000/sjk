package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.Market;

public interface MarketSecurityService {

    boolean allowAccess(String marketName, String key);

    List<Market> findMarkets();
}
