package com.ijinshan.sjk.config;

public enum EnumMarket {
    YINGYONGSO("yingyongso"), SHOUJIKONG_CHANNEL("shoujikong_channel"), // 来自应用汇
    IJINSHAN("ijinshan"), SHOUJIKONG("shoujikong"), // 来自本系统
    EOEMARKET("eoemarket"), //
    APPCHINA("AppChina"), //
    HIAPK("hiapk"), //
    M91("m91"), //
    ;
    private String name;

    EnumMarket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
