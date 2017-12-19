package com.ijinshan.sjk.vo;
/**
 * 
 * <pre>
 * @author Du WangXi
 * Create on 2013-3-1 上午9:55:38
 * 提供给管理后台使用
 * </pre>
 */
public class PhoneInfoVo {

    private String phoneType;
    private byte cpu;
    private Integer phoneId;
    private String brand;
    public PhoneInfoVo() {
        super();
    }
    
    public PhoneInfoVo(String phoneType, byte cpu, Integer phoneId, String brand) {
        super();
        this.phoneType = phoneType;
        this.cpu = cpu;
        this.phoneId = phoneId;
        this.brand = brand;
    }

    public String getPhoneType() {
        return phoneType;
    }
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }
    public byte getCpu() {
        return cpu;
    }
    public void setCpu(byte cpu) {
        this.cpu = cpu;
    }
    public Integer getPhoneId() {
        return phoneId;
    }
    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    
}
