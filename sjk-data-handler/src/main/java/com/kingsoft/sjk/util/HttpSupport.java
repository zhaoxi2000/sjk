package com.kingsoft.sjk.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.input.CountingInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpSupport {
    private static final Logger logger = LoggerFactory.getLogger(HttpSupport.class);

    public static InputStream getMethod(HttpURLConnection conn) throws URISyntaxException, IOException {
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/html,application/json;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh,en;q=0.8");
        conn.setRequestProperty("Accept-Charset", "utf-8;q=0.7,*;q=0.3");
        conn.setRequestProperty("Accept-Encoding", "gzip");

        conn.connect();
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        String transferEncoding = conn.getHeaderField("Transfer-Encoding");
        InputStream originInputStream = conn.getInputStream();
        if (transferEncoding == null) {
            return originInputStream;
        }
        if (transferEncoding != null && !"chunked".equals(transferEncoding.toLowerCase())) {
            return originInputStream;
        }
        CountingInputStream counter = null;
        String contentEncoding = conn.getHeaderField("Content-Encoding");
        if (contentEncoding != null && "gzip".equals(contentEncoding.toLowerCase())) {
            counter = new CountingInputStream(new GZIPInputStream(originInputStream));
        } else {
            counter = new CountingInputStream(originInputStream);
        }
        return counter;
    }
}
