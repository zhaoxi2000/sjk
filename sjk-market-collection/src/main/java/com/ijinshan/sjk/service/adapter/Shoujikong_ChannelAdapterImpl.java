package com.ijinshan.sjk.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.po.MarketApp;

@Service
public class Shoujikong_ChannelAdapterImpl implements MarketAppFromMarketAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Shoujikong_ChannelAdapterImpl.class);

    @Override
    public void preHandle(MarketApp mApp) {
        // set to db
        if (mApp.getId() != null) {
            mApp.setApkId(mApp.getId());
        }
        mApp.setDetailUrl(null);
    }

}
