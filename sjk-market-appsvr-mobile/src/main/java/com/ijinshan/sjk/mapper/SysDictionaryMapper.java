package com.ijinshan.sjk.mapper;

import org.apache.ibatis.annotations.Select;

public interface SysDictionaryMapper {

    @Select("select intValue from SysDictionary where name = 'recommendCount'")
    public int getAppsRollRecommendNum();

}
