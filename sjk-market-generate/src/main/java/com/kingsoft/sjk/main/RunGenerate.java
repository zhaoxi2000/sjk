package com.kingsoft.sjk.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kingsoft.sjk.service.AppService;

public class RunGenerate {
    private static final Logger logger = LoggerFactory.getLogger(RunGenerate.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 检测 判断 是否有程序运行
        // 将在指定目录 创建一个文件，run.lock
        // 程序运行结束 或者 异常将 删除 这个文件
        // 如果 有此文件存在 ，说明程序已经在运行状态
        String lockFilePath = "/data/apps/sjk-market-generate/run.lock";
        File lockFile = null;

        try {

            try {
                lockFile = new File(lockFilePath);
                if (lockFile.exists()) {
                    logger.info("The programe is running.It will exit.");
                    System.exit(0);
                } else {
                    FileUtils.forceMkdir(lockFile.getParentFile());
                    lockFile.createNewFile();
                    logger.info("The programe is goto run.");
                }
            } catch (Exception e) {
                logger.debug("lock file is not exists.The programe will run.");
                try {
                    FileUtils.forceMkdir(lockFile.getParentFile());
                    lockFile.createNewFile();
                } catch (IOException e1) {
                    logger.error("create lock file error:", e1);
                    System.exit(0);
                }

            }

            long start = System.currentTimeMillis();
            try {
                logger.info("BEGIN..");

                ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
                AppService processor = ctx.getBean("appServiceImpl", AppService.class);
                // 如果 传入了参数 1，表示 全部生成
                if (args.length > 0 && args[0].equals("1")) {
                    processor.findData(true);
                } else if (args.length > 0 && !args[0].equals("1")) {
                    processor.processAppById(Integer.valueOf(args[0]));
                } else {
                    processor.findData(false);
                }

                logger.info("OK!END!");
                logger.info("End! Cost times:{} seconds", (System.currentTimeMillis() - start) / 1000);
            } catch (Exception e) {
                logger.error("exception:", e);
            }
        } finally {

            FileUtils.deleteQuietly(lockFile);
            if (lockFile.exists()) {
                logger.error(" Delete lockFile undo!");
            }
            System.exit(0);
        }

    }
}
