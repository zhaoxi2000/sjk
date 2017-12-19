package com.ijinshan.sjk.txservice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.txservice.MarketService;

@Service
public class MarketServiceImpl implements MarketService {
    private static final Logger logger = LoggerFactory.getLogger(MarketServiceImpl.class);

    @Resource(name = "marketDaoImpl")
    private MarketDao dao;

    @Override
    public List<Market> listMarketRank() {
        return dao.listMarketRank();
    }

}
