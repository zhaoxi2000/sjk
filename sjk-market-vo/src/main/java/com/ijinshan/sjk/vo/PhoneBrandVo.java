/**
 * 
 */
package com.ijinshan.sjk.vo;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-26 下午8:07:20
 * </pre>
 */
public class PhoneBrandVo {
    private String brand;
    private String type;
    private int cputype;
    private int phoneId;

    public PhoneBrandVo(String brand, String type, int cputype, int phoneId) {
        super();
        this.brand = brand;
        this.type = type;
        this.cputype = cputype;
        this.phoneId = phoneId;
    }

    public PhoneBrandVo() {
        super();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCputype() {
        return cputype;
    }

    public void setCputype(int cputype) {
        this.cputype = cputype;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    @Override
    public String toString() {
        return "PhoneBrandVo [brand=" + brand + ", type=" + type + ", cputype=" + cputype + ", phoneId=" + phoneId
                + "]";
    }

}
