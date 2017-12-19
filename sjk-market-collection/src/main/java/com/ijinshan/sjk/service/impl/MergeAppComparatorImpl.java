package com.ijinshan.sjk.service.impl;

import java.util.Date;
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

import com.ijinshan.sjk.config.EnumMarket;

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
    public int compare(ComparableBaseApp newApp, ComparableBaseApp bakApp, Market newAppOwnerMarket,
            Market bakAppOwnerMarket) {
        MarketStatus marketStatus = MarketStatus.valueOf(bakAppOwnerMarket.getStatus().toUpperCase());
        switch (marketStatus) {
            case DROP: {
                if (newAppOwnerMarket.equals(bakAppOwnerMarket)) {
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
        if (MarketStatus.valueOf(newAppOwnerMarket.getStatus().toUpperCase()) == MarketStatus.DROP) {
            return -1;
        }
        int newAppScore = 0, bakAppScore = 0;
        final int compareCount = 10;
        if (newApp.getVersionCode() > bakApp.getVersionCode()) {
            newAppScore += 1 << (compareCount - 1);
        } else if (bakApp.getVersionCode() > newApp.getVersionCode()) {
            bakAppScore += 1 << (compareCount - 1);
        }

        if (newApp.getMarketName().equals(bakApp.getMarketName())) {
            if (newApp.getLastUpdateTime().after(bakApp.getLastUpdateTime())) {
                newAppScore += 1 << (compareCount - 3);
            } else if (newApp.getLastUpdateTime().before(bakApp.getLastUpdateTime())) {
                newAppScore += 1 << (compareCount - 3);
            }
            if (!newApp.getDownloadUrl().equals(bakApp.getDownloadUrl())) {
                newAppScore += 1 << (compareCount - 3);
            }
        } else {
            if (newAppOwnerMarket.getRank() < bakAppOwnerMarket.getRank()) {
                newAppScore += 1 << (compareCount - 2);
            } else if (bakAppOwnerMarket.getRank() < newAppOwnerMarket.getRank()) {
                bakAppScore += 1 << (compareCount - 2);
            }
            // data from TABLE: MarketApp to TABLE: App.
            // 市场不相同下,但是versionCode一样,相同的版本!
            if (newApp instanceof MarketApp && bakApp instanceof App) {
                if (newApp.getVersionCode() == bakApp.getVersionCode()) {
                    // VersionCode相同, 先来先到逻辑 . 渠道优先
                    switch (EnumMarket.valueOf(newApp.getMarketName().toUpperCase())) {
                        case SHOUJIKONG_CHANNEL: {
                            newAppScore += 1 << (compareCount - 3);
                        }
                        default: {
                            if (newApp.getLastUpdateTime().after(bakApp.getLastUpdateTime())) {
                                // 比如:
                                // MarketApp newApp lastUpdateTime: 2013-04-10
                                // 11:50:00
                                // App bakApp lastUpdateTime: 2013-04-10
                                // 10:50:00
                                // bakAppScore
                                bakAppScore += 1 << (compareCount - 3);
                            } else if (newApp.getLastUpdateTime().before(bakApp.getLastUpdateTime())) {
                                // 比如:
                                // MarketApp newApp lastUpdateTime: 2013-04-11
                                // 10:50:00
                                // App bakApp lastUpdateTime: 2013-04-11
                                // 11:50:00
                                // bakAppScore
                                newAppScore += 1 << (compareCount - 3);
                            }
                            break;
                        }
                    }
                }
            }
        }

        // 扫描逻辑, 优先级也高 . 重新扫描了相同市场的APK包.
        if (newApp instanceof MarketApp && bakApp instanceof App) {
            if (newApp.getMarketName().equals(bakApp.getMarketName())) {
                if (((MarketApp) newApp).getAppFetchTime() != null && ((App) bakApp).getLastFetchTime() != null) {
                    Date marketAppFetchTime = ((MarketApp) newApp).getAppFetchTime();
                    Date appFetchTime = ((App) bakApp).getLastFetchTime();
                    boolean update = marketAppFetchTime.before(appFetchTime);
                    if (update) {
                        newAppScore += 1 << (compareCount - 4);
                    }
                }
                if (newApp.getVersion() != null && !newApp.getVersion().equals(bakApp.getVersion())) {
                    newAppScore += 1 << (compareCount - 5);
                }
            }
        }
        return newAppScore - bakAppScore;
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
