package com.ijinshan.sjk.vo;

public class Downloads implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long marketAppDown;
    private String marketName;

    public long getMarketAppDown() {
        return marketAppDown;
    }

    public void setMarketAppDown(long marketAppDown) {
        this.marketAppDown = marketAppDown;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
