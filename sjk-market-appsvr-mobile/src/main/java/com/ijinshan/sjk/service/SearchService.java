package com.ijinshan.sjk.service;

import java.io.IOException;

import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.util.Pager;

/**
 * @author Linzuxiong
 */
public interface SearchService {
    String[] quickTips(String keywords) throws IOException;

    Pager<MobileSearchApp> search(String q, Keyword keywordModel, Integer page, Integer rows, Integer noAds,
            Integer official) throws Exception;

    long searchCount(String q, Keyword keywordModel, Short otherCatalog, Boolean noAds, Boolean official)
            throws Exception;

    /**
     * 第一次初始化.
     */
    void init();

    /**
     * 
     */
    void reset();

    /**
     * 重置类的variables
     * 
     * @throws IOException
     */
    void resetClassFields() throws IOException;

    Keyword getTargetKeyword(String q);

}
