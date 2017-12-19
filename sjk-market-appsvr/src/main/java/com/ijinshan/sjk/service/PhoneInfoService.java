package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.vo.BrandListVo;
import com.ijinshan.sjk.vo.PhoneVo;

public interface PhoneInfoService {

    List<PhoneVo> findPhoneInfoList(String brand);

    List<Integer> findPhoneInfoByParams(int cputype, String brand, String product);

    List<String> getAllPhoneBrand();

    List<BrandListVo> findPhoneLetterList();
}
