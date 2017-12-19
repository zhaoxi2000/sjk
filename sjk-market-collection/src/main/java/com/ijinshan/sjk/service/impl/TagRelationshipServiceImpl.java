package com.ijinshan.sjk.service.impl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.service.TagRelationshipService;

@Service
public class TagRelationshipServiceImpl implements TagRelationshipService {
    private static final Logger logger = LoggerFactory.getLogger(TagRelationshipServiceImpl.class);

    @Override
    public void deleteByAppId(Session session, int appId) {
    }
}
