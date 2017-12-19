package com.ijinshan.sjk.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.CatalogConvertorDao;
import com.ijinshan.sjk.dao.CatalogDao;
import com.ijinshan.sjk.dao.MarketAppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.service.AttachmentService;
import com.ijinshan.sjk.service.BigGamePackService;
import com.ijinshan.sjk.service.MarketAppService;
import com.ijinshan.sjk.service.MergeAppComparator;
import com.ijinshan.sjk.vo.ArrayParamVO;
import com.ijinshan.util.PathUtils;

@Service
public class MarketAppServiceImpl implements MarketAppService {
    private static final Logger logger = LoggerFactory.getLogger(MarketAppServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "marketAppDaoImpl")
    private MarketAppDao dao;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    @Resource(name = "mergeAppComparatorImpl")
    private MergeAppComparator mergeAppComparator;

    @Resource(name = "attachmentServiceImpl")
    private AttachmentService attachmentService;

    @Resource(name = "bigGamePackServiceImpl")
    private BigGamePackService bigGamePackService;

    @Autowired
    CatalogConvertorDao catalogConvertorDao;

    @Autowired
    CatalogDao catalogDao;

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public boolean searchExists(EnumMarket enumMarket, String pkName) {
        MarketApp marketApp = dao.getPKName(enumMarket, pkName);
        if (marketApp != null) {
            return true;
        }
        return false;
    }

    @Override
    public MarketApp getTop(String pkname) {
        List<MarketApp> list = dao.getByPackagename(pkname);
        if (list != null && !list.isEmpty()) {
            MarketApp marketApp = mergeAppComparator.getTop(list);
            return marketApp;
        } else {
            return null;
        }
    }

    @Override
    public MarketApp getTop(Session sess, String pkname, String signatureSha1) {
        List<MarketApp> list = dao.getByApk(sess, pkname, signatureSha1);
        if (list != null && !list.isEmpty()) {
            MarketApp marketApp = mergeAppComparator.getTop(list);
            return marketApp;
        }
        return null;
    }

    public boolean saveOrUpdate(MarketApp marketApp) {
        marketApp.setLastUpdateTime(new Date());
        if (marketApp.getId() > 0) {
            dao.update(marketApp);
        } else {
            dao.save(marketApp);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        MarketApp app = dao.get(id);
        if (app != null) {
            dao.delete(app);
        }
        return true;
    }

    @Override
    public MarketApp get(int id) {
        return dao.get(id);
    }

    @Override
    public void saveOrUpdateForIjinshan(MultipartHttpServletRequest multipartReq, MarketApp marketApp,
            @RequestParam(required = false) ArrayParamVO paramVo) throws IllegalStateException, IOException,
            URISyntaxException {

        // saveOrUpdate(marketApp);
        Integer marketAppId = null;
        if (marketApp.getId() > 0) {
            dao.update(marketApp);
            marketAppId = marketApp.getId();
        } else {
            marketAppId = (Integer) dao.save(marketApp);
        }

        // 更新、添加大游戏包
        logger.debug("test:{}", paramVo == null);
        if (null != paramVo && null != paramVo.getBigGamePacks() && paramVo.getBigGamePacks().length > 0) {
            List<com.ijinshan.sjk.vo.BigGamePack> bigGamePakcList = Arrays.asList(paramVo.getBigGamePacks());
            if (null != marketAppId) {
                List<BigGamePack> bgame = bigGamePackService.getBigGamePacksByMarketAppid(marketAppId);
                logger.debug("id:{}", marketApp.getId());
                List<Integer> ids = new ArrayList<Integer>();
                if (null != bgame && bgame.size() > 0) {
                    for (BigGamePack biggame : bgame) {
                        ids.add(biggame.getMarketAppId());
                    }
                }
                if (null != ids && ids.size() > 0) {
                    bigGamePackService.deleteByMarketAppIds(ids);
                }
            }
            List<BigGamePack> bpList = changeToVo(bigGamePakcList, marketAppId);
            bigGamePackService.saveBatch(bpList);
        }

        // 更新上上传
        updateIndexImg(multipartReq, marketApp);

        // 更新生成Logo
        updateLogo(multipartReq, marketApp);

        // cover images
        attachmentService.saveOrUpdateImageFile(marketApp, multipartReq);
    }

    public void updateIndexImg(MultipartHttpServletRequest multipartReq, MarketApp marketApp) throws IOException,
            MalformedURLException, URISyntaxException {
        int id = marketApp.getId();

        // 大游戏首页图片.浏览器上传用户本地文件 或 下载远端服务器 图片.
        String indexImgUrlBak = multipartReq.getParameter("oldIndexImgUrl"); // app.getIndexImgUrl();
        boolean needToDeleteIndexImgUrlBak = false;
        String remoteIndexImgUrl = multipartReq.getParameter("remoteIndexImgUrl");
        MultipartFile file = multipartReq.getFile("indexImgFile");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            subPath = attachmentService.saveMarketAppFile(id, file);
        } else if (StringUtils.isNotBlank(remoteIndexImgUrl)) {
            // 拉取图片.
            subPath = attachmentService.saveMarketAppFile(id, remoteIndexImgUrl);
        }
        if (StringUtils.isNotBlank(subPath)) {
            String indexImgUrl = PathUtils.concate(appConfig.getDestBigGameUploadBaseurl(), subPath);
            marketApp.setIndexImgUrl(indexImgUrl);
            saveOrUpdate(marketApp);
            needToDeleteIndexImgUrlBak = true;
        }
        if (appConfig.isDeleteUploadImageFile() && StringUtils.isNotBlank(indexImgUrlBak) && needToDeleteIndexImgUrlBak) {
            attachmentService.deleteFile(indexImgUrlBak);
        }
    }

