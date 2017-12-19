/**
 * 
 */
package com.ijinshan.sjk.config;

/**
 * @author LinZuXiong
 */
public enum AppStatus {
    ADD((byte) 1, "add"), //
    UPDATE((byte) 2, "update"), //
    DELETE((byte) 3, "delete"), //
    ;
    private byte val;
    private String name;

    private AppStatus(byte val, String name) {
        this.val = val;
        this.name = name;
    }

    public byte getVal() {
        return val;
    }

    private void setVal(byte val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static AppStatus appStatus(byte val) {
        switch (val) {
            case 1:
                return ADD;
            case 2:
                return UPDATE;
            case 3:
                return DELETE;
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
}
