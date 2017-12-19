package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.Market;

public interface MarketDao extends BaseDao<Market> {

    List<Market> listMarketRank();

}
