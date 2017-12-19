package com.kingsoft.sjk.po;

public class ScreenImage {
    private static long serialVersionUID = -94089148540552702L;
    private String imageUrl;
    private int sort;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static void setSerialversionuid(long serialversionuid) {
        serialVersionUID = serialversionuid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
