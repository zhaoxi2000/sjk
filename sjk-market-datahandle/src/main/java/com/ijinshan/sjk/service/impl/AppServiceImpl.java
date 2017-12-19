package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.service.AppService;

@Transactional
@Service
public class AppServiceImpl implements AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    @Override
    public void handle() {
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteDeplicationAppByPknameMarketName() {
        List<App> deplicationAppByPknameMarketName = appDao.getLg2AppByPknameMarketName();
        if (deplicationAppByPknameMarketName != null) {
            int count = 0;
            int deleteCount = 0;
            for (App app : deplicationAppByPknameMarketName) {
                if (++count % 30 == 0) {

                }
                String pkname = app.getPkname();
                String marketname = app.getMarketName();

                List<App> handleApps = appDao.getApps(pkname, marketname);
                Assert.isTrue(handleApps.size() > 1);
                int realdownlaodSum = 0, lastDayDownload = 0, deltaDownload = 0;
                for (int i = 0; i < handleApps.size(); i++) {
                    App handleApp = handleApps.get(i);
                    realdownlaodSum += handleApp.getRealDownload();
                    lastDayDownload += handleApp.getLastDayDownload();
                    deltaDownload = Math.max(deltaDownload, handleApp.getDeltaDownload());
                }
                App firstApp = handleApps.get(0);
                firstApp.setRealDownload(realdownlaodSum);
                firstApp.setLastDayDownload(lastDayDownload);
                firstApp.setDeltaDownload(deltaDownload);
                appDao.update(firstApp);
                for (int i = 1; i < handleApps.size(); i++) {
                    appDao.delete(handleApps.get(i));
                    deleteCount++;
                }
            }// end loop
            logger.info("delete {} records.", deleteCount);
        }

    }
}
