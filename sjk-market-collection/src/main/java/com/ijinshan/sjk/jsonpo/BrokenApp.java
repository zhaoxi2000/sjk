package com.ijinshan.sjk.jsonpo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokenApp {
    private static final Logger logger = LoggerFactory.getLogger(BrokenApp.class);

    private int id;
    private String link;
    private int statusCode;

    private BrokenApp() {
        super();
    }

    private BrokenApp(int id, String link, int statusCode) {
        super();
        this.id = id;
        this.link = link;
        this.statusCode = statusCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static Logger getLogger() {
        return logger;
    }

}
