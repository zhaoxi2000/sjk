package com.kingsoft.sjk.util;

public enum SvrResult {
    HTTP_INVALID_RESULT(600, "http_status_invalid_result,not be cached"), //
    HTTP_INVALID_REQUEST(610, "Server may be under attacked!"), //
    DB_SERVER_EXCEPTION(630, "Database server error!"), //
    APP_SERVER_EXCEPTION(640, "Appsvr error!"), //

    OK(0x0, "OK!"), ERROR(0x1, "Server Error!"), // SERVER STATUS
    PARAMS_ERROR(0x10, "Param Error!"), // client
    PARAMS_EMPTY(0x10, "Param Empty value!"), // client
    NO_DATA(0x20, "Has no data!"), // 没有数据
    PART_DATA(0x30, "Incomplete data!"), // 没有数据
    //
    ;
    private int code;
    private String msg;

    SvrResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private class Code {

    }
}
