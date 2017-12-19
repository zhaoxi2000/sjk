package com.ijinshan.util;

import java.util.Date;

import org.joda.time.DateTime;

public class HighAndLowDate {
    private Date high;
    private Date low;
    private String dayFormat = "yyyy-MM-dd";

    public HighAndLowDate(long date) {
        super();
        Date current = new Date(date);
        DateTime low = new DateTime(current);
        low = low.hourOfDay().setCopy(0);
        low = low.minuteOfHour().setCopy(0);
        low = low.secondOfMinute().setCopy(0);
        low = low.millisOfSecond().setCopy(0);

        DateTime high = low.plusDays(1).minusMillis(1);

        this.low = low.toDate();
        this.high = high.toDate();
    }

    private HighAndLowDate(Date high, Date low) {
        super();
        this.high = high;
        this.low = low;
    }

    public Date getHigh() {
        return high;
    }

    public void setHigh(Date high) {
        this.high = high;
    }

    public Date getLow() {
        return low;
    }

    public void setLow(Date low) {
        this.low = low;
    }

}
