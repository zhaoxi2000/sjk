package com.ijinshan.sjk.config;

public enum TagType {
    TOPIC((short) 1, "toipc", "专题"), //
    NormalTag((short) 2, "normalTag", "普通标签"), //
    ;
    private short val;
    private String name;
    private String cnName;

    private TagType(short val, String name, String cnName) {
        this.val = val;
        this.name = name;
        this.cnName = cnName;
    }

    public short getVal() {
        return val;
    }

    public void setVal(short val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

}
