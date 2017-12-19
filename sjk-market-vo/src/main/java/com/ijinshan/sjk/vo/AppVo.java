package com.ijinshan.sjk.vo;

import java.io.Serializable;
import java.util.Date;

public class AppVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int subCatalog;
    private String version;
    private String name;
    private String marketName;
    private int size;
    private String downloadUrl;
    private String logoUrl;
    private String description;
    private String publisherShortName;
    private Short minsdkversion;
    private long versionCode;
    private String osversion;
    private String pkname;
    private Date lastUpdateTime;
    private String detailUrl;
    private float price;
    private String screens;
    private float starRating;
    private int viewCount;
    private int downloadRank;
    private short supportpad;
    private String strImageUrls;
    private String shortDesc;
    private String indexImgUrl;
    private String notice;
    private String officeHomepage;
    private String language;
    private String permissions;
    private String signatureSha1;
    private String officialSigSha1;
    private String advertises;
    private String adActionTypes;
    private String adPopupTypes;
    private byte adRisk;
    private byte virusKind;
    private String virusBehaviors;
    private String virusName;
    private String md5;
    private int appId;
    private String logo1url;

    public AppVo() {
        super();
    }

    public AppVo(int subCatalog, String version, String name, String marketName, int size, String downloadUrl,
            String logoUrl, String description, String publisherShortName, Short minsdkversion, long versionCode,
            String osversion, String pkname, Date lastUpdateTime, String detailUrl, float price, String screens,
            float starRating, int viewCount, int downloadRank, short supportpad, String strImageUrls, String shortDesc,
            String indexImgUrl, String notice, String officeHomepage, String language, String permissions,
            String signatureSha1, String officialSigSha1, String advertises, String adActionTypes, String adPopupTypes,
            byte adRisk, byte virusKind, String virusBehaviors, String virusName, String md5, int appId, String logo1url) {
        super();
        this.subCatalog = subCatalog;
        this.version = version;
        this.name = name;
        this.marketName = marketName;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.logoUrl = logoUrl;
        this.description = description;
        this.publisherShortName = publisherShortName;
        this.minsdkversion = minsdkversion;
        this.versionCode = versionCode;
        this.osversion = osversion;
        this.pkname = pkname;
        this.lastUpdateTime = lastUpdateTime;
        this.detailUrl = detailUrl;
        this.price = price;
        this.screens = screens;
        this.starRating = starRating;
        this.viewCount = viewCount;
        this.downloadRank = downloadRank;
        this.supportpad = supportpad;
        this.strImageUrls = strImageUrls;
        this.shortDesc = shortDesc;
        this.indexImgUrl = indexImgUrl;
        this.notice = notice;
        this.officeHomepage = officeHomepage;
        this.language = language;
        this.permissions = permissions;
        this.signatureSha1 = signatureSha1;
        this.officialSigSha1 = officialSigSha1;
        this.advertises = advertises;
        this.adActionTypes = adActionTypes;
        this.adPopupTypes = adPopupTypes;
        this.adRisk = adRisk;
        this.virusKind = virusKind;
        this.virusBehaviors = virusBehaviors;
        this.virusName = virusName;
        this.md5 = md5;
        this.appId = appId;
        this.logo1url = logo1url;
    }

    public int getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(int subCatalog) {
        this.subCatalog = subCatalog;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisherShortName() {
        return publisherShortName;
    }

    public void setPublisherShortName(String publisherShortName) {
        this.publisherShortName = publisherShortName;
    }

    public Short getMinsdkversion() {
        return minsdkversion;
    }

    public void setMinsdkversion(Short minsdkversion) {
        this.minsdkversion = minsdkversion;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    public String getPkname() {
        return pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getScreens() {
        return screens;
    }

    public void setScreens(String screens) {
        this.screens = screens;
    }

    public float getStarRating() {
        return starRating;
    }

    public void setStarRating(float starRating) {
        this.starRating = starRating;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getDownloadRank() {
        return downloadRank;
    }

    public void setDownloadRank(int downloadRank) {
        this.downloadRank = downloadRank;
    }

    public short getSupportpad() {
        return supportpad;
    }

    public void setSupportpad(short supportpad) {
        this.supportpad = supportpad;
    }

    public String getStrImageUrls() {
        return strImageUrls;
    }

    public void setStrImageUrls(String strImageUrls) {
        this.strImageUrls = strImageUrls;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getIndexImgUrl() {
        return indexImgUrl;
    }

    public void setIndexImgUrl(String indexImgUrl) {
        this.indexImgUrl = indexImgUrl;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getOfficeHomepage() {
        return officeHomepage;
    }

    public void setOfficeHomepage(String officeHomepage) {
        this.officeHomepage = officeHomepage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getSignatureSha1() {
        return signatureSha1;
    }

    public void setSignatureSha1(String signatureSha1) {
        this.signatureSha1 = signatureSha1;
    }

    public String getOfficialSigSha1() {
        return officialSigSha1;
    }

    public void setOfficialSigSha1(String officialSigSha1) {
        this.officialSigSha1 = officialSigSha1;
    }

    public String getAdvertises() {
        return advertises;
    }

    public void setAdvertises(String advertises) {
        this.advertises = advertises;
    }

    public String getAdActionTypes() {
        return adActionTypes;
    }

    public void setAdActionTypes(String adActionTypes) {
        this.adActionTypes = adActionTypes;
    }

    public String getAdPopupTypes() {
        return adPopupTypes;
    }

    public void setAdPopupTypes(String adPopupTypes) {
        this.adPopupTypes = adPopupTypes;
    }

    public byte getAdRisk() {
        return adRisk;
    }

    public void setAdRisk(byte adRisk) {
        this.adRisk = adRisk;
    }

    public byte getVirusKind() {
        return virusKind;
    }

    public void setVirusKind(byte virusKind) {
        this.virusKind = virusKind;
    }

    public String getVirusBehaviors() {
        return virusBehaviors;
    }

    public void setVirusBehaviors(String virusBehaviors) {
        this.virusBehaviors = virusBehaviors;
    }

    public String getVirusName() {
        return virusName;
    }

    public void setVirusName(String virusName) {
        this.virusName = virusName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getLogo1url() {
        return logo1url;
    }

    public void setLogo1url(String logo1url) {
        this.logo1url = logo1url;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
