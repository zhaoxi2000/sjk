package com.kingsoft.sjk.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.config.ChangeOutputImpl;
import com.kingsoft.sjk.dao.AppDao;
import com.kingsoft.sjk.dao.AppDictDao;
import com.kingsoft.sjk.dao.ExtendDataDao;
import com.kingsoft.sjk.dao.ScreenImageDao;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.AppDict;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.ScreenImage;
import com.kingsoft.sjk.service.AppService;
import com.kingsoft.sjk.util.GenerateHelper;
import com.kingsoft.sjk.util.Template;
import com.kingsoft.sjk.util.VeTemplate;

@Service
public class AppServiceImpl implements AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
    @Autowired
    private AppDao dao;

    // @Autowired
    // private Template template;

    @Autowired
    private AppDictDao daoDict;

    @Autowired
    private ExtendDataDao daoExtendData;

    @Autowired
    private ScreenImageDao daoScreenImage;

    @Resource(name = "changeOutputImpl")
    private ChangeOutputImpl changeOutputImpl;

    @Resource(name = "appConfig")
    private AppConfig config;

    private List<AppDict> appDicts;

    private List<ScreenImage> screenImageList;

    private List<ExtendData> extendDataList;

    @Override
    public List<App> FindAll() {

        String curDateString = DateFormatUtils.format(DateUtils.addDays(new Date(), config.getGenerateDate()),
                "yyyy-MM-dd HH:mm:ss");
        Date curDate = DateUtils.addDays(new Date(), config.getGenerateDate());
        appDicts = daoDict.findAll();
        screenImageList = daoScreenImage.findAll();
        extendDataList = daoExtendData.findAll();
        org.apache.velocity.Template templateVelocity = new VeTemplate(config.getAppGenerateTemplateBaseDir(),
                "app.html").getTemplate();
        Template template = new Template(config.getAppGenerateTemplateBaseDir(), "detail.html");
        List<App> list;
        logger.debug("run time :{}", curDateString);
        if (config.getGenerateDate() == 0) {
            list = dao.findAll();
        } else {
            list = dao.findAll(curDate);
        }
        for (App app : list) {
            setApp(app);
            // genetatePage(app);
            // genetatePage(app, template);
            genetatePageVelocity(app, templateVelocity);
        }
        return list;
    }

    private void genetatePage(App app, Template template) {
        File file = new File(config.getAppDetailGenerateBasePath() + "/" + (app.getId() / 500) + "/" + app.getId()
                + ".html");
        if (!file.exists()
                || FileUtils.isFileOlder(file, app.geteUpdateDate() == null ? new Date() : app.geteUpdateDate())) {
            GenerateHelper.generateAppDetail(file, app, template, config);
            logger.debug(String.format("new genetate app ID:%d,  Name:%s, path:%s ", app.getId(), app.getName(),
                    file.getPath()));
        }
    }

    @SuppressWarnings("unused")
    private void genetatePageVelocity(App app, org.apache.velocity.Template template) {
        File file = new File(config.getAppDetailGenerateBasePath() + "/" + (app.getId() / 500) + "/" + app.getId()
                + ".html");
        if (!file.exists()
                || FileUtils.isFileOlder(file, app.geteUpdateDate() == null ? new Date() : app.geteUpdateDate())) {
            GenerateHelper.generateAppDetailByTemplate(file, app, template);
            logger.debug(String.format("new genetate app ID:%d,  Name:%s, path:%s ", app.getId(), app.getName(),
                    file.getPath()));
        }
    }

    private void setApp(App app) {
        getExtendDataBySoftId(app);
        getScreenImagesBySoftId(app);
        changeOutputImpl.setUrls(app);
        app.setLanguage(getDictVersoNames(13, app.getLanguage()));
        app.setOSVersion(getDictNames(4, app.getOSVersion()));
        app.setOSVersionGroupName(getDictName(91, app.getOSVersionGroupID()));
    }

    @Override
    public App AppDetail(int softid) {
        logger.debug("TODO Auto-generated method stub");
        return null;
    }

    // private void setScreenImages(App app) {
    // List<ScreenImage> screenImages =
    // daoScreenImage.getAppImages(app.getId());
    // for (ScreenImage screenImage : screenImages) {
    // changeOutputImpl.setAppScreenUrl(screenImage);
    // }
    // app.setScreenImages(screenImages);
    // }
    //
    // private void setExtendDatas(App app) {
    // List<ExtendData> extendDataList =
    // daoExtendData.getAppExtendData(app.getId());
    // for (ExtendData ext : extendDataList) {
    // changeOutputImpl.setAppExtendDataUrl(ext);
    // }
    // app.setExtendDataList(extendDataList);
    // }

    /* 公共方法：获取游戏数据包 */
    private void getExtendDataBySoftId(App app) {
        if (app.getHaveData() == 1) {
            List<ExtendData> list = new ArrayList<ExtendData>();
            for (ExtendData data : extendDataList) {
                if (data.getSoftIDs().contains("," + app.getId() + ",")) {
                    changeOutputImpl.setAppExtendDataUrl(data);
                    list.add(data);
                }
            }
            app.setExtendDataList(list);
        }
    }

    /* 公共方法：获取应用截图图片 */
    private void getScreenImagesBySoftId(App app) {
        List<ScreenImage> list = new ArrayList<ScreenImage>();
        for (ScreenImage screenImage : screenImageList) {
            if (screenImage.getSoftID() == app.getId()) {
                changeOutputImpl.setAppScreenUrl(screenImage);
                list.add(screenImage);
            }
        }
        app.setScreenImages(list);
    }

    /* 公共方法：获取知道值 */
    private String getDictName(int typeId, int dicId) {
        String result = "";
        for (AppDict dict : appDicts) {
            if (dict.getTypeID() == typeId && dict.getId() == dicId) {
                result = dict.getName();
            }
        }
        return result;
    }

    /* 公共方法：获取知道值 */
    private String getDictNames(int typeId, String dicIds) {
        String result = "";
        for (AppDict dict : appDicts) {
            if (dict.getTypeID() == typeId) {
                String temp = "," + dict.getId() + ",";
                if (dicIds.contains(temp)) {
                    result = result + dict.getName() + ",";
                }
            }
        }
        return result;
    }

    /* 公共方法：获取知道值 */
    private String getDictVersoNames(int typeId, String dicIds) {
        String result = "";
        for (AppDict dict : appDicts) {
            if (dict.getTypeID() == typeId) {
                String temp = "," + dict.getId() + ",";
                if (!dicIds.contains(temp)) {
                    result = result + dict.getName() + ",";
                }
            }
        }
        return result;
    }
}
