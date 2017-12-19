package com.ijinshan.sjk.config;

public enum Sort {
    DAILY(1, "lastDayDelta"), //
    WEEKLY(2, "lastWeekDelta"), //
    REAL_DOWNLOAD(3, "realDownload"), //
    DOWNLOADRANK(4, "downloadRank"), //
    ;
    private int val;
    private String dbColumnName;

    private Sort(int val, String dbColumnName) {
        this.val = val;
        this.dbColumnName = dbColumnName;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public void setDbColumnName(String dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public static Sort sort(int val) {
        switch (val) {
            case 1:
                return DAILY;
            case 2:
                return WEEKLY;
            case 3:
                return REAL_DOWNLOAD;
            case 4:
                return DOWNLOADRANK;
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    public static String getColumnName(int val) {
        switch (val) {
            case 1:
                return DAILY.dbColumnName;
            case 2:
                return WEEKLY.dbColumnName;
            case 3:
                return REAL_DOWNLOAD.dbColumnName;
            case 4:
                return DOWNLOADRANK.dbColumnName;
            default: {
                return "downloadRank";
            }
        }
    }

}
