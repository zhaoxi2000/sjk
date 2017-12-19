package com.ijinshan.sjk.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.dao.RollinfoDao;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.service.RollinfoService;
import com.ijinshan.sjk.vo.RollinfoDetail;

@Service
public class RollinfoServiceImpl implements RollinfoService {
    private static final Logger logger = LoggerFactory.getLogger(RollinfoServiceImpl.class);
    @Resource(name = "rollinfoDaoImpl")
    private RollinfoDao dao;

    @Override
    public List<RollinfoDetail> getRollinfoDetail() {
        return dao.getRollinfoDetail();
    }

    @Override
    public int save(List<Integer> appIds, short catalog) {
        Assert.isTrue(appIds.size() > 0, "Invalid params!");
        List<Rollinfo> list = new ArrayList<Rollinfo>(appIds.size());
        for (Integer appId : appIds) {
            Rollinfo rollinfo = new Rollinfo();
            rollinfo.setAppId(appId);
            rollinfo.setCatalog(catalog);
            rollinfo.setRecommend(false);
            rollinfo.setRank(999999999);
            list.add(rollinfo);
        }
        int count = 0;
        for (Rollinfo t : list) {
            Serializable pk = dao.save(t);
            if (pk != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int deleteByAppIds(List<Integer> appIds) {
        Assert.isTrue(appIds.size() > 0, "Invalid params!");
        return dao.delete(appIds);
    }

    @Override
    public int updateRecommand(List<Integer> appIds) {
        return dao.updateRecommand(appIds);
    }

    @Override
    public int updateUnRecommand(List<Integer> appIds) {
        return dao.updateUnRecommand(appIds);
    }

    @Override
    public int deleteByAppId(Session session, int appId) {
        return dao.deleteByAppId(session, appId);
    }
}
