package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.vo.RecommendWordVo;

public interface KeywordMapper {

    @Select("select rankType, targetKeyword from Keyword where name = #{name}")
    Keyword get(@Param(value = "name") String name);
    
    @Select("SELECT count(1) FROM HotWord WHERE Type=2")
    public int getRecommendWordVosCount();
    
    @Select("SELECT id, name, color FROM HotWord WHERE Type=2 ORDER BY Rank LIMIT ${offset} , ${pageSize}")
    public List<RecommendWordVo> getRecommendWordVos(@Param(value = "offset") int offset, @Param(value = "pageSize") int pageSize);
}
