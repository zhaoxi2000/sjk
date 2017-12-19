package com.ijinshan.sjk.txservice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.CatalogDao;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.txservice.CatalogService;

@Service
public class CatalogServiceImpl implements CatalogService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

    @Resource(name = "catalogDaoImpl")
    private CatalogDao dao;

    @Override
    public List<Catalog> list() {
        return dao.list();
    }

}
