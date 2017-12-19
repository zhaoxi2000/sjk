package com.ijinshan.sjk.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ijinshan.sjk.po.BigGamePack;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-2-26 下午2:36:16
 * 提供给pc端大游戏安装包使用
 * </pre>
 */
public class AppForBigGamesVo implements java.io.Serializable {

    private static final long serialVersionUID = 7005163795031372280L;

    private Integer id;
    private String version;
    private int marketAppId;
    private String marketName;
    private String name;
    private String engName;
    private short catalog;
    private int subCatalog;
    // private int size;
    // private long freeSize;
    // private String downloadUrl;
    private String logoUrl;
    private String description;
    private String updateInfo;
    private String publisherShortName;
    private Short minsdkversion;
    private long versionCode;
    private String osversion;
    private String pkname;
    private Date lastUpdateTime;
    private Date lastFetchTime;
    private String detailUrl;
    private float price;
    private String screens;
    private String models;
    private String keywords;
    private float starRating;
    private int viewCount;
    private int downloadRank;
    private short supportpad;
    private String enumStatus;
    private String strImageUrls;
    private boolean auditCatalog;
    private boolean hidden;
    private String pageUrl;
    private String shortDesc;
    private String indexImgUrl;
    private String notice;
    private String officeHomepage;
    private String language;
    private boolean audit;
    private byte pathStatus;
    private String permissions;
    private String signatureSha1;
    private String officialSigSha1;
    private Date apkScanTime;
    private String advertises;
    private String adActionTypes;
    private String adPopupTypes;
    private byte adRisk;
    private int realDownload;
    private int deltaDownload;
    private int lastDayDownload;
    private int lastDayDelta;
    private int lastWeekDownload;
    private int lastWeekDelta;
    private byte virusKind;
    private String virusBehaviors;
    private String virusName;
    private String md5;
    private int appId;
    private int apkId;
    private String logo1url;
    private int scSta;

    public int getScSta() {
        return scSta;
    }

    public void setScSta(int scSta) {
        this.scSta = scSta;
    }

    private List<BigGamePack> apkPackList = new ArrayList<BigGamePack>();

    public AppForBigGamesVo() {
        super();
    }

