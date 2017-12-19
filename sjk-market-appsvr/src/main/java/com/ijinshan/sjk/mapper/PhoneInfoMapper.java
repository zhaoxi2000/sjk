package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ijinshan.sjk.po.PhoneBasicInfo;
import com.ijinshan.sjk.vo.PhoneVo;

public interface PhoneInfoMapper {

    List<PhoneVo> findPhoneInfoList(@Param(value = "brand") String brand);

    List<Integer> findPhoneInfoByParams(@Param(value = "cputype") int cputype, @Param(value = "brand") String brand,
            @Param(value = "product") String product);

    @Select(value = "SELECT  DISTINCT a.Brand FROM PhoneBasicInfo a ")
    List<String> getAllPhoneBrand();

    List<PhoneBasicInfo> getPhoneList(@Param(value = "product") String product, @Param(value = "brand") String brand);
}
