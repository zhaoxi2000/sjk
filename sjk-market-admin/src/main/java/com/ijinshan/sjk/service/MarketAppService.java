package com.ijinshan.sjk.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.vo.ArrayParamVO;

public interface MarketAppService {
    long count();

    /**
     * 取出优先级最高的应用
     * 
     * @param pkname
     * @return
     */
    MarketApp getTop(String pkname);

    /**
     * 取出优先级最高的应用
     * 
     * @param sess
     * @param pkname
     * @param signatureSha1
     * @return
     */
    MarketApp getTop(Session sess, String pkname, String signatureSha1);

    /** ijinshan 市场 方法 */
    void saveOrUpdateForIjinshan(MultipartHttpServletRequest multipartReq, MarketApp marketApp, ArrayParamVO paramVo)
            throws IllegalStateException, IOException, URISyntaxException;

    boolean delete(int id);

    MarketApp get(int id);

    List<MarketApp> search(EnumMarket market, Short catalog, Integer subCatalog, int page, int rows, String keywords,
            Integer id, Integer cputype, String sort, String order, Date startDate, Date endDate);

    long countForSearching(EnumMarket market, Short catalog, Integer subCatalog, String keywords, Integer id,
            Date startDate, Date endDate);

    /** 初始化ijinshan市场 */
    void updateVirtualMarket();

    /** 批量把其他市场数据转移到ijinshan市场 */
    void updateAppShiftIjinshan(List<Integer> appIds);

    /** 批量删除ijinshan市场数据 */
    int deleteByIds(List<Integer> ids);

    boolean searchExists(EnumMarket enumMarket, String pkName);

    boolean editCatalog(List<Integer> ids, short catalog, int subCatalog, String subCatalogName);

    boolean editCatalog(int id, short catalog, int subCatalog, String subCatalogName);

    boolean updateKeyWords(int id, String keyWorks);
}
