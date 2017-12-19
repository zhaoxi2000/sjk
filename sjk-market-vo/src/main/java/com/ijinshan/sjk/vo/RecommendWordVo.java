package com.ijinshan.sjk.vo;

import java.io.Serializable;

public class RecommendWordVo implements Serializable{
    private static final long serialVersionUID = -9067921663714419463L;
    
    private Integer id;
    private String name;
    private int color;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    
}
