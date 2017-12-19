/**
 * 
 */
package com.kingsoft.sjk.game.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.BigGamePack;
import com.kingsoft.sjk.game.dao.IBaseDao;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-26 上午9:54:14
 * </pre>
 */
@Repository
public class BigGamePackDaoImpl implements IBaseDao<BigGamePack> {
    private static final Logger logger = LoggerFactory.getLogger(BigGamePackDaoImpl.class);

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
    public void delete(BigGamePack entity) {
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
    public Integer save(BigGamePack entity) {
        Session session = null;
        Integer pkId = null;
        try {
            session = sessionFactory.openSession();
            pkId = (Integer) session.save(entity);
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            session.flush();
            session.clear();
            session.close();
        }

        return pkId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#update(java.lang.Object)
     */
    @Override
    public void update(BigGamePack entity) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#find(java.lang.Object)
     */
    @Override
    public BigGamePack find(BigGamePack entity) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.game.dao.IBaseDao#findById(java.lang.Integer)
     */
    @Override
    public BigGamePack findById(Integer id) {
        return null;
    }
}
