package com.kingsoft.sjk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private String appPageBaseUrl = "";
    private String appGenerateTemplateBaseDir = "";
    private String appDetailGenerateBasePath = "";
    private int pagesize = 10000;
    private int generateDate = 0;
    private boolean debug = false;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getAppPageBaseUrl() {
        return appPageBaseUrl;
    }

    public void setAppPageBaseUrl(String appPageBaseUrl) {
        this.appPageBaseUrl = appPageBaseUrl;
    }

    public String getAppGenerateTemplateBaseDir() {
        return appGenerateTemplateBaseDir;
    }

    public void setAppGenerateTemplateBaseDir(String appGenerateTemplateBaseDir) {
        this.appGenerateTemplateBaseDir = appGenerateTemplateBaseDir;
    }

    public String getAppDetailGenerateBasePath() {
        return appDetailGenerateBasePath;
    }

    public void setAppDetailGenerateBasePath(String appDetailGenerateBasePath) {
        this.appDetailGenerateBasePath = appDetailGenerateBasePath;
    }

    public int getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(int generateDate) {
        this.generateDate = generateDate;
    }

    public static Logger getLogger() {
        return logger;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

}
