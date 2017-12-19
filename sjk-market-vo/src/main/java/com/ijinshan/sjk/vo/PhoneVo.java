package com.ijinshan.sjk.vo;
/*
 * 提供给管理后台使用
 */
public class PhoneVo {

    private String phoneType;
    private Integer cpu;
    private Integer phoneId;

    private PhoneVo() {
        super();
    }

    private PhoneVo(String phoneType, Integer cpu, Integer phoneId) {
        super();
        this.phoneType = phoneType;
        this.cpu = cpu;
        this.phoneId = phoneId;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

}
