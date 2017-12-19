package com.ijinshan.sjk.market;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 */
public class MarketApp implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String version;
    private int appId;
    private int apkId;
    private String name;
    private short catalog;
    private int subCatalog;
    private String subCatalogName;
    private int size;
    private String downloadUrl;
    private String logoUrl;
    private String description;
    private String updateInfo;
    private String publisherShortName;
    private Short minsdkversion;
    private long versionCode;
    private String osversion;
    private String pkname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
    private String detailUrl;
    private float price;
    private String screens;
    private String models;
    private String keywords;
    private float starRating;
    private int viewCount;
    private int downloads;
    private short supportpad;
    private String enumStatus;
    private String strImageUrls;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date marketApkScanTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date marketUpdateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appFetchTime;
    private String signatureSha1;
    private String advertises;
    private String adActionTypes;
    private String adPopupTypes;
    private byte adRisk;
    private byte virusKind;
    private String virusBehaviors;
    private String virusName;
    private String permissions;
    private String md5;
    private String packageSjdbMd5;
    private String signatureMd5;
    private String quickMd5;
    private long freeSize;
    private String indexImgUrl = "";
    private String language = "";
    private String shortDesc;
    private byte pathStatus;
    private Integer status;
    private String[] imageUrls = null;

    public MarketApp() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getApkId() {
        return apkId;
    }

    public void setApkId(int apkId) {
        this.apkId = apkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCatalog() {
        return catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

    public int getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(int subCatalog) {
        this.subCatalog = subCatalog;
    }

    public String getSubCatalogName() {
        return subCatalogName;
    }

    public void setSubCatalogName(String subCatalogName) {
        this.subCatalogName = subCatalogName;
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

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
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

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public short getSupportpad() {
        return supportpad;
    }

    public void setSupportpad(short supportpad) {
        this.supportpad = supportpad;
    }

    public String getEnumStatus() {
        return enumStatus;
    }

    public void setEnumStatus(String enumStatus) {
        this.enumStatus = enumStatus;
    }

    public String getStrImageUrls() {
        return strImageUrls;
    }

    public void setStrImageUrls(String strImageUrls) {
        this.strImageUrls = strImageUrls;
    }

    public Date getMarketApkScanTime() {
        return marketApkScanTime;
    }

    public void setMarketApkScanTime(Date marketApkScanTime) {
        this.marketApkScanTime = marketApkScanTime;
    }

    public Date getMarketUpdateTime() {
        return marketUpdateTime;
    }

    public void setMarketUpdateTime(Date marketUpdateTime) {
        this.marketUpdateTime = marketUpdateTime;
    }

    public Date getAppFetchTime() {
        return appFetchTime;
    }

    public void setAppFetchTime(Date appFetchTime) {
        this.appFetchTime = appFetchTime;
    }

    public String getSignatureSha1() {
        return signatureSha1;
    }

    public void setSignatureSha1(String signatureSha1) {
        this.signatureSha1 = signatureSha1;
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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPackageSjdbMd5() {
        return packageSjdbMd5;
    }

    public void setPackageSjdbMd5(String packageSjdbMd5) {
        this.packageSjdbMd5 = packageSjdbMd5;
    }

    public String getSignatureMd5() {
        return signatureMd5;
    }

    public void setSignatureMd5(String signatureMd5) {
        this.signatureMd5 = signatureMd5;
    }

    public String getQuickMd5() {
        return quickMd5;
    }

    public void setQuickMd5(String quickMd5) {
        this.quickMd5 = quickMd5;
    }

    public long getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(long freeSize) {
        this.freeSize = freeSize;
    }

    public String getIndexImgUrl() {
        return indexImgUrl;
    }

    public void setIndexImgUrl(String indexImgUrl) {
        this.indexImgUrl = indexImgUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public byte getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(byte pathStatus) {
        this.pathStatus = pathStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
