package com.ijinshan.sjk.vo.pc;

import java.io.Serializable;

public class ScanApp implements Serializable {

    // id, catalog , pkname, minsdkversion, name, downloadUrl,
    // logoUrl, size, version, versionCode,
    // updateInfo, pathStatus,
    // signatureSHA1, officialSigSHA1

    private static final long serialVersionUID = -5158906528637270848L;
    private short catalog;
    private String pkname;
    private Short minsdkversion;
    private String version;
    private int size;
    private String downloadUrl;
    private String logoUrl;
    private long versionCode;
    private String updateInfo;
    private byte pathStatus;
    private String signatureSha1;
    private String officialSigSha1;
    private String marketName;
    private String pageUrl;
    private int scSta;

    public int getScSta() {
        return scSta;
    }

    public void setScSta(int scSta) {
        this.scSta = scSta;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public ScanApp() {
        super();
    }

    public short getCatalog() {
        return catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

    public String getPkname() {
        return pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    public Short getMinsdkversion() {
        return minsdkversion;
    }

    public void setMinsdkversion(Short minsdkversion) {
        this.minsdkversion = minsdkversion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public byte getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(byte pathStatus) {
        this.pathStatus = pathStatus;
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

    public final String getMarketName() {
        return marketName;
    }

    public final void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
