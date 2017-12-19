package com.ijinshan.sjk.service.programmeimpl;

import java.io.File;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.dao.AccessMarketDao;
import com.ijinshan.sjk.po.Market;

@Service
public class NotifyDataServiceImpl extends FileImporterMarketServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(NotifyDataServiceImpl.class);

    @Resource(name = "accessShoujikong_ChannelDaoImpl")
    private AccessMarketDao accessMarketDao;

    private final String marketName = EnumMarket.SHOUJIKONG_CHANNEL.getName();

    public void importByUrl(String marketName, String download) {
        try {
            Session session = sessions.openSession();
            Market market = null;
            try {
                market = this.marketDao.getByName(session, marketName);
            } finally {
                session.close();
            }

            String filename = super.extractFilename(download);
            String destPath = getFullDestPath(filename) + ".notify.csv";
            File file = null;
            int again = 0;
            do {
                file = getAccessMarketDao().getFileByUrl(download, destPath);
                if (file != null) {
                    logger.info("Prepare import data!");
                    super.tryImport(market, file);
                    again = 0;
                } else {
                    again++;
                    if (again < TRY_TIME) {
                        sleepForTry();
                    }
                }
            } while (file == null && again < 3);
            if (file == null) {
                logger.info("It is a fail!!!End to importByUrl for {}.", marketName);
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        logger.info("End to importByUrl for {}", marketName);
    }

    @Override
    public AccessMarketDao getAccessMarketDao() {
        return accessMarketDao;
    }

    @Override
    public void setAccessMarketDao(AccessMarketDao accessMarketDao) {
        this.accessMarketDao = accessMarketDao;
    }

    @Override
    public String getMarketName() {
        return marketName;
    }

}
