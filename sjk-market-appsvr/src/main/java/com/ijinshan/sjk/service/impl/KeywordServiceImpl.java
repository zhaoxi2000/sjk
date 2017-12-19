package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.mapper.KeywordMapper;
import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.service.KeywordService;
import com.ijinshan.sjk.vo.RecommendWordVo;

@Service
public class KeywordServiceImpl implements KeywordService {
    private static final Logger logger = LoggerFactory.getLogger(KeywordServiceImpl.class);

    @Resource(name = "keywordMapper")
    private KeywordMapper keywordMapper;

    @Override
    public Keyword getByName(String name) {
        return keywordMapper.get(name);
    }
    
    @Override
    public List<RecommendWordVo> getRecommendWordVos(final int rows) {
        return keywordMapper.getRecommendWordVos(rows);
    }
}
