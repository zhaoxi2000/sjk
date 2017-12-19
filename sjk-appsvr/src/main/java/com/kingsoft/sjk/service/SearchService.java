package com.kingsoft.sjk.service;

import java.util.List;

import javax.annotation.PostConstruct;

import com.kingsoft.sjk.po.App;

/**
 * 搜索接口
 * 
 * @author wuzhenyu
 * @since 2012-07-25
 */
public interface SearchService {

    /**
     * 搜索
     * 
     * @param typeId
     * @param keyword
     * @return
     */
    List<App> search(Integer typeId, String keyword);

    /**
     * 关键字提示
     * 
     * @param typeId
     * @param keyword
     * @return
     */
    List<String> getSpellSuggestion(Integer typeId, String keyword);

    /**
     * 拼写提示
     * 
     * @param typeId
     * @param keyword
     * @return
     */
    List<String> spellChecker(Integer typeId, String keyword);

    @PostConstruct
    void init();

    /**
     * 初始化索引
     * 
     * @param typeId
     * @param subTypeId
     * @throws Exception
     */
    void initAppIndex(Integer typeId, Integer subTypeId) throws Exception;

    /**
     * 初始化TrieTree
     * 
     * @param typeId
     * @throws Exception
     */
    void initTrieDict(Integer typeId) throws Exception;

    /**
     * @param typeId
     * @return
     * @throws Exception
     */
    boolean updateIndex(Integer typeId) throws Exception;
}
