package com.ijinshan.sjk.dao.market;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import net.minidev.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ijinshan.sjk.config.JsonObjectMapperFactory;
import com.ijinshan.sjk.dao.AccessMarketDao;
import com.ijinshan.sjk.dao.adapter.MarketOutputConvertor;
import com.ijinshan.sjk.jsonpo.HiapkPaginationMarketApp;
import com.ijinshan.sjk.jsonpo.PaginationMarketApp;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.util.HttpSupport;

/**
 * hiapk安卓市场, 动态接口访问形式.
 * 
 * @author Linzuxiong
 */
@Repository
public class AccessHiapkDaoImpl implements AccessMarketDao {
    private static final Logger logger = LoggerFactory.getLogger(AccessHiapkDaoImpl.class);
    private static final Logger marketLogger = LoggerFactory.getLogger("db.market.hiapk");

    @Override
    public PaginationMarketApp getMarketAppForFull(URIBuilder builder) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaginationMarketApp getMarketAppForFull(Market market) throws Exception {
        String postContent = this.requestContentOfAll(market);
        marketLogger.info("Full api: {} postContent: {}", market.getMarketName(), postContent);
        PaginationMarketApp pagination = getJsonByHttp(market, postContent);
        if (pagination == null) {
            marketLogger.error("Access hiapk , return null!");
            return null;
        }
        marketLogger.info("{}\t getTotals: {}\t postContent: {}", market.getMarketName(), pagination.getTotals(),
                postContent);
        pagination.setMarketName(market.getMarketName());
        if (pagination.getTotals() < 1) {
            pagination.setTotalPages(0);
        } else {
            if (pagination.getTotals() % market.getPageSize() == 0) {
                pagination.setTotalPages(pagination.getTotals() / market.getPageSize());
            } else {
                pagination.setTotalPages(pagination.getTotals() / market.getPageSize() + 1);
            }
        }
        pagination.setCurrentPage(market.getFullLastReqCurrentPage() + 1);
        pagination.setPageSize(market.getPageSize());
        return pagination;
    }

    @Override
    public PaginationMarketApp getMarketAppForIncrement(URIBuilder builder) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaginationMarketApp getMarketAppForIncrement(Market market) throws Exception {
        String postContent = this.requestContentOfIncrement(market);
        marketLogger.info("Increment api: {} postContent: {}", market.getMarketName(), postContent);
        PaginationMarketApp marketApps = getJsonByHttp(market, postContent);
        if (marketApps == null) {
            return null;
        }
        marketApps.setMarketName(market.getMarketName());
        if (marketApps.getTotals() < 1) {
            marketApps.setTotalPages(0);
        } else {
            if (marketApps.getTotals() % market.getPageSize() == 0) {
                marketApps.setTotalPages(marketApps.getTotals() / market.getPageSize());
            } else {
                marketApps.setTotalPages(marketApps.getTotals() / market.getPageSize() + 1);
            }
        }
        marketApps.setCurrentPage(market.getIncrementLastReqCurrentPage() + 1);
        marketApps.setPageSize(market.getPageSize());
        return marketApps;
    }

    public PaginationMarketApp getJsonByHttp(Market market, String postContent) throws MalformedURLException,
            IOException, URISyntaxException, Exception {
        HttpURLConnection conn = null;
        InputStream postInputStream = null;
        PaginationMarketApp rest = null;
        InputStream input = null;
        try {
            postInputStream = IOUtils.toInputStream(postContent);
            URL url = new URL(market.getFullUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Content-Encoding", "application/json;charset=UTF-8");
            input = HttpSupport.postMethod(conn, postInputStream);
            IOUtils.closeQuietly(postInputStream);
            if (input != null) {
                HiapkPaginationMarketApp hiapk = null;
                try {
                    hiapk = JsonObjectMapperFactory.getInstance().getObjectMapper()
                            .readValue(input, new TypeReference<HiapkPaginationMarketApp>() {
                            });
                    rest = MarketOutputConvertor.getPaginationMarketApp(market, hiapk);
                } finally {
                    IOUtils.closeQuietly(input);
                }
            }
        } finally {
            com.ijinshan.util.IOUtils.closeQuietly(postInputStream, input);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rest;
    }

    private String requestContentOfAll(Market market) {
        JSONObject json = new JSONObject();
        final String action = "android.getall";
        json.put("action", action);
        json.put("sc", market.getSecurity());
        json.put("pno", market.getFullLastReqCurrentPage() + 1);
        json.put("psize", market.getPageSize());
        StringBuilder chkCode = new StringBuilder(action).append(market.getSecurity()).append(market.getLoginKey());
        json.put("chkcode", DigestUtils.md5Hex(chkCode.toString()));
        json.put("islastest", 1);
        return json.toJSONString();
    }

    private String requestContentOfIncrement(Market market) {
        JSONObject json = new JSONObject();
        final String action = "android.getincrement";
        json.put("action", action);
        json.put("sc", market.getSecurity());
        json.put("pno", market.getIncrementLastReqCurrentPage() + 1);
        json.put("psize", market.getPageSize());
        StringBuilder chkCode = new StringBuilder(action).append(market.getSecurity()).append(market.getLoginKey());
        String chkcode = DigestUtils.md5Hex(chkCode.toString());
        json.put("chkcode", chkcode);
        json.put("islastest", 1);
        Date fetch = market.getIncrementLastTime();
        fetch = fetch == null ? new Date() : fetch;
        // special logical.
        DateTime jodaDt = new DateTime(fetch);
        jodaDt = jodaDt.minusMinutes(60);
        json.put("timestamp", jodaDt.getMillis() / 1000);
        return json.toJSONString();
    }

    public String getCatalog(Market market, short catalog) throws URISyntaxException, IOException {
        String postContent = this.requestContentOfCatalog(market, catalog);
        URL url = null;
        HttpURLConnection conn = null;
        InputStream postInputStream = null;
        InputStream input = null;
        String result = null;
        try {
            url = new URL(market.getFullUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Content-Encoding", "application/json;charset=UTF-8");
            postInputStream = IOUtils.toInputStream(postContent);
            input = HttpSupport.postMethod(conn, postInputStream);
            result = IOUtils.toString(input);
        } finally {
            com.ijinshan.util.IOUtils.closeQuietly(input, postInputStream);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    private String requestContentOfCatalog(Market market, short catalog) {
        JSONObject json = new JSONObject();
        final String action = "android.getcategory";
        json.put("action", action);
        json.put("sc", market.getSecurity());
        json.put("pid", catalog);
        StringBuilder chkCode = new StringBuilder(action).append(market.getSecurity()).append(market.getLoginKey());
        json.put("chkcode", DigestUtils.md5Hex(chkCode.toString()));
        return json.toJSONString();
    }

    @Override
    public String getFullUrl(Market market) {
        throw new UnsupportedOperationException();
    }

    @Override
    public File getFileByUrl(String strUrl, String destPath) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public File decompress(File file) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIncrementUrl(Market market) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getOffUrl(Market market) {
        throw new UnsupportedOperationException();
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public Logger getMarketlogger() {
        return marketLogger;
    }

}
