package com.ijinshan.sjk.vo;

public class MarketCatalog implements java.io.Serializable {
    private static final long serialVersionUID = 2701251142281188325L;
    private int id = 0;
    private String marketName;
    private short catalog;
    private int subCatalog;
    private String subCatalogName;
    private short targetCatalog;
    private int targetSubCatalog;
    private boolean isConvertor = false;

    public MarketCatalog() {

    }

    public MarketCatalog(String marketName, short catalog, Integer subCatalog, String subCatalogName,
            Boolean isConvertor) {
        super();
        this.marketName = marketName;
        this.catalog = catalog;
        this.subCatalog = subCatalog;
        this.subCatalogName = subCatalogName;
        this.isConvertor = isConvertor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setTargetCatalog(Short targetCatalog) {
        this.targetCatalog = targetCatalog;
    }

    public void setTargetSubCatalog(Integer targetSubCatalog) {
        this.targetSubCatalog = targetSubCatalog;
    }

    public MarketCatalog(Short targetCatalog, Integer targetSubCatalog) {
        super();
        this.targetCatalog = targetCatalog;
        this.targetSubCatalog = targetSubCatalog;
    }

    public short getTargetCatalog() {
        return targetCatalog;
    }

    public void setTargetCatalog(short targetCatalog) {
        this.targetCatalog = targetCatalog;
    }

    public int getTargetSubCatalog() {
        return targetSubCatalog;
    }

    public void setTargetSubCatalog(int targetSubCatalog) {
        this.targetSubCatalog = targetSubCatalog;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public short getCatalog() {
        return catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

    public Integer getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(Integer subCatalog) {
        this.subCatalog = subCatalog;
    }

    public String getSubCatalogName() {
        return subCatalogName;
    }

    public void setSubCatalogName(String subCatalogName) {
        this.subCatalogName = subCatalogName;
    }

    public Boolean getIsConvertor() {
        return isConvertor;
    }

    public void setIsConvertor(Boolean isConvertor) {
        this.isConvertor = isConvertor;
    }

}
