package com.ijinshan.util;

/**
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LinZuXiong
 */
public final class DateString {
    private static final Logger logger = LoggerFactory.getLogger(DateString.class);
    public static final String dateFormatter = "yyyy-MM-dd";
    public static final String dateTimeFormatter = "yyyy-MM-dd HH:mm:ss";

    public static String newDateString() {
        return new java.text.SimpleDateFormat(dateFormatter).format(new java.util.Date());
    }

    public static String newDateTimeString() {
        return new java.text.SimpleDateFormat(dateTimeFormatter).format(new java.util.Date());
    }

    public static String parseDate(Date date) {
        return new java.text.SimpleDateFormat(dateFormatter).format(date);
    }

    public static String parseDateTime(Date date) {
        return new java.text.SimpleDateFormat(dateTimeFormatter).format(date);
    }

    /**
     * 字符串转换成日期
     * 
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormatter);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
