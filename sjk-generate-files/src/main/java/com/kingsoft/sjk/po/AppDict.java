package com.kingsoft.sjk.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AppDict", catalog = "AppMgr")
public class AppDict {

    private int id;
    private int typeID;
    private String name;
    private int orderID;
    private String OtherName;
    private String childIDs;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "TypeID")
    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "OrderID")
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Column(name = "OtherName")
    public String getOtherName() {
        return OtherName;
    }

    public void setOtherName(String otherName) {
        OtherName = otherName;
    }

    @Column(name = "ChildIDs")
    public String getChildIDs() {
        return childIDs;
    }

    public void setChildIDs(String childIDs) {
        this.childIDs = childIDs;
    }

}
