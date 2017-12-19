package com.ijinshan.sjk.jsonpo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.MarketApp;

public class NotifyOne {
    private static final Logger logger = LoggerFactory.getLogger(NotifyOne.class);

    private String key;
    private String marketName;
    private int id;
    private byte opType;
    private MarketApp app;

    private NotifyOne() {
        super();
    }

    private NotifyOne(String key, String marketName, int id, byte opType, MarketApp app) {
        super();
        this.key = key;
        this.marketName = marketName;
        this.id = id;
        this.opType = opType;
        this.app = app;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getOpType() {
        return opType;
    }

    public void setOpType(byte opType) {
        this.opType = opType;
    }

    public MarketApp getApp() {
        return app;
    }

    public void setApp(MarketApp app) {
        this.app = app;
    }

    public static Logger getLogger() {
        return logger;
    }

}
