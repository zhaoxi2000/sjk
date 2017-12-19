package com.ijinshan.sjk.po;

import java.io.Serializable;

public class RecommendWord implements Serializable{
    private static final long serialVersionUID = -6971968356579731043L;

    private Integer id;
    private String name;
    /** 1=PC 2=mobile */
    private int type;
    /**越大越前*/
    private int rank;
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
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    
    
}
