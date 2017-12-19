package com.ijinshan.sjk.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Viapp", catalog = "AndroidMarket")
public class Viapp implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(Viapp.class);

    private int id;
    private int catalog;
    private int subCatalog;
    private int noAds;
    private int official;
    private int downloadRank;
    private int lastDayDelta;
    private int lastWeekDelta;
    private int realDownload;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    public Viapp() {

    }

    public Viapp(int id, int catalog, int subCatalog, int noAds, int official, int downloadRank,
            Date lastUpdateTime,int lastDayDelta,int lastWeekDelta,int realDownload) {
        this.id = id;
        this.catalog = catalog;
        this.subCatalog = subCatalog;
        this.noAds = noAds;
        this.official = official;
        this.downloadRank = downloadRank;
        this.lastUpdateTime = lastUpdateTime;
        this.lastDayDelta = lastDayDelta;
        this.lastWeekDelta = lastWeekDelta;
        this.realDownload = realDownload;
    }

    
    
    
    @Column(name = "LastDayDelta", nullable = false)
    public final int getLastDayDelta() {
        return lastDayDelta;
    }

    public final void setLastDayDelta(int lastDayDelta) {
        this.lastDayDelta = lastDayDelta;
    }
    @Column(name = "LastWeekDelta", nullable = false)
    public final int getLastWeekDelta() {
        return lastWeekDelta;
    }

    public final void setLastWeekDelta(int lastWeekDelta) {
        this.lastWeekDelta = lastWeekDelta;
    }
    @Column(name = "RealDownload", nullable = false)
    public final int getRealDownload() {
        return realDownload;
    }

    public final void setRealDownload(int realDownload) {
        this.realDownload = realDownload;
    }

    @Id
    @Column(name = "Id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Catalog", nullable = false)
    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
    }

    @Column(name = "SubCatalog", nullable = false)
    public int getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(int subCatalog) {
        this.subCatalog = subCatalog;
    }

    @Column(name = "NoAds", nullable = false)
    public int getNoAds() {
        return noAds;
    }

    public void setNoAds(int noAds) {
        this.noAds = noAds;
    }

    @Column(name = "Official", nullable = false)
    public int getOfficial() {
        return official;
    }

    public void setOfficial(int official) {
        this.official = official;
    }

    @Column(name = "DownloadRank", nullable = false)
    public int getDownloadRank() {
        return downloadRank;
    }

    public void setDownloadRank(int downloadRank) {
        this.downloadRank = downloadRank;
    }

    @Column(name = "LastUpdateTime", nullable = false)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static Logger getLogger() {
        return logger;
    }

}
