package com.ijinshan.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PathUtils {
    private static final Logger logger = LoggerFactory.getLogger(PathUtils.class);

    public static String concate(String prefixDir, String subPath) {
        if (prefixDir.endsWith("/")) {
            return new StringBuilder(prefixDir.length() + subPath.length()).append(prefixDir).append(subPath)
                    .toString();
        } else {
            return new StringBuilder(prefixDir.length() + 1 + subPath.length()).append(prefixDir).append("/")
                    .append(subPath).toString();
        }
    }

    public static void makeSureDir(File dstDir) {
        if (dstDir != null && !dstDir.exists()) {
            try {
                FileUtils.forceMkdir(dstDir);
            } catch (IOException e) {
                logger.error(dstDir.getAbsolutePath(), e);
            }
        }
    }

}
