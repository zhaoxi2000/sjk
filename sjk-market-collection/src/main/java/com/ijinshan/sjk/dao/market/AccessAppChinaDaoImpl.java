package com.ijinshan.sjk.dao.market;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.AccessMarketDao;
import com.ijinshan.sjk.jsonpo.PaginationMarketApp;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.util.HttpSupport;
import com.ijinshan.util.IOUtils;

/**
 * appchina 应用汇, 文件访问形式.
 * 
 * @author Linzuxiong
 */
@Repository
public class AccessAppChinaDaoImpl implements AccessMarketDao {
    private static final Logger logger = LoggerFactory.getLogger(AccessAppChinaDaoImpl.class);
    private static final Logger marketLogger = LoggerFactory.getLogger("db.market.appchina");

    @Override
    public PaginationMarketApp getMarketAppForFull(Market market) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaginationMarketApp getMarketAppForIncrement(Market market) throws Exception {
        URIBuilder builder = new URIBuilder(market.getIncrementUrl());
        builder.setParameter("lastTime", String.valueOf(market.getIncrementLastTime()));
        return getMarketAppForIncrement(builder);
    }

    @Override
    public File getFileByUrl(String strUrl, String destPath) throws Exception {
        File dest = new File(destPath);
        URL url = null;
        InputStream input = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            input = HttpSupport.getMethod(conn);
            if (input == null) {
                return null;
            }
            FileUtils.copyInputStreamToFile(input, dest);
            IOUtils.closeQuietly(input);
            if (dest.length() != Long.parseLong(conn.getHeaderField("Content-Length"))) {
                throw new RuntimeException();
            }
        } finally {
            IOUtils.closeQuietly(input);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return dest;
    }

    @Override
    public File decompress(File src) throws IOException {
        if (!src.getName().endsWith(".gz")) {
            return src;
        }
        if (!src.exists()) {
            return src;
        }
        File dest = null;
        FileInputStream instream = null;
        GZIPInputStream ginstream = null;
        FileOutputStream outstream = null;
        try {
            instream = new FileInputStream(src);
            ginstream = new GZIPInputStream(instream);
            dest = new File(src.getAbsolutePath().replace(".gz", ""));
            outstream = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int len;
            while ((len = ginstream.read(buf)) > 0) {
                outstream.write(buf, 0, len);
            }
        } finally {
            IOUtils.closeQuietly(outstream, ginstream);
        }
        return dest;
    }

    @Override
    public String getFullUrl(Market market) {
        if (market == null) {
            return null;
        }
        // http://img.yingyonghui.com/interface/ijinshan/ijinshan_all_{day}.csv.gz
        DateTime dest = new DateTime();
        String date = new java.text.SimpleDateFormat("yyyyMMdd").format(dest.toDate());
        StringBuilder url = new StringBuilder(market.getFullUrl().length());
        url.append(market.getFullUrl());
        if (!market.getFullUrl().endsWith("/")) {
            url.append("/");
        }
        url.append("ijinshan_all_");
        url.append(date);
        url.append(".csv.gz");
        url.append("?t=");
        url.append(System.currentTimeMillis());
        return url.toString();
    }

    @Override
    public String getOffUrl(Market market) {
        if (market == null) {
            return null;
        }
        // http://img.yingyonghui.com/interface/ijinshan/ijinshan_off_{day}_{hour}.csv
        String date = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        Calendar calendar = Calendar.getInstance();
        String fullApiUrl = market.getFullUrl();
        StringBuilder url = new StringBuilder(fullApiUrl.length());
        url.append(fullApiUrl);
        if (!fullApiUrl.endsWith("/")) {
            url.append("/");
        }
        url.append("ijinshan_off_");
        url.append(date);
        url.append("_");
        url.append(calendar.get(Calendar.HOUR_OF_DAY));
        url.append(".csv");
        url.append("?t=");
        url.append(System.currentTimeMillis());
        return url.toString();
    }

    @Override
    public String getIncrementUrl(Market market) {
        if (market == null) {
            return null;
        }
        // http://img.yingyonghui.com/interface/ijinshan/ijinshan_on_{day}_{hour}.csv.gz
        DateTime dt = new DateTime(market.getIncrementLastTime());
        dt = dt.plusHours(1);
        String date = new java.text.SimpleDateFormat("yyyyMMdd_H").format(dt.toDate());
        String incrementApiUrl = market.getIncrementUrl();
        StringBuilder url = new StringBuilder(incrementApiUrl.length());
        url.append(incrementApiUrl);
        if (!incrementApiUrl.endsWith("/")) {
            url.append("/");
        }
        url.append("ijinshan_on_");
        url.append(date);
        url.append(".csv.gz");
        url.append("?t=");
        url.append(System.currentTimeMillis());
        final String fileUrl = url.toString();
        logger.info("AppChina : {}", fileUrl);
        return fileUrl;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public PaginationMarketApp getMarketAppForFull(URIBuilder builder) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaginationMarketApp getMarketAppForIncrement(URIBuilder builder) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getMarketlogger() {
        return marketLogger;
    }
}
