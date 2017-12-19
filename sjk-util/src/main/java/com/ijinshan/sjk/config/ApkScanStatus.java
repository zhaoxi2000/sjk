/**
 * 
 */
package com.ijinshan.sjk.config;

/**
 * @author LinZuXiong
 */
public enum ApkScanStatus {

    NEED_SCAN(1, ""), //
    NEED_DONE(10, ""), //

    ;
    private int val;
    private String name;

    ApkScanStatus(int val) {

    }

    private ApkScanStatus(int val, String name) {
        this.val = val;
        this.name = name;
    }

    public int getVal() {
        return val;
    }

    private void setVal(int val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

}
