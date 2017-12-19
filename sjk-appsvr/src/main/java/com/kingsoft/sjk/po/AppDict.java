package com.kingsoft.sjk.po;

public class AppDict {

    private int id;
    private int typeID;
    private String name;
    private int orderID;
    private String otherName;
    private String childIDs;

    public AppDict(int id, int typeID, String name, int orderID, String otherName, String childIDs) {
        super();
        this.id = id;
        this.typeID = typeID;
        this.name = name;
        this.orderID = orderID;
        this.otherName = otherName;
        this.childIDs = childIDs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getChildIDs() {
        return childIDs;
    }

    public void setChildIDs(String childIDs) {
        this.childIDs = childIDs;
    }

}
