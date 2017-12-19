/**
 * 
 */
package com.ijinshan.sjk.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.BigGamePackDao;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.util.HibernateHelper;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-1-9 下午8:16:45
 * </pre>
 */
@Repository
public class BigGamePackDaoImpl extends SmartAbstractBaseDao<BigGamePack> implements BigGamePackDao {
    private static final Logger logger = LoggerFactory.getLogger(BigGamePackDaoImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#get(java.io.Serializable)
     */
    @Override
    public BigGamePack get(Serializable id) {
        return (BigGamePack) getCurrentSession().get(BigGamePack.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#save(org.hibernate.Session,
     * java.lang.Object)
     */
    @Override
    public Serializable save(Session sess, BigGamePack t) {

        return sess.save(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#save(java.lang.Object)
     */
    @Override
    public Serializable save(BigGamePack t) {
        return this.getCurrentSession().save(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#update(org.hibernate.Session,
     * java.lang.Object)
     */
    @Override
    public void update(Session session, BigGamePack t) {
        session.update(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#update(java.lang.Object)
     */
    @Override
    public void update(BigGamePack t) {
        this.getCurrentSession().update(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#saveOrUpdate(org.hibernate.Session,
     * java.lang.Object)
     */
    @Override
    public void saveOrUpdate(Session session, BigGamePack t) {
        session.saveOrUpdate(t);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(BigGamePack t) {
        this.getCurrentSession().saveOrUpdate(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#delete(java.lang.Object)
     */
    @Override
    public void delete(BigGamePack t) {
        this.getCurrentSession().delete(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#count()
     */
    @Override
    public long count() {
        Query query = this.getCurrentSession().createQuery("select count(id) from BigGamePack");
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#count(org.hibernate.Session)
     */
    @Override
    public long count(Session session) {
        Query query = session.createQuery("select count(id) from BigGamePack");
        Object o = query.uniqueResult();
        return Long.valueOf(o.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.BaseDao#delete(org.hibernate.Session,
     * java.lang.Object)
     */
    @Override
    public void delete(Session session, BigGamePack t) {
        session.delete(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ijinshan.sjk.dao.impl.AloneAbstractBaseDao#getType()
     */
    @Override
    public Class<BigGamePack> getType() {
        return BigGamePack.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ijinshan.sjk.dao.BigGamePackDao#getBigGamePacksByMarketAppid(java
     * .lang.Integer)
     */
    @Override
    public List<BigGamePack> getBigGamePacksByMarketAppid(Integer marketAppId) {
        Query query = this.getCurrentSession().createQuery("from BigGamePack a where a.marketAppId=?")
                .setInteger(0, marketAppId);
        return HibernateHelper.list(query);
    }

}
