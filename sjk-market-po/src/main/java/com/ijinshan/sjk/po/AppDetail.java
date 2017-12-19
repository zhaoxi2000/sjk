package com.ijinshan.sjk.po;

// Generated 2012-9-8 11:29:51 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * App generated by hbm2java
 */
@Entity
@Table(name = "App", catalog = "AndroidMarket", uniqueConstraints = { @UniqueConstraint(columnNames = "Pkname"),
        @UniqueConstraint(columnNames = "MarketAppId") })
public class AppDetail implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String version;
    private int marketAppId;
    private String marketName;
    private String name;
    private short catalog;
    private String catalogName;
    private int subCatalog;
    private String subCatalogName;
    private int size;
    private String downloadUrl;
    private String logoUrl;
    private String description;
    private String updateInfo;
    private String publisherShortName;
    private Short minsdkversion;
    private int versionCode;
    private String osversion;
    private String pkname;
    private Date lastUpdateTime;
    private String lastUpdateTimeStr;
    private String detailUrl;
    private float price;
    private String screens;
    private String models;
    private String keywords;
    private int starRating;
    private int viewCount;
    private short supportpad;
    private String enumStatus;
    private String strImageUrls;
    private boolean auditCatalog;
    private boolean hidden;
    private String pageUrl;

    public AppDetail() {
    }

    public AppDetail(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AppDetail(int marketAppId, String marketName, String name, short catalog, int subCatalog, int size,
            String downloadUrl, String logoUrl, String description, String publisherShortName, int versionCode,
            String osversion, String pkname, float price, int starRating, int viewCount, short supportpad,
            String enumStatus, boolean auditCatalog, boolean hidden) {
        this.marketAppId = marketAppId;
        this.marketName = marketName;
        this.name = name;
        this.catalog = catalog;
        this.subCatalog = subCatalog;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.logoUrl = logoUrl;
        this.description = description;
        this.publisherShortName = publisherShortName;
        this.versionCode = versionCode;
        this.osversion = osversion;
        this.pkname = pkname;
        this.price = price;
        this.starRating = starRating;
        this.viewCount = viewCount;
        this.supportpad = supportpad;
        this.enumStatus = enumStatus;
        this.auditCatalog = auditCatalog;
        this.hidden = hidden;
    }

    public AppDetail(int marketAppId, String marketName, String name, short catalog, int subCatalog, int size,
            String downloadUrl, String logoUrl, String description, String updateInfo, String publisherShortName,
            Short minsdkversion, int versionCode, String osversion, String pkname, Date lastUpdateTime,
            String detailUrl, float price, String screens, String models, String keywords, int starRating,
            int viewCount, short supportpad, String enumStatus, String strImageUrls, boolean auditCatalog,
            boolean hidden, String pageUrl) {
        this.marketAppId = marketAppId;
        this.marketName = marketName;
        this.name = name;
        this.catalog = catalog;
        this.subCatalog = subCatalog;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.logoUrl = logoUrl;
        this.description = description;
        this.updateInfo = updateInfo;
        this.publisherShortName = publisherShortName;
        this.minsdkversion = minsdkversion;
        this.versionCode = versionCode;
        this.osversion = osversion;
        this.pkname = pkname;
        this.lastUpdateTime = lastUpdateTime;
        this.detailUrl = detailUrl;
        this.price = price;
        this.screens = screens;
        this.models = models;
        this.keywords = keywords;
        this.starRating = starRating;
        this.viewCount = viewCount;
        this.supportpad = supportpad;
        this.enumStatus = enumStatus;
        this.strImageUrls = strImageUrls;
        this.auditCatalog = auditCatalog;
        this.hidden = hidden;
        this.pageUrl = pageUrl;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Version", nullable = false, length = 100)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "MarketAppId", unique = true, nullable = false)
    public int getMarketAppId() {
        return this.marketAppId;
    }

    public void setMarketAppId(int marketAppId) {
        this.marketAppId = marketAppId;
    }

    @Column(name = "MarketName", nullable = false, length = 20)
    public String getMarketName() {
        return this.marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Column(name = "Name", nullable = false, length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Catalog", nullable = false)
    public short getCatalog() {
        return this.catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

    @Column(name = "SubCatalog", nullable = false)
    public int getSubCatalog() {
        return this.subCatalog;
    }

    public void setSubCatalog(int subCatalog) {
        this.subCatalog = subCatalog;
    }

    @Column(name = "Size", nullable = false)
    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Column(name = "DownloadUrl", nullable = false, length = 200)
    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Column(name = "LogoUrl", nullable = false, length = 200)
    public String getLogoUrl() {
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Column(name = "Description", nullable = false, length = 65535)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "UpdateInfo", nullable = true, length = 65535)
    public String getUpdateInfo() {
        return this.updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    @Column(name = "PublisherShortName", nullable = false, length = 45)
    public String getPublisherShortName() {
        return this.publisherShortName;
    }

    public void setPublisherShortName(String publisherShortName) {
        this.publisherShortName = publisherShortName;
    }

    @Column(name = "Minsdkversion")
    public Short getMinsdkversion() {
        return this.minsdkversion;
    }

    public void setMinsdkversion(Short minsdkversion) {
        this.minsdkversion = minsdkversion;
    }

    @Column(name = "VersionCode", nullable = false)
    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    @Column(name = "OSVersion", nullable = false, length = 45)
    public String getOsversion() {
        return this.osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    @Column(name = "Pkname", unique = true, nullable = false, length = 200)
    public String getPkname() {
        return this.pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastUpdateTime", length = 19)
    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "DetailUrl", length = 450)
    public String getDetailUrl() {
        return this.detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @Column(name = "Price", nullable = false, precision = 5)
    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name = "Screens", length = 200)
    public String getScreens() {
        return this.screens;
    }

    public void setScreens(String screens) {
        this.screens = screens;
    }

    @Column(name = "Models", length = 65535)
    public String getModels() {
        return this.models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    @Column(name = "Keywords", length = 65535)
    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name = "StarRating", nullable = false)
    public int getStarRating() {
        return this.starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    @Column(name = "ViewCount", nullable = false)
    public int getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Column(name = "Supportpad", nullable = false)
    public short getSupportpad() {
        return this.supportpad;
    }

    public void setSupportpad(short supportpad) {
        this.supportpad = supportpad;
    }

    @Column(name = "EnumStatus", nullable = false, length = 6)
    public String getEnumStatus() {
        return this.enumStatus;
    }

    public void setEnumStatus(String enumStatus) {
        this.enumStatus = enumStatus;
    }

    @Column(name = "StrImageUrls", length = 65535)
    public String getStrImageUrls() {
        return this.strImageUrls;
    }

    public void setStrImageUrls(String strImageUrls) {
        this.strImageUrls = strImageUrls;
    }

    @Column(name = "AuditCatalog", nullable = false)
    public boolean isAuditCatalog() {
        return this.auditCatalog;
    }

    public void setAuditCatalog(boolean auditCatalog) {
        this.auditCatalog = auditCatalog;
    }

    @Column(name = "Hidden", nullable = false)
    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Column(name = "PageUrl", length = 450)
    public String getPageUrl() {
        return this.pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Transient
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Transient
    public String getSubCatalogName() {
        return subCatalogName;
    }

    public void setSubCatalogName(String subCatalogName) {
        this.subCatalogName = subCatalogName;
    }

    @Transient
    public String getLastUpdateTimeStr() {
        return lastUpdateTimeStr;
    }

    public void setLastUpdateTimeStr(String lastUpdateTimeStr) {
        this.lastUpdateTimeStr = lastUpdateTimeStr;
    }

}