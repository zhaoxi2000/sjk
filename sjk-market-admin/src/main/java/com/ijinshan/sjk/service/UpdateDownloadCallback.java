package com.ijinshan.sjk.service;

import org.hibernate.Session;

import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;

public interface UpdateDownloadCallback {

    int doIn(Session session, App a, AppDao appDao);
}
