package com.ijinshan.sjk.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAdmin;
import com.ijinshan.sjk.po.Rollinfo;

public interface AppService {

    boolean updateBatchCatalog(List<Integer> ids, short catalog, int subCatalog);

    boolean updateCatalog(int id, short catalog, int subCatalog);

    List<App> list(short catalog, Integer subCatalog, int currentPage, int pageSize);

    boolean delete(List<Integer> ids);

    boolean updateHide(List<Integer> ids);

    /**
     * 审核
     */
    boolean updateAudit(List<Integer> ids);

    /**
     * 免审核
     */
    boolean updateNoNeedAudit(List<Integer> ids);

    /**
     * 取消审核
     */
    boolean updateNoAudit(List<Integer> ids);

    boolean updateShow(List<Integer> ids, StringBuilder outputMessage);

    long count(short catalog, Integer subCatalog);

    List<App> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id, String sort,
            String order);

    long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id);

    List<AppAdmin> search(Short catalog, Integer subCatalog, int page, int rows, String keywords, Integer id,
            String sort, String order, Date startDate, Date endDate, Boolean official, Integer audit);

    long countForSearching(Short catalog, Integer subCatalog, String keywords, Integer id, Date startDate,
            Date endDate, Boolean official, Integer audit);

    App get(int id);

    void saveOrUpdate(App app);

    void saveOrUpdateForBiggame(App app, MultipartHttpServletRequest multipartReq) throws IllegalStateException,
            IOException, URISyntaxException;

    void saveOrUpdate(MultipartHttpServletRequest multipartReq, App app) throws IllegalStateException, IOException,
            URISyntaxException;

    List<Rollinfo> searchForRolling(Short catalog, Integer subCatalog, int page, int rows, String keywords,
            String sort, String order);

    void updateLogo(MultipartHttpServletRequest multipartReq, int id) throws IllegalStateException, IOException,
            URISyntaxException;

    void updateDownload(List<Integer> ids, Integer realDownload, int deltaDownload);

    long countForSearchingRolling(Short catalog, Integer subCatalog, String keywords);

}
