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

@Entity
public class MonApp implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private int marketAppId;
    private String marketName;
    private String newMarketName;
    private String name;
    private String newName;
    private String version;
    private long versionCode;
    private String newVersion;
    private long newVersionCode;
    private String downloadUrl;
    private String signatureSHA1;
    private String pkname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date marketApkScanTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appFetchTime;
    private int apkId;

    public MonApp() {
    }

    public MonApp(Integer id, int marketAppId, String marketName, String newMarketName, String name, String newName,
            String version, String newVersion, long versionCode, long newVersionCode, String downloadUrl,
            String signatureSHA1, String pkname, Date marketApkScanTime, Date appFetchTime, int apkId) {
        super();
        this.id = id;
        this.marketAppId = marketAppId;
        this.marketName = marketName;
        this.newMarketName = newMarketName;
        this.name = name;
        this.newName = newName;
        this.version = version;
        this.newVersion = newVersion;
        this.versionCode = versionCode;
        this.newVersionCode = newVersionCode;
        this.downloadUrl = downloadUrl;
        this.signatureSHA1 = signatureSHA1;
        this.pkname = pkname;
        this.marketApkScanTime = marketApkScanTime;
        this.appFetchTime = appFetchTime;
        this.apkId = apkId;
    }

    @Override
    public String toString() {
        return "MonApp [id=" + id + ", marketAppId=" + marketAppId + ", marketName=" + marketName + ", newMarketName="
                + newMarketName + ", name=" + name + ", newName=" + newName + ", version=" + version + ", newVersion="
                + newVersion + ", versionCode=" + versionCode + ", newVersionCode=" + newVersionCode + ", downloadUrl="
                + downloadUrl + ", signatureSHA1=" + signatureSHA1 + ", pkname=" + pkname + ", marketApkScanTime="
                + marketApkScanTime + ", appFetchTime=" + appFetchTime + ", apkId=" + apkId + "]";
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

    @Column(name = "Version", nullable = false, length = 125)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "MarketAppId", nullable = false)
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

    @Column(name = "Name", nullable = false, length = 80)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DownloadUrl", nullable = false, length = 200)
    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Column(name = "VersionCode", nullable = false)
    public long getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    @Column(name = "Pkname", nullable = false, length = 200)
    public String getPkname() {
        return this.pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    @Column(name = "ApkId", nullable = false)
    public int getApkId() {
        return this.apkId;
    }

    public void setApkId(int apkId) {
        this.apkId = apkId;
    }

    @Column(name = "NewMarketName", nullable = false)
    public String getNewMarketName() {
        return newMarketName;
    }

    public void setNewMarketName(String newMarketName) {
        this.newMarketName = newMarketName;
    }

    @Column(name = "NewName", nullable = false)
    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Column(name = "NewVersion", nullable = false)
    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    @Column(name = "NewVersionCode", nullable = false)
    public long getNewVersionCode() {
        return newVersionCode;
    }

    public void setNewVersionCode(long newVersionCode) {
        this.newVersionCode = newVersionCode;
    }

    @Column(name = "SignatureSHA1", nullable = false)
    public String getSignatureSHA1() {
        return signatureSHA1;
    }

    public void setSignatureSHA1(String signatureSHA1) {
        this.signatureSHA1 = signatureSHA1;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MarketApkScanTime", nullable = false, length = 19)
    public Date getMarketApkScanTime() {
        return marketApkScanTime;
    }

    public void setMarketApkScanTime(Date marketApkScanTime) {
        this.marketApkScanTime = marketApkScanTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AppFetchTime", nullable = false, length = 19)
    public Date getAppFetchTime() {
        return appFetchTime;
    }

    public void setAppFetchTime(Date appFetchTime) {
        this.appFetchTime = appFetchTime;
    }

}
