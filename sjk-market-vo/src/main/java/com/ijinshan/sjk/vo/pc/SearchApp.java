package com.ijinshan.sjk.vo.pc;

import java.io.Serializable;
import java.util.Date;

public class SearchApp implements Serializable {

    private static final long serialVersionUID = -2143586420398102031L;

    private int id;
    private String name = "";
    private String logoUrl = "";
    private String version = "";
    private int marketAppId;
    private short catalog;
    private int subCatalog;
    private String downloadUrl = "";
    private String description = "";
    private long versionCode;
    private String pkname = "";
    private Date lastUpdateTime;
    private String pageUrl = "";
    private int downloadRank;
    private String signatureSha1 = "";
    private String officialSigSha1 = "";
    private String adActionTypes = "";
    private String adPopupTypes = "";
    private byte adRisk;
    private byte virusKind;
    private String marketName = "";
    private int size;
    private long freeSize;
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
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
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

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
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

    public final String getPageUrl() {
        return pageUrl;
    }

    public final void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getDownloadRank() {
        return downloadRank;
    }

    public void setDownloadRank(int downloadRank) {
        this.downloadRank = downloadRank;
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

    public final String getMarketName() {
        return marketName;
    }

    public final void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public final int getSize() {
        return size;
    }

    public final void setSize(int size) {
        this.size = size;
    }

    public final long getFreeSize() {
        return freeSize;
    }

    public final void setFreeSize(long freeSize) {
        this.freeSize = freeSize;
    }

    public final byte getPathStatus() {
        return pathStatus;
    }

    public final void setPathStatus(byte pathStatus) {
        this.pathStatus = pathStatus;
    }

}
