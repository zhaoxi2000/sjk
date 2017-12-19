package com.ijinshan.sjk.vo.pc.biggame;

import java.util.Date;

public class Apk {

    private int bigGamePackId;
    private int marketAppId;
    private byte cputype;
    private String url = "";
    private int size;
    private long freeSize;
    private Date marketUpdateTime;
    private String unsupportPhoneType = "";

    public int getBigGamePackId() {
        return bigGamePackId;
    }

    public void setBigGamePackId(int bigGamePackId) {
        this.bigGamePackId = bigGamePackId;
    }

    public int getMarketAppId() {
        return marketAppId;
    }

    public void setMarketAppId(int marketAppId) {
        this.marketAppId = marketAppId;
    }

    public byte getCputype() {
        return cputype;
    }

    public void setCputype(byte cputype) {
        this.cputype = cputype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url == null) {
            return;
        }
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(long freeSize) {
        this.freeSize = freeSize;
    }

    public Date getMarketUpdateTime() {
        return marketUpdateTime;
    }

    public void setMarketUpdateTime(Date marketUpdateTime) {
        this.marketUpdateTime = marketUpdateTime;
    }

    public String getUnsupportPhoneType() {
        return unsupportPhoneType;
    }

    public void setUnsupportPhoneType(String unsupportPhoneType) {
        if (unsupportPhoneType == null) {
            return;
        }
        this.unsupportPhoneType = unsupportPhoneType;
    }

}
