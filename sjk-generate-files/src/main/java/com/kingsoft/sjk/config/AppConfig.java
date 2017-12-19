package com.kingsoft.sjk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    // private static String templateDir;
    //
    // static {
    // URL url = AppConfig.class.getClass().getClassLoader().getResource(".");
    // File file = new File(url.getPath());
    // logger.debug("load template path");
    // templateDir = file.getAbsolutePath() + "\\template";
    // }

    /* 应用排显示条目数 */
    private int appsRankTopNum = 10;
    /*
     * 下载地址URLhttp://www.yingyong.so/DownLoad.aspx?id=15&tid=1&file=
     * http://file.yingyong.so/file/12/Mass_Effect_Infiltrator_v1_0_30.apk
     * http://file.yingyong.so/file/{id/500}
     */
    /* 下载地址URL */
    // "http://www.yingyong.so/DownLoad.aspx?id=15&tid=1&file="

    private String downLoadBaseUrl = "http://www.yingyong.so/DownLoad.aspx?id=%d&tid=1&file=%s";

    /*
     * 下载Data地址URL http://www.yingyong.so/DownLoad.aspx?id=15&tid=2&file=
     * http://
     * file.yingyong.so/file/gamedata/YingyongSo_Mass_Effect_Infiltrator_v1_0_30
     * .zip
     */
    private String downLoadDataBaseUrl = "http://www.yingyong.so/DownLoad.aspx?id=%d&tid=2&file=%s";
    /* 一键安装包 */
    private String downLoadOneKeyPackBaseUrl = "http://softdl.ijinshan.com/sjkappmgr/oneKeyPack/";
    /* 应用Logo */
    private String appLogoBaseUrl = "http://www.yingyong.so/img/logo/";
    /* 应用截图 */
    private String appImageBaseUrl = "http://www.yingyong.so/img/topimg/";
    /* 详情页URL */
    private String appPageBaseUrl = "http://app.sjk.ijinshan.com/sjk/page/";
    /* extenddata 截图 */
    private String appDataImageBaseUrl = "http://www.yingyong.so/img/extenddata/";

    private String webCssOrJsBaseUrl = "http://pg.yingyong.so";

    private String appGenerateTemplateBaseDir = "/loker/JavaWorkspace/sjk/sjk-generate-files/src/main/resources/template/";

    /* 应用详情页文件生成 Path */
    private String appDetailGenerateBasePath = "C:/data/appsdata/sjk/static-file/page/";

    /* 生成最近多少天的页面 */
    private int generateDate = -10;

    public int getGenerateDate() {
        return generateDate;
    }

    public String getAppGenerateTemplateBaseDir() {
        return appGenerateTemplateBaseDir;
    }

    public void setAppGenerateTemplateBaseDir(String appGenerateTemplateBaseDir) {
        this.appGenerateTemplateBaseDir = appGenerateTemplateBaseDir;
    }

    public void setGenerateDate(int generateDate) {
        this.generateDate = generateDate;
    }

    public String getWebCssOrJsBaseUrl() {
        return webCssOrJsBaseUrl;
    }

    public void setWebCssOrJsBaseUrl(String webCssOrJsBaseUrl) {
        this.webCssOrJsBaseUrl = webCssOrJsBaseUrl;
    }

    public String getDownLoadBaseUrl() {
        return downLoadBaseUrl;
    }

    public void setDownLoadBaseUrl(String downLoadBaseUrl) {
        this.downLoadBaseUrl = downLoadBaseUrl;
    }

    public String getAppLogoBaseUrl() {
        return appLogoBaseUrl;
    }

    public void setAppLogoBaseUrl(String appLogoBaseUrl) {
        this.appLogoBaseUrl = appLogoBaseUrl;
    }

    public String getAppImageBaseUrl() {
        return appImageBaseUrl;
    }

    public void setAppImageBaseUrl(String appImageBaseUrl) {
        this.appImageBaseUrl = appImageBaseUrl;
    }

    public int getAppsRankTopNum() {
        return appsRankTopNum;
    }

    public void setAppsRankTopNum(int appsRankTopNum) {
        this.appsRankTopNum = appsRankTopNum;
    }

    public String getAppPageBaseUrl() {
        return appPageBaseUrl;
    }

    public void setAppPageBaseUrl(String appPageBaseUrl) {
        this.appPageBaseUrl = appPageBaseUrl;
    }

    public String getDownLoadDataBaseUrl() {
        return downLoadDataBaseUrl;
    }

    public void setDownLoadDataBaseUrl(String downLoadDataBaseUrl) {
        this.downLoadDataBaseUrl = downLoadDataBaseUrl;
    }

    public String getAppDataImageBaseUrl() {
        return appDataImageBaseUrl;
    }

    public void setAppDataImageBaseUrl(String appDataImageBaseUrl) {
        this.appDataImageBaseUrl = appDataImageBaseUrl;
    }

    public String getAppDetailGenerateBasePath() {
        return appDetailGenerateBasePath;
    }

    public void setAppDetailGenerateBasePath(String appDetailGenerateBasePath) {
        this.appDetailGenerateBasePath = appDetailGenerateBasePath;
    }

    public String getDownLoadOneKeyPackBaseUrl() {
        return downLoadOneKeyPackBaseUrl;
    }

    public void setDownLoadOneKeyPackBaseUrl(String downLoadOneKeyPackBaseUrl) {
        this.downLoadOneKeyPackBaseUrl = downLoadOneKeyPackBaseUrl;
    }

}
