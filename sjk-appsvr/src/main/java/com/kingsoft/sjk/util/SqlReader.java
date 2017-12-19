package com.kingsoft.sjk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取Sql 语句,来直接查询, 不用再在java中格式化sql语句
 * 
 * @author LinZuXiong
 */
public final class SqlReader {
    private static final Logger logger = LoggerFactory.getLogger(SqlReader.class);

    /**
     * @param cl
     * @param name
     * @see {@link Class#getResourceAsStream(String)}
     * @return
     * @throws IOException
     */
    public static String getSqlFileToString(Class<?> cl, String name) throws IOException {
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            is = cl.getResourceAsStream(name);
            br = new BufferedReader(new InputStreamReader(is), 1000);
            sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                if (!line.contains("--")) {
                    sb.append(line).append(" ");
                }
                // else {
                // //
                // logger.debug("In the file of {} .This line which has comments is :\t{}",
                // // line);
                // }
            }
        } finally {
            br.close();
            is.close();
        }
        // String content = sb.toString();
        // logger.info("SQL:\t{}", content);
        return sb.toString();
    }

    /**
     * @param name
     * @see {@link Class#getResourceAsStream(String)}
     * @return
     * @throws IOException
     */
    public static String getSqlFileToString(String name) throws IOException {
        return getSqlFileToString(SqlReader.class, name);
    }
}
