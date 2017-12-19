package com.kingsoft.sjk.util;

public class GameTrieTree extends DATrieTree {

    static class GameTrieTreeHolder {
        static GameTrieTree instance = new GameTrieTree();
    }

    public static GameTrieTree getInstance() {
        return GameTrieTreeHolder.instance;
    }

}
