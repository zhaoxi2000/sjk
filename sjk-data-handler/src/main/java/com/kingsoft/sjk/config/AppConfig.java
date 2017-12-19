package com.kingsoft.sjk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private String appPageBaseUrl = "";
    private String appImgBaseurl = "";
    private String appImgStorePath = "";
    private String excelBasePath = "";
    private String resultInfoPath = "";

    public AppConfig() {
        super();
    }

    public AppConfig(String appPageBaseUrl, String appImgBaseurl, String appImgStorePath, String excelBasePath,
            String resultInfoPath) {
        super();
        this.appPageBaseUrl = appPageBaseUrl;
        this.appImgBaseurl = appImgBaseurl;
        this.appImgStorePath = appImgStorePath;
        this.excelBasePath = excelBasePath;
        this.resultInfoPath = resultInfoPath;
    }

    public String getAppPageBaseUrl() {
        return appPageBaseUrl;
    }

    public void setAppPageBaseUrl(String appPageBaseUrl) {
        this.appPageBaseUrl = appPageBaseUrl;
    }

    public String getAppImgBaseurl() {
        return appImgBaseurl;
    }

    public void setAppImgBaseurl(String appImgBaseurl) {
        this.appImgBaseurl = appImgBaseurl;
    }

    public String getAppImgStorePath() {
        return appImgStorePath;
    }

    public void setAppImgStorePath(String appImgStorePath) {
        this.appImgStorePath = appImgStorePath;
    }

    public String getExcelBasePath() {
        return excelBasePath;
    }

    public void setExcelBasePath(String excelBasePath) {
        this.excelBasePath = excelBasePath;
    }

    public String getResultInfoPath() {
        return resultInfoPath;
    }

    public void setResultInfoPath(String resultInfoPath) {
        this.resultInfoPath = resultInfoPath;
    }

}
