package com.ijinshan.sjk.dao.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.TextParser;
import com.ijinshan.sjk.po.MarketApp;

/**
 * AppChina textparser
 * 
 * @author LinZuXiong
 */
@Repository
public class TextParserImpl implements TextParser {
    private static final Logger logger = LoggerFactory.getLogger(TextParserImpl.class);
    public static final String dateFormatter = "yyyy-MM-dd";
    public static final String dateTimeFormatter = "yyyy-MM-dd HH:mm:ss";

    @Override
    public MarketApp parser(String marketName, String one) throws ParseException {
        final String[] infos = one.split("\",\"");
        int id = 0;
        if (infos[0].charAt(0) == '\"') {
            id = Integer.parseInt(infos[0].substring(1));
        } else {
            id = Integer.parseInt(infos[0]);
        }
        short catalog = Short.parseShort(infos[1]);
        int subCatalog = Integer.parseInt(infos[2]);
        if (catalog == 0 && subCatalog == 0) {
            logger.info("Donot save catalog and subcatalog are both 0!");
            return null;
        }
        String subCatalogName = infos[3];
        String name = infos[4].trim();
        int size = Integer.parseInt(infos[5]);
        String downloadUrl = infos[6];
        String logoUrl = infos[7];
        String description = infos[8];
        String updateInfo = infos[9];
        String imageUrls = infos[10];
        String publisherShortName = infos[11];
        String version = infos[12];
        short minsdkversion = Short.parseShort(infos[13]);
        long versionCode = Long.valueOf(infos[14]);
        String pkname = infos[15];
        if (pkname == null || pkname.isEmpty()) {
            throw new NullPointerException();
        }
        Date lastUpdateTime = null;
        try {
            lastUpdateTime = new SimpleDateFormat(dateTimeFormatter).parse(infos[16]);
        } catch (Exception e) {
            lastUpdateTime = new SimpleDateFormat(dateFormatter).parse(infos[16]);
        }
        String detailUrl = infos[17];
        float price = Float.parseFloat(infos[18]);
        String osversion = infos[19];
        String screens = infos[20];
        String models = infos[21];
        String keywords = infos[22];
        float starRating = 0;
        if (!infos[23].isEmpty()) {
            float src = Float.parseFloat(infos[23]);
            starRating = src >= 0.0 ? src : starRating;
        }
        int viewCount = 0;
        if (!infos[24].isEmpty()) {
            viewCount = Integer.parseInt(infos[24]);
        }
        // int downloads = 0;
        // if (!infos[25].isEmpty()) {
        // downloads = Integer.parseInt(infos[25]);
        // }
        short supportpad = 0;
        if (!infos[26].isEmpty()) {
            supportpad = Short.parseShort(infos[26]);
        }
        Integer status = -1;
        String num = infos[27].replaceAll("[\",]", "");
        status = Integer.parseInt(num);

        Integer scSta = 0;
        if (infos.length > 28 && !infos[28].isEmpty()) {
            scSta = Integer.parseInt(infos[28].replaceAll("[\",]", ""));
        }

        MarketApp app = new MarketApp();
        app.setMarketName(marketName);
        app.setId(id);
        // app.setAppId(id);
        app.setApkId(id);
        app.setCatalog(catalog);
        app.setSubCatalog(subCatalog);
        app.setSubCatalogName(subCatalogName);
        app.setName(name);
        app.setSize(size);
        app.setDownloadUrl(downloadUrl);
        app.setLogoUrl(logoUrl);
        app.setDescription(description);
        app.setUpdateInfo(updateInfo);
        app.setStrImageUrls(imageUrls);
        app.setPublisherShortName(publisherShortName);
        app.setVersion(version);
        app.setMinsdkversion(minsdkversion);
        app.setVersionCode(versionCode);
        app.setPkname(pkname);
        app.setLastUpdateTime(lastUpdateTime);
        app.setDetailUrl(detailUrl);
        app.setPrice(price);
        app.setOsversion(osversion);
        app.setScreens(screens);
        app.setModels(models);
        app.setKeywords(keywords);
        app.setStarRating(starRating);
        app.setViewCount(viewCount);
        // app.setDownloads(downloads);
        app.setSupportpad(supportpad);
        app.setStatus(status);
        app.setScSta(scSta);
        return app;
    }
}
