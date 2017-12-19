package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.MetroDao;
import com.ijinshan.sjk.mapper.AppMapper;
import com.ijinshan.sjk.mapper.MetorMapper;
import com.ijinshan.sjk.mapper.MoMixFeaturedMapper;
import com.ijinshan.sjk.mapper.TagMapper;
import com.ijinshan.sjk.service.MetroService;
import com.ijinshan.sjk.vo.MetroMobileParamVO;
import com.ijinshan.sjk.vo.MetroMobileVO;
import com.ijinshan.sjk.vo.MetroOldMobileVO;
import com.ijinshan.sjk.vo.MetroVO;
import com.ijinshan.sjk.vo.TagMobileVo;

@Service
public class MetroServiceImpl implements MetroService {
    private static final Logger logger = LoggerFactory.getLogger(MetroServiceImpl.class);
    @Resource(name = "metroDaoImpl")
    private MetroDao metroDao;

    @Resource(name = "metorMapper")
    private MetorMapper metorMapper;

    @Resource(name = "moMixFeaturedMapper")
    private MoMixFeaturedMapper moMixFeaturedMapper;

    @Resource(name = "appMapper")
    private AppMapper appMapper;

    @Resource(name = "tagMapper")
    private TagMapper tagMapper;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Override
    public List<MetroVO> list() {
        List<MetroVO> list = metroDao.list();
        return list;
    }

    // 得到MoFeaturedlist列表
    @Override
    public List<MetroOldMobileVO> getMoFeaturedlist() {
        List<MetroOldMobileVO> metroList = metorMapper.getMoFeaturedlist();
        return metroList;
    }

    @Override
    public List<MetroMobileVO> getMixFeaturedlist() {
        List<MetroMobileVO> mvolist = moMixFeaturedMapper.getMixFeaturedlist();

        return mvolist;
    }

    @Override
    public Map<Integer, MetroMobileParamVO> getMetroMobileList() {
        Map<Integer, MetroMobileParamVO> map = new HashMap<Integer, MetroMobileParamVO>();
        MetroMobileParamVO parmVo1 = new MetroMobileParamVO();
        MetroMobileParamVO parmVo2 = new MetroMobileParamVO();
        MetroMobileParamVO parmVo3 = new MetroMobileParamVO();
        MetroMobileParamVO parmVo4 = new MetroMobileParamVO();
        List<MetroMobileVO> metr1 = new ArrayList<MetroMobileVO>();
        List<MetroMobileVO> metr2 = new ArrayList<MetroMobileVO>();
        List<MetroMobileVO> metr3 = new ArrayList<MetroMobileVO>();
        List<MetroMobileVO> metr4 = new ArrayList<MetroMobileVO>();
        List<MetroMobileVO> mvolist = moMixFeaturedMapper.getMixFeaturedlist();
        List<TagMobileVo> taglist = tagMapper.getTagList();
        for (MetroMobileVO metroMobileVO : mvolist) {
            if (metroMobileVO.getPicType() == 1) {
                metr1.add(metroMobileVO);
            } else if (metroMobileVO.getPicType() == 2) {
                metr2.add(metroMobileVO);
            } else if (metroMobileVO.getPicType() == 3) {
                metr3.add(metroMobileVO);
            } else if (metroMobileVO.getPicType() == 4) {
                metr4.add(metroMobileVO);
            }
        }
        for (TagMobileVo tag : taglist) {
            if (tag.getPicType() == 1) {
                metr1.add(getMetroVo(tag));
            } else if (tag.getPicType() == 2) {
                metr2.add(getMetroVo(tag));
            } else if (tag.getPicType() == 3) {
                metr3.add(getMetroVo(tag));
            } else if (tag.getPicType() == 4) {
                metr4.add(getMetroVo(tag));
            }
        }

        parmVo1.setMetrolist(metr1);
        parmVo2.setMetrolist(metr2);
        parmVo3.setMetrolist(metr3);
        parmVo4.setMetrolist(metr4);
        map.put(1, parmVo1);
        map.put(2, parmVo2);
        map.put(3, parmVo3);
        map.put(4, parmVo4);
        return map;
    }

    public MetroMobileVO getMetroVo(TagMobileVo tagVo) {
        MetroMobileVO mv = new MetroMobileVO();
        if (tagVo != null) {
            mv.setAppOrTagId(tagVo.getId());
            mv.setBigPics(tagVo.getBigPics());
            mv.setCatalog(tagVo.getCatalog());
            mv.setMediumPics(tagVo.getMediumPics());
            mv.setImgUrl(tagVo.getImgUrl());
            mv.setName(tagVo.getName());
            mv.setPicType(tagVo.getPicType());
            mv.setSmallPics(tagVo.getSmallPics());
            mv.setType(2);
        }
        return mv;
    }

}
