package com.ijinshan.sjk.vo;

import java.sql.Time;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.LocalTime;

public final class TimeTable {

    private int id;
    private Time beginTime;
    private Time endTime;
    private LocalTime beginJodaLocalTime = null;
    private LocalTime endJodaLocalTime = null;

    private int maxCount;
    private int probability;
    private AtomicInteger maxCountAtomic = null;

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final Time getBeginTime() {
        return beginTime;
    }

    public final void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public final Time getEndTime() {
        return endTime;
    }

    public final void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public final int getMaxCount() {
        return maxCount;
    }

    public final void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public final int getProbability() {
        return probability;
    }

    public final void setProbability(int probability) {
        this.probability = probability;
    }


    public final AtomicInteger getMaxCountAtomic() {
        return maxCountAtomic;
    }

    public final void setMaxCountAtomic(AtomicInteger maxCountAtomic) {
        this.maxCountAtomic = maxCountAtomic;
    }

    public final LocalTime getBeginJodaLocalTime() {
        return beginJodaLocalTime;
    }

    public final void setBeginJodaLocalTime(LocalTime beginJodaLocalTime) {
        this.beginJodaLocalTime = beginJodaLocalTime;
    }

    public final LocalTime getEndJodaLocalTime() {
        return endJodaLocalTime;
    }

    public final void setEndJodaLocalTime(LocalTime endJodaLocalTime) {
        this.endJodaLocalTime = endJodaLocalTime;
    }

}
