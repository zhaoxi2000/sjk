/**
 * 
 */
package com.ijinshan.sjk.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ijinshan.sjk.service.AppService;

/**
 * @author LinZuXiong
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.debug("Begin...");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        AppService service = ctx.getBean("appServiceImpl", AppService.class);
        service.deleteDeplicationAppByPknameMarketName();
        logger.debug("End successfully!");
        ((ClassPathXmlApplicationContext) ctx).close();
        System.exit(0);
    }
}
