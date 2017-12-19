package com.ijinshan.sjk.service;

public interface DownloadsUpdateService {

    void updateDownloadsFromMarket();

    /**
     * 更新要展示给用户的下载量.
     */
    void update();

    /**
     * 更新前日下载增量
     */
    void updateDay();

    /**
     * 更新一周的下载增量
     */
    void updateWeek();

    void topUpdateData();

    /**
     * 分页处理deltaDownload <> 0 的数据. 更新展示的下载量
     * 
     * @param cb
     * @return
     */
    int handlePagination(UpdateDownloadCallback cb);

}
