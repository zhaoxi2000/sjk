package com.kingsoft.sjk.po;

public class AppType implements java.io.Serializable {
    private static final long serialVersionUID = 1070122833181025483L;

    private int id;
    private transient int parentId;
    private String name;
    private String description;
    private int totalCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
