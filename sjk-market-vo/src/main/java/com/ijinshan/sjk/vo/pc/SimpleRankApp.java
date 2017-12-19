package com.ijinshan.sjk.vo.pc;

import java.io.Serializable;

/**
 * 非常简单的榜单, 不要有简介和描述性的信息.
 * 
 * @author Linzuxiong
 */
public class SimpleRankApp implements Serializable {

    private static final long serialVersionUID = -3312565841912349432L;
    private int id;
    private String name;
    private String marketName = "";
    private String logoUrl = "";
    private String pageUrl = "";
    private byte catalog;
    private int subCatalog;
    private String pkname = "";
    private String version = "";
    private long downloadRank;
    private String downloadUrl = "";
    private long versionCode;
    private int size;
    private String signatureSha1 = "";
    private String officialSigSha1 = "";
    private byte pathStatus;
    private int scSta;

    public int getScSta() {
        return scSta;
    }

    public void setScSta(int scSta) {
        this.scSta = scSta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }
        this.name = name;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        if (marketName == null) {
            return;
        }
        this.marketName = marketName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        if (logoUrl == null) {
            return;
        }
        this.logoUrl = logoUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        if (pageUrl == null) {
            return;
        }
        this.pageUrl = pageUrl;
    }

    public byte getCatalog() {
        return catalog;
    }

    public void setCatalog(byte catalog) {
        this.catalog = catalog;
    }

    public int getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(int subCatalog) {
        this.subCatalog = subCatalog;
    }

    public String getPkname() {
        return pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (version == null) {
            return;
        }
        this.version = version;
    }

    public long getDownloadRank() {
        return downloadRank;
    }

    public void setDownloadRank(long downloadRank) {
        this.downloadRank = downloadRank;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
        if (signatureSha1 == null) {
            return;
        }
        this.signatureSha1 = signatureSha1;
    }

    public String getOfficialSigSha1() {
        return officialSigSha1;
    }

    public void setOfficialSigSha1(String officialSigSha1) {
        if (officialSigSha1 == null) {
            return;
        }
        this.officialSigSha1 = officialSigSha1;
    }

}
