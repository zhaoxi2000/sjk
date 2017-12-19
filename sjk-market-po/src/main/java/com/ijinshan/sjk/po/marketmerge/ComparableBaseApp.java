package com.ijinshan.sjk.po.marketmerge;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//@Entity
//@Table(name = "App", catalog = "AndroidMarket")
public class ComparableBaseApp {

    private Integer id;

    protected String marketName;
    protected String pkname;
    protected long versionCode;
    protected String version;
    protected Date lastUpdateTime;
    private String downloadUrl;

    public ComparableBaseApp() {
        super();
    }

    public ComparableBaseApp(Integer id, String marketName, String pkname, long versionCode, String version,
            Date lastUpdateTime) {
        super();
        this.id = id;
        this.marketName = marketName;
        this.pkname = pkname;
        this.versionCode = versionCode;
        this.version = version;
        this.lastUpdateTime = lastUpdateTime;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "MarketName", nullable = false, length = 20)
    public String getMarketName() {
        return this.marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Column(name = "Pkname", unique = true, nullable = false, length = 200)
    public String getPkname() {
        return this.pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    @Column(name = "VersionCode", nullable = false)
    public long getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastUpdateTime", length = 19)
    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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

}
