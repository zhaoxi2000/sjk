package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.mapper.KeywordMapper;
import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.service.KeywordService;
import com.ijinshan.sjk.vo.RecommendWordVo;
import com.ijinshan.util.HibernateHelper;

@Service
public class KeywordServiceImpl implements KeywordService {
    private static final Logger logger = LoggerFactory.getLogger(KeywordServiceImpl.class);

    public static final int MAX_ROWS = 30;
    
    @Resource(name = "keywordMapper")
    private KeywordMapper keywordMapper;

    @Override
    public Keyword getByName(String name) {
        return keywordMapper.get(name);
    }
    
    @Override
    public int getRecommendWordVosCount() {
        return keywordMapper.getRecommendWordVosCount();
    }
    
    @Override
    public List<RecommendWordVo> getRecommendWordVos(final int currentPage, final int pageSize) {
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        Assert.isTrue(currentPage > 0, "currentPage negative!");
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        return keywordMapper.getRecommendWordVos(offset, pageSize);
    }
}
