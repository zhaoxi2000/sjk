package com.ijinshan.sjk.config;

import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @author LinZuXiong
 */
public class AppConfig {

    public static final int BATCH_SIZE = 30;

    /**
     * lucene search constant .
     */
    private boolean initIndex = false;
    private String[] queryFields = new String[] { "name", "description" };

    /** lucene disk IO config */
    private String allIndexDir = "/data/appsdata/sjk-market-lucene-index/all/index";
    private String allQuickTipsIndexDir = "/data/appsdata/sjk-market-lucene-index/all/quicktips/index";
    private String allNameDict = "/data/appsdata/sjk-market-lucene-index/all/name.txt";
    private String allSpellcheckDir = "/data/appsdata/sjk-market-lucene-index/all/spellcheck/index";
    private String redisHost = "10.20.223.114";
    private int redisPort = 6370;
    private int resisTimeOut = 500;
    private int redisClientMaxCount = 10;

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public int getResisTimeOut() {
        return resisTimeOut;
    }

    public void setResisTimeOut(int resisTimeOut) {
        this.resisTimeOut = resisTimeOut;
    }

    /** lucene disk IO config end */

    private Set<String> trustIPs = null;

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

    public String getAllIndexDir() {
        return allIndexDir;
    }

    public void setAllIndexDir(String allIndexDir) {
        this.allIndexDir = allIndexDir;
    }

    public String getAllQuickTipsIndexDir() {
        return allQuickTipsIndexDir;
    }

    public void setAllQuickTipsIndexDir(String allQuickTipsIndexDir) {
        this.allQuickTipsIndexDir = allQuickTipsIndexDir;
    }

    public String getAllNameDict() {
        return allNameDict;
    }

    public void setAllNameDict(String allNameDict) {
        this.allNameDict = allNameDict;
    }

    public String getAllSpellcheckDir() {
        return allSpellcheckDir;
    }

    public void setAllSpellcheckDir(String allSpellcheckDir) {
        this.allSpellcheckDir = allSpellcheckDir;
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

    public final int getRedisClientMaxCount() {
        return redisClientMaxCount;
    }

    public final void setRedisClientMaxCount(int redisClientMaxCount) {
        this.redisClientMaxCount = redisClientMaxCount;
    }

}
