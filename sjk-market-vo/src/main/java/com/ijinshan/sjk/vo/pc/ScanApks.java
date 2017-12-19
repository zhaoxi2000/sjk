package com.ijinshan.sjk.vo.pc;

import java.util.List;

public class ScanApks {

    private String pkname = "";
    private short catalog;
    private String officialSigSha1 = "";
    private String logoUrl = "";
    private List<SimpleScanApk> apks;

    public String getPkname() {
        return pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    public String getOfficialSigSha1() {
        return officialSigSha1;
    }

    public void setOfficialSigSha1(String officialSigSha1) {
        if (officialSigSha1 == null) {
            return;
        }
        this.officialSigSha1 = officialSigSha1;
    }

    public short getCatalog() {
        return catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        if (logoUrl == null) {
            return;
        }
        this.logoUrl = logoUrl;
    }

    public List<SimpleScanApk> getApks() {
        return apks;
    }

    public void setApks(List<SimpleScanApk> apks) {
        this.apks = apks;
    }

}
