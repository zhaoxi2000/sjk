package com.kingsoft.sjk.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.Permissions;
import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.config.ChangeOutputImpl;
import com.kingsoft.sjk.config.TagType;
import com.kingsoft.sjk.dao.AppDao;
import com.kingsoft.sjk.dao.BigGamePackDao;
import com.kingsoft.sjk.po.AppTags;
import com.kingsoft.sjk.po.Catalog;
import com.kingsoft.sjk.service.AppService;
import com.kingsoft.sjk.util.GenerateHelper;
import com.kingsoft.sjk.util.VeTemplate;

@Service
public class AppServiceImpl implements AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
    @Autowired
    private AppDao dao;

    @Autowired
    private BigGamePackDao bigGamePackDao;

    @Resource(name = "changeOutputImpl")
    private ChangeOutputImpl changeOutputImpl;

    @Resource(name = "appConfig")
    private AppConfig config;

    @Override
    public void findData(boolean isAll) {
        // 获得所有分类
        Map<Integer, Catalog> catalogs = dao.initCatalog();
        // Map<Integer, Permissions> permissionsMap = dao.initPermissions();
        // 获得所有应用标签
        Map<Integer, List<AppTags>> AppTagsNormalTags = dao.initAppTags(TagType.NormalTag);
        // 获得所有专题
        Map<Integer, List<AppTags>> AppTagsTopics = dao.initAppTags(TagType.TOPIC);
        String curDateString = DateFormatUtils.format(DateUtils.addDays(new Date(), config.getGenerateDate()),
                "yyyy-MM-dd HH:mm:ss");

        Date curDate = DateUtils.addDays(new Date(), config.getGenerateDate());

        org.apache.velocity.Template templateVelocity = new VeTemplate(config.getAppGenerateTemplateBaseDir(),
                "app.html").getTemplate();

        List<App> list;

        if (config.isDebug() && !isAll) {
            logger.debug("It is test env.");
            logger.debug("begin process {} data.", curDateString);
            list = dao.findData(curDate);

            int i = 0;
            for (App app : list) {
                if (i > 1000)
                    break;
                genetatePageVelocity(app, templateVelocity, catalogs.get(app.getSubCatalog()), null,
                        AppTagsNormalTags.get(app.getId()), AppTagsTopics.get(app.getId()), isAll);
                i++;
            }
            logger.info("generate size: {}", i);
            list.clear();
        } else {
            long modfiyTime = templateVelocity.getLastModified();
            long currDayTime = System.currentTimeMillis();

            if ((currDayTime - modfiyTime) / (1000 * 60) <= 10 || isAll) {
                logger.debug("generate all data.");
                // 分页获取，分页生成
                int total = dao.count();
                int rows = 10000;
                int toatalPage = total % rows == 0 ? total / rows : total / rows + 1;
                curDate = null;
                // logger.info("all data,get data perPageSize,total:{},pages:{},paeSize:{},curentPgee:{}",
                // new int[] { total, rows,
                // toatalPage });
                for (int i = 0; i < toatalPage; i++) {
                    list = dao.queryPager(total, i + 1, rows, null);// 全量生成，无需要传入日期

                    logger.info("all data,total:{},paeSize:{},toatalPages:{},curentPgee:{}", new Integer[] { total,
                            rows, toatalPage, i + 1 });
                    // logger.info("curent pgee:{}", i);
                    genetatePageVelocity(catalogs, AppTagsNormalTags, AppTagsTopics, templateVelocity, list, isAll);
                }

                logger.info("generate size: {}", total);

            } else {
                logger.debug("part data. {} ", curDate);
                list = dao.findData(curDate);
                if (list != null) {
                    logger.info("generate size: {}", list.size());
                }
                isAll = false;
                genetatePageVelocity(catalogs, AppTagsNormalTags, AppTagsTopics, templateVelocity, list, isAll);
                list.clear();
            }

            // logger.debug("now : {}", new Date());
        }

    }

    private void genetatePageVelocity(Map<Integer, Catalog> catalogs, Map<Integer, List<AppTags>> AppTagsNormalTags,
            Map<Integer, List<AppTags>> AppTagsTopics, org.apache.velocity.Template templateVelocity, List<App> list,
            boolean isAll) {
        for (App app : list) {
            genetatePageVelocity(app, templateVelocity, catalogs.get(app.getSubCatalog()), null,
                    AppTagsNormalTags.get(app.getId()), AppTagsTopics.get(app.getId()), isAll);
        }
    }

    private void genetatePageVelocity(App app, org.apache.velocity.Template template, Catalog catalog,
            Map<Integer, Permissions> permissionsMap, List<AppTags> appTags, List<AppTags> AppTopic, boolean isAll) {

        // 过滤掉一些东西
        // 更新字段 过滤
        String updateInfo = app.getUpdateInfo();
        if (StringUtils.isNotEmpty(updateInfo)) {
            updateInfo = updateInfo.replaceAll("\r\n", "<br />").replaceAll("\n", "<br />");
            updateInfo = updateInfo.replaceAll("\\\\n", "");
            app.setUpdateInfo(updateInfo);
        }

        String description = app.getDescription();
        if (StringUtils.isNotEmpty(description)) {
            description = description.replaceAll("\r\n", "<br />").replaceAll("\n", "<br />");
            description = description.replaceAll("\\\\n", "");

            app.setDescription(description);
        }

        StringBuilder detailPath = new StringBuilder();
        detailPath.append("/").append(app.getId() / 500).append("/").append(app.getId()).append(".html");

        File file = new File(config.getAppDetailGenerateBasePath() + detailPath.toString());

        // if (StringUtils.isEmpty(app.getPageUrl())) {
        // 设置url
        app.setPageUrl(config.getAppPageBaseUrl() + detailPath.toString());

        // }

        // 根据分类查找对应的大游戏集合
        List<BigGamePack> bigGameList = bigGamePackDao.getBigGameByMarkAppId(app.getMarketAppId());

        // 如果 是取倒是，则不用判断 原文件是否存在 ，一律全部重新生成
        if (isAll) {
            GenerateHelper.generateAppDetailByTemplate(file, app, template, catalog, permissionsMap, appTags, AppTopic,
                    bigGameList);

            dao.updatePageUrl(app);// 需要时才更新 db
            return;
        }

        // 如果模板的最后 修改时间 比 应用 新，则需要全部重新生成 ，而不用应用 判断 文件 是否存在
        if (!file.exists() || FileUtils.isFileOlder(file, template.getLastModified())
                || FileUtils.isFileOlder(file, app.getLastFetchTime() == null ? new Date() : app.getLastFetchTime())
                || FileUtils.isFileOlder(file, app.getLastUpdateTime() == null ? new Date() : app.getLastUpdateTime())) {

            GenerateHelper.generateAppDetailByTemplate(file, app, template, catalog, permissionsMap, appTags, AppTopic,
                    bigGameList);

            dao.updatePageUrl(app);// 需要时才更新 db
        }
    }

    @Override
    public void processAppById(int softid) {
        App app = dao.findById(softid);

        // 获得所有分类
        Map<Integer, Catalog> catalogs = dao.initCatalog();

        Map<Integer, List<AppTags>> AppTagsNormalTags = dao.initAppTags(TagType.NormalTag);

        Map<Integer, List<AppTags>> AppTagsTopics = dao.initAppTags(TagType.TOPIC);

        logger.debug("begin process app {} data.", softid);

        org.apache.velocity.Template templateVelocity = new VeTemplate(config.getAppGenerateTemplateBaseDir(),
                "app.html").getTemplate();

        genetatePageVelocity(app, templateVelocity, catalogs.get(app.getSubCatalog()), null,
                AppTagsNormalTags.get(app.getId()), AppTagsTopics.get(app.getId()), true);

    }

}
