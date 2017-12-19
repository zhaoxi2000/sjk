package com.ijinshan.sjk.jsonpo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokenLink {
    private static final Logger logger = LoggerFactory.getLogger(BrokenLink.class);

    private String key;
    private String marketName;
    private int count;
    private List<BrokenApp> data;

    private BrokenLink() {
        super();
    }

    private BrokenLink(String key, String marketName, int count, List<BrokenApp> data) {
        super();
        this.key = key;
        this.marketName = marketName;
        this.count = count;
        this.data = data;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<BrokenApp> getData() {
        return data;
    }

    public void setData(List<BrokenApp> data) {
        this.data = data;
    }

    public static Logger getLogger() {
        return logger;
    }

}
