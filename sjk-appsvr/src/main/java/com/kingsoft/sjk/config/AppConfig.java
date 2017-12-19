package com.kingsoft.sjk.config;

public class AppConfig {

    /** 应用排行榜显示条目数 */
    private int appsRankTopNum = 10;
    /**
     * 下载地址URLhttp://www.yingyong.so/DownLoad.aspx?id=15&tid=1&file=
     * http://file.yingyong.so/file/12/Mass_Effect_Infiltrator_v1_0_30.apk
     * http://file.yingyong.so/file/{id/500}
     * 
     * <pre>
     * http://www.yingyong.so/DownLoad.aspx?id=15&tid=1&file=
     * </pre>
     */
    private String downLoadBaseUrl = "http://www.yingyong.so/DownLoad.aspx?id=%d&tid=1&file=%s";

    /**
     * 下载Data地址URL http://www.yingyong.so/DownLoad.aspx?id=15&tid=2&file=
     * http:// file.yingyong.so/file/gamedata/
     * YingyongSo_Mass_Effect_Infiltrator_v1_0_30 .zip
     */
    private String downLoadDataBaseUrl = "http://www.yingyong.so/DownLoad.aspx?id=%d&tid=2&file=%s";
    /** 应用Logo */
    private String appLogoBaseUrl = "http://www.yingyong.so/img/logo/";
    /** 应用截图 */
    private String appImageBaseUrl = "http://www.yingyong.so/img/topimg/";
    /** 详情页URL */
    private String appPageBaseUrl = "http://app.sjk.ijinshan.com/sjk/page/";
    /** 专题封面图片 **/
    private String topicImageBaseUrl = "%s";
    /** extenddata 截图 */
    private String appDataImageBaseUrl = "http://www.yingyong.so/img/extenddata/";
    /* 一键安装包 */
    private String downLoadOneKeyPackBaseUrl = "http://softdl.ijinshan.com/sjkappmgr/oneKeyPack/";
    /**
     * 搜索业务
     **/
    private boolean initIndex = false;
    private String gameIndexDir = "/data/appsdata/sjk/game/indexDir";
    private String gameSpellIndexDir = "/data/appsdata/sjk/game/spellIndexDir";
    private String gameSuggestDict = "/data/appsdata/sjk/game/suggestdic.txt";

    private String softIndexDir = "/data/appsdata/sjk/soft/indexDir";
    private String softSpellIndexDir = "/data/appsdata/sjk/soft/spellIndexDir";
    private String softSuggestDict = "/data/appsdata/sjk/soft/suggestdic.txt";
    private int searchResNum = 20;
    private int suggestNum = 5;
    private int softTypeId = 1;
    private int gameTypeId = 2;
    private String[] queryFields = new String[] { "name" };

    public AppConfig() {
        super();
    }

    public boolean isInitIndex() {
        return initIndex;
    }

    public void setInitIndex(boolean initIndex) {
        this.initIndex = initIndex;
    }

    public int getSearchResNum() {
        return searchResNum;
    }

    public void setSearchResNum(int searchResNum) {
        this.searchResNum = searchResNum;
    }

    public String getDownLoadBaseUrl() {
        return downLoadBaseUrl;
    }

    public void setDownLoadBaseUrl(String downLoadBaseUrl) {
        this.downLoadBaseUrl = downLoadBaseUrl;
    }

    public String getAppLogoBaseUrl() {
        return appLogoBaseUrl;
    }

    public void setAppLogoBaseUrl(String appLogoBaseUrl) {
        this.appLogoBaseUrl = appLogoBaseUrl;
    }

    public String getAppImageBaseUrl() {
        return appImageBaseUrl;
    }

    public void setAppImageBaseUrl(String appImageBaseUrl) {
        this.appImageBaseUrl = appImageBaseUrl;
    }

    /**
     * @return 应用排行榜显示条目数
     */
    public int getAppsRankTopNum() {
        return appsRankTopNum;
    }

    public void setAppsRankTopNum(int appsRankTopNum) {
        this.appsRankTopNum = appsRankTopNum;
    }

    public String getAppPageBaseUrl() {
        return appPageBaseUrl;
    }

    public void setAppPageBaseUrl(String appPageBaseUrl) {
        this.appPageBaseUrl = appPageBaseUrl;
    }

    public String getDownLoadDataBaseUrl() {
        return downLoadDataBaseUrl;
    }

    public void setDownLoadDataBaseUrl(String downLoadDataBaseUrl) {
        this.downLoadDataBaseUrl = downLoadDataBaseUrl;
    }

    public String getAppDataImageBaseUrl() {
        return appDataImageBaseUrl;
    }

    public void setAppDataImageBaseUrl(String appDataImageBaseUrl) {
        this.appDataImageBaseUrl = appDataImageBaseUrl;
    }

    public int getSuggestNum() {
        return suggestNum;
    }

    public void setSuggestNum(int suggestNum) {
        this.suggestNum = suggestNum;
    }

    public String[] getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(String[] queryFields) {
        this.queryFields = queryFields;
    }

    public String getGameIndexDir() {
        return gameIndexDir;
    }

    public void setGameIndexDir(String gameIndexDir) {
        this.gameIndexDir = gameIndexDir;
    }

    public String getGameSpellIndexDir() {
        return gameSpellIndexDir;
    }

    public void setGameSpellIndexDir(String gameSpellIndexDir) {
        this.gameSpellIndexDir = gameSpellIndexDir;
    }

    public String getGameSuggestDict() {
        return gameSuggestDict;
    }

    public void setGameSuggestDict(String gameSuggestDict) {
        this.gameSuggestDict = gameSuggestDict;
    }

    public String getSoftIndexDir() {
        return softIndexDir;
    }

    public void setSoftIndexDir(String softIndexDir) {
        this.softIndexDir = softIndexDir;
    }

    public String getSoftSpellIndexDir() {
        return softSpellIndexDir;
    }

    public void setSoftSpellIndexDir(String softSpellIndexDir) {
        this.softSpellIndexDir = softSpellIndexDir;
    }

    public String getSoftSuggestDict() {
        return softSuggestDict;
    }

    public void setSoftSuggestDict(String softSuggestDict) {
        this.softSuggestDict = softSuggestDict;
    }

    public int getSoftTypeId() {
        return softTypeId;
    }

    public void setSoftTypeId(int softTypeId) {
        this.softTypeId = softTypeId;
    }

    public int getGameTypeId() {
        return gameTypeId;
    }

    public void setGameTypeId(int gameTypeId) {
        this.gameTypeId = gameTypeId;
    }

    public String getDownLoadOneKeyPackBaseUrl() {
        return downLoadOneKeyPackBaseUrl;
    }

    public void setDownLoadOneKeyPackBaseUrl(String downLoadOneKeyPackBaseUrl) {
        this.downLoadOneKeyPackBaseUrl = downLoadOneKeyPackBaseUrl;
    }

    public String getTopicImageBaseUrl() {
        return this.topicImageBaseUrl;
    }

    public void setTopicImageBaseUrl(String topicImageBaseUrl) {
        this.topicImageBaseUrl = topicImageBaseUrl;
    }

}
