package com.ijinshan.sjk.ntxservice;

import java.io.IOException;

import org.apache.lucene.search.ScoreDoc;

public interface QuickTipsService1 {
    ScoreDoc[] prefixSearch(String q) throws IOException;

    String[] quickTips(String q) throws IOException;

}
