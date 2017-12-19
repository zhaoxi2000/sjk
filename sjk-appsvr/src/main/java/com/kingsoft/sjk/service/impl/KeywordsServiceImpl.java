package com.kingsoft.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kingsoft.sjk.dao.KeywordsDao;
import com.kingsoft.sjk.po.Keywords;
import com.kingsoft.sjk.service.KeywordsService;

@Service
public class KeywordsServiceImpl implements KeywordsService {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsServiceImpl.class);

    @Resource(name = "keywordsDaoImpl")
    private KeywordsDao dao;

    @Override
    public List<Keywords> getHotKeywords() {
        return dao.list();
    }
}
