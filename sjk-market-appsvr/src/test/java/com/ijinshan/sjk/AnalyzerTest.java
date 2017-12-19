package com.ijinshan.sjk;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AnalyzerTest {
    private static final Logger logger = LoggerFactory.getLogger(AnalyzerTest.class);

    Analyzer analyzer = new IKAnalyzer(true);

    @Test
    public void test() throws IOException {
        TokenStream ts = analyzer.tokenStream("field", new StringReader("大大大战争"));
        while (ts.incrementToken()) {
            System.out.println("token : " + ts);
        }
    }
}
