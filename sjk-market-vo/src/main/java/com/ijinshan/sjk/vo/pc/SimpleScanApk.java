package com.ijinshan.sjk.vo.pc;

public class SimpleScanApk {

    private static final long serialVersionUID = -5158906528637270848L;
    private Short minsdkversion;
    private String version = "";
    private int size;
    private String downloadUrl = "";
    private long versionCode;
    private String updateInfo;
    private byte pathStatus;
    private String signatureSha1 = "";
    private String marketName = "";
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

    public final Short getMinsdkversion() {
        return minsdkversion;
    }

    public final void setMinsdkversion(Short minsdkversion) {
        this.minsdkversion = minsdkversion;
    }

    public final String getVersion() {
        return version;
    }

    public final void setVersion(String version) {
        if (version == null) {
            return;
        }
        this.version = version;
    }

    public final int getSize() {
        return size;
    }

    public final void setSize(int size) {
        this.size = size;
    }

    public final String getDownloadUrl() {
        return downloadUrl;
    }

    public final void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public final long getVersionCode() {
        return versionCode;
    }

    public final void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public final String getUpdateInfo() {
        return updateInfo;
    }

    public final void setUpdateInfo(String updateInfo) {
        if (updateInfo == null) {
            return;
        }
        this.updateInfo = updateInfo;
    }

    public final byte getPathStatus() {
        return pathStatus;
    }

    public final void setPathStatus(byte pathStatus) {
        this.pathStatus = pathStatus;
    }

    public final String getSignatureSha1() {
        return signatureSha1;
    }

    public final void setSignatureSha1(String signatureSha1) {
        if (signatureSha1 == null) {
            return;
        }
        this.signatureSha1 = signatureSha1;
    }

    public final String getMarketName() {
        return marketName;
    }

    public final void setMarketName(String marketName) {
        if (marketName == null) {
            return;
        }
        this.marketName = marketName;
    }

    public static final long getSerialversionuid() {
        return serialVersionUID;
    }

}
