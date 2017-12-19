package com.ijinshan.sjk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <pre>
 * @author Du WangXi
 * Create on 2013-3-1 上午9:45:56
 * 提供给分类排序列表使用
 * </pre>
 */
public class MobileSearchApp implements Serializable {
    
    private static final long serialVersionUID = -3732186183313409243L;
    
    private int id;
    private String name = "";
    private String logoUrl = "";
    private String version = "";
    private short catalog;
    private String downloadUrl = "";
    private long versionCode;
    private String pkname = "";
    private Date lastUpdateTime;
    private int downloadRank;
    private String signatureSha1 = "";
    private String officialSigSha1 = "";
    private String adActionTypes = "";
    private String adPopupTypes = "";
    private byte virusKind;
    private int size;
    private String marketname;
    
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
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public short getCatalog() {
        return catalog;
    }
    public void setCatalog(short catalog) {
        this.catalog = catalog;
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
    public byte getVirusKind() {
        return virusKind;
    }
    public void setVirusKind(byte virusKind) {
        this.virusKind = virusKind;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getMarketname() {
        return marketname;
    }
    public void setMarketname(String marketname) {
        this.marketname = marketname;
    }
}
