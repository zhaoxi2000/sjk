package com.ijinshan.sjk.service;

import java.util.List;
import java.util.Map;

import com.ijinshan.sjk.vo.MetroMobileParamVO;
import com.ijinshan.sjk.vo.MetroMobileVO;
import com.ijinshan.sjk.vo.MetroOldMobileVO;
import com.ijinshan.sjk.vo.MetroVO;

public interface MetroService {

    List<MetroVO> list();

    List<MetroOldMobileVO> getMoFeaturedlist();

    List<MetroMobileVO> getMixFeaturedlist();

    Map<Integer, MetroMobileParamVO> getMetroMobileList();

}
