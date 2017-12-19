package com.ijinshan.sjk.dao;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;

import com.ijinshan.sjk.jsonpo.PaginationMarketApp;
import com.ijinshan.sjk.po.Market;

public interface AccessMarketDao {
    Logger getMarketlogger();

    PaginationMarketApp getMarketAppForFull(Market market) throws Exception;

    PaginationMarketApp getMarketAppForFull(URIBuilder builder) throws Exception;

    PaginationMarketApp getMarketAppForIncrement(Market market) throws Exception;

    PaginationMarketApp getMarketAppForIncrement(URIBuilder builder) throws Exception;

    String getFullUrl(Market market);

    File getFileByUrl(String strUrl, String destPath) throws Exception;

    File decompress(File file) throws IOException;

    /**
     * 根据市场上次拉取增量的时间 , 加上1个小时避开重复拉前上个小时的文件.
     * 
     * @param market
     * @return
     */
    String getIncrementUrl(Market market);

    String getOffUrl(Market market);

}
