package com.ijinshan.sjk.po.marketmerge;

import java.util.Date;

public class BehaviorApkOfMarketApp {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Date marketApkScanTime;
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

    public BehaviorApkOfMarketApp() {
        super();
    }

    public BehaviorApkOfMarketApp(Integer id, Date marketApkScanTime) {
        super();
        this.id = id;
        this.marketApkScanTime = marketApkScanTime;
    }

    public BehaviorApkOfMarketApp(Date marketApkScanTime) {
        super();
        this.marketApkScanTime = marketApkScanTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMarketApkScanTime() {
        return this.marketApkScanTime;
    }

    public void setMarketApkScanTime(Date marketApkScanTime) {
        this.marketApkScanTime = marketApkScanTime;
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

    public byte getAdRisk() {
        return adRisk;
    }

    public void setAdRisk(byte adRisk) {
        this.adRisk = adRisk;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getAppFetchTime() {
        return appFetchTime;
    }

    public void setAppFetchTime(Date appFetchTime) {
        this.appFetchTime = appFetchTime;
    }

}
