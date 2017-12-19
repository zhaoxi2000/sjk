package com.ijinshan.sjk.ntxservice;

import org.apache.lucene.search.TopDocs;

import com.ijinshan.sjk.config.SearchRankType;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.util.Pager;

public interface SearchService1 {

    /**
     * 搜索总入口方法.缩小结果集,默认按下载量排序
     * 
     * @param q
     * @param page
     * @param rows
     * @param noAds
     * @param official
     * @return
     * @throws Exception
     */
    Pager<MobileSearchApp> search(String q, int page, int rows, Integer noAds, Integer official) throws Exception;

    /**
     * 取出分页的数据
     * 
     * @param topDocs
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    Pager<MobileSearchApp> searchInTotalhitsByQuery(TopDocs topDocs, int page, int rows) throws Exception;

    /**
     * 获取在lucene中的搜索总结果集.并已排序.
     * 
     * @param q
     * @param bNoAds
     * @param bOfficial
     * @return
     * @throws Exception
     */
    TopDocs getSearchTotalhits(String q, Boolean bNoAds, Boolean bOfficial) throws Exception;

    TopDocs getSearchTotalhitsByMultiplyPhrase(String q, String[] qs, Boolean bNoAds, Boolean bOfficial)
            throws Exception;

    TopDocs getSearchTotalhitsBySinglePhrase(String targetQ, SearchRankType searchRankType, Boolean bNoAds,
            Boolean bOfficial) throws Exception;

}
