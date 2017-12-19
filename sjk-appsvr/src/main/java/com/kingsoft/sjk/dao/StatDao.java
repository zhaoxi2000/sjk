package com.kingsoft.sjk.dao;

public interface StatDao {

    /**
     * 下载完成
     * 
     * @param appId
     * @return
     */
    boolean gatherDownloadStat(int appId);

    /**
     * 点击下载
     * 
     * @param appId
     * @return
     */
    boolean gatherClickStat(int appId);

}
