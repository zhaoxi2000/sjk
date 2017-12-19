package com.ijinshan.sjk.dao.market;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ijinshan.sjk.config.JsonObjectMapperFactory;
import com.ijinshan.sjk.dao.AccessMarketDao;
import com.ijinshan.sjk.jsonpo.IncrementMarketApp;
import com.ijinshan.sjk.jsonpo.PaginationMarketApp;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.util.HttpSupport;

/**
 * eoemarket 优亿市场, 动态接口访问形式.
 * 
 * @author Linzuxiong
 */
@Repository
public class AccessEoemarketDaoImpl implements AccessMarketDao {
    private static final Logger logger = LoggerFactory.getLogger(AccessEoemarketDaoImpl.class);
    private static final Logger marketLogger = LoggerFactory.getLogger("db.market.eoemarket");

    public PaginationMarketApp getPagination(String url, int currentPage, String key, int maxrow) throws Exception {
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("currentPage", String.valueOf(currentPage));
        builder.setParameter("key", key);
        builder.setParameter("maxrow", String.valueOf(maxrow));
        return getMarketAppForFull(builder);
    }

    public PaginationMarketApp getIncrementMarketApp(String url, int currentPage, long lastTime, String key,
            int maxrow, String security) throws Exception {
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("currentPage", String.valueOf(currentPage));
        builder.setParameter("lastTime", String.valueOf(lastTime));
        builder.setParameter("key", key);
        builder.setParameter("security", String.valueOf(security));
        builder.setParameter("maxrow", String.valueOf(maxrow));
        return getMarketAppForIncrement(builder);
    }

    @Override
    public PaginationMarketApp getMarketAppForFull(URIBuilder builder) throws Exception {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream input = null;
        PaginationMarketApp rest = null;
        try {
            url = new URL(builder.build().toString());
            conn = (HttpURLConnection) url.openConnection();
            input = HttpSupport.getMethod(conn);
            if (input != null) {
                rest = JsonObjectMapperFactory.getInstance().getObjectMapper()
                        .readValue(input, new TypeReference<PaginationMarketApp>() {
                        });
            }
        } finally {
            IOUtils.closeQuietly(input);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rest;
    }

    @Override
    public PaginationMarketApp getMarketAppForIncrement(URIBuilder builder) throws Exception {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream input = null;
        IncrementMarketApp rest = null;
        try {
            url = new URL(builder.build().toString());
            conn = (HttpURLConnection) url.openConnection();
            input = HttpSupport.getMethod(conn);
            if (input != null) {
                rest = JsonObjectMapperFactory.getInstance().getObjectMapper()
                        .readValue(input, new TypeReference<IncrementMarketApp>() {
                        });
            }
        } catch (Exception e) {
            if (url != null) {
                logger.error("Have a exception! {}", url.toString());
            }
            throw e;
        } finally {
            IOUtils.closeQuietly(input);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rest;
    }

    @Override
    public PaginationMarketApp getMarketAppForFull(Market market) throws Exception {
        String url = market.getFullUrl();
        Assert.isTrue(!url.isEmpty());
        int currentPage = market.getFullLastReqCurrentPage() + 1;
        return this.getPagination(url, currentPage, market.getLoginKey(), market.getPageSize());
    }

    @Override
    public PaginationMarketApp getMarketAppForIncrement(Market market) throws Exception {
        String url = market.getIncrementUrl();
        Assert.isTrue(!url.isEmpty());
        Date lastTimeInDb = market.getIncrementLastTime();
        lastTimeInDb = lastTimeInDb == null ? new Date() : lastTimeInDb;
        long lastTime = 0L;
        if (lastTimeInDb != null) {
            lastTime = lastTimeInDb.getTime();
        }
        DateTime jodaDt = new DateTime(lastTime);
        jodaDt = jodaDt.minusMinutes(60);

        int currentPage = market.getIncrementLastReqCurrentPage() + 1;
        int pageSize = market.getPageSize();
        PaginationMarketApp increment = this.getIncrementMarketApp(url, currentPage, jodaDt.getMillis(),
                market.getLoginKey(), pageSize, market.getSecurity());
        return increment;
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

    @Override
    public Logger getMarketlogger() {
        return marketLogger;
    }
}
