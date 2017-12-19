package com.kingsoft.sjk.config;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.ScreenImage;

@Repository
public class ChangeOutputImpl {
    private static final Logger logger = LoggerFactory.getLogger(ChangeOutputImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    public void setUrls(App app) {
        String downloadUrl = String.format(appConfig.getDownLoadBaseUrl(), (app.getId() / 500), app.getDownLoadLink());
        app.setDownloadUrl(downloadUrl);
        if (!StringUtils.isEmpty(app.getLogo())) {
            StringBuilder logoUrl = new StringBuilder(appConfig.getAppLogoBaseUrl().length() + app.getLogo().length());
            logoUrl.append(appConfig.getAppLogoBaseUrl()).append(app.getLogo());
            app.setLogoUrl(logoUrl.toString());
        }
        app.setPageUrl(appConfig.getAppPageBaseUrl() + "/" + (app.getId() / 500) + "/" + app.getId() + ".html");

        if (!StringUtils.isEmpty(app.getOneKeyPack())) {
            app.setOneKeyPackUrl(appConfig.getDownLoadOneKeyPackBaseUrl() + app.getOneKeyPack());
        }

    }

    public void setAppScreenUrl(ScreenImage appScreenImage) {
        String imageUrl = appConfig.getAppImageBaseUrl() + appScreenImage.getImageUrl();
        appScreenImage.setImageUrl(imageUrl);
    }

    public void setAppExtendDataUrl(ExtendData ext) {
        String downloadUrl = appConfig.getDownLoadDataBaseUrl() + ext.getDownloadLink();
        ext.setDownloadUrl(downloadUrl);
        if (ext.getScreenshot() != null) {
            StringBuilder imageUrl = new StringBuilder(appConfig.getAppDataImageBaseUrl().length()
                    + ext.getScreenshot().length());
            imageUrl.append(appConfig.getAppDataImageBaseUrl()).append(ext.getScreenshot());
            ext.setImageUrl(imageUrl.toString());
        }
    }
}
