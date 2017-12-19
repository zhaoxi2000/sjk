package com.kingsoft.sjk.game.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultInfo {
    private static final Logger logger = LoggerFactory.getLogger(ResultInfo.class);

    private Integer id;
    private String folder;

    public ResultInfo() {
        super();
    }

    public ResultInfo(Integer id, String folder) {
        super();
        this.id = id;
        this.folder = folder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public static Logger getLogger() {
        return logger;
    }

}
