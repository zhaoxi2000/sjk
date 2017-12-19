package com.kingsoft.sjk.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "AndroidApp", catalog = "AppMgr")
public class App {

    private static long serialVersionUID = 5755756813223754518L;
    protected int id;
    protected String name;
    protected String originalName;
    protected String otherNames;
    protected String publisherShortName;
    protected int catalog;
    protected int subCatalog;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected transient Date updateDate;
    protected String updateDateStr;
    protected String version;
    protected int size;
    protected long oneKeyPackSize;
    protected String language;
    protected transient int ageLimit;
    protected String oSVersion;
    protected String resolution;
    protected transient String platform;
    protected String description;
    protected String updateInfo;
    protected String associateIDs;
    protected int rank;
    protected String logo;
    protected transient String qRCode;
    protected String downLoadLink;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected transient Date postDate;
    protected String postDateStr;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected transient Date editDate;
    protected String editDateStr;
    protected String poster;
    protected int state;
    protected int haveData;
    protected String permit;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected transient Date eUpdateDate;
    protected String eUpdateDateStr;
    protected int isGray;
    protected transient int oSVersionGroupID;
    protected int authType;
    protected transient int sourceID;
    protected transient String sourceUrl;
    protected transient String pKName;
    protected transient int versionCode;
    protected transient byte isReview;
    protected transient byte allowGuest;
    protected transient byte allowRepeat;
    protected transient int softType;
    protected transient int noStandardVersion;
    protected List<ExtendData> extendDataList;
    protected List<ScreenImage> screenImages;
    protected String images;
    protected String oSVersionGroupName;
    protected String downloadUrl;
    protected String logoUrl;
    protected String pageUrl;
    protected transient String oneKeyPack;
    protected String oneKeyPackUrl;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "OriginalName")
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Column(name = "OtherNames")
    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    @Column(name = "PublisherShortName")
    public String getPublisherShortName() {
        return publisherShortName;
    }

    public void setPublisherShortName(String publisherShortName) {
        this.publisherShortName = publisherShortName;
    }

    @Column(name = "Catalog")
    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
    }

    @Column(name = "SubCatalog")
    public int getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(int subCatalog) {
        this.subCatalog = subCatalog;
    }

    @Column(name = "UpdateDate")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "Version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "Size")
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Column(name = "Language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(name = "AgeLimit")
    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    @Column(name = "OSVersion")
    public String getOSVersion() {
        return oSVersion;
    }

    public void setOSVersion(String oSVersion) {
        this.oSVersion = oSVersion;
    }

    @Column(name = "Resolution")
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Column(name = "Platform")
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Column(name = "OneKeyPackSize")
    public long getOneKeyPackSize() {
        return oneKeyPackSize;
    }

    public void setOneKeyPackSize(long oneKeyPackSize) {
        this.oneKeyPackSize = oneKeyPackSize;
    }

    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "UpdateInfo")
    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    @Column(name = "AssociateIDs")
    public String getAssociateIDs() {
        return associateIDs;
    }

    public void setAssociateIDs(String associateIDs) {
        this.associateIDs = associateIDs;
    }

    @Column(name = "Rank")
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Column(name = "Logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Column(name = "QRCode")
    public String getqRCode() {
        return qRCode;
    }

    public void setqRCode(String qRCode) {
        this.qRCode = qRCode;
    }

    @Column(name = "DownLoadLink")
    public String getDownLoadLink() {
        return downLoadLink;
    }

    public void setDownLoadLink(String downLoadLink) {
        this.downLoadLink = downLoadLink;
    }

    @Column(name = "PostDate")
    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Column(name = "EditDate")
    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    @Column(name = "Poster")
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Column(name = "State")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Column(name = "HaveData")
    public int getHaveData() {
        return haveData;
    }

    public void setHaveData(int haveData) {
        this.haveData = haveData;
    }

    @Column(name = "Permit")
    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    @Column(name = "EUpdateDate")
    public Date geteUpdateDate() {
        return eUpdateDate;
    }

    public void seteUpdateDate(Date eUpdateDate) {
        this.eUpdateDate = eUpdateDate;
    }

    @Column(name = "IsGray")
    public int getIsGray() {
        return isGray;
    }

    public void setIsGray(int isGray) {
        this.isGray = isGray;
    }

    @Column(name = "OSVersionGroupID")
    public int getOSVersionGroupID() {
        return oSVersionGroupID;
    }

    public void setOSVersionGroupID(int oSVersionGroupID) {
        this.oSVersionGroupID = oSVersionGroupID;
    }

    @Transient
    public String getOSVersionGroupName() {
        return oSVersionGroupName;
    }

    public void setOSVersionGroupName(String oSVersionGroupName) {
        this.oSVersionGroupName = oSVersionGroupName;
    }

    @Column(name = "AuthType")
    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    @Column(name = "SourceID")
    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    @Column(name = "SourceUrl")
    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Column(name = "PKName")
    public String getpKName() {
        return pKName;
    }

    public void setpKName(String pKName) {
        this.pKName = pKName;
    }

    @Column(name = "VersionCode")
    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    @Column(name = "IsReview")
    public byte getIsReview() {
        return isReview;
    }

    public void setIsReview(byte isReview) {
        this.isReview = isReview;
    }

    @Column(name = "AllowGuest")
    public byte getAllowGuest() {
        return allowGuest;
    }

    public void setAllowGuest(byte allowGuest) {
        this.allowGuest = allowGuest;
    }

    @Column(name = "AllowRepeat")
    public byte getAllowRepeat() {
        return allowRepeat;
    }

    public void setAllowRepeat(byte allowRepeat) {
        this.allowRepeat = allowRepeat;
    }

    @Column(name = "SoftType")
    public int getSoftType() {
        return softType;
    }

    public void setSoftType(int softType) {
        this.softType = softType;
    }

    @Column(name = "OneKeyPack")
    public String getOneKeyPack() {
        return oneKeyPack;
    }

    public void setOneKeyPack(String oneKeyPack) {
        this.oneKeyPack = oneKeyPack;
    }

    @Column(name = "NoStandardVersion")
    public int getNoStandardVersion() {
        return noStandardVersion;
    }

    public void setNoStandardVersion(int noStandardVersion) {
        this.noStandardVersion = noStandardVersion;
    }

    @Transient
    public List<ExtendData> getExtendDataList() {
        return extendDataList;
    }

    public void setExtendDataList(List<ExtendData> extendDataList) {
        this.extendDataList = extendDataList;
    }

    @Transient
    public List<ScreenImage> getScreenImages() {
        return screenImages;
    }

    public void setScreenImages(List<ScreenImage> screenImages) {
        this.screenImages = screenImages;
    }

    @Transient
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Transient
    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Transient
    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Transient
    public String getOneKeyPackUrl() {
        return oneKeyPackUrl;
    }

    public void setOneKeyPackUrl(String oneKeyPackUrl) {
        this.oneKeyPackUrl = oneKeyPackUrl;
    }

    @Transient
    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    @Transient
    public String getoSVersion() {
        return oSVersion;
    }

    public void setoSVersion(String oSVersion) {
        this.oSVersion = oSVersion;
    }

    @Transient
    public String getPostDateStr() {
        return postDateStr;
    }

    public void setPostDateStr(String postDateStr) {
        this.postDateStr = postDateStr;
    }

    @Transient
    public String getEditDateStr() {
        return editDateStr;
    }

    public void setEditDateStr(String editDateStr) {
        this.editDateStr = editDateStr;
    }

    @Transient
    public String geteUpdateDateStr() {
        return eUpdateDateStr;
    }

    public void seteUpdateDateStr(String eUpdateDateStr) {
        this.eUpdateDateStr = eUpdateDateStr;
    }

    @Transient
    public int getoSVersionGroupID() {
        return oSVersionGroupID;
    }

    public void setoSVersionGroupID(int oSVersionGroupID) {
        this.oSVersionGroupID = oSVersionGroupID;
    }

    @Transient
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Transient
    public String getoSVersionGroupName() {
        return oSVersionGroupName;
    }

    public void setoSVersionGroupName(String oSVersionGroupName) {
        this.oSVersionGroupName = oSVersionGroupName;
    }

}
