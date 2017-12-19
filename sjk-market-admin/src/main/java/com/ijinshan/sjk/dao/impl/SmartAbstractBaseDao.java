/**
 * 
 */
package com.ijinshan.sjk.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * @author huyouzhi
 * Create on 2013-1-9 下午8:22:20
 * 仅注入sessionFactory，供子类实现调用
 * </pre>
 */
public abstract class SmartAbstractBaseDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(SmartAbstractBaseDao.class);

    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public abstract Class<T> getType();
}
