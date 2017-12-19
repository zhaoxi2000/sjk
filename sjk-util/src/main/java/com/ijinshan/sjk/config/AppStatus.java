/**
 * 
 */
package com.ijinshan.sjk.config;

/**
 * @author LinZuXiong
 */
public enum AppStatus {

    MARKET_DATA_MAX(0xF, "max market data", "市场区位的最大值"), //
    MARKET_DATA_MASK(0x7, "market_data_mask", "market_data_mask"), //
    // 更新市场数据程序 0x0 ~ 0xF 第一个16进制位
    ADD(0x1, "add", "市场新增"), //
    UPDATE(0x2, "update", "市场更新"), //
    DELETE(0x3, "delete", "市场下架"), //
    UNCHANGED(0x4, "unchanged", "市场数据无变化"), //

    // special market data status.
    MARKET_MASK(0x8, "market_mask", "market_mask"), //
    MARKET_DROP(0x8, "market_drop", "市场不可用!"), // 市场出现完全不可用的时候.

    DETECT_MIN(0x10, "min detect url data", "URL监控程序"), //
    DETECT_MASK(0xF0, "", ""), //
    // URL监控程序 0x10 ~ 0xF0 第二个16进制位
    DETECT_OFF(0x10, "detect_off", ""), // scan detect 检测程序发现404

    MANUAL_MIN(0x100, "min manual data", "人工运营最小值"), //
    MANUAL_MASK(0x300, "manual_mask", "manual_mask"), //
    // 人工运营 0x100 ~ 0xF00 第三个16进制位
    MANUAL_ADD(0x0100, "", "手动添加"), // 不知道的理由
    MANUAL_UPDATE(0x0200, "", "手动修改"), //
    MANUAL_HIDDEN_MASK(0x400, "", ""), //
    MANUAL_HIDDEN(0x0400, "", "手动隐藏"), //
    MANUAL_SHOW(0x0000, "", "手动恢复显示"), //

    AUDIT_MIN(0x1000, "min audit date", "人工审核最小值"), //
    AUDIT_MASK(0x3000, "audit_mask ", "audit_mask"), //
    // 人工审核 0x1000 ~ 0xF000 第四个16进制位
    AUDIT_NO(0x01000, "", "未审核"), //
    AUDIT_YES(0x02000, "", "审核"), //
    AUDIT_NO_NEED(0x03000, "", "免审核"), //
    ;
    private int val;
    private String name;
    private String description;

    AppStatus(int val) {

    }

    private AppStatus(int val, String name, String description) {
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

    public static AppStatus appStatus(int val) {
        switch (val) {
            case 0x1:
                return ADD;
            case 0x2:
                return UPDATE;
            case 0x3:
                return DELETE;
            case 0x4:
                return UNCHANGED;
            case 0x10:
                return DETECT_OFF;
            case 0x300:
                return MANUAL_HIDDEN;
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
}
