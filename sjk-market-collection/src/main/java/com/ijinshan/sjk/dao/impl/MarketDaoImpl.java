package com.ijinshan.sjk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MarketDaoImpl extends AbstractBaseDao<Market> implements MarketDao {
    private static final Logger logger = LoggerFactory.getLogger(MarketDaoImpl.class);

    @Override
    public Class<Market> getType() {
        return Market.class;
    }

    @Override
    public Market getByName(String marketName) {
        return getByName(getSession(), marketName);
    }

    @Override
    public Market getByName(Session session, String marketName) {
        Criteria cri = session.createCriteria(Market.class);
        cri.add(Restrictions.eq("marketName", marketName));
        cri.setCacheable(true);
        return (Market) cri.uniqueResult();
    }

    @Override
    public boolean allowAccess(String marketName, String key) {
        Criteria cri = getSession().createCriteria(Market.class);
        Market entity = new Market();
        entity.setMarketName(marketName);
        entity.setAllowAccessKey(key);
        Example exa = Example.create(entity);
        exa.excludeZeroes();
        cri.add(exa);
        return cri.uniqueResult() != null;
    }

    @Override
    public void merge(Session session, Market market) {
        session.merge(market);
    }

    @Override
    public List<Market> list() {
        Criteria cri = getSession().createCriteria(Market.class);
        cri.setCacheable(true);
        List<Market> list = HibernateHelper.list(cri);
        return list;
    }

}
