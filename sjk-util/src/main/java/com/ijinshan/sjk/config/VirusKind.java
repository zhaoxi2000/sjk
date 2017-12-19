package com.ijinshan.sjk.config;

public enum VirusKind {
    // 1: gray
    // 2: white
    // 3: black
    // 4: unknown
    GRAY(1), //
    WHITE(2), //
    BLACK(3), //
    UNKNOWN(4), //
    ;
    private final int val;

    VirusKind(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

}
