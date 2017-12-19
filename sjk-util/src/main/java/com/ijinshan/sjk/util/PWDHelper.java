package com.ijinshan.sjk.util;

import org.apache.commons.codec.digest.DigestUtils;

public final class PWDHelper {

    public static String escape(String pwd) {
        return DigestUtils.md5Hex(pwd + "金山卫士手机助手");
    }

}
