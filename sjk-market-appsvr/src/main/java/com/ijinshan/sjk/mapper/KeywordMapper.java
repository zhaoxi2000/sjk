package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.vo.RecommendWordVo;

public interface KeywordMapper {

    @Select("select rankType, targetKeyword from Keyword where name = #{name}")
    Keyword get(@Param(value = "name") String name);
    
    @Select("SELECT id, name, color FROM HotWord WHERE Type=1 ORDER BY Rank LIMIT #{rows}")
    public List<RecommendWordVo> getRecommendWordVos(final int rows);
}
