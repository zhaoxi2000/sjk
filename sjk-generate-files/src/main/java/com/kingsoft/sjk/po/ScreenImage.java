package com.kingsoft.sjk.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ScreenShots", catalog = "AppMgr")
public class ScreenImage {
    private static long serialVersionUID = -94089148540552702L;
    private int id;
    private int softID;
    private String imageUrl;
    private int sort;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "SoftID")
    public int getSoftID() {
        return softID;
    }

    public void setSoftID(int softID) {
        this.softID = softID;
    }

    @Column(name = "SRC")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "OrderBy")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
