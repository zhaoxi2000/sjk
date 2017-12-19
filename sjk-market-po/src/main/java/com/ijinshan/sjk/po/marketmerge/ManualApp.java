package com.ijinshan.sjk.po.marketmerge;

/**
 * 人工运营的数据.
 * 
 * @author Linzuxiong
 */
public class ManualApp {
    private String engName;
    private String logoUrl;
    private String shortDesc;

    private String keywords;

    public ManualApp() {
        super();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

}
