package com.ijinshan.sjk.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "App", catalog = "AndroidMarket", uniqueConstraints = @UniqueConstraint(columnNames = "Pkname"))
public class DownloadOfApp {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;

    private int realDownload;
    private int deltaDownload;
    private int lastDayDownload;
    private int lastDayDelta;
    private int lastWeekDownload;
    private int lastWeekDelta;

    public DownloadOfApp() {
        super();
    }

    public DownloadOfApp(Integer id, String name, int lastDayDownload, int lastDayDelta, int lastWeekDownload,
            int lastWeekDelta) {
        super();
        this.id = id;
        this.name = name;
        this.lastDayDownload = lastDayDownload;
        this.lastDayDelta = lastDayDelta;
        this.lastWeekDownload = lastWeekDownload;
        this.lastWeekDelta = lastWeekDelta;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "RealDownload", nullable = false)
    public int getRealDownload() {
        return this.realDownload;
    }

    public void setRealDownload(int realDownload) {
        this.realDownload = realDownload;
    }

    @Column(name = "DeltaDownload", nullable = false)
    public int getDeltaDownload() {
        return this.deltaDownload;
    }

    public void setDeltaDownload(int deltaDownload) {
        this.deltaDownload = deltaDownload;
    }

    @Column(name = "LastDayDownload", nullable = false)
    public int getLastDayDownload() {
        return this.lastDayDownload;
    }

    public void setLastDayDownload(int lastDayDownload) {
        this.lastDayDownload = lastDayDownload;
    }

    @Column(name = "LastDayDelta", nullable = false)
    public int getLastDayDelta() {
        return this.lastDayDelta;
    }

    public void setLastDayDelta(int lastDayDelta) {
        this.lastDayDelta = lastDayDelta;
    }

    @Column(name = "LastWeekDownload", nullable = false)
    public int getLastWeekDownload() {
        return this.lastWeekDownload;
    }

    public void setLastWeekDownload(int lastWeekDownload) {
        this.lastWeekDownload = lastWeekDownload;
    }

    @Column(name = "LastWeekDelta", nullable = false)
    public int getLastWeekDelta() {
        return this.lastWeekDelta;
    }

    public void setLastWeekDelta(int lastWeekDelta) {
        this.lastWeekDelta = lastWeekDelta;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
