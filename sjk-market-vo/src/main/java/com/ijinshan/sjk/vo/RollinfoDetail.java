package com.ijinshan.sjk.vo;

import com.ijinshan.sjk.po.Rollinfo;

public final class RollinfoDetail extends Rollinfo {
    private static final long serialVersionUID = 1L;
    // app name
    private String name;

    public RollinfoDetail(int appId, boolean recommend, int rank, String name) {
        this.appId = appId;
        this.recommend = recommend;
        this.rank = rank;
        this.name = name;
    }

    public RollinfoDetail() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
