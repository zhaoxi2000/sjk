package com.ijinshan.sjk.service;

import java.util.List;

import org.hibernate.Session;

import com.ijinshan.sjk.jsonpo.BrokenApp;
import com.ijinshan.sjk.po.MarketApp;

public interface MarketAppService {

    MarketApp getTop(Session session, String pkname, String signatureSha1);

    void delete(String marketName, List<BrokenApp> data);
}
