package com.kingsoft.sjk.po;

/**
 * 基类
 * 
 * @author LinZuXiong
 */
public class App implements java.io.Serializable {

    private static final long serialVersionUID = -1533523661857849140L;

    protected int id;
    protected String name;
    protected String packageName;
    protected String logoUrl;
    protected String version;
    protected String downloadUrl;
    protected String oneKeyPackUrl;
    protected long size;
    protected long oneKeyPackSize;
    protected String pageUrl;
    protected transient String oneKeyPack;
    protected transient String downloadLink;
    protected transient String logo;
    protected boolean haveData;

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getOneKeyPackUrl() {
        return oneKeyPackUrl;
    }

    public void setOneKeyPackUrl(String oneKeyPackUrl) {
        this.oneKeyPackUrl = oneKeyPackUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getOneKeyPackSize() {
        return oneKeyPackSize;
    }

    public void setOneKeyPackSize(long oneKeyPackSize) {
        this.oneKeyPackSize = oneKeyPackSize;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getOneKeyPack() {
        return oneKeyPack;
    }

    public void setOneKeyPack(String oneKeyPack) {
        this.oneKeyPack = oneKeyPack;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isHaveData() {
        return haveData;
    }

    public void setHaveData(boolean haveData) {
        this.haveData = haveData;
    }

    public float getStarRating() {
        return starRating;
    }

    public void setStarRating(float starRating) {
        this.starRating = starRating;
    }

    public int getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(int downTimes) {
        this.downTimes = downTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected float starRating;
    protected int downTimes;
    protected String description;
    protected String shortDesc;
    protected String status;

}
