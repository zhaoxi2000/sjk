package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.service.MarketSecurityService;

@Service
public class MarketSecurityServiceImpl implements MarketSecurityService {
    private static final Logger logger = LoggerFactory.getLogger(MarketSecurityServiceImpl.class);

    @Resource(name = "marketDaoImpl")
    private MarketDao marketDao;

    @Override
    public boolean allowAccess(String marketName, String key) {
        return marketDao.allowAccess(marketName, key);
    }

    @Override
    public List<Market> findMarkets() {
        return marketDao.list();
    }
}
