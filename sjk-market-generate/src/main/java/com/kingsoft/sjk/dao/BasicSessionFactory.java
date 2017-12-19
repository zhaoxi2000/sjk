package com.kingsoft.sjk.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

public abstract class BasicSessionFactory {

    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;
}
