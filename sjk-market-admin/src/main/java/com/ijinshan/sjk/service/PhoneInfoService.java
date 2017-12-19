package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.PhoneBasicInfo;
import com.ijinshan.sjk.vo.PhoneAdminVo;
import com.ijinshan.sjk.vo.PhoneVo;

public interface PhoneInfoService {

    List<PhoneVo> findPhoneInfoList(String brand);

    List<Integer> findPhoneInfoByParams(int cputype, String brand, String product);

    List<String> findAllPhoneBrand();

    List<PhoneAdminVo> findPhoneListByParams(String brand, String product);

    List<PhoneBasicInfo> getPhoneBaseInfoById(String phoneIds);
}
