package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.mapper.BigGamePackMapper;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.service.BigGamePackService;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:17:12
 * </pre>
 */

@Service
public class BigGamePackServiceImpl implements BigGamePackService {
    private static final Logger logger = LoggerFactory.getLogger(BigGamePackServiceImpl.class);

    // @Resource(name = "appDaoImpl")
    // private AppDao appDao;

    // @Resource(name = "appConfig")
    // private AppConfig appConfig;
    //
    // @Resource(name = "appMapper")
    // private AppMapper appMapper;

    @Resource(name = "bigGamePackMapper")
    private BigGamePackMapper bigGamePackMapper;

    // @Resource(name = "cdnCacheImpl")
    // private CDNCache cdnCache;

    @Override
    public List<BigGamePack> getBigGameByMarkAppId(int marketAppId) {
        return bigGamePackMapper.getBigGameByMarkAppId(marketAppId);
    }

}
