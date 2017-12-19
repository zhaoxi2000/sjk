package com.ijinshan.sjk.service.programmeimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.dao.TextParser;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.service.AbstractBaseMarketService;
import com.ijinshan.util.IOUtils;

public class FileImporterMarketServiceImpl extends AbstractBaseMarketService {
    private static final Logger logger = LoggerFactory.getLogger(FileImporterMarketServiceImpl.class);

    @Resource(name = "textParserImpl")
    private TextParser textParser;

    @Override
    public void importFull() {
        logger.info("{}. Begin to importFullByFile.", this.getMarketName());
        try {
            Market market = null;
            Session session = super.sessions.openSession();
            try {
                market = getMarket(session);
            } finally {
                session.close();
            }
            String strUrl = getAccessMarketDao().getFullUrl(market);
            String filename = extractFilename(strUrl);
            String destPath = getFullDestPath(filename);
            File file = null;
            int again = 0;
            do {
                file = getAccessMarketDao().getFileByUrl(strUrl, destPath);
                // file = new File("/ijinshan_all_20130220.csv.gz.txt");
                if (file != null && file.exists()) {
                    tryImport(market, file);
                    again = 0;
                } else {
                    again++;
                    if (again < TRY_TIME) {
                        sleepForTry();
                    }
                }
            } while (file == null && again < TRY_TIME);
            if (file == null) {
                logger.info("It is a fail!!!End to importFullByFile for {}. Url: {}", this.getMarketName(), strUrl);
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        logger.info("{}. End up importing FullByFile!", this.getMarketName());
    }

    @Override
    public void importIncrement() {
        logger.info("{}. Begin to importIncrementByFile.", this.getMarketName());
        try {
            // 增量文件,处理遗漏的Job! 循环取下载地址. 如果请求的当次时间不是在这一个小时.
            Date now = null, lastTime = null;
            boolean nextFile = false;
            do {
                now = new Date();
                Session session = super.sessions.openSession();
                Market market = null;
                try {
                    market = getMarket(session);
                } finally {
                    session.close();
                }

                lastTime = market.getIncrementLastTime();
                int nowHour = new DateTime(now).hourOfDay().get(), lastTimeHour = new DateTime(lastTime).hourOfDay()
                        .get();
                // 这个代码写的很昏. 请优化这个方法块.
                if (lastTime.getTime() > now.getTime()
                        || (Math.abs(now.getTime() - lastTime.getTime()) < 3600 * 1000 && nowHour == lastTimeHour)) {
                    logger.info("{}. importIncrementByFile end!", this.getMarketName());
                    return;
                }
                String strUrl = getAccessMarketDao().getIncrementUrl(market);
                String filename = extractFilename(strUrl);
                String destPath = getFullDestPath(filename);
                File file = null;
                int again = 0;
                do {
                    file = getAccessMarketDao().getFileByUrl(strUrl, destPath);
                    // file = new File("/ijinshan_all_20130220.csv.gz.txt");
                    if (file != null && file.exists()) {
                        tryImport(market, file);
                        again = 0;
                        lastTime = new DateTime(lastTime).plusHours(1).toDate();
                        resetMarketForIncrement(lastTime);
                    } else {
                        again++;
                        if (again < TRY_TIME) {
                            sleepForTry();
                        }
                    }
                } while (file == null && again < TRY_TIME);
                if (file == null) {
                    logger.info("It is a fail!!!End to importIncrementByFile for {}. Url : {}", this.getMarketName(),
                            strUrl);
                    // 尝试了几次还没有 , 那就真没有这个文件.
                    resetMarketForIncrement(new DateTime(lastTime).plusMinutes(10).toDate());
                }
                // 上次拉取的时间若不在相同的小时内.
                nextFile = (now.getTime() > lastTime.getTime()) && nowHour != lastTimeHour;
            } while (nextFile);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        logger.info("{}. importIncrementByFile end!", this.getMarketName());
    }

    protected void tryImport(final Market market, final File file) throws IOException {
        File decompressFile = getAccessMarketDao().decompress(file);
        saveOrUpdateByReadFile(decompressFile, market);
        if (file.exists() && file.isFile()) {
            logger.debug("Delete a file {}", file.getName());
            FileUtils.deleteQuietly(file);
        }
        if (decompressFile.exists() && decompressFile.isFile()) {
            logger.debug("Delete a file {}", decompressFile.getName());
            FileUtils.deleteQuietly(decompressFile);
        }
    }

    protected String extractFilename(String strUrl) {
        String[] query = strUrl.split("/");
        String filename = query[query.length - 1];
        if (filename.contains("?")) {
            filename = filename.split("\\?")[0];
        }
        return filename;
    }

    protected String getFullDestPath(String filename) {
        StringBuilder path = new StringBuilder();
        path.append(appConfig.getTmpFullDir());
        if (!appConfig.getTmpFullDir().endsWith("/")) {
            path.append("/");
        }
        path.append(this.getMarketName()).append("-");
        path.append(filename);
        return path.toString();
    }

    public void saveOrUpdateByReadFile(File file, Market market) throws IOException {
        // logger.debug("{}. Begin to read file...", market.getMarketName());
        BufferedReader br = null;
        int pageNum = 0, count = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fis, "UTF-8"), 1200 * market.getPageSize());
            List<String> content = new ArrayList<String>(market.getPageSize());
            String one = null;
            while ((one = br.readLine()) != null) {
                if (!one.isEmpty()) {
                    count++;
                    content.add(one);
                    if (content.size() == market.getPageSize()) {
                        pageNum++;
                        saveOnePage(content, market);
                        content.clear();
                    }
                }
            }
            if (!content.isEmpty()) {
                pageNum++;
                saveOnePage(content, market);
                content.clear();
            }
        } finally {
            IOUtils.closeQuietly(br);
        }
        logger.info("Read {} file done! The records' pageNum : {}\t count is : {}", market.getMarketName(), pageNum,
                count);
    }

