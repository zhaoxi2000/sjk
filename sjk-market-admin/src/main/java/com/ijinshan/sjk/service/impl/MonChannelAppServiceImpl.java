package com.ijinshan.sjk.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.com.sjk.sms.SmsBase;
import com.ijinshan.sjk.config.SmsConfig;
import com.ijinshan.sjk.dao.MonUserChannelAppDao;
import com.ijinshan.sjk.po.MonUserChannelApp;
import com.ijinshan.sjk.service.MonChannelAppService;

@Service
public class MonChannelAppServiceImpl implements MonChannelAppService {
    private static final Logger logger = LoggerFactory.getLogger(MonChannelAppServiceImpl.class);

    @Autowired
    private MonUserChannelAppDao monUserChannelAppDao;

    @Autowired
    private SmsConfig smsConfig;

    private static String smsTile = "金山手机助手渠道包更新提醒：";

    @Override
    public MonUserChannelApp get(int id) {
        return monUserChannelAppDao.get(id);
    }

    @Override
    public boolean saveAndUpdate(MonUserChannelApp app, MonUserChannelApp oldEntity) {
        if (app.getId() > 0) {
            if (oldEntity != null) {
                app.setCoverVersionCode(oldEntity.getCoverVersionCode());
                app.setCoverVerson(oldEntity.getCoverVerson());
                app.setCoverMarketName(oldEntity.getCoverMarketName());
                app.setLastFetchTime(oldEntity.getLastFetchTime());
                app.setSMailStatus(oldEntity.getSMailStatus());
                app.setVersion(oldEntity.getVersion());
                app.setVersionCode(oldEntity.getVersionCode());
                app.setOptTime(oldEntity.getOptTime());
                monUserChannelAppDao.update(app);
            }
        } else {
            app.setCreateTime(new Date());
            monUserChannelAppDao.save(app);
        }
        return true;
    }

    @Override
    public boolean isExists(String marketName, Integer apkId) {
        return monUserChannelAppDao.isExists(marketName, apkId);
    }

    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return monUserChannelAppDao.deleteByIds(ids);
    }

    @Override
    public List<MonUserChannelApp> queryList(String keyword, Boolean autoCover, int page, int rows, String order,
            String sort) {
        return monUserChannelAppDao.queryList(keyword, autoCover, page, rows, order, sort);
    }

    @Override
    public long countForSearching(String keyword, Boolean autoCover) {
        return monUserChannelAppDao.countForSearching(keyword, autoCover);
    }

    @Override
    public boolean updateSmsUserChannelUpdate(List<MonUserChannelApp> list) {
        if (list == null) {
            return false;
        }
        for (MonUserChannelApp monUserChannelApp : list) {
            if (monUserChannelApp != null && monUserChannelApp.isAutoCover() && monUserChannelApp.isAcceptSms()) {
                StringBuilder sbStr = new StringBuilder();
                // sbStr.append("您好，您提供给金山手机助手的<<").append(monUserChannelApp.getAppName()).append(">>渠道包版本过低(")
                // .append("VersionName:").append(monUserChannelApp.getVersion()).append("  VersionCode:")
                // .append(monUserChannelApp.getVersionCode()).append(") ")
                // .append(monUserChannelApp.getCoverMarketName()).append("市场已有更新版本").append("(VersionName:")
                // .append(monUserChannelApp.getCoverVerson()).append("  VersionCode:")
                // .append(monUserChannelApp.getCoverVersionCode()).append(");请尽快提供更新包到 \"莫凯琪\"")
                // .append(smsConfig.getSmsContact());
                sbStr.append("【渠道包更新提醒】您好，您提供于金山手机助手的《").append(monUserChannelApp.getAppName()).append("》")
                        .append("渠道包版本(").append(monUserChannelApp.getVersion()).append(")过低，现")
                        .append(monUserChannelApp.getCoverMarketName()).append("市场已有更新版本(")
                        .append(monUserChannelApp.getCoverVerson()).append("),请尽快更新渠道包。")
                        .append(smsConfig.getSmsContact());
                logger.info(sbStr.toString());
                int reslut = SmsBase.sendMessage(monUserChannelApp.getPhone(), sbStr.toString(), smsConfig);
                logger.info("status :{}", reslut);
                monUserChannelApp.setOptTime(new Date());
                monUserChannelApp.setSMailStatus(reslut);
                monUserChannelAppDao.update(monUserChannelApp);
                logger.info("update status :");
            }
        }
        return true;
    }

    @Override
    public List<MonUserChannelApp> queryList(List<Integer> ids) {
        return monUserChannelAppDao.queryList(ids);
    }

}
