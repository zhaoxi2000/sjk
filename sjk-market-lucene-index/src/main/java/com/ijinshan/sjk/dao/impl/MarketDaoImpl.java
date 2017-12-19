package com.ijinshan.sjk.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.dao.AbstractBaseDao;
import com.ijinshan.sjk.dao.MarketDao;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.util.HibernateHelper;

@Repository
public class MarketDaoImpl extends AbstractBaseDao<Market> implements MarketDao {
    private static final Logger logger = LoggerFactory.getLogger(MarketDaoImpl.class);

    @Override
    public List<Market> listMarketRank() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("marketName"));
        proList.add(Projections.property("rank"));
        Criteria cri = getSession().createCriteria(Market.class);
        cri.setProjection(proList);
        List<Object[]> list = HibernateHelper.list(cri);
        List<Market> markets = null;
        if (list != null && !list.isEmpty()) {
            markets = new ArrayList<Market>(list.size());
            for (Object[] obj : list) {
                Market m = new Market();
                m.setMarketName((String) obj[0]);
                m.setRank((Integer) obj[1]);
                markets.add(m);
            }
        }
        return markets;
    }

    @Override
    public Class<Market> getType() {
        return Market.class;
    }

}