    public AppForBigGamesVo(Integer id, String version, int marketAppId, String marketName, String name,
            String engName, short catalog, int subCatalog, String logoUrl, String description, String updateInfo,
            String publisherShortName, Short minsdkversion, long versionCode, String osversion, String pkname,
            Date lastUpdateTime, Date lastFetchTime, String detailUrl, float price, String screens, String models,
            String keywords, float starRating, int viewCount, int downloadRank, short supportpad, String enumStatus,
            String strImageUrls, boolean auditCatalog, boolean hidden, String pageUrl, String shortDesc,
            String indexImgUrl, String notice, String officeHomepage, String language, boolean audit, byte pathStatus,
            String permissions, String signatureSha1, String officialSigSha1, Date apkScanTime, String advertises,
            String adActionTypes, String adPopupTypes, byte adRisk, int realDownload, int deltaDownload,
            int lastDayDownload, int lastDayDelta, int lastWeekDownload, int lastWeekDelta, byte virusKind,
            String virusBehaviors, String virusName, String md5, int appId, int apkId, String logo1url,
            List<BigGamePack> apkPackList,int scSta) {
        super();
        this.id = id;
        this.version = version;
        this.marketAppId = marketAppId;
        this.marketName = marketName;
        this.name = name;
        this.engName = engName;
        this.catalog = catalog;
        this.subCatalog = subCatalog;

        this.logoUrl = logoUrl;
        this.description = description;
        this.updateInfo = updateInfo;
        this.publisherShortName = publisherShortName;
        this.minsdkversion = minsdkversion;
        this.versionCode = versionCode;
        this.osversion = osversion;
        this.pkname = pkname;
        this.lastUpdateTime = lastUpdateTime;
        this.lastFetchTime = lastFetchTime;
        this.detailUrl = detailUrl;
        this.price = price;
        this.screens = screens;
        this.models = models;
        this.keywords = keywords;
        this.starRating = starRating;
        this.viewCount = viewCount;
        this.downloadRank = downloadRank;
        this.supportpad = supportpad;
        this.enumStatus = enumStatus;
        this.strImageUrls = strImageUrls;
        this.auditCatalog = auditCatalog;
        this.hidden = hidden;
        this.pageUrl = pageUrl;
        this.shortDesc = shortDesc;
        this.indexImgUrl = indexImgUrl;
        this.notice = notice;
        this.officeHomepage = officeHomepage;
        this.language = language;
        this.audit = audit;
        this.pathStatus = pathStatus;
        this.permissions = permissions;
        this.signatureSha1 = signatureSha1;
        this.officialSigSha1 = officialSigSha1;
        this.apkScanTime = apkScanTime;
        this.advertises = advertises;
        this.adActionTypes = adActionTypes;
        this.adPopupTypes = adPopupTypes;
        this.adRisk = adRisk;
        this.realDownload = realDownload;
        this.deltaDownload = deltaDownload;
        this.lastDayDownload = lastDayDownload;
        this.lastDayDelta = lastDayDelta;
        this.lastWeekDownload = lastWeekDownload;
        this.lastWeekDelta = lastWeekDelta;
        this.virusKind = virusKind;
        this.virusBehaviors = virusBehaviors;
        this.virusName = virusName;
        this.md5 = md5;
        this.appId = appId;
        this.apkId = apkId;
        this.logo1url = logo1url;
        this.apkPackList = apkPackList;
        this.scSta = scSta;
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

    public int getMarketAppId() {
        return marketAppId;
    }

    public void setMarketAppId(int marketAppId) {
        this.marketAppId = marketAppId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
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
        if (description != null && !description.isEmpty()) {
            description = description.replaceAll("\\<.*?>", "");
            description = description.replaceAll("\\</.+>", "");
            description = description.replaceAll("\\</.+", "");
            description = description.replaceAll("\\<.+", "");
            final int len = description.length() > 100 ? 100 : description.length();
            this.description = description.substring(0, len);
        }
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

    public Date getLastFetchTime() {
        return lastFetchTime;
    }

    public void setLastFetchTime(Date lastFetchTime) {
        this.lastFetchTime = lastFetchTime;
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

    public boolean isAuditCatalog() {
        return auditCatalog;
    }

    public void setAuditCatalog(boolean auditCatalog) {
        this.auditCatalog = auditCatalog;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
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

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public byte getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(byte pathStatus) {
        this.pathStatus = pathStatus;
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

    public Date getApkScanTime() {
        return apkScanTime;
    }

    public void setApkScanTime(Date apkScanTime) {
        this.apkScanTime = apkScanTime;
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

    public int getRealDownload() {
        return realDownload;
    }

    public void setRealDownload(int realDownload) {
        this.realDownload = realDownload;
    }

    public int getDeltaDownload() {
        return deltaDownload;
    }

    public void setDeltaDownload(int deltaDownload) {
        this.deltaDownload = deltaDownload;
    }

    public int getLastDayDownload() {
        return lastDayDownload;
    }

    public void setLastDayDownload(int lastDayDownload) {
        this.lastDayDownload = lastDayDownload;
    }

    public int getLastDayDelta() {
        return lastDayDelta;
    }

    public void setLastDayDelta(int lastDayDelta) {
        this.lastDayDelta = lastDayDelta;
    }

    public int getLastWeekDownload() {
        return lastWeekDownload;
    }

    public void setLastWeekDownload(int lastWeekDownload) {
        this.lastWeekDownload = lastWeekDownload;
    }

    public int getLastWeekDelta() {
        return lastWeekDelta;
    }

    public void setLastWeekDelta(int lastWeekDelta) {
        this.lastWeekDelta = lastWeekDelta;
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

    public int getApkId() {
        return apkId;
    }

    public void setApkId(int apkId) {
        this.apkId = apkId;
    }

    public String getLogo1url() {
        return logo1url;
    }

    public void setLogo1url(String logo1url) {
        this.logo1url = logo1url;
    }

    public List<BigGamePack> getApkPackList() {
        return apkPackList;
    }

    public void setApkPackList(List<BigGamePack> apkPackList) {
        this.apkPackList = apkPackList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
