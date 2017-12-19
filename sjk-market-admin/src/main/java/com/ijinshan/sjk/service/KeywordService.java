package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.Keyword;

public interface KeywordService {

    List<Keyword> search(int page, int rows, String q, String sort, String order);

    long countForSearching(String q);

    void saveOrUpdate(Keyword keyword);

    boolean delete(List<Integer> ids);

    Keyword get(int id);

}
