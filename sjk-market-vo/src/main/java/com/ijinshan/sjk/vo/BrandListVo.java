package com.ijinshan.sjk.vo;

import java.util.ArrayList;
import java.util.List;

public class BrandListVo {

    private String firstLetter;
    private List<String> brand = new ArrayList<String>();

    public BrandListVo() {
        super();
    }

    public BrandListVo(String firstLetter, List<String> brand) {
        super();
        this.firstLetter = firstLetter;
        this.brand = brand;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public List<String> getBrand() {
        return brand;
    }

    public void setBrand(List<String> brand) {
        this.brand = brand;
    }

}
