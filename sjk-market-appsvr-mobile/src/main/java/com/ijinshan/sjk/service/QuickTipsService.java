package com.ijinshan.sjk.service;

import java.io.IOException;

import org.apache.lucene.search.ScoreDoc;

/**
 * 搜索建议的交互业务.
 * 
 * @author Linzuxiong
 */
public interface QuickTipsService {

    /**
     * 内部前缀匹配名称
     * 
     * @param q
     * @return
     * @throws IOException
     */
    ScoreDoc[] prefixSearch(String q) throws IOException;

    /**
     * 前缀匹配名称提示.
     * 
     * @param q
     * @return 去重, 名称按下载量排序返回.
     * @throws IOException
     */
    String[] quickTips(String q) throws IOException;

    void reset() throws IOException;

}