    public void updateLogo(MultipartHttpServletRequest multipartReq, MarketApp marketApp) throws IOException,
            MalformedURLException, URISyntaxException {
        int id = marketApp.getId();
        // logo. 浏览器上传用户本地文件 或 下载远端服务器 图片.
        String logoUrlBak = multipartReq.getParameter("oldLogoUrl");// app.getLogoUrl();
        boolean needToDeleteLogoUrlBak = false;
        String remoteLogoUrl = multipartReq.getParameter("remoteLogoUrl");
        MultipartFile file = multipartReq.getFile("logoFile");
        String logoSubPath = null;
        if (file != null && !file.isEmpty()) {
            logoSubPath = attachmentService.saveMarketAppFile(id, file);
        } else if (StringUtils.isNotBlank(remoteLogoUrl)) {
            // 拉取图片.
            logoSubPath = attachmentService.saveMarketAppFile(id, remoteLogoUrl);
        }
        if (StringUtils.isNotBlank(logoSubPath)) {
            String logoUrl = PathUtils.concate(appConfig.getDestBigGameUploadBaseurl(), logoSubPath);
            marketApp.setLogoUrl(logoUrl);
            saveOrUpdate(marketApp);
            needToDeleteLogoUrlBak = true;
        }
        if (appConfig.isDeleteUploadImageFile() && StringUtils.isNotBlank(logoUrlBak) && needToDeleteLogoUrlBak) {
            attachmentService.deleteFile(logoUrlBak);
        }
    }

    @Override
    public List<MarketApp> search(EnumMarket enumMarket, Short catalog, Integer subCatalog, int page, int rows,
            String keywords, Integer id, Integer cputype, String sort, String order, Date startDate, Date endDate) {
        return dao.search(enumMarket, catalog, subCatalog, page, rows, keywords, id, cputype, sort, order, startDate,
                endDate);
    }

    @Override
    public long countForSearching(EnumMarket enumMarket, Short catalog, Integer subCatalog, String keywords,
            Integer id, Date startDate, Date endDate) {
        return dao.countForSearching(enumMarket, catalog, subCatalog, keywords, id, startDate, endDate);
    }

    /*
     * 批量把其他市场数据转移到ijinshan市场
     */
    @Override
    public void updateAppShiftIjinshan(List<Integer> appIds) {
        List<App> list = appDao.getApps(appIds);
        MarketApp marketApp = null;
        for (App app : list) {
            if (!app.getMarketName().contains(EnumMarket.SHOUJIKONG.getName())) {
                marketApp = new MarketApp();
                appToMarketApp(app, marketApp);
                Integer marketAppId = (Integer) dao.save(marketApp);
                app.setMarketName(EnumMarket.SHOUJIKONG.getName());
                app.setMarketAppId(marketAppId);
                appDao.update(app);
            }
        }
        list.clear();
    }

    /* 初始化ijinshan市场 */
    @Override
    public void updateVirtualMarket() {
        List<App> list = appDao.queryVirtualApp();
        MarketApp marketApp = null;
        for (App app : list) {
            marketApp = dao.getPKName(EnumMarket.SHOUJIKONG, app.getPkname());
            // 不存在添加
            if (marketApp == null) {
                marketApp = new MarketApp();
                appToMarketApp(app, marketApp);
                Integer marketAppId = (Integer) dao.save(marketApp);
                app.setMarketName(EnumMarket.SHOUJIKONG.getName());
                app.setMarketAppId(marketAppId);
                appDao.update(app);
            }
        }
        list.clear();
    }

