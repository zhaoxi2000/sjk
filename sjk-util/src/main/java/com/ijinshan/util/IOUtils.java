package com.ijinshan.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * We use SLF4J because of its defacto standard.
 * 
 * @author LinZuXiong
 * @see org.slf4j.Logger
 */
public final class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    /**
     * No any Throwable
     * 
     * @param c
     *            type:java.io.Closeable
     */
    public static void closeQuietly(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (IOException e) {
            logger.error("Exception", e);
        }
    }

    /**
     * No any Throwable
     */
    public static void closeQuietly(Closeable... cs) {
        try {
            for (Closeable c : cs)
                if (c != null) {
                    c.close();
                }
        } catch (IOException e) {
            logger.error("Exception", e);
        }
    }
}
