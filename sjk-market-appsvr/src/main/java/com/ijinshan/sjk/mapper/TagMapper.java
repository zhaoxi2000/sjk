package com.ijinshan.sjk.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ijinshan.sjk.vo.pc.SimpleTag;

public interface TagMapper {

    @Select("select id, name, tagDesc, imgUrl from Tag where id = #{id}")
    public SimpleTag get(@Param(value = "id") int id);

}
