package com.ijinshan.sjk.vo;

import com.ijinshan.sjk.po.App;

public class TagApps {
    private int id;
    private int rank;
    private String shortDesc;
    private App app;

    public TagApps() {

    }

    public TagApps(int id, int rank, String shortDesc) {
        super();
        this.id = id;
        this.rank = rank;
        this.shortDesc = shortDesc;
    }

    public TagApps(int id, int rank, String shortDesc, App app) {
        super();
        this.id = id;
        this.rank = rank;
        this.shortDesc = shortDesc;
        this.app = app;
        String appDesc = app.getDescription();
        if (appDesc != null && !appDesc.isEmpty()) {
            appDesc = appDesc.replaceAll("\\<.*?>", "");
            appDesc = appDesc.replaceAll("\\</.+>", "");
            appDesc = appDesc.replaceAll("\\</.+", "");
            appDesc = appDesc.replaceAll("\\<.+", "");
            final int len = appDesc.length() > 50 ? 50 : appDesc.length();
            app.setDescription(appDesc.substring(0, len));
        } else {
            app.setDescription("");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

}
