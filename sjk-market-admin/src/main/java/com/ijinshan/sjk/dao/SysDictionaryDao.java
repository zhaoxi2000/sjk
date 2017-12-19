package com.ijinshan.sjk.dao;

import com.ijinshan.sjk.po.SysDictionary;

public interface SysDictionaryDao extends BaseDao<SysDictionary> {
    int updateDicValue(Integer id, Integer intValue);

    int updateDicValue(String name, Integer intValue);
}
