package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.MarketStatus;
import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.po.marketmerge.ComparableBaseApp;
import com.ijinshan.sjk.service.MergeAppComparator;

@Service
public class MergeAppComparatorImpl implements MergeAppComparator {
    private static final Logger logger = LoggerFactory.getLogger(MergeAppComparatorImpl.class);

    @Resource(name = "marketDaoImpl")
    private MarketDao marketDao;

    @Override
    public int compare(MarketApp mApp, App app, Market currentMarket, Market appOwnerMarket) {
        return this.compare(mApp, (ComparableBaseApp) app, currentMarket, appOwnerMarket);

    }

    @Override
    public int compare(ComparableBaseApp newApp, ComparableBaseApp bakApp, Market currentMarket,
            Market bakAppOwnerMarket) {
        MarketStatus marketStatus = MarketStatus.valueOf(bakAppOwnerMarket.getStatus().toUpperCase());
        switch (marketStatus) {
        case DROP: {
            if (currentMarket.equals(bakAppOwnerMarket)) {
                // 如果是都在marketApp表, 则正常逻辑.
            } else {
                // 如果是marketApp 要处理到app.则判断bakApp是不是属于废弃市场
                return 1;
            }
        }
        default: {
            break;
        }
        }
        float mAppScore = 0, appScore = 0;
        final int compareCount = 5;
        if (newApp.getVersionCode() > bakApp.getVersionCode()) {
            mAppScore += 1 << (compareCount - 1);
        } else if (bakApp.getVersionCode() > newApp.getVersionCode()) {
            appScore += 1 << (compareCount - 1);
        }
        if (currentMarket.getRank() < bakAppOwnerMarket.getRank()) {
            mAppScore += 1 << (compareCount - 2);
        } else if (bakAppOwnerMarket.getRank() < currentMarket.getRank()) {
            appScore += 1 << (compareCount - 2);
        }
        if (newApp.getLastUpdateTime().getTime() > bakApp.getLastUpdateTime().getTime()) {
            mAppScore += 1 << (compareCount - 3);
        } else if (bakApp.getLastUpdateTime().getTime() > newApp.getLastUpdateTime().getTime()) {
            appScore += 1 << (compareCount - 3);
        }
        return mAppScore > appScore ? 1 : -1;
    }

    @Override
    public MarketApp getTop(List<MarketApp> list) {
        MarketApp top = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            MarketApp app = list.get(i);
            float topAppScore = 0, appScore = 0;
            final int compareCount = 5;
            if (top.getVersionCode() > app.getVersionCode()) {
                topAppScore += 1 << (compareCount - 1);
            } else if (app.getVersionCode() > top.getVersionCode()) {
                appScore += 1 << (compareCount - 1);
            }
            Market marketOfTop = marketDao.getByName(top.getMarketName());
            Market marketOfApp = marketDao.getByName(app.getMarketName());
            if (marketOfTop.getRank() < marketOfApp.getRank()) {
                topAppScore += 1 << (compareCount - 2);
            } else if (marketOfApp.getRank() < marketOfTop.getRank()) {
                appScore += 1 << (compareCount - 2);
            }
            if (top.getLastUpdateTime().getTime() > app.getLastUpdateTime().getTime()) {
                topAppScore += 1 << (compareCount - 3);
            } else if (app.getLastUpdateTime().getTime() > top.getLastUpdateTime().getTime()) {
                appScore += 1 << (compareCount - 3);
            }

            if (topAppScore > appScore) {
                top = app;
            }
        }
        return top;
    }

}
