package com.ijinshan.sjk.service;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.vo.RollinfoDetail;

public interface RollinfoService {
    List<RollinfoDetail> getRollinfoDetail();

    int save(List<Integer> appIds, short catalog);

    int deleteByAppIds(List<Integer> appIds);

    int updateRecommand(List<Integer> appIds);

    int updateUnRecommand(List<Integer> appIds);

    int deleteByAppId(Session session, int appId);
}
