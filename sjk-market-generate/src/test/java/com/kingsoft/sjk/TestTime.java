/**
 * 
 */
package com.kingsoft.sjk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-25 下午6:54:08
 * </pre>
 */
public class TestTime {
    private static final Logger logger = LoggerFactory.getLogger(TestTime.class);

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // 2013-02-25 16:45:00

        Date spDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2013-02-25 16:45:00");

        String curDateString = DateFormatUtils.format(DateUtils.addDays(spDate, -1), "yyyy-MM-dd");
        logger.debug(curDateString);
    }
}
