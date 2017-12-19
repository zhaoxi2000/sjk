package com.ijinshan.sjk.service;

import org.hibernate.Session;

public interface TagRelationshipService {

    void deleteByAppId(Session session, int appId);

}
