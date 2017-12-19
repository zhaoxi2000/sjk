/**
 * 
 */
package com.ijinshan.sjk.config;

/**
 * @author LinZuXiong
 */
public enum MarketStatus {
    OK((byte) 1, "OK"), //
    DROP((byte) 2, "DROP"), //
    ;
    private byte val;
    private String name;

    MarketStatus(byte val) {

    }

    private MarketStatus(byte val, String name) {
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

}
