/**
 * 
 */
package com.ijinshan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Linzuxiong
 */
public final class StringHelper {
    private static final Logger logger = LoggerFactory.getLogger(StringHelper.class);

    public static String join(String seperator, String[] strings) {
        int length = strings.length;
        if (length == 0)
            return "";
        StringBuilder buf = new StringBuilder(length * strings[0].length()).append(strings[0]);
        for (int i = 1; i < length; i++) {
            buf.append(seperator).append(strings[i]);
        }
        return buf.toString();
    }
}
