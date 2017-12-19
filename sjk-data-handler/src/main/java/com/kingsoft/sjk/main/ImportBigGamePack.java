/**
 * 
 */
package com.kingsoft.sjk.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kingsoft.sjk.game.service.BigGameDataHandle;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-26 上午9:46:32
 * </pre>
 */
public class ImportBigGamePack {
    private static final Logger logger = LoggerFactory.getLogger(ImportBigGamePack.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        logger.debug("Begin process  info ...");
        // 查询所有 的大游戏数据，并将其导入bigGamePack
        long start = System.currentTimeMillis();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("./context.xml");
        BigGameDataHandle bigGameDataHandle = ctx.getBean("bigGameDataHandle", BigGameDataHandle.class);

        bigGameDataHandle.handleData();
        long end = System.currentTimeMillis();

        logger.debug("end.cost times:{}s", (end - start) / 1000l);

    }
}
