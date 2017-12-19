package com.ijinshan.sjk.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.ijinshan.sjk.po.marketmerge.ComparableBaseApp;

@Entity
public class MonChannelApp extends ComparableBaseApp implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String version;
    private int appId;
    private int apkId;
    private String marketName;
    private String name;
    private String downloadUrl;
    private String publisherShortName;
    private Short minsdkversion;
    private long versionCode;
    private String pkname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date marketApkScanTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date marketUpdateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appFetchTime;

    public MonChannelApp() {
    }

    public MonChannelApp(Integer id, String version, int appId, int apkId, String marketName, String name,
            String downloadUrl, String publisherShortName, Short minsdkversion, long versionCode, String pkname,
            Date lastUpdateTime, Date marketApkScanTime, Date marketUpdateTime, Date appFetchTime) {
        super();
        this.id = id;
        this.version = version;
        this.appId = appId;
        this.apkId = apkId;
        this.marketName = marketName;
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.publisherShortName = publisherShortName;
        this.minsdkversion = minsdkversion;
        this.versionCode = versionCode;
        this.pkname = pkname;
        this.lastUpdateTime = lastUpdateTime;
        this.marketApkScanTime = marketApkScanTime;
        this.marketUpdateTime = marketUpdateTime;
        this.appFetchTime = appFetchTime;
    }

    @Override
    public String toString() {
        return "MonMarketApp [id=" + id + ", version=" + version + ", appId=" + appId + ", apkId=" + apkId
                + ", marketName=" + marketName + ", name=" + name + ", downloadUrl=" + downloadUrl
                + ", publisherShortName=" + publisherShortName + ", minsdkversion=" + minsdkversion + ", versionCode="
                + versionCode + ", pkname=" + pkname + ", lastUpdateTime=" + lastUpdateTime + ", marketApkScanTime="
                + marketApkScanTime + ", marketUpdateTime=" + marketUpdateTime + ", appFetchTime=" + appFetchTime + "]";
    }

    @Override
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    @Column(name = "Version", nullable = false, length = 125)
    public String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "AppId", nullable = false)
    public int getAppId() {
        return this.appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @Column(name = "ApkId", nullable = false)
    public int getApkId() {
        return this.apkId;
    }

    public void setApkId(int apkId) {
        this.apkId = apkId;
    }

    @Override
    @Column(name = "MarketName", nullable = false, length = 20)
    public String getMarketName() {
        return this.marketName;
    }

    @Override
    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Column(name = "Name", nullable = false, length = 80)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    @Override
    @Column(name = "DownloadUrl", nullable = false, length = 200)
    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    @Override
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Column(name = "PublisherShortName", nullable = false, length = 145)
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

    @Override
    @Column(name = "VersionCode", nullable = false)
    public long getVersionCode() {
        return this.versionCode;
    }

    @Override
    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    @Column(name = "Pkname", nullable = false, length = 200)
    public String getPkname() {
        return this.pkname;
    }

    @Override
    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastUpdateTime", nullable = false, length = 19)
    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    @Override
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MarketApkScanTime", nullable = false, length = 19)
    public Date getMarketApkScanTime() {
        return this.marketApkScanTime;
    }

    public void setMarketApkScanTime(Date marketApkScanTime) {
        this.marketApkScanTime = marketApkScanTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MarketUpdateTime", nullable = false, length = 19)
    public Date getMarketUpdateTime() {
        return this.marketUpdateTime;
    }

    public void setMarketUpdateTime(Date marketUpdateTime) {
        this.marketUpdateTime = marketUpdateTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AppFetchTime", nullable = false, length = 19)
    public Date getAppFetchTime() {
        return this.appFetchTime;
    }

    public void setAppFetchTime(Date appFetchTime) {
        this.appFetchTime = appFetchTime;
    }

}
