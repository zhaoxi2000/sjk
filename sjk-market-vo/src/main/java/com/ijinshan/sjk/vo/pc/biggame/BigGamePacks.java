/**
 * 
 */
package com.ijinshan.sjk.vo.pc.biggame;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-4-16 下午4:16:33
 * </pre>
 */
public class BigGamePacks {

    private int id;
    private String name;
    private String marketName = "";
    private int marketAppId = 0;
    private String logoUrl = "";
    private String pageUrl = "";
    private int subCatalog;
    private String pkname = "";
    private String version = "";
    private long downloadRank;
    private long versionCode;
    private String signatureSha1 = "";
    private String officialSigSha1 = "";
    private int scSta;
    private short pathStatus = 0;

    private String indexImgUrl = "";
    private String adActionTypes = "";
    private String adPopupTypes = "";
    private short virusKind = 0;
    private String shortDesc = "";
    private String description = "";

    List<Apk> apkPackList = new ArrayList<Apk>();

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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
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
        this.version = version;
    }

    public long getDownloadRank() {
        return downloadRank;
    }

    public void setDownloadRank(long downloadRank) {
        this.downloadRank = downloadRank;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
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

    public int getScSta() {
        return scSta;
    }

    public void setScSta(int scSta) {
        this.scSta = scSta;
    }

    public String getIndexImgUrl() {
        return indexImgUrl;
    }

    public void setIndexImgUrl(String indexImgUrl) {
        this.indexImgUrl = indexImgUrl;
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

    public final short getVirusKind() {
        return virusKind;
    }

    public final void setVirusKind(short virusKind) {
        this.virusKind = virusKind;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
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
            final int len = description.length() > 20 ? 20 : description.length();
            this.description = description.substring(0, len);
        }
    }

    public final int getMarketAppId() {
        return marketAppId;
    }

    public final void setMarketAppId(int marketAppId) {
        this.marketAppId = marketAppId;
    }

    public final short getPathStatus() {
        return pathStatus;
    }

    public final void setPathStatus(short pathStatus) {
        this.pathStatus = pathStatus;
    }

    public List<Apk> getApkPackList() {
        return apkPackList;
    }

    public void setApkPackList(List<Apk> apkPackList) {
        this.apkPackList = apkPackList;
    }

}
