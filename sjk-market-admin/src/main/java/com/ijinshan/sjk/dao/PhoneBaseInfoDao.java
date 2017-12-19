package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.PhoneBasicInfo;

public interface PhoneBaseInfoDao extends BaseDao<PhoneBasicInfo> {

    List<String> findAllPhoneBrand();

    List<PhoneBasicInfo> findPhoneListByBrand(String brand);

    List<PhoneBasicInfo> findPhoneListByParams(String brand, String product);

    List<PhoneBasicInfo> getPhoneBaseInfoListByIds(List<Integer> ids);

}