    public void saveOnePage(List<String> content, Market market) {
        // 组装marketApps
        List<MarketApp> marketApps = new ArrayList<MarketApp>(content.size());
        for (String one : content) {
            if (one.isEmpty()) {
                continue;
            }
            try {
                MarketApp mApp = textParser.parser(market.getMarketName(), one);
                if (mApp != null) {
                    marketApps.add(mApp);
                }
            } catch (Exception e) {
                getDbexceptionapplogger().error("{}", one);
                getDbexceptionapplogger().error("Exception", e);
            }
        }
        if (marketApps.isEmpty()) {
            return;
        }
        Session session = sessions.openSession();
        Transaction tx = null;
        try {
            List<MarketApp> offMarketApps = new ArrayList<MarketApp>();
            session.setDefaultReadOnly(false);
            tx = session.beginTransaction();
            savePaginationMarketApp(session, market, marketApps, offMarketApps);
            tx.commit();
            session.close();
            deleteMarketAppsTransaction(offMarketApps);
            marketApps.clear();
            offMarketApps.clear();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Exception", e);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        // logger.info("Save one page for {} done.", market.getMarketName());
    }

    public void doDelete() {
        try {
            Market market = null;
            Session session = super.sessions.openSession();
            try {
                market = getMarket(session);
            } finally {
                session.close();
            }
            String strUrl = getAccessMarketDao().getOffUrl(market);
            String filename = extractFilename(strUrl);
            String destPath = getFullDestPath(filename);
            File file = null;
            file = getAccessMarketDao().getFileByUrl(strUrl, destPath);
            if (file != null) {
                try {
                    deleteByReadFile(file, market);
                } finally {
                    if (file.exists() && file.isFile()) {
                        logger.debug("Delete a file {}", file.getName());
                        FileUtils.deleteQuietly(file);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    private void deleteByReadFile(File file, Market market) throws IOException {
        logger.info("Begin to read the file of off app...");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            List<Integer> apkIdsOfMarket = new ArrayList<Integer>();
            String one = null;
            while ((one = br.readLine()) != null) {
                if (!one.isEmpty()) {
                    String[] ids = one.trim().split(",");
                    for (String s : ids) {
                        apkIdsOfMarket.add(Integer.parseInt(s));
                    }
                }
            }
            if (!apkIdsOfMarket.isEmpty()) {
                offAppService.deleteBatchByApkIdsOfMarketApp(market.getMarketName(), apkIdsOfMarket);
                apkIdsOfMarket.clear();
                apkIdsOfMarket = null;
            }
        } finally {
            IOUtils.closeQuietly(br);
        }
        logger.info("Read the file of off app done! Delete done!");
    }

}
