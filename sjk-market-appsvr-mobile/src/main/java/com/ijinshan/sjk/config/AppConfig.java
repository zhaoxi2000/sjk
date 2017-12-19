package com.ijinshan.sjk.config;

import java.util.Set;

/**
 * @author LinZuXiong
 */
public class AppConfig {

    public static final int BATCH_SIZE = 30;

    private int scanIfHit = 30;
    private int scanIfMiss = 300;
    private String appurl;
    private String tagurl;

    /** 应用排行榜显示条目数 */
    private int appsRankTopNum = 8;

    private int keywordMaxLength = 30;
    /**
     * lucene search constant .
     */
    private boolean initIndex = false;
    private String[] queryFields = new String[] { "name", "description" };
    private int searchNum = 20;
    private int spellCheckerNum = 3;
    private int quickTipsNum = 6;
    private int searchMaxNum = 20;

    /** lucene disk IO config */
    private String oldAllIndexDir = "/data/appsdata/sjk-market-mobile/all/index";
    private String oldAllQuickTipsIndex = "/data/appsdata/sjk-market-mobile/all/quicktips/index";
    private String oldAllSpellCheckerDir = "/data/appsdata/sjk-market-mobile/all/spellcheck/index";

    private short biggameCatalog = 100;
    private String biggameIndexDir = "/data/appsdata/sjk-market/biggame/";
    private String biggameDic = "/data/appsdata/sjk-market/biggame/dictionary.txt";
    private String biggameSpellcheckDir = "/data/appsdata/sjk-market/biggamespellcheck/";

    private String allQuickTipsIndexDir = "/data/appsdata/sjk-market-lucene-index/all/quicktips/";
    private String allIndexDir = "/data/appsdata/sjk-market-lucene-index/all/search/";
    private String allSpellCheckerDir = "/data/appsdata/sjk-market-lucene-index/all/spellcheck/";

    /** lucene disk IO config */

    private Set<String> trustIPs = null;

    public String getDestTagAppUploadBaseurl() {
        return destTagAppUploadBaseurl;
    }

    public void setDestTagAppUploadBaseurl(String destTagAppUploadBaseurl) {
        this.destTagAppUploadBaseurl = destTagAppUploadBaseurl;
    }

    private String destTagAppUploadBaseurl = "http://app.sjk.ijinshan.com/market/img/tag/";

    public AppConfig() {
        super();
    }

    public int getScanIfHit() {
        return scanIfHit;
    }

    public void setScanIfHit(int scanIfHit) {
        this.scanIfHit = scanIfHit;
    }

    public int getScanIfMiss() {
        return scanIfMiss;
    }

    public void setScanIfMiss(int scanIfMiss) {
        this.scanIfMiss = scanIfMiss;
    }

    public int getAppsRankTopNum() {
        return appsRankTopNum;
    }

    public void setAppsRankTopNum(int appsRankTopNum) {
        this.appsRankTopNum = appsRankTopNum;
    }

    public int getKeywordMaxLength() {
        return keywordMaxLength;
    }

    public void setKeywordMaxLength(int keywordMaxLength) {
        this.keywordMaxLength = keywordMaxLength;
    }

    public boolean isInitIndex() {
        return initIndex;
    }

    public void setInitIndex(boolean initIndex) {
        this.initIndex = initIndex;
    }

    public String[] getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(String[] queryFields) {
        this.queryFields = queryFields;
    }

    public int getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(int searchNum) {
        this.searchNum = searchNum;
    }

    public int getSpellCheckerNum() {
        return spellCheckerNum;
    }

    public void setSpellCheckerNum(int spellCheckerNum) {
        this.spellCheckerNum = spellCheckerNum;
    }

    public int getQuickTipsNum() {
        return quickTipsNum;
    }

    public void setQuickTipsNum(int quickTipsNum) {
        this.quickTipsNum = quickTipsNum;
    }

    public String getOldAllIndexDir() {
        return oldAllIndexDir;
    }

    public void setOldAllIndexDir(String oldAllIndexDir) {
        this.oldAllIndexDir = oldAllIndexDir;
    }

    public String getOldAllQuickTipsIndex() {
        return oldAllQuickTipsIndex;
    }

    public void setOldAllQuickTipsIndex(String oldAllQuickTipsIndex) {
        this.oldAllQuickTipsIndex = oldAllQuickTipsIndex;
    }

    public String getOldAllSpellCheckerDir() {
        return oldAllSpellCheckerDir;
    }

    public void setOldAllSpellCheckerDir(String oldAllSpellCheckerDir) {
        this.oldAllSpellCheckerDir = oldAllSpellCheckerDir;
    }

    public static int getBatchSize() {
        return BATCH_SIZE;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public String getTagurl() {
        return tagurl;
    }

    public void setTagurl(String tagurl) {
        this.tagurl = tagurl;
    }

    public Set<String> getTrustIPs() {
        return trustIPs;
    }

    public void setTrustIPs(Set<String> trustIPs) {
        this.trustIPs = trustIPs;
    }

    public short getBiggameCatalog() {
        return biggameCatalog;
    }

    public void setBiggameCatalog(short biggameCatalog) {
        this.biggameCatalog = biggameCatalog;
    }

    public String getBiggameIndexDir() {
        return biggameIndexDir;
    }

    public void setBiggameIndexDir(String biggameIndexDir) {
        this.biggameIndexDir = biggameIndexDir;
    }

    public String getBiggameDic() {
        return biggameDic;
    }

    public void setBiggameDic(String biggameDic) {
        this.biggameDic = biggameDic;
    }

    public String getBiggameSpellcheckDir() {
        return biggameSpellcheckDir;
    }

    public void setBiggameSpellcheckDir(String biggameSpellcheckDir) {
        this.biggameSpellcheckDir = biggameSpellcheckDir;
    }

    public String getAllQuickTipsIndexDir() {
        return allQuickTipsIndexDir;
    }

    public void setAllQuickTipsIndexDir(String allQuickTipsIndexDir) {
        this.allQuickTipsIndexDir = allQuickTipsIndexDir;
    }

    public String getAllIndexDir() {
        return allIndexDir;
    }

    public void setAllIndexDir(String allIndexDir) {
        this.allIndexDir = allIndexDir;
    }

    public String getAllSpellCheckerDir() {
        return allSpellCheckerDir;
    }

    public void setAllSpellCheckerDir(String allSpellCheckerDir) {
        this.allSpellCheckerDir = allSpellCheckerDir;
    }

    public int getSearchMaxNum() {
        return searchMaxNum;
    }

    public void setSearchMaxNum(int searchMaxNum) {
        this.searchMaxNum = searchMaxNum;
    }
}
