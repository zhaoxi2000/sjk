package com.ijinshan.sjk.util;

public enum SvrResult {
    HTTP_INVALID_RESULT(600, "http_status_invalid_result,not be cached"), //
    HTTP_INVALID_REQUEST(610, "Server may be under attacked!"), //
    DB_SERVER_EXCEPTION(630, "Database server error!"), //
    APP_SERVER_EXCEPTION(640, "Appsvr error!"), //

    OK(0x0, "OK!"), ERROR(0x1, "Server Error!"), // SERVER STATUS
    CLIENT_ILLEGAL_LOGIN(0x10, "Illegal Login"), // client
    CLIENT_PARAMS_ERROR(0x11, "Param Error!"), // client
    CLIENT_PARAMS_EMPTY(0x12, "Param Empty value!"), // client
    NO_DATA(0x20, "Has no data!"), // 没有数据
    PART_DATA(0x30, "Incomplete data!"), // 部份数据
    INVALID_JSON(0x40, "Invalid JSON"), // client

    SERVER_DB_ERROR(0x1000, "SERVER_DB_ERROR"), // db error
    SERVER_MEMCACHED_ERROR(0x2000, "SERVER_MEMCACHED_ERROR"), // memcached
                                                              // error
    SERVER_REDIS_ERROR(0x3000, "SERVER_REDIS_ERROR"), // memcached error
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
}
