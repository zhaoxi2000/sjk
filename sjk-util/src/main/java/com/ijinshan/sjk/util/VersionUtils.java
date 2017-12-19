package com.ijinshan.sjk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VersionUtils {
    private static final Logger logger = LoggerFactory.getLogger(VersionUtils.class);

    /**
     * Compare two apks's version
     * 
     * @param v1
     * @param v2
     * @return If v1 > v2 , return 1 . If v1 < v2 , return -1;
     * @throws UnexceptedComparableException
     */
    public static int compare(final String v1, final String v2) throws UnexceptedComparableException {
        if (v1 == null && v2 == null) {
            throw new NullPointerException();
        }
        if (v1 == null && !v2.isEmpty()) {
            return -1;
        }
        if (!v1.isEmpty() && v2 == null) {
            return 1;
        }
        if (v1.equals(v2)) {
            return 0;
        }

        final String numbericV1 = toNumbericVersion(v1);
        final String numbericV2 = toNumbericVersion(v2);

        // \d+{.\d+}+
        final boolean containsDot = v1.contains(".") || v2.contains(".");
        if (containsDot) {
            String[] v1AllKindVers = numbericV1.split("\\.");
            String[] v2AllKindVers = numbericV2.split("\\.");
            // let's assure v2 > v1 , return negative. or v1 > v2 , return
            // positive.
            if (v1AllKindVers.length >= v2AllKindVers.length) {
                return -compareNumericAndDotVersion(v2AllKindVers, v1AllKindVers);
            } else {
                return compareNumericAndDotVersion(v1AllKindVers, v2AllKindVers);
            }
        }

        logger.info("Cann't compare {} to {} ", v1, v2);
        throw new UnexceptedComparableException("Cann't judge the two version!");
    }

    /**
     * 将人为定义的版本号全部替换成 \d+(.\d)*形式
     * 
     * @param originalVersion
     * @return
     */
    public static String toNumbericVersion(String originalVersion) {
        String result = null;
        originalVersion = originalVersion.toLowerCase().replace('v', '\u0000');
        originalVersion = originalVersion.replace("v", "");
        originalVersion = originalVersion.replace("V", "");
        originalVersion = originalVersion.replace("build", ".0.");
        originalVersion = originalVersion.replace("Build", ".0.");
        originalVersion = originalVersion.replace(" ", ".0.");
        originalVersion = originalVersion.replace("alpha", ".1.");
        originalVersion = originalVersion.replace("beta", ".2.");
        originalVersion = originalVersion.replace("release", ".3.");
        originalVersion = originalVersion.replace("rel", ".3.");
        originalVersion = originalVersion.replace("-", ".0");
        originalVersion = originalVersion.replace("+", ".1");
        Pattern pattern = Pattern.compile("([a-z]+?)");
        Matcher m = pattern.matcher(originalVersion);
        if (m.find()) {
            logger.debug("groupCount: {}", m.groupCount());
            StringBuilder replacement = new StringBuilder(".");
            for (int i = 0; i < m.groupCount(); i++) {
                String target = m.group(i);
                for (char c : target.toCharArray()) {
                    replacement.append((int) c).append(".");
                }
                replacement.append(target);
            }
            result = replacement.toString();
        } else {
            result = originalVersion;
        }
        return result;
    }

    public static int compareNumericAndDotVersion(String[] shorterLength, String[] longerLength) {
        int s = Integer.MIN_VALUE, l = Integer.MAX_VALUE;
        for (int i = 0; i < shorterLength.length; i++) {
            if (shorterLength[i].isEmpty()) {
                s = 0;
            } else {
                s = Integer.valueOf(shorterLength[i]);
            }
            if (longerLength[i].isEmpty()) {
                l = 0;
            } else {
                l = Integer.valueOf(longerLength[i]);
            }
            if (s > l) {
                return 1;
            }
            if (s < l) {
                return -1;
            }
        }
        if (shorterLength.length < longerLength.length) {
            return 1;
        }
        return 0;
    }
}
