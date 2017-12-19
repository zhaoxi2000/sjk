package com.ijinshan.sjk.service;

import com.ijinshan.sjk.po.SysDictionary;

public interface SysDictionaryService {
    boolean saveOrUpdate(SysDictionary entity);

    boolean updateDicValue(Integer id, Integer intValue);

    boolean updateDicValue(String name, Integer intValue);

    SysDictionary get(Integer id);
}
