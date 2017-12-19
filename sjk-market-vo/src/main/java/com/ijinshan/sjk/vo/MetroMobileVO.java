package com.ijinshan.sjk.vo;
public class MetroMobileVO {

    private int appOrTagId;
    private int type;
    private int picType;
    private String bigPics;
    private String mediumPics;
    private String smallPics;
    private int catalog;
    private String ImgUrl;
    private String name;
    private String pkname;
    private String versionCode ;
    private String signatureSHA1 ;
    private String officialSigSHA1 ;
    public MetroMobileVO() {
        super();
    }
    public int getAppOrTagId() {
        return appOrTagId;
    }
    public void setAppOrTagId(int appOrTagId) {
        this.appOrTagId = appOrTagId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getPicType() {
        return picType;
    }
    public void setPicType(int picType) {
        this.picType = picType;
    }
    public String getBigPics() {
        return bigPics;
    }
    public void setBigPics(String bigPics) {
        this.bigPics = bigPics;
    }
    public String getMediumPics() {
        return mediumPics;
    }
    public void setMediumPics(String mediumPics) {
        this.mediumPics = mediumPics;
    }
    public String getSmallPics() {
        return smallPics;
    }
    public void setSmallPics(String smallPics) {
        this.smallPics = smallPics;
    }
    public int getCatalog() {
        return catalog;
    }
    public void setCatalog(int catalog) {
        this.catalog = catalog;
    }
    public String getImgUrl() {
        return ImgUrl;
    }
    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPkname() {
        return pkname;
    }
    public void setPkname(String pkname) {
        this.pkname = pkname;
    }
    public String getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
    public String getSignatureSHA1() {
        return signatureSHA1;
    }
    public void setSignatureSHA1(String signatureSHA1) {
        this.signatureSHA1 = signatureSHA1;
    }
    public String getOfficialSigSHA1() {
        return officialSigSHA1;
    }
    public void setOfficialSigSHA1(String officialSigSHA1) {
        this.officialSigSHA1 = officialSigSHA1;
    }
    
}
