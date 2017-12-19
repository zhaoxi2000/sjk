package com.ijinshan.sjk.service.impl;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.service.CDNCache;

@Repository(value = "cdnCacheImpl")
public class CDNCacheImpl implements CDNCache {
    private static final Logger logger = LoggerFactory.getLogger(CDNCacheImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    public final int intervalMin = 15;
    public final long interval = 1000 * 60 * intervalMin;

    public final byte[] minutesOfHour = { 0, 15, 30, 45 };

    public final String RFC1123_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    public final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");

    public final DateTimeFormatter fmt = ISODateTimeFormat.dateTime().withLocale(Locale.CHINESE);
    /**
     * unit: day
     */
    public final int expiresDaysForScansoftIfMiss = 14;
    public final String maxAgeIfMiss = new StringBuilder("max-age=")
            .append(expiresDaysForScansoftIfMiss * 24 * 60 * 60).toString();
    /**
     * unit: minute
     */
    public final int expiresMinuteForScansoftIfHit = 20;
    public final String maxAgeIfHit = new StringBuilder("max-age=").append(expiresMinuteForScansoftIfHit * 60)
            .toString();

    @Override
    public final long getCurrentTimeCloseToPrevInterval() {
        long keepMinute = System.currentTimeMillis() / interval * interval;
        DateTime dt = new DateTime(keepMinute);
        int min = dt.getMinuteOfHour();
        min = getPrevIntervalMinute(min);
        dt = dt.minuteOfHour().setCopy(min);
        return dt.getMillis();
    }

    @Override
    public void setCacheScanSoftIfMiss(HttpServletResponse resp) {
        DateTime dt = new DateTime();

        dt = dt.millisOfDay().setCopy(0);
        resp.setHeader("Last-Modified", toGMT(dt));
        dt = dt.plusDays(expiresDaysForScansoftIfMiss);
        resp.setHeader("Expires", toGMT(dt));
        resp.setHeader("Cache-Control", maxAgeIfMiss);
    }

    private String toGMT(DateTime dt) {
        String strDt = null;
        strDt = DateFormatUtils.format(dt.toDate(), RFC1123_DATE_PATTERN, GMT_TIME_ZONE, Locale.US);
        return strDt;
    }

    private String toUTC(DateTime dt) {
        String strDt = dt.withZone(DateTimeZone.UTC).toString(RFC1123_DATE_PATTERN, Locale.US);
        return strDt;
    }

    @Override
    public void setCacheScanSoftIfHit(HttpServletResponse resp) {
        DateTime now = new DateTime();

        long keepMinute = now.getMillis() / interval * interval;
        DateTime keepMinuteDateTime = new DateTime(keepMinute);
        int min = keepMinuteDateTime.getMinuteOfHour();
        min = getPrevIntervalMinute(min);
        DateTime lastModified = keepMinuteDateTime.minuteOfHour().setCopy(min);
        resp.setHeader("Last-Modified", toGMT(lastModified));
        DateTime expires = now.plusMinutes(expiresMinuteForScansoftIfHit);
        resp.setHeader("Expires", toGMT(expires));
        resp.setHeader("Cache-Control", maxAgeIfHit);
    }

    private int getPrevIntervalMinute(int min) {
        for (byte allowedMin : minutesOfHour) {
            if (min >= allowedMin) {
                min = allowedMin;
                break;
            }
        }
        return min;
    }
}
