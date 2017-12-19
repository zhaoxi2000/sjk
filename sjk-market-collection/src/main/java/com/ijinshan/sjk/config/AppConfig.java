package com.ijinshan.sjk.config;

import java.util.Set;

public class AppConfig {

    public static final int BATCH_SIZE = 30;

    public static final int BATCH_SIZE_OF_TRANC = BATCH_SIZE * 10;

    private boolean updateAudit = false;

    private String tmpFullDir = "/data/appsdata/sjk-market-collection/tmp/";

    private Set<String> trustIPs = null;

    public boolean isUpdateAudit() {
        return updateAudit;
    }

    public void setUpdateAudit(boolean updateAudit) {
        this.updateAudit = updateAudit;
    }

    public String getTmpFullDir() {
        return tmpFullDir;
    }

    public void setTmpFullDir(String tmpFullDir) {
        this.tmpFullDir = tmpFullDir;
    }

    public Set<String> getTrustIPs() {
        return trustIPs;
    }

    public void setTrustIPs(Set<String> trustIPs) {
        this.trustIPs = trustIPs;
    }

    public static int getBatchSize() {
        return BATCH_SIZE;
    }

    public static int getBatchSizeOfTranc() {
        return BATCH_SIZE_OF_TRANC;
    }

}
