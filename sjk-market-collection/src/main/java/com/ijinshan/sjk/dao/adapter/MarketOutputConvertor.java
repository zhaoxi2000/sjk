package com.ijinshan.sjk.dao.adapter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.config.AppStatus;
import com.ijinshan.sjk.jsonpo.HiapkMarketApp;
import com.ijinshan.sjk.jsonpo.HiapkPaginationMarketApp;
import com.ijinshan.sjk.jsonpo.PaginationMarketApp;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;

/**
 * 用不了适配器模式做数据转换.
 * 
 * @author LinZuXiong
 */
public class MarketOutputConvertor {
    private static final Logger logger = LoggerFactory.getLogger(MarketOutputConvertor.class);

    public static PaginationMarketApp getPaginationMarketApp(Market market, HiapkPaginationMarketApp hiapk) {
        PaginationMarketApp marketApps = new PaginationMarketApp();
        List<MarketApp> data = null;
        if (hiapk.getData() != null) {
            data = new ArrayList<MarketApp>(hiapk.getData().size());
            for (HiapkMarketApp apk : hiapk.getData()) {
                MarketApp e = new MarketApp();
                e.setMarketName(market.getMarketName().trim());
                e.setId(apk.getSoftcode());
                e.setAppId(apk.getSoftcode());
                e.setApkId(apk.getApkid());
                e.setCatalog(apk.getPid());
                e.setSubCatalog(apk.getCid());
                if (e.getCatalog() == 0 || e.getSubCatalog() == 0) {
                    continue;
                }
                e.setDetailUrl(apk.getSourceurl());
                // e.setDownloads(apk.getTotaldownload());
                e.setDescription(apk.getDescription());
                e.setEnumStatus(apk.getStatus());
                e.setStatus(AppStatus.valueOf(apk.getStatus().toUpperCase()).getVal());
                e.setDownloadUrl(apk.getDownurl());
                e.setLastUpdateTime(apk.getPublishtime());
                e.setLogoUrl(apk.getIconurl());
                e.setMinsdkversion(apk.getMinsdkversion() < 0 ? 0 : apk.getMinsdkversion());
                e.setModels("");
                e.setName(apk.getApkname().trim());
                e.setOsversion(apk.getPlatform());
                e.setPkname(apk.getPackagename());
                // e.setPrice(0f);
                e.setPublisherShortName(apk.getDevelopername());
                e.setScreens(apk.getDpi());
                e.setSize(apk.getApksize());
                e.setStarRating(apk.getTotalstar());
                e.setStrImageUrls(apk.getImageurl());
                e.setSubCatalogName(apk.getCname());
                e.setUpdateInfo(apk.getUpdatedescription());
                e.setVersion(apk.getVersionname());
                e.setVersionCode(apk.getVersioncode());
                e.setKeywords("");
                // e.setViewCount(viewCount)
                data.add(e);
            }
        }
        marketApps.setMarketName(market.getMarketName());
        marketApps.setTotals(hiapk.getTotals());
        marketApps.setData(data);
        return marketApps;
    }
}
