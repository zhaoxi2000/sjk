package com.ijinshan.sjk.vo;

public class TagMobileVo {
    private int id;
    private String name;
    private String tagDesc;
    private int catalog;
    private byte tagType;
    private String imgUrl;
    private int picType;
    private String bigPics;
    private String mediumPics;
    private String smallPics;

    public TagMobileVo() {

    }

    private TagMobileVo(int id, String name, String tagDesc, int catalog, byte tagType, String imgUrl, int picType,
            String bigPics, String mediumPics, String smallPics) {
        super();
        this.id = id;
        this.name = name;
        this.tagDesc = tagDesc;
        this.catalog = catalog;
        this.tagType = tagType;
        this.imgUrl = imgUrl;
        this.picType = picType;
        this.bigPics = bigPics;
        this.mediumPics = mediumPics;
        this.smallPics = smallPics;
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

    public byte getTagType() {
        return tagType;
    }

    public void setTagType(byte tagType) {
        this.tagType = tagType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

}
