package com.ijinshan.sjk.mapper;

import java.util.List;

import com.ijinshan.sjk.vo.MetroMobileVO;
import com.ijinshan.sjk.vo.MetroOldMobileVO;

public interface MetorMapper {

    List<MetroOldMobileVO> getMoFeaturedlist();

    List<MetroMobileVO> getMixFeaturedlist();

}
