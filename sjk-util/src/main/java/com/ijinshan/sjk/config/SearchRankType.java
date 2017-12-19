package com.ijinshan.sjk.config;

public enum SearchRankType {
    DOWNLOAD("DOWNLOAD"), //
    DOCUMENT("DOCUMENT"), //
    ONLY_NAME_DOCUMENT("ONLY_NAME_DOCUMENT"), //
    ONLY_NAME_DOWNLOAD("ONLY_NAME_DOWNLOAD"), //
    ;//
    private String name;

    SearchRankType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
