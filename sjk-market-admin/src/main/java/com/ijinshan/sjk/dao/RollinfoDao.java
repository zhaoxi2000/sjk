package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.vo.RollinfoDetail;

public interface RollinfoDao extends BaseDao<Rollinfo> {
    List<RollinfoDetail> getRollinfoDetail();

    int delete(List<Integer> appIds);

    int deleteNotExistsApp();

    int deleteNotExistsApp(Session session);

    int updateRecommand(List<Integer> appIds);

    int updateUnRecommand(List<Integer> appIds);

    int deleteByAppId(Session session, int appId);
}
