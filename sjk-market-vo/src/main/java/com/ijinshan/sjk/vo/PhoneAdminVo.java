package com.ijinshan.sjk.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * <pre>
 * @author Du WangXi
 * Create on 2013-3-1 上午9:52:15
 * 提供给管理后台使用，根据品版查询型号
 * </pre>
 */

public class PhoneAdminVo  {

    private String brand;
    
    private List<PhoneInfoVo> phonelist = new ArrayList<PhoneInfoVo>();

    public PhoneAdminVo() {
        super();
    }

    public PhoneAdminVo(String brand, List<PhoneInfoVo> phonelist) {
        super();
        this.brand = brand;
        this.phonelist = phonelist;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<PhoneInfoVo> getPhonelist() {
        return phonelist;
    }

    public void setPhonelist(List<PhoneInfoVo> phonelist) {
        this.phonelist = phonelist;
    }
}
