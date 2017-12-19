package com.ijinshan.sjk.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

//Generated 2013-4-10 15:20:59 by Hibernate Tools 3.4.0.CR1
@Entity
@Table(name = "MonUserChannelApp", catalog = "AndroidMarket", uniqueConstraints = @UniqueConstraint(columnNames = "Id"))
public class MonUserChannelApp {
    private static final long serialVersionUID = 4697861071808642368L;
    private int id;
    private String appName;
    private String devName;
    private String userName;
    private String marketName;
    private String version;
    private long versionCode;
    private String coverMarketName;
    private String coverVerson;
    private long coverVersionCode;
    private int apkId;
    private String phone;
    private boolean acceptSms;
    private String email;
    private boolean acceptMail;
    private boolean status;
    private boolean autoCover;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;
    private Date lastFetchTime;
    private int sMailStatus;

    public MonUserChannelApp() {
    };

    public MonUserChannelApp(String appName, String devName, String marketName, int apkId) {
        super();
        this.appName = appName;
        this.devName = devName;
        this.marketName = marketName;
        this.apkId = apkId;
        this.acceptSms = true;
        this.acceptMail = true;
        this.status = true;
        this.autoCover = false;
        this.createTime = new Date(); 
    }

    public MonUserChannelApp(String appName, String devName, String marketName, String version, long versionCode,
            int apkId) {
        super();
        this.appName = appName;
        this.devName = devName;
        this.marketName = marketName;
        this.version = version;
        this.versionCode = versionCode;
        this.apkId = apkId;
        this.acceptSms = true;
        this.acceptMail = true;
        this.status = true;
        this.autoCover = false;
        this.createTime = new Date();
    }

    public MonUserChannelApp(int id, String appName, String devName, String userName, String marketName,
            String version, long versionCode, String coverMarketName, String coverVerson, long coverVersionCode,
            int apkId, String phone, boolean acceptSms, String email, boolean acceptMail, boolean status,
            boolean autoCover, Date createTime, Date optTime) {
        super();
        this.id = id;
        this.appName = appName;
        this.devName = devName;
        this.userName = userName;
        this.marketName = marketName;
        this.version = version;
        this.versionCode = versionCode;
        this.coverMarketName = coverMarketName;
        this.coverVerson = coverVerson;
        this.coverVersionCode = coverVersionCode;
        this.apkId = apkId;
        this.phone = phone;
        this.acceptSms = acceptSms;
        this.email = email;
        this.acceptMail = acceptMail;
        this.status = status;
        this.autoCover = autoCover;
        this.createTime = createTime; 
    }

    @Override
    public String toString() {
        return "MonUserChannelApp [id=" + id + ", appName=" + appName + ", devName=" + devName + ", userName="
                + userName + ", marketName=" + marketName + ", version=" + version + ", versionCode=" + versionCode
                + ", coverMarketName=" + coverMarketName + ", coverVerson=" + coverVerson + ", coverVersionCode="
                + coverVersionCode + ", apkId=" + apkId + ", phone=" + phone + ", acceptSms=" + acceptSms + ", email="
                + email + ", acceptMail=" + acceptMail + ", status=" + status + ", autoCover=" + autoCover
                + ", createTime=" + createTime + ", optTime=" + optTime + "]";
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "AppName", nullable = false, length = 100)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Column(name = "UserName", nullable = false, length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "DevName", nullable = false, length = 50)
    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    @Column(name = "MarketName", length = 50)
    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Column(name = "ApkId")
    public int getApkId() {
        return apkId;
    }

    public void setApkId(int apkId) {
        this.apkId = apkId;
    }

    @Column(name = "Phone", length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "AcceptSms")
    public boolean isAcceptSms() {
        return acceptSms;
    }

    public void setAcceptSms(boolean acceptSms) {
        this.acceptSms = acceptSms;
    }

    @Column(name = "Email", length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "AcceptMail")
    public boolean isAcceptMail() {
        return acceptMail;
    }

    public void setAcceptMail(boolean acceptMail) {
        this.acceptMail = acceptMail;
    }

    @Column(name = "SMailStatus")
    public int getSMailStatus() {
        return sMailStatus;
    }

    public void setSMailStatus(int sMailStatus) {
        this.sMailStatus = sMailStatus;
    }

    @Column(name = "Status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "AutoCover")
    public boolean isAutoCover() {
        return autoCover;
    }

    public void setAutoCover(boolean autoCover) {
        this.autoCover = autoCover;
    }

    @Column(name = "Version", nullable = false, length = 20)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "VersionCode", nullable = false)
    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    @Column(name = "CoverMarketName", nullable = false, length = 50)
    public String getCoverMarketName() {
        return coverMarketName;
    }

    public void setCoverMarketName(String coverMarketName) {
        this.coverMarketName = coverMarketName;
    }

    @Column(name = "CoverVerson", nullable = false, length = 20)
    public String getCoverVerson() {
        return coverVerson;
    }

    public void setCoverVerson(String coverVerson) {
        this.coverVerson = coverVerson;
    }

    @Column(name = "CoverVersionCode", nullable = false)
    public long getCoverVersionCode() {
        return coverVersionCode;
    }

    public void setCoverVersionCode(long coverVersionCode) {
        this.coverVersionCode = coverVersionCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateTime", nullable = false, length = 19)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OptTime", nullable = false, length = 19)
    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastFetchTime", nullable = false, length = 19)
    public Date getLastFetchTime() {
        return lastFetchTime;
    }

    public void setLastFetchTime(Date lastFetchTime) {
        this.lastFetchTime = lastFetchTime;
    }

}
