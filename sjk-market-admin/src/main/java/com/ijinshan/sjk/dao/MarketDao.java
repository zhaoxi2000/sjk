package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ijinshan.sjk.po.Market;

public interface MarketDao extends BaseDao<Market> {

    Market getByName(String marketName);

    Market getByName(Session session, String marketName);

    SessionFactory getSessions();

    boolean allowAccess(String marketName, String key);

    void merge(Session session, Market market);

    List<Market> list();

}
