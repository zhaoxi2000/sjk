package com.ijinshan.sjk.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.SysDictionaryDao;
import com.ijinshan.sjk.po.SysDictionary;
import com.ijinshan.sjk.service.SysDictionaryService;

@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryServiceImpl.class);

    @Autowired
    SysDictionaryDao dao;

    @Override
    public boolean saveOrUpdate(SysDictionary entity) {
        if (entity.getId() > 0) {
            dao.save(entity);
        } else {
            dao.update(entity);
        }
        return true;
    }

    @Override
    public boolean updateDicValue(Integer id, Integer intValue) {
        return dao.updateDicValue(id, intValue) > 0;
    }

    @Override
    public SysDictionary get(Integer id) {
        return dao.get(id);
    }

    @Override
    public boolean updateDicValue(String name, Integer intValue) {
        return dao.updateDicValue(name, intValue) > 0;
    }

}
