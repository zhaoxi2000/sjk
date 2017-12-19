package com.kingsoft.sjk.util;

public class SoftTrieTree extends DATrieTree {

    static class SoftTrieTreeHolder {
        static SoftTrieTree instance = new SoftTrieTree();
    }

    public static SoftTrieTree getInstance() {
        return SoftTrieTreeHolder.instance;
    }
}
