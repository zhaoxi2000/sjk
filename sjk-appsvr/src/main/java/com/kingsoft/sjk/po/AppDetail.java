package com.kingsoft.sjk.po;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class AppDetail extends App {

    private static final long serialVersionUID = 5755756813223754518L;
    private String version;
    private String language;
    private int rate;
    private String osVersionGroup;
    private String publisherName;
    private String description;
    private String updateInfo;
    private List<ExtendData> extendDataList;
    private List<ScreenImage> screenImages;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditDate;

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getOsVersionGroup() {
        return osVersionGroup;
    }

    public void setOsVersionGroup(String osVersionGroup) {
        this.osVersionGroup = osVersionGroup;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public List<ScreenImage> getScreenImages() {
        return screenImages;
    }

    public void setScreenImages(List<ScreenImage> screenImages) {
        this.screenImages = screenImages;
    }

    public List<ExtendData> getExtendDataList() {
        return extendDataList;
    }

    public void setExtendDataList(List<ExtendData> extendDataList) {
        this.extendDataList = extendDataList;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

}
