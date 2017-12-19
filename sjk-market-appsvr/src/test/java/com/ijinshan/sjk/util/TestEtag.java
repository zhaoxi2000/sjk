/**
 * 
 */
package com.ijinshan.sjk.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-25 下午7:20:51
 * </pre>
 */
public class TestEtag {
    private static final Logger logger = LoggerFactory.getLogger(TestEtag.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        /*
         * for(int x = 97;x < 123; x ++){ System.out.println("==>" + (char)x); }
         */

        System.out.println("test=" + checkEqual('z', "  99999"));
        System.out.println("trim()==" + "".trim().toString());
        // System.out.println("test===>" + getFirstCharacter("mPHONE"));

        /*
         * String string =
         * "{\"result\":{\"code\":0,\"msg\":\"OK!\"},\"data\":[\"abc表情大全\",\"abc绘图\",\"abc字母卡片\",\"abc\",\"abc123\",\"abc27新闻\",\"abc7洛杉矶时钟\",\"abcd舞蹈\",\"abc为孩子们学习\"]}"
         * ;
         * 
         * String etagString = DigestUtils.md5DigestAsHex(string.getBytes());
         * 
         * System.out.println("0664ade1a54f3742da43fed04a63717e4");
         * System.out.println(etagString);
         */
    }

    // 判断两个数是否相等
    public static boolean checkEqual(char chr1, String chars) {
        if (StringUtils.isNotEmpty(chars.trim().toString())) {
            // if(PinYinUtils.converterToSpell(chars.trim().toString()).toLowerCase().substring(0,1).equals(chr1+"")){
            if (getFirstCharacter(PinYinUtils.converterToSpell(chars.trim().toString()).toLowerCase())
                    .equals(chr1 + "")) {
                logger.debug("chars:{}",
                        PinYinUtils.converterToSpell(chars.trim().toString()).toLowerCase().substring(0, 1));
                return true;
            }/*
              * else
              * if(Character.isDigit(PinYinUtils.converterToSpell(chars.trim
              * ().toString()).toLowerCase().substring(0,1).toCharArray()[0]) &&
              * PinYinUtils
              * .converterToSpell(chars.trim().toString()).toLowerCase
              * ().contains(chr1+"")){
              * logger.debug("chars contant chr1:{}",PinYinUtils
              * .converterToSpell
              * (chars.trim().toString()).toLowerCase().contains(chr1+""));
              * return true; }
              */
        }

        return false;
    }

    // 如果字符中包含有数字则取最近一个的字符
    public static String getFirstCharacter(String charts) {
        if (StringUtils.isNotEmpty(charts.trim().toString())) {
            for (int i = 0; i < charts.toCharArray().length; i++) {
                if (!Character.isDigit(charts.toCharArray()[i])) {
                    return (charts.toCharArray()[i] + "").toLowerCase();
                }
            }
        }
        return "";
    }

    protected static String generateETagHeaderValue(byte[] bytes) {
        StringBuilder builder = new StringBuilder("\"0");
        DigestUtils.appendMd5DigestAsHex(bytes, builder);
        builder.append('"');
        return builder.toString();
    }
}
