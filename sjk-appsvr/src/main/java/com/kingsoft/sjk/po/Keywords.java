package com.kingsoft.sjk.po;

import java.io.Serializable;

public class Keywords implements Serializable {

    private static final long serialVersionUID = -5971897625925432474L;
    protected transient int id;
    protected String keyWords;
    protected String keyWordsPY;

    public Keywords() {
        super();
    }

    public Keywords(int id, String keyWords, String keyWordsPY) {
        super();
        this.id = id;
        this.keyWords = keyWords;
        this.keyWordsPY = keyWordsPY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getKeyWordsPY() {
        return keyWordsPY;
    }

    public void setKeyWordsPY(String keyWordsPY) {
        this.keyWordsPY = keyWordsPY;
    }

}