    private void appToMarketApp(App app, MarketApp marketApp) {
        marketApp.setAdActionTypes(app.getAdActionTypes());
        marketApp.setAdPopupTypes(app.getAdActionTypes());
        marketApp.setAdvertises(app.getAdvertises());
        marketApp.setApkId(app.getAppId());
        marketApp.setIndexImgUrl(app.getIndexImgUrl());
        marketApp.setShortDesc(app.getShortDesc());
        marketApp.setFreeSize(app.getFreeSize());
        marketApp.setLanguage(app.getLanguage());
        marketApp.setPathStatus(app.getPathStatus());
        marketApp.setAppId(app.getAppId());
        marketApp.setDescription(app.getDescription());
        marketApp.setDetailUrl(app.getDetailUrl());
        // marketApp.setDownloads(app.getDownloads());
        marketApp.setDownloadUrl(app.getDownloadUrl());
        marketApp.setEnumStatus(app.getEnumStatus());
        marketApp.setKeywords(app.getKeywords());
        marketApp.setLastUpdateTime(app.getLastUpdateTime());
        marketApp.setLogoUrl(app.getLogoUrl());
        marketApp.setMarketName(EnumMarket.SHOUJIKONG.getName());
        marketApp.setMarketUpdateTime(new Date());
        marketApp.setMinsdkversion(app.getMinsdkversion());
        marketApp.setModels(app.getModels());
        marketApp.setName(app.getName());
        marketApp.setOsversion(app.getOsversion());
        marketApp.setPermissions(app.getPermissions());
        marketApp.setPkname(app.getPkname());
        marketApp.setPrice(app.getPrice());
        marketApp.setPublisherShortName(app.getPublisherShortName());
        marketApp.setScreens(app.getScreens());
        marketApp.setSignatureSha1(app.getSignatureSha1());
        marketApp.setSize(app.getSize());
        marketApp.setAdRisk((byte) 0);
        marketApp.setStarRating(app.getStarRating());
        marketApp.setStrImageUrls(app.getStrImageUrls());

        Catalog catalogEntity = catalogDao.get(app.getSubCatalog());
        String subCatalogName = catalogEntity == null ? "" : catalogEntity.getName();
        marketApp.setSubCatalogName(subCatalogName);
        marketApp.setCatalog(app.getCatalog());
        marketApp.setSubCatalog(app.getSubCatalog());

        marketApp.setSupportpad(app.getSupportpad());
        marketApp.setUpdateInfo(app.getUpdateInfo());
        marketApp.setVersion(app.getVersion());
        marketApp.setVersionCode(app.getVersionCode());
        marketApp.setViewCount(app.getViewCount());
        marketApp.setVirusBehaviors(app.getVirusBehaviors());
        marketApp.setVirusKind(app.getVirusKind());
        marketApp.setVirusName(app.getVirusName());
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        int count = dao.deleteByIds(ids);
        if (count == ids.size()) {
            for (Integer id : ids) {
                appDao.deleteByMarketApp(id);
            }
        }
        return count;
    }

    @Override
    public boolean editCatalog(List<Integer> ids, short catalog, int subCatalog, String subCatalogName) {
        Assert.notEmpty(ids);
        int rows = dao.editCatalog(ids, catalog, subCatalog, subCatalogName);
        return rows == ids.size();
    }

    @Override
    public boolean editCatalog(int id, short catalog, int subCatalog, String subCatalogName) {
        List<Integer> ids = new ArrayList<Integer>(1);
        ids.add(id);
        int rows = dao.editCatalog(ids, catalog, subCatalog, subCatalogName);
        return rows == 1;
    }

    // 将po转化为vo

    public List<BigGamePack> changeToVo(List<com.ijinshan.sjk.vo.BigGamePack> voList, int marketAppId) {
        List<BigGamePack> list = new ArrayList<BigGamePack>();
        if (null != voList && voList.size() > 0) {
            for (com.ijinshan.sjk.vo.BigGamePack bigGamePack : voList) {
                // 在这里的时候，如果大小、解压大小都为空，那么认为信息不完整，不予保存
                if (bigGamePack.getCputype() > 0) {
                    BigGamePack bp = new BigGamePack();
                    bp.setMarketAppId(marketAppId);
                    bp.setMarketUpdateTime(new Date());
                    bp.setCputype(bigGamePack.getCputype().byteValue());
                    if (null != bigGamePack.getFreeSize()) {
                        bp.setFreeSize(bigGamePack.getFreeSize());
                    }
                    if (null != bigGamePack.getSize()) {
                        bp.setSize(bigGamePack.getSize());
                    }
                    bp.setUnsupportPhoneType(bigGamePack.getUnsupportPhoneType());
                    bp.setUrl(bigGamePack.getUrl());
                    if (null != bigGamePack.getBigGamePackId()) {
                        bp.setBigGamePackId(bigGamePack.getBigGamePackId());
                    }
                    list.add(bp);
                }

            }
        }
        // voList.clear();
        return list;
    }

    @Override
    public boolean updateKeyWords(int id, String keyWorks) {
        MarketApp marketApp = dao.get(id);
        if (marketApp != null) {
            marketApp.setKeywords(keyWorks);
            dao.update(marketApp);
        }
        return true;
    }
    // // marketApp状态更新
    // private void marketAppStatusUpdate(MarketApp app) {
    // if (app.getId() > 0) {
    // app.setStatus(AppStatus.getManualAppStatus(app.getStatus(),
    // AppStatus.MANUAL_UPDATE));
    // } else {
    // app.setStatus(AppStatus.getManualAppStatus(app.getStatus(),
    // AppStatus.MANUAL_ADD));
    // }
    // }
}
