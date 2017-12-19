package com.ijinshan.sjk.vo.pc;

public class App4TagList {
    private Integer id;
    private long freeSize;
    private String name;
    private String marketName;
    private String logoUrl;
    private String pageUrl;
    private short catalog;
    private int subCatalog;
    private String pkname;
    private String version;
    private String downloadUrl;
    private long versionCode;
    private int size;
    private String signatureSha1;
    private String officialSigSha1;
    private byte pathStatus;
    private int scSta;
    private App4TagList() {
        super();
        // TODO Auto-generated constructor stub
    }
    private App4TagList(Integer id, long freeSize, String name, String marketName, String logoUrl, String pageUrl,
            short catalog, int subCatalog, String pkname, String version, String downloadUrl, long versionCode,
            int size, String signatureSha1, String officialSigSha1, byte pathStatus, int scSta) {
        super();
        this.id = id;
        this.freeSize = freeSize;
        this.name = name;
        this.marketName = marketName;
        this.logoUrl = logoUrl;
        this.pageUrl = pageUrl;
        this.catalog = catalog;
        this.subCatalog = subCatalog;
        this.pkname = pkname;
        this.version = version;
        this.downloadUrl = downloadUrl;
        this.versionCode = versionCode;
        this.size = size;
        this.signatureSha1 = signatureSha1;
        this.officialSigSha1 = officialSigSha1;
        this.pathStatus = pathStatus;
        this.scSta = scSta;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public long getFreeSize() {
        return freeSize;
    }
    public void setFreeSize(long freeSize) {
        this.freeSize = freeSize;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMarketName() {
        return marketName;
    }
    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }
    public String getLogoUrl() {
        return logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    public String getPageUrl() {
        return pageUrl;
    }
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
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
    public String getPkname() {
        return pkname;
    }
    public void setPkname(String pkname) {
        this.pkname = pkname;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public long getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getSignatureSha1() {
        return signatureSha1;
    }
    public void setSignatureSha1(String signatureSha1) {
        this.signatureSha1 = signatureSha1;
    }
    public String getOfficialSigSha1() {
        return officialSigSha1;
    }
    public void setOfficialSigSha1(String officialSigSha1) {
        this.officialSigSha1 = officialSigSha1;
    }
    public byte getPathStatus() {
        return pathStatus;
    }
    public void setPathStatus(byte pathStatus) {
        this.pathStatus = pathStatus;
    }
    public int getScSta() {
        return scSta;
    }
    public void setScSta(int scSta) {
        this.scSta = scSta;
    }
    
}
