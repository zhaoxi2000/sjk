package com.ijinshan.sjk.vo.pc;

import java.util.Date;

public class LatestApp extends App4Summary {

    private static final long serialVersionUID = -959236338514374057L;
    private Date lastUpdateTime;

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}
