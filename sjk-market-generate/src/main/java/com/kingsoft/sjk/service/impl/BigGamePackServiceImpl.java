package com.kingsoft.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.po.BigGamePack;
import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.dao.BigGamePackDao;
import com.kingsoft.sjk.service.BigGamePackService;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:17:12
 * </pre>
 */

@Service
public class BigGamePackServiceImpl implements BigGamePackService {
    private static final Logger logger = LoggerFactory.getLogger(BigGamePackServiceImpl.class);

    @Autowired
    private BigGamePackDao bigGamePackDao;

    @Resource(name = "appConfig")
    private AppConfig config;

    @Override
    public List<BigGamePack> getBigGameByMarkAppId(int marketAppId) {
        return bigGamePackDao.getBigGameByMarkAppId(marketAppId);
    }

}
