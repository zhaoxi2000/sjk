package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.mapper.PhoneInfoMapper;
import com.ijinshan.sjk.service.PhoneInfoService;
import com.ijinshan.sjk.util.PinYinUtils;
import com.ijinshan.sjk.vo.BrandListVo;
import com.ijinshan.sjk.vo.PhoneVo;

@Service
public class PhoneInfoServiceImpl implements PhoneInfoService {
    private static final Logger logger = LoggerFactory.getLogger(PhoneInfoServiceImpl.class);
    private static final String[] str = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "phoneInfoMapper")
    private PhoneInfoMapper phoneInfoMapper;

    @Override
    public List<PhoneVo> findPhoneInfoList(String brand) {
        return phoneInfoMapper.findPhoneInfoList(brand);
    }

    @Override
    public List<Integer> findPhoneInfoByParams(int cputype, String brand, String product) {
        return phoneInfoMapper.findPhoneInfoByParams(cputype, brand, product);
    }

    @Override
    public List<String> getAllPhoneBrand() {
        return phoneInfoMapper.getAllPhoneBrand();
    }

    @Override
    public List<BrandListVo> findPhoneLetterList() {
        List<BrandListVo> brandList = new ArrayList<BrandListVo>(26);
        for (int i = 0; i < str.length; i++) {
            BrandListVo vo = new BrandListVo();
            vo.setFirstLetter(str[i]);
            brandList.add(vo);
        }
        List<String> list = phoneInfoMapper.getAllPhoneBrand();
        for (String temp : list) {
            char charact = getFirstCharacter(PinYinUtils.converterToSpell(temp.trim().toString()).toUpperCase());
            if ((charact) >= 65) {
                BrandListVo bvo = brandList.get((charact - 65));
                bvo.getBrand().add(temp);
            }
        }
        return brandList;

    }

    // 判断两个数是否相等
    // public boolean checkEqual(char chr1, String chars) {
    // if (StringUtils.isNotEmpty(chars.trim().toString())) {
    // if
    // (getFirstCharacter(PinYinUtils.converterToSpell(chars.trim().toString()).toUpperCase())
    // .equals(chr1 + "")) {
    // return true;
    // }
    // }
    // return false;
    // }

    // 如果字符中包含有数字则取最近一个的字符
    // public String getFirstCharacter(String charts) {
    public char getFirstCharacter(String charts) {
        if (StringUtils.isNotEmpty(charts.trim().toString())) {
            for (int i = 0; i < charts.toCharArray().length; i++) {
                if (!Character.isDigit(charts.toCharArray()[i])) {
                    // return (charts.toCharArray()[i] + "").toUpperCase();
                    return charts.toCharArray()[i];
                }
            }
        }
        return 0;
    }

}
