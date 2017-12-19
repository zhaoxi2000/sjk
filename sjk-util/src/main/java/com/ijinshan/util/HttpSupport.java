package com.ijinshan.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.input.CountingInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpSupport {
    private static final Logger logger = LoggerFactory.getLogger(HttpSupport.class);

    public static InputStream getMethod(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("GET");
        setCommonProperty(conn);
        conn.setDoOutput(false);
        return getResponse(conn);
    }

    public static InputStream postMethod(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("POST");
        setCommonProperty(conn);
        return getResponse(conn);
    }

    public static InputStream postMethod(HttpURLConnection conn, InputStream inputStream) throws IOException {
        conn.setRequestMethod("POST");
        setCommonProperty(conn);
        OutputStream outputStream = null;
        try {
            outputStream = conn.getOutputStream();
            final int bNum = 4096;
            byte[] buffer = new byte[bNum];
            int len = -1;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return getResponse(conn);
    }

    public static void setCommonProperty(HttpURLConnection conn) {
        final int oneMin = 60000;
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(oneMin);
        conn.setReadTimeout(oneMin * 5);
        conn.setRequestProperty("Accept", "text/plain,text/html,application/json;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh,en;q=0.8");
        conn.setRequestProperty("Accept-Charset", "utf-8;q=0.7,*;q=0.3");
        conn.setRequestProperty("Accept-Encoding", "gzip");
        conn.setRequestProperty("User-Agent", "ijinshan ijinshan/spider");
    }

    public static InputStream getResponse(HttpURLConnection conn) throws IOException {
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
