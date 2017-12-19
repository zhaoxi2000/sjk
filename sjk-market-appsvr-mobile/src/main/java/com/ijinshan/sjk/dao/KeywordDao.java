package com.ijinshan.sjk.dao;

import com.ijinshan.sjk.po.Keyword;

public interface KeywordDao extends BaseDao<Keyword> {
    boolean exists(String name);

    Keyword get(String name);
}
