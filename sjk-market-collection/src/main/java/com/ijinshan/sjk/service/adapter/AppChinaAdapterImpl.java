package com.ijinshan.sjk.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.po.MarketApp;

@Service
public class AppChinaAdapterImpl implements MarketAppFromMarketAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AppChinaAdapterImpl.class);

    @Override
    public void preHandle(MarketApp mApp) {
        // set to db
        if (mApp.getId() != null) {
            mApp.setApkId(mApp.getId());
        }
    }

}
