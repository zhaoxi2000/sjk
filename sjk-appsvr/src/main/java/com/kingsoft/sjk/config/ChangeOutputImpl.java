package com.kingsoft.sjk.config;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.ScreenImage;
import com.kingsoft.sjk.po.Topic;

@Repository
public class ChangeOutputImpl {
    private static final Logger logger = LoggerFactory.getLogger(ChangeOutputImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    private static final int MOD = 500;

    public <T extends App> void setUrls(T app) {
        String downloadUrl = String.format(appConfig.getDownLoadBaseUrl(), app.getId() / MOD, app.getDownloadLink());
        app.setDownloadUrl(downloadUrl);

        if (app.getLogo() != null) {
            StringBuilder logoUrl = new StringBuilder(appConfig.getAppLogoBaseUrl().length() + app.getLogo().length());
            logoUrl.append(appConfig.getAppLogoBaseUrl()).append(app.getLogo());
            app.setLogoUrl(logoUrl.toString());
        }
        if (!StringUtils.isEmpty(app.getOneKeyPack())) {
            app.setOneKeyPackUrl(appConfig.getDownLoadOneKeyPackBaseUrl() + app.getOneKeyPack());
        } else {
            /* 如果没有一键安装包 没有手势 */
            app.setHaveData(false);
        }

        String pageUrl = new StringBuilder(appConfig.getAppPageBaseUrl()).append(app.getId() / MOD).append("/")
                .append(app.getId()).append(".html").toString();
        app.setPageUrl(pageUrl);
    }

    public <T extends Topic> void setUrls(T topic) {
        // TODO 完善修改连接
        topic.setImgUrl(String.format(appConfig.getTopicImageBaseUrl(), topic.getImgUrl()));
    }

    public void setAppScreenUrl(ScreenImage appScreenImage) {
        String imageUrl = appConfig.getAppImageBaseUrl() + appScreenImage.getImageUrl();
        appScreenImage.setImageUrl(imageUrl);
    }

    public void setAppExtendDataUrl(ExtendData ext) {
        String downloadUrl = new StringBuilder(appConfig.getDownLoadDataBaseUrl()).append(ext.getDownloadLink())
                .toString();
        ext.setDownloadUrl(downloadUrl);
        if (ext.getScreenshot() != null || !ext.getScreenshot().isEmpty()) {
            StringBuilder imageUrl = new StringBuilder(appConfig.getAppDataImageBaseUrl().length()
                    + ext.getScreenshot().length());
            imageUrl.append(appConfig.getAppDataImageBaseUrl()).append(ext.getScreenshot());
            ext.setImageUrl(imageUrl.toString());
        }
    }
}
