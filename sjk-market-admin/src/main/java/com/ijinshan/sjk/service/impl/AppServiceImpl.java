package com.ijinshan.sjk.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.AppStatus;
import com.ijinshan.sjk.dao.AppAndTagDao;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAdmin;
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.AttachmentService;
import com.ijinshan.util.PathUtils;

@Service
public class AppServiceImpl implements AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    @Resource(name = "appAndTagDaoImpl")
    private AppAndTagDao appTagDao;

    @Resource(name = "attachmentServiceImpl")
    private AttachmentService attachmentService;

    @Resource(name = "appHistory4IndexDaoImpl")
    private AppHistory4IndexDao appHistory4IndexDao;

    /**
     * 批量修改分类
     */
    @Override
    public boolean updateBatchCatalog(List<Integer> ids, short catalog, int subCatalog) {
        Assert.notEmpty(ids);
        int rows = appDao.editCatalog(ids, catalog, subCatalog);

        if (rows == ids.size()) {
            for (Integer id : ids) {
                appHistory4IndexDao.saveOrUpdate(id);// 为索引 准备
            }
        }
        return rows == ids.size();
    }

    @Override
    public boolean updateCatalog(int id, short catalog, int subCatalog) {
        List<Integer> ids = new ArrayList<Integer>(1);
        int rows = appDao.editCatalog(ids, catalog, subCatalog);

        if (rows == 1) {
            appHistory4IndexDao.saveOrUpdate(id);
        }
        return rows == 1;
    }

    @Override
    public List<App> list(short catalog, Integer subCatalog, int currentPage, int pageSize) {
        List<App> list = appDao.listForBase(catalog, subCatalog, currentPage, pageSize);
        return list;
    }

    @Override
    public boolean delete(List<Integer> ids) {
        int count = appDao.deleteByIds(ids);
        if (count == ids.size()) {
            appHistory4IndexDao.updateAppStatus2Del(ids);// 为索引 设置
            return true;
        }
        return false;
    }

    //
    @Override
    public boolean updateHide(List<Integer> ids) {
        List<App> list = appDao.getApps(ids);
        for (App app : list) {
            // App状态设置
            app.setStatus(getAppStatus(app.getStatus(), AppStatus.MANUAL_HIDDEN, AppStatus.MANUAL_HIDDEN_MASK));
            saveOrUpdate(app);
        }

        int count = appDao.updateHide(ids);

        if (count == ids.size()) {
            appHistory4IndexDao.updateAppStatus2Del(ids);// 为索引 设置
            return true;

        }
        return false;
    }

    @Override
    public boolean updateShow(List<Integer> ids, StringBuilder outputMessage) {
        StringBuilder sbMessage = new StringBuilder();
        List<App> list = appDao.getApps(ids);
        for (App app : list) {
            // App状态设置
            // if( 404 || 更新程序下架 ) { 不可恢复}
            int status = app.getStatus();
            if ((status & AppStatus.DETECT_MASK.getVal()) != AppStatus.DETECT_OFF.getVal()
                    && (status & AppStatus.MARKET_DATA_MASK.getVal()) != AppStatus.DELETE.getVal()) {
                app.setStatus(getAppStatus(app.getStatus(), AppStatus.MANUAL_SHOW, AppStatus.MANUAL_HIDDEN_MASK));
                app.setHidden(false);
                saveOrUpdate(app);
            } else {
                sbMessage.append("ID：").append(app.getId()).append("Name：").append(app.getName()).append("\n");
            }
        }
        if (sbMessage.length() > 0) {
            outputMessage.append("由于检查404或应用下架原因，以下应用无法操作显示：\n").append(sbMessage.toString());
        } else {
            for (Integer id : ids) {
                appHistory4IndexDao.saveOrUpdate(id);// 更新状态
            }
            outputMessage.append("操作显示成功！");
        }
        return true;
    }

    @Override
    public long count(short catalog, Integer subCatalog) {
        return appDao.count(catalog, subCatalog);
    }

    @Override
    public List<AppAdmin> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id,
            String sort, String order, Date startDate, Date endDate, Boolean official, Integer audit) {
        return appDao.search(catalog, subCatalog, page, rows, keywords, id, sort, order, startDate, endDate, official,
                audit);
    }

    @Override
    public long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id, Date startDate,
            Date endDate, Boolean official, Integer audit) {
        return appDao.countForSearching(catalog, subCatalog, keywords, id, startDate, endDate, official, audit);
    }

    @Override
    public List<App> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id,
            String sort, String order) {
        return appDao.search(catalog, subCatalog, page, rows, keywords, id, sort, order);
    }

    @Override
    public List<Rollinfo> searchForRolling(Short catalog, Integer subCatalog, int page, int rows, String keywords,
            String sort, String order) {
        return appDao.searchForRolling(catalog, subCatalog, page, rows, keywords, sort, order);
    }

    @Override
    public long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id) {
        return appDao.countForSearching(catalog, subCatalog, keywords, id);
    }

    @Override
    public App get(int id) {
        return appDao.get(id);
    }

    @Override
    public void saveOrUpdate(App app) {
        app.setAudit(true);
        app.setLastUpdateTime(new Date());
        app.setDownloadRank(app.getRealDownload() + app.getDeltaDownload());
        appDao.saveOrUpdate(app);
        appHistory4IndexDao.saveOrUpdate(app.getId());

    }

    @Override
    public void saveOrUpdateForBiggame(App app, MultipartHttpServletRequest multipartReq) throws IllegalStateException,
            IOException, URISyntaxException {
        appStatusUpdate(app);
        appDao.saveOrUpdate(app);
        updateIndexImg(multipartReq, app);// 首页图
        updateLogo(multipartReq, app);// icon图
        // cover images 截图
        attachmentService.saveOrUpdateImageFile(app, multipartReq);
    }

    public void updateIndexImg(MultipartHttpServletRequest multipartReq, App app) throws IOException,
            MalformedURLException, URISyntaxException {
        int id = app.getId();

        // 大游戏首页图片.浏览器上传用户本地文件 或 下载远端服务器 图片.
        String indexImgUrlBak = multipartReq.getParameter("oldIndexImgUrl"); // app.getIndexImgUrl();
        boolean needToDeleteIndexImgUrlBak = false;
        String remoteIndexImgUrl = multipartReq.getParameter("remoteIndexImgUrl");
        MultipartFile file = multipartReq.getFile("indexImgFile");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            subPath = attachmentService.saveFile(id, file);
        } else if (StringUtils.isNotBlank(remoteIndexImgUrl)) {
            // 拉取图片.
            subPath = attachmentService.saveFile(id, remoteIndexImgUrl);
        }
        if (StringUtils.isNotBlank(subPath)) {
            String indexImgUrl = PathUtils.concate(appConfig.getDestUploadBaseurl(), subPath);
            app.setIndexImgUrl(indexImgUrl);
            saveOrUpdate(app);
            needToDeleteIndexImgUrlBak = true;
        }
        if (StringUtils.isNotBlank(indexImgUrlBak) && needToDeleteIndexImgUrlBak) {
            attachmentService.deleteFile(indexImgUrlBak);
        }
    }

    @Override
    public void updateLogo(MultipartHttpServletRequest multipartReq, int id) throws IllegalStateException, IOException,
            URISyntaxException {
        App app = appDao.get(id);
        app.setLastUpdateTime(new Date());
        updateLogo(multipartReq, app);
        appHistory4IndexDao.saveOrUpdate(id);

    }

    public void updateLogo(MultipartHttpServletRequest multipartReq, App app) throws IOException,
            MalformedURLException, URISyntaxException {
        int id = app.getId();
        // logo. 浏览器上传用户本地文件 或 下载远端服务器 图片.
        String logoUrlBak = multipartReq.getParameter("oldLogoUrl");// app.getLogoUrl();
        boolean needToDeleteLogoUrlBak = false;
        String remoteLogoUrl = multipartReq.getParameter("remoteLogoUrl");
        MultipartFile file = multipartReq.getFile("logoFile");
        String logoSubPath = null;
        if (file != null && !file.isEmpty()) {
            logoSubPath = attachmentService.saveFile(id, file);
        } else if (StringUtils.isNotBlank(remoteLogoUrl)) {
            // 拉取图片.
            logoSubPath = attachmentService.saveFile(id, remoteLogoUrl);
        }
        if (StringUtils.isNotBlank(logoSubPath)) {
            String logoUrl = PathUtils.concate(appConfig.getDestUploadBaseurl(), logoSubPath);
            app.setLogoUrl(logoUrl);
            saveOrUpdate(app);
            needToDeleteLogoUrlBak = true;
        }
        if (StringUtils.isNotBlank(logoUrlBak) && needToDeleteLogoUrlBak) {
            attachmentService.deleteFile(logoUrlBak);
        }

        appHistory4IndexDao.saveOrUpdate(app.getId());
    }

    @Override
    public void saveOrUpdate(MultipartHttpServletRequest multipartReq, App app) throws IllegalStateException,
            IOException, URISyntaxException {
        app.setAudit(true);
        saveOrUpdateForBiggame(app, multipartReq);
    }

    // 获取表单 标签数据
    private List<AppAndTag> getAppTags(MultipartHttpServletRequest multipartReq) {
        List<AppAndTag> list = new ArrayList<AppAndTag>();
        String[] ids = multipartReq.getParameterValues("appTagId");
        String[] appIds = multipartReq.getParameterValues("appId");
        String[] tagNames = multipartReq.getParameterValues("tagName");
        String[] tagIds = multipartReq.getParameterValues("tagId");
        AppAndTag tag = null;
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                tag = new AppAndTag();
                tag.setId(Integer.valueOf(StringUtils.defaultIfEmpty(ids[i], "0")));
                tag.setAppId(Integer.valueOf(StringUtils.defaultIfEmpty(appIds[i], "0")));
                tag.setTagId(Integer.valueOf(StringUtils.defaultIfEmpty(tagIds[i], "0")));
                tag.setTagName(StringUtils.defaultIfEmpty(tagNames[i], ""));
                list.add(tag);
            }
        }
        return list;
    }

    /**
     * 修改、保存 应用标签
     */
    private void saveOrUpdateAppTag(List<AppAndTag> list) {
        for (AppAndTag tag : list) {
            // 删除 标签名为空，应用Id为空的 标签。
            if (tag.getId() > 0 && (StringUtils.isEmpty(tag.getTagName()) && tag.getAppId() < 1)) {
                appTagDao.delete(tag);
            } else {
                // 保存或添加标签
                appTagDao.saveOrUpdate(tag);
            }
        }
    }

    @Override
    public void updateDownload(List<Integer> ids, Integer realDownload, int deltaDownload) {
        appDao.updateDownload(ids, realDownload, deltaDownload);

        for (Integer id : ids) {
            appHistory4IndexDao.saveOrUpdate(id);
        }
    }

    @Override
    public long countForSearchingRolling(Short catalog, Integer subCatalog, String keywords) {

        return appDao.countForSearchingRolling(catalog, subCatalog, keywords);
    }

    // App状态更新
    private void appStatusUpdate(App app) {
        if (app.getId() > 0) {
            // 如果应用是隐藏状态 Status为MANUAL_HIDDEN((int) 0x0300, "", "手动隐藏")
            // 则不修改操作状态。
            app.setStatus(getAppStatus(app.getStatus(), AppStatus.MANUAL_UPDATE, AppStatus.MANUAL_MASK));
        } else {
            app.setStatus(getAppStatus(app.getStatus(), AppStatus.MANUAL_ADD, AppStatus.MANUAL_MASK));
        }
    }

    private int getAppStatus(int oldStatus, AppStatus newStatus, AppStatus maskStatus) {
        int status = (oldStatus & ~maskStatus.getVal()) | newStatus.getVal();
        return status;
    }

    @Override
    public boolean updateAudit(List<Integer> ids) {
        List<App> list = appDao.getApps(ids);
        if (list != null) {
            for (App app : list) {
                // 审核
                app.setStatus(getAppStatus(app.getStatus(), AppStatus.AUDIT_YES, AppStatus.AUDIT_MASK));
                appDao.saveOrUpdate(app);
            }
            return list.size() == ids.size();
        } else {
            return false;
        }
    }

    @Override
    public boolean updateNoNeedAudit(List<Integer> ids) {
        List<App> list = appDao.getApps(ids);
        if (list != null) {
            for (App app : list) {
                // 免审核
                app.setStatus(getAppStatus(app.getStatus(), AppStatus.AUDIT_NO_NEED, AppStatus.AUDIT_MASK));
                appDao.saveOrUpdate(app);
            }
            return list.size() == ids.size();
        } else {
            return false;
        }
    }

    @Override
    public boolean updateNoAudit(List<Integer> ids) {
        List<App> list = appDao.getApps(ids);
        if (list != null) {
            for (App app : list) {
                // 取消审核
                app.setStatus(getAppStatus(app.getStatus(), AppStatus.AUDIT_NO, AppStatus.AUDIT_MASK));
                appDao.saveOrUpdate(app);
            }
            return list.size() == ids.size();
        } else {
            return false;
        }
    }
}