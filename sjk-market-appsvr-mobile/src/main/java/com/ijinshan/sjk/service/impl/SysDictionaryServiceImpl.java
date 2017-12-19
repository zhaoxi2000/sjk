package com.ijinshan.sjk.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.mapper.SysDictionaryMapper;
import com.ijinshan.sjk.service.SysDictionaryService;

@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryServiceImpl.class);

    // @Resource(name = "sysDictionaryDaoImpl")
    // private SysDictionaryDao dao;

    @Resource(name = "sysDictionaryMapper")
    private SysDictionaryMapper sysDictionaryMapper;

    @Override
    public int getAppsRollRecommendNum() {
        return sysDictionaryMapper.getAppsRollRecommendNum();
        // return dao.getAppsRollRecommendNum();
    }

}
