package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.vo.RecommendWordVo;

public interface KeywordService {

    Keyword getByName(String q);

    public int getRecommendWordVosCount();
    
    public List<RecommendWordVo> getRecommendWordVos(final int currentPage, final int pageSize);
}
