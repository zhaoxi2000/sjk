package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.PhoneBaseInfoDao;
import com.ijinshan.sjk.dao.PhoneRelationsDao;
import com.ijinshan.sjk.po.PhoneBasicInfo;
import com.ijinshan.sjk.po.PhoneRelations;
import com.ijinshan.sjk.service.PhoneInfoService;
import com.ijinshan.sjk.vo.PhoneAdminVo;
import com.ijinshan.sjk.vo.PhoneInfoVo;
import com.ijinshan.sjk.vo.PhoneVo;

@Service
public class PhoneInfoServiceImpl implements PhoneInfoService {
    private static final Logger logger = LoggerFactory.getLogger(PhoneInfoServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "phoneBaseInfoDaoImpl")
    private PhoneBaseInfoDao phoneBaseInfoDao;

    @Resource(name = "phoneRelationsDaoImpl")
    private PhoneRelationsDao phoneRelationsDao;

    @Override
    public List<PhoneVo> findPhoneInfoList(String brand) {
        return null;
    }

    @Override
    public List<Integer> findPhoneInfoByParams(int cputype, String brand, String product) {
        return null;
    }

    @Override
    public List<String> findAllPhoneBrand() {
        return phoneBaseInfoDao.findAllPhoneBrand();
    }

    @Override
    public List<PhoneAdminVo> findPhoneListByParams(String brand, String product) {
        List<PhoneAdminVo> adminVo = new ArrayList<PhoneAdminVo>();
        List<PhoneBasicInfo> baseInfoList = phoneBaseInfoDao.findPhoneListByParams(brand, product);
        for (PhoneBasicInfo baseInfo : baseInfoList) {
            List<PhoneRelations> prel = phoneRelationsDao.findPhoneRelationsByParam(baseInfo.getProductId(),
                    baseInfo.getId());
            if (prel.size() > 0 && prel.get(0).getCpu() > 0) {
                PhoneAdminVo pVo = new PhoneAdminVo();
                pVo.setBrand(baseInfo.getBrand());
                PhoneInfoVo fVo = new PhoneInfoVo();
                fVo.setBrand(baseInfo.getBrand());
                fVo.setPhoneId(baseInfo.getId());
                fVo.setCpu(prel.get(0).getCpu());
                fVo.setPhoneType(baseInfo.getProduct());
                pVo.getPhonelist().add(fVo);
                adminVo.add(pVo);
            }
        }
        return adminVo;
    }

    @Override
    public List<PhoneBasicInfo> getPhoneBaseInfoById(String phoneIds) {
        List<Integer> ids = new ArrayList<Integer>();
        if (StringUtils.isNotEmpty(phoneIds)) {
            String[] str = phoneIds.split("_");
            for (String temp : str) {
                if (StringUtils.isNotEmpty(temp)) {
                    ids.add(Integer.parseInt(temp));
                }
            }
        }
        return phoneBaseInfoDao.getPhoneBaseInfoListByIds(ids);
    }

}
