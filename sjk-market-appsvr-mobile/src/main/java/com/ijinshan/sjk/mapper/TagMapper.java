package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.vo.TagMobileVo;

public interface TagMapper {

    public List<Tag> getList();

    public TagMobileVo getById(@Param(value = "id") int id);

    public List<TagMobileVo> getByIds(@Param(value = "ids") List<Integer> ids);

    public List<TagMobileVo> getTagList();

}
