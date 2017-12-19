package com.kingsoft.sjk.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ExtendData", catalog = "AppMgr")
public class ExtendData {

    private static long serialVersionUID = -94034148540552702L;
    private int id;
    private int typeId;
    private String name;
    private Long fileSize;
    private transient String downloadLink;
    private String downloadUrl;
    private transient String screenshot;
    private String imageUrl;
    private String description;
    private String softIDs;
    private String sdPath;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "TypeId")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FileSize")
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Column(name = "DownloadLink")
    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Transient
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Column(name = "Screenshot")
    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    @Transient
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SoftIDs")
    public String getSoftIDs() {
        return softIDs;
    }

    public void setSoftIDs(String softIDs) {
        this.softIDs = softIDs;
    }

    @Column(name = "SDPath")
    public String getSDPath() {
        return sdPath;
    }

    public void setSDPath(String sdPath) {
        this.sdPath = sdPath;
    }

}
