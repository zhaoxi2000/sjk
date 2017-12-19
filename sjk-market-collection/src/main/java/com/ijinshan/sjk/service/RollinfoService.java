package com.ijinshan.sjk.service;

import org.hibernate.Session;

public interface RollinfoService {

    int deleteByAppId(Session session, int appId);

    int deleteByAppId(int appId);

}
