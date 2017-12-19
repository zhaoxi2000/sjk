package com.ijinshan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultDateTime {

    public static Date defaultDateTime;
    static {
        try {
            defaultDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-01-01 00:00:00");
        } catch (ParseException e) {
            defaultDateTime = null;
        }
    }

    public static final Date getDefaultDateTime() {
        return defaultDateTime;
    }

    private static final void setDefaultDateTime(Date defaultDateTime) {
        DefaultDateTime.defaultDateTime = defaultDateTime;
    }

}
