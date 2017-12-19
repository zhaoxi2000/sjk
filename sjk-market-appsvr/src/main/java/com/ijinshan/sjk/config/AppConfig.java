package com.ijinshan.sjk.config;

import java.util.Set;

/**
 * @author LinZuXiong
 */
public class AppConfig {

    public static final int BATCH_SIZE = 30;

    private int scanIfHit = 30;
    private int scanIfMiss = 300;

    /** 应用排行榜显示条目数 */
    private int appsRankTopNum = 8;

    private int keywordMaxLength = 30;

    /**
     * lucene search
     */
    private boolean initIndex = false;
    private String[] queryFields = new String[] { "name", "description" };
    private int searchMaxNum = 20;
    private int spellCheckerNum = 3;
    private int quickTipsNum = 6;
    private String quickTipsIndex = "/data/appsdata/sjk-market/quicktips/index";
    private String indexDir = "/data/appsdata/sjk-market/all/index";
    private String spellCheckerDir = "/data/appsdata/sjk-market/all/spellcheckindex";

    /**
     * soft begin
     **/
    private short softCatalog = 1;
    private String softIndexDir = "/data/appsdata/sjk-market/soft/";
    private String softDic = "/data/appsdata/sjk-market/soft/dictionary.txt";
    private String softSpellcheckDir = "/data/appsdata/sjk-market/softspellcheck/";
    /**
     * soft end
     */
    /**
     * game begin
     */
    private short gameCatalog = 2;
    private String gameIndexDir = "/data/appsdata/sjk-market/game/";
    private String gameDic = "/data/appsdata/sjk-market/game/dictionary.txt";
    private String gameSpellcheckDir = "/data/appsdata/sjk-market/gamespellcheck/";
    /**
     * game end
     */
    /**
     * game begin
     */
    private short biggameCatalog = 100;
    private String biggameIndexDir = "/data/appsdata/sjk-market/biggame/";
    private String biggameDic = "/data/appsdata/sjk-market/biggame/dictionary.txt";
    private String biggameSpellcheckDir = "/data/appsdata/sjk-market/biggamespellcheck/";

    private String allQuickTipsIndexDir = "/data/appsdata/sjk-market-lucene-index/all/quicktips/";
    private String allIndexDir = "/data/appsdata/sjk-market-lucene-index/all/search/";
    private String allSpellCheckerDir = "/data/appsdata/sjk-market-lucene-index/all/spellcheck/";

   
    private double redisFindMax = 9999999999999999999999999999999.0;
    
   

    public double getRedisFindMax() {
        return redisFindMax;
    }

    public void setRedisFindMax(double redisFindMax) {
        this.redisFindMax = redisFindMax;
    }

    /**
     * 分页限制
     */
    private int maxOffset = 2000;

    public int getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(int maxOffset) {
        this.maxOffset = maxOffset;
    }

    private Set<String> trustIPs = null;

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

    public int getSearchMaxNum() {
        return searchMaxNum;
    }

    public void setSearchMaxNum(int searchMaxNum) {
        this.searchMaxNum = searchMaxNum;
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

    public String getQuickTipsIndex() {
        return quickTipsIndex;
    }

    public void setQuickTipsIndex(String quickTipsIndex) {
        this.quickTipsIndex = quickTipsIndex;
    }

    public String getIndexDir() {
        return indexDir;
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
    }

    public String getSpellCheckerDir() {
        return spellCheckerDir;
    }

    public void setSpellCheckerDir(String spellCheckerDir) {
        this.spellCheckerDir = spellCheckerDir;
    }

    public short getSoftCatalog() {
        return softCatalog;
    }

    public void setSoftCatalog(short softCatalog) {
        this.softCatalog = softCatalog;
    }

    public String getSoftIndexDir() {
        return softIndexDir;
    }

    public void setSoftIndexDir(String softIndexDir) {
        this.softIndexDir = softIndexDir;
    }

    public String getSoftDic() {
        return softDic;
    }

    public void setSoftDic(String softDic) {
        this.softDic = softDic;
    }

    public String getSoftSpellcheckDir() {
        return softSpellcheckDir;
    }

    public void setSoftSpellcheckDir(String softSpellcheckDir) {
        this.softSpellcheckDir = softSpellcheckDir;
    }

    public short getGameCatalog() {
        return gameCatalog;
    }

    public void setGameCatalog(short gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    public String getGameIndexDir() {
        return gameIndexDir;
    }

    public void setGameIndexDir(String gameIndexDir) {
        this.gameIndexDir = gameIndexDir;
    }

    public String getGameDic() {
        return gameDic;
    }

    public void setGameDic(String gameDic) {
        this.gameDic = gameDic;
    }

    public String getGameSpellcheckDir() {
        return gameSpellcheckDir;
    }

    public void setGameSpellcheckDir(String gameSpellcheckDir) {
        this.gameSpellcheckDir = gameSpellcheckDir;
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

    public Set<String> getTrustIPs() {
        return trustIPs;
    }

    public void setTrustIPs(Set<String> trustIPs) {
        this.trustIPs = trustIPs;
    }

    public static int getBatchSize() {
        return BATCH_SIZE;
    }

    public final String getAllSpellCheckerDir() {
        return allSpellCheckerDir;
    }

    public final void setAllSpellCheckerDir(String allSpellCheckerDir) {
        this.allSpellCheckerDir = allSpellCheckerDir;
    }

}
