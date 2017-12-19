/**
 * 
 */
package com.ijinshan.sjk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-1-31 下午3:24:20
 * </pre>
 */
public class ScanApp implements Serializable {

    private static final long serialVersionUID = -3630686887073317643L;

    // id, catalog , pkname, minsdkversion, name, downloadUrl,
    // logoUrl, size, version, versionCode,
    // updateInfo, pathStatus,
    // signatureSHA1, officialSigSHA1

    private Integer id;
    private String version;
    private String name;
    private short catalog;
    private int size;
    private String downloadUrl;
    private String logoUrl;
    private String updateInfo;
    private Short minsdkversion;
    private long versionCode;
    private String pkname;
    private String signatureSha1;
    private String officialSigSha1;
    private Date lastUpdateTime;

    public ScanApp() {
        super();
    }

    public ScanApp(Integer id, String version, String name, short catalog, int size, String downloadUrl,
            String logoUrl, String updateInfo, Short minsdkversion, long versionCode, String pkname,
            String signatureSha1, String officialSigSha1) {
        super();
        this.id = id;
        this.version = version;
        this.name = name;
        this.catalog = catalog;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.logoUrl = logoUrl;
        this.updateInfo = updateInfo;
        this.minsdkversion = minsdkversion;
        this.versionCode = versionCode;
        this.pkname = pkname;
        this.signatureSha1 = signatureSha1;
        this.officialSigSha1 = officialSigSha1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCatalog() {
        return catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
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

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public Short getMinsdkversion() {
        return minsdkversion;
    }

    public void setMinsdkversion(Short minsdkversion) {
        this.minsdkversion = minsdkversion;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
