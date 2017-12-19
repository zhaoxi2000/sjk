package com.ijinshan.sjk.po;

public class CatalogInfo extends Catalog {

    private static final long serialVersionUID = 1L;

    private transient int id;
    private transient int rank;
    private transient String keywords;
    private transient String description;

    private short catalog;
    private int subCatalog;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
