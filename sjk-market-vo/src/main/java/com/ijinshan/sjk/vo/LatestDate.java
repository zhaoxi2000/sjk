package com.ijinshan.sjk.vo;

public class LatestDate {
    private short catalog;
    private int subCatalog;
    private String lastUpdateTime;

    public LatestDate() {
        super();
    }

    public LatestDate(short catalog, int subCatalog, String lastUpdateTime) {
        super();
        this.catalog = catalog;
        this.subCatalog = subCatalog;
        this.lastUpdateTime = lastUpdateTime;
    }

    public short getCatalog() {
        return catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

    public int getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(int subCatalog) {
        this.subCatalog = subCatalog;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}
