package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ijinshan.sjk.po.Tag;

public interface TagMapper {

    @Select(value = { "SELECT id, name FROM AndroidMarket.Tag where tagtype = 2" })
    List<Tag> getTags();

}
