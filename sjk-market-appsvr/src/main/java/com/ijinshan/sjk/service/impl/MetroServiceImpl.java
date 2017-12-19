package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.MetroDao;
import com.ijinshan.sjk.service.MetroService;
import com.ijinshan.sjk.vo.MetroVO;

@Service
public class MetroServiceImpl implements MetroService {
    private static final Logger logger = LoggerFactory.getLogger(MetroServiceImpl.class);

    @Resource(name = "metroDaoImpl")
    private MetroDao metroDao;

    @Override
    public List<MetroVO> list() {
        List<MetroVO> list = metroDao.list();
        return list;
    }
}
