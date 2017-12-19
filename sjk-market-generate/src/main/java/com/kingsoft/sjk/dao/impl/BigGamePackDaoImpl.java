package com.kingsoft.sjk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.BigGamePack;
import com.kingsoft.sjk.dao.BasicSessionFactory;
import com.kingsoft.sjk.dao.BigGamePackDao;
import com.kingsoft.sjk.util.HibernateHelper;

@Repository
public class BigGamePackDaoImpl extends BasicSessionFactory implements BigGamePackDao {
    private static final Logger logger = LoggerFactory.getLogger(BigGamePackDaoImpl.class);

    @Override
    public List<BigGamePack> getBigGameByMarkAppId(Integer marketAppId) {
        Session session = null;
        List<BigGamePack> bigGameList = null;
        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from BigGamePack where marketAppId =:marketAppId");
            query.setParameter("marketAppId", marketAppId);
            bigGameList = HibernateHelper.list(query);
        } finally {
            session.close();
        }
        return bigGameList;
    }

}
