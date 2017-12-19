package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.Keyword;

public interface KeywordDao extends BaseDao<Keyword> {

    List<Keyword> search(int page, int rows, String q, String sort, String order);

    long countForSearching(String q);

    long deleteByIds(List<Integer> ids);

}
