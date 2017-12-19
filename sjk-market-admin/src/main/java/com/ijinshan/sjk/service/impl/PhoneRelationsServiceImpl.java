package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.AppAndTagDao;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.PhoneRelationsDao;
import com.ijinshan.sjk.po.PhoneRelations;
import com.ijinshan.sjk.service.PhoneRelationsService;

@Service
public class PhoneRelationsServiceImpl implements PhoneRelationsService {
    private static final Logger logger = LoggerFactory.getLogger(PhoneRelationsServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    @Resource(name = "appAndTagDaoImpl")
    private AppAndTagDao appTagDao;

    @Resource(name = "phoneRelationsDaoImpl")
    private PhoneRelationsDao phoneRelationsDao;

    @Override
    public List<PhoneRelations> findPhoneRelationsByParam(String productId, Integer phoneid) {
        return phoneRelationsDao.findPhoneRelationsByParam(productId, phoneid);
    }

}