package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.com.sjk.sms.SmsBase;
import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.config.SmsConfig;
import com.ijinshan.sjk.dao.MonMarketAppDao;
import com.ijinshan.sjk.dao.MonUserChannelAppDao;
import com.ijinshan.sjk.po.MonApp;
import com.ijinshan.sjk.po.MonChannelApp;
import com.ijinshan.sjk.po.MonUserChannelApp;
import com.ijinshan.sjk.service.RunMonChannelService;

@Service
public class RunMonChannelServiceImpl implements RunMonChannelService {
    private static final Logger logger = LoggerFactory.getLogger(RunMonChannelServiceImpl.class);

    @Autowired
    private MonMarketAppDao monMarketAppDao;

    @Autowired
    private MonUserChannelAppDao monUserChannelAppDao;

    @Autowired
    private SmsConfig smsConfig;

    @Override
    public void updateMonChannelApp() {
        logger.info("Begin run Mon Channel App...");
        // 增量同步渠道包数据
        updateSysnMakertAppToMonChannelApp();
        // 检测渠道包,更状态
        List<MonUserChannelApp> smsList = checkChannelAppUdate();
        // 发送短信
        smsMonUserChannelApp(smsList);
        if (smsList != null) {
            smsList.clear();
        }
        logger.info("Begin run Mon Channel App ...");
    }

    /** 检测渠道包，并且把渠道包状态进行修改 */
    private List<MonUserChannelApp> checkChannelAppUdate() {
        List<MonApp> monAppList = monMarketAppDao.queryCoverApp(EnumMarket.SHOUJIKONG_CHANNEL.getName());
        List<MonUserChannelApp> list = monUserChannelAppDao.queryList(EnumMarket.SHOUJIKONG_CHANNEL.getName(), true);
        if (list == null || monAppList == null) {
            logger.info("Channel packet that is not covered");
            return null;
        }
        List<MonUserChannelApp> updateList = new ArrayList<MonUserChannelApp>();
        List<MonUserChannelApp> smsList = new ArrayList<MonUserChannelApp>();
        StringBuilder sbStr = null;
        for (MonUserChannelApp monUserChannelApp : list) {
            MonApp monApp = existMonUserChannelApp(monUserChannelApp, monAppList);
            if (monApp != null) {
                if (!monUserChannelApp.isAutoCover()) {
                    sbStr = new StringBuilder();
                    sbStr.append("marketName:").append(monUserChannelApp.getMarketName()).append("  name:")
                            .append(monUserChannelApp.getApkId());
                    logger.info("Channel package from being overwritten");
                    // 如果检查到有渠道包覆盖 设置是否覆盖为True
                    monUserChannelApp.setAutoCover(true);
                    monUserChannelApp.setMarketName(monApp.getMarketName());
                    monUserChannelApp.setVersion(monApp.getVersion());
                    monUserChannelApp.setVersionCode(monApp.getVersionCode());
                    monUserChannelApp.setCoverMarketName(monApp.getNewMarketName());
                    monUserChannelApp.setCoverVerson(monApp.getNewVersion());
                    monUserChannelApp.setCoverVersionCode(monApp.getNewVersionCode());
                    monUserChannelApp.setLastFetchTime(monApp.getAppFetchTime());
                    monUserChannelAppDao.update(monUserChannelApp);
                    smsList.add(monUserChannelApp);
                    updateList.add(monUserChannelApp);
                }
            } else {
                // 如果检测没有渠道包覆盖 则设置是否渠道包覆盖为true 修改成false
                if (monUserChannelApp.isAutoCover()) {
                    monUserChannelApp.setAutoCover(false);
                    monUserChannelApp.setCoverMarketName("");
                    monUserChannelApp.setCoverVerson("");
                    monUserChannelApp.setCoverVersionCode(0);
                    monUserChannelApp.setSMailStatus(2);
                    updateList.add(monUserChannelApp);
                }
            }
        }
        if (updateList != null) {
            for (MonUserChannelApp monUserChannelApp : updateList) {
                monUserChannelAppDao.update(monUserChannelApp);
            }
        }
        return smsList;
    }

    /**
     * 批量发送短信
     * 
     * @param smsList
     *            ：被覆盖的渠道包短信提醒
     */
    private void smsMonUserChannelApp(List<MonUserChannelApp> smsList) {
        try {
            if (smsList != null && !smsConfig.isSmsDisable()) {
                for (MonUserChannelApp monUserChannelApp : smsList) {
                    if (StringUtils.isNotEmpty(monUserChannelApp.getPhone())) {
                        StringBuilder sbStr = new StringBuilder();
                        // sbStr.append("您好，您提供给金山手机助手的<<").append(monUserChannelApp.getAppName()).append(">>渠道包版本过低(")
                        // .append("VersionName:").append(monUserChannelApp.getVersion()).append("  VersionCode:")
                        // .append(monUserChannelApp.getVersionCode()).append(") ")
                        // .append(monUserChannelApp.getCoverMarketName()).append("市场已有更新版本")
                        // .append("(VersionName:").append(monUserChannelApp.getCoverVerson())
                        // .append("  VersionCode:").append(monUserChannelApp.getCoverVersionCode())
                        // .append(");请尽快提供更新包到 \"莫凯琪\"").append(smsConfig.getSmsContact());
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
                        logger.info("update status");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("sms MonUserChannelApp error:", e);
        }

    }

    /**
     * 验证客户的渠道包是否被覆盖
     * 
     * @param monUserChannelApp
     *            ：用户渠道包对象
     * @param monAppList
     *            ：被覆盖的渠道包列表
     * @return MonApp
     */
    private MonApp existMonUserChannelApp(MonUserChannelApp monUserChannelApp, List<MonApp> monAppList) {
        for (MonApp monApp : monAppList) {
            if (monApp.getApkId() == monUserChannelApp.getApkId()
                    && monApp.getMarketName().equals(monUserChannelApp.getMarketName())) {
                return monApp;
            }
        }
        return null;
    }

    /**
     * 增量同步渠道包数据
     */
    private void updateSysnMakertAppToMonChannelApp() {
        List<MonChannelApp> monChannelAppList = monMarketAppDao
                .queryChannelApp(EnumMarket.SHOUJIKONG_CHANNEL.getName());
        if (monChannelAppList == null) {
            logger.info("Is not covered by the application");
            return;
        }
        for (MonChannelApp monChannelApp : monChannelAppList) {
            MonUserChannelApp entity = monUserChannelAppDao.getChannelApp(monChannelApp.getMarketName(),
                    monChannelApp.getApkId());
            if (entity == null) {
                // 推广市场如果同步过来新的数据，增量添加的渠道包用户管理中
                // entity = new MonUserChannelApp(monChannelApp.getName(),
                // monChannelApp.getPublisherShortName(),
                // monChannelApp.getMarketName(), monChannelApp.getApkId());
                entity = new MonUserChannelApp(monChannelApp.getName(), monChannelApp.getPublisherShortName(),
                        monChannelApp.getMarketName(), monChannelApp.getVersion(), monChannelApp.getVersionCode(),
                        monChannelApp.getApkId());
                entity.setSMailStatus(2);
                monUserChannelAppDao.save(entity);
            }
        }
    }
}
