package com.ijinshan.sjk.config;

public class AppConfig {

    public static final int BATCH_SIZE = 30;
    public static final int BATCH_SIZE_OF_TRANC = BATCH_SIZE * 10;

    private String tmpFullDir = "/data/appsdata/sjk-market/tmp/";
    private String tmpUploadDir = "/data/apps/static-web/sjk/app/market/tmp/img/";
    private String tmpUploadBaseurl = "http://app.sjk.ijinshan.com/market/tmp/img/";

    private String destUploadDir = "/data/apps/static-web/sjk/market/img/";
    private String destUploadBaseurl = "http://app.sjk.ijinshan.com/market/img/";

    private String destTagUploadDir = "/data/apps/static-web/sjk/market/img/tag/";
    private String destTagUploadBaseurl = "http://app.sjk.ijinshan.com/market/img/tag/";

    private String destMobileTagUploadDir = "/data/apps/static-web/sjk/app/market/img/mobile/tag/";
    private String destMobileTagUploadBaseurl = "http://app.sjk.ijinshan.com/market/img/mobile/tag/";
    private String destMobileFeaturedUploadDir = "/data/apps/static-web/sjk/app/market/img/mobile/featured/";
    private String destMobileFeaturedUploadBaseurl = "http://app.sjk.ijinshan.com/market/img/mobile/featured/";

    private String destMomixFeaturedUploadDir = "/data/apps/static-web/sjk/app/market/img/momix/featured/";
    private String destMomixFeaturedUploadBaseurl = "http://app.sjk.ijinshan.com/market/img/momix/featured/";

    private String destBigGameUploadDir = "/data/apps/static-web/sjk/app/market/img/biggame/";
    private String destBigGameUploadBaseurl = "http://app.sjk.ijinshan.com/market/img/biggame/";

    private boolean updateAudit = false;
    private boolean deleteUploadImageFile = false;

    public String getTmpFullDir() {
        return tmpFullDir;
    }

    public void setTmpFullDir(String tmpFullDir) {
        this.tmpFullDir = tmpFullDir;
    }

    public String getTmpUploadDir() {
        return tmpUploadDir;
    }

    public void setTmpUploadDir(String tmpUploadDir) {
        this.tmpUploadDir = tmpUploadDir;
    }

    public String getDestUploadDir() {
        return destUploadDir;
    }

    public void setDestUploadDir(String destUploadDir) {
        this.destUploadDir = destUploadDir;
    }

    public String getDestUploadBaseurl() {
        return destUploadBaseurl;
    }

    public void setDestUploadBaseurl(String destUploadBaseurl) {
        this.destUploadBaseurl = destUploadBaseurl;
    }

    public String getTmpUploadBaseurl() {
        return tmpUploadBaseurl;
    }

    public void setTmpUploadBaseurl(String tmpUploadBaseurl) {
        this.tmpUploadBaseurl = tmpUploadBaseurl;
    }

    public String getDestTagUploadDir() {
        return destTagUploadDir;
    }

    public void setDestTagUploadDir(String destTagUploadDir) {
        this.destTagUploadDir = destTagUploadDir;
    }

    public String getDestTagUploadBaseurl() {
        return destTagUploadBaseurl;
    }

    public void setDestTagUploadBaseurl(String destTagUploadBaseurl) {
        this.destTagUploadBaseurl = destTagUploadBaseurl;
    }

    public String getDestMobileTagUploadDir() {
        return destMobileTagUploadDir;
    }

    public void setDestMobileTagUploadDir(String destMobileTagUploadDir) {
        this.destMobileTagUploadDir = destMobileTagUploadDir;
    }

    public String getDestMobileTagUploadBaseurl() {
        return destMobileTagUploadBaseurl;
    }

    public void setDestMobileTagUploadBaseurl(String destMobileTagUploadBaseurl) {
        this.destMobileTagUploadBaseurl = destMobileTagUploadBaseurl;
    }

    public String getDestMobileFeaturedUploadDir() {
        return destMobileFeaturedUploadDir;
    }

    public void setDestMobileFeaturedUploadDir(String destMobileFeaturedUploadDir) {
        this.destMobileFeaturedUploadDir = destMobileFeaturedUploadDir;
    }

    public String getDestMobileFeaturedUploadBaseurl() {
        return destMobileFeaturedUploadBaseurl;
    }

    public void setDestMobileFeaturedUploadBaseurl(String destMobileFeaturedUploadBaseurl) {
        this.destMobileFeaturedUploadBaseurl = destMobileFeaturedUploadBaseurl;
    }

    public String getDestBigGameUploadDir() {
        return destBigGameUploadDir;
    }

    public void setDestBigGameUploadDir(String destBigGameUploadDir) {
        this.destBigGameUploadDir = destBigGameUploadDir;
    }

    public String getDestBigGameUploadBaseurl() {
        return destBigGameUploadBaseurl;
    }

    public void setDestBigGameUploadBaseurl(String destBigGameUploadBaseurl) {
        this.destBigGameUploadBaseurl = destBigGameUploadBaseurl;
    }

    public String getDestMomixFeaturedUploadDir() {
        return destMomixFeaturedUploadDir;
    }

    public void setDestMomixFeaturedUploadDir(String destMomixFeaturedUploadDir) {
        this.destMomixFeaturedUploadDir = destMomixFeaturedUploadDir;
    }

    public String getDestMomixFeaturedUploadBaseurl() {
        return destMomixFeaturedUploadBaseurl;
    }

    public void setDestMomixFeaturedUploadBaseurl(String destMomixFeaturedUploadBaseurl) {
        this.destMomixFeaturedUploadBaseurl = destMomixFeaturedUploadBaseurl;
    }

    public boolean isDeleteUploadImageFile() {
        return deleteUploadImageFile;
    }

    public void setDeleteUploadImageFile(boolean deleteUploadImageFile) {
        this.deleteUploadImageFile = deleteUploadImageFile;
    }

    public boolean isUpdateAudit() {
        return updateAudit;
    }

    public void setUpdateAudit(boolean updateAudit) {
        this.updateAudit = updateAudit;
    }

    public static int getBatchSize() {
        return BATCH_SIZE;
    }

    public static int getBatchSizeOfTranc() {
        return BATCH_SIZE_OF_TRANC;
    }

}
