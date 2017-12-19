package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.MarketAppDao;
import com.ijinshan.sjk.jsonpo.BrokenApp;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.service.MarketAppService;
import com.ijinshan.sjk.service.MergeAppComparator;
import com.ijinshan.sjk.service.OffAppService;

@Service
public class MarketAppServiceImpl implements MarketAppService {
    private static final Logger logger = LoggerFactory.getLogger(MarketAppServiceImpl.class);

    @Resource(name = "mergeAppComparatorImpl")
    private MergeAppComparator mergeAppComparator;

    @Resource(name = "offAppServiceImpl")
    protected OffAppService offAppService;

    @Resource(name = "marketAppDaoImpl")
    private MarketAppDao dao;

    @Override
    public MarketApp getTop(Session session, String pkname, String signatureSha1) {
        List<MarketApp> list = dao.getByApk(session, pkname, signatureSha1);
        if (list != null && !list.isEmpty()) {
            MarketApp marketApp = mergeAppComparator.getTop(list);
            return marketApp;
        }
        return null;
    }

    @Override
    public void delete(String marketName, List<BrokenApp> data) {
        offAppService.deleteByBrokenApp(marketName, data);
    }

}
