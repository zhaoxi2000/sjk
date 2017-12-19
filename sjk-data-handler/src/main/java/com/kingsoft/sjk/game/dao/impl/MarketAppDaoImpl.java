/**
 * 
 */
package com.kingsoft.sjk.game.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.MarketApp;
import com.kingsoft.sjk.game.dao.IBaseDao;
import com.kingsoft.sjk.game.dao.MarketAppDao;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-26 上午9:51:27
 * </pre>
 */
@Repository
public class MarketAppDaoImpl implements IBaseDao<MarketApp>, MarketAppDao {
    private static final Logger logger = LoggerFactory.getLogger(MarketAppDaoImpl.class);

    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#deleteById(java.lang.Integer)
     */
    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#delete(java.lang.Object)
     */
    @Override
    public void delete(MarketApp entity) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#isExist(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean isExist(String vid, String pid, String names, String models) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#isExist(java.lang.String,
     * java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public boolean isExist(String vid, String pid, Integer os_version, Integer os_bit) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#save(java.lang.Object)
     */
    @Override
    public Integer save(MarketApp entity) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#update(java.lang.Object)
     */
    @Override
    public void update(MarketApp entity) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#find(java.lang.Object)
     */
    @Override
    public MarketApp find(MarketApp entity) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#findById(java.lang.Integer)
     */
    @Override
    public MarketApp findById(Integer id) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.MarketAppDao#getAllBigGameFromMarketApp()
     */
    @Override
    public List<MarketApp> getAllBigGameFromMarketApp() {
        String hqlStr = "from MarketApp a where a.catalog =100";
        List<MarketApp> list = null;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            list = session.createQuery(hqlStr).list();
        } finally {
            session.flush();
            session.clear();
            session.close();
        }

        return list;
    }

}
