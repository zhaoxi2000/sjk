package com.ijinshan.sjk.web;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class MarketControllerTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MarketControllerTest.class);

    @Test
    public void testListMarkert() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8080/sjk-market-admin/market/list.json";
        URIBuilder urlb = new URIBuilder(url);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(urlb.build());
        HttpResponse response = httpClient.execute(httpget);
        logger.debug("URL:{}\n{}\n{}", url, response.getStatusLine(), response.getEntity());
    }

    @Test
    public void testBrokenLink() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8080/sjk-market-admin/market/brokenLink.d";
        URIBuilder urlb = new URIBuilder(url);
        String test = "";
        urlb.setParameter("c", test);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httPost = new HttpPost(urlb.build());
        HttpResponse response = httpClient.execute(httPost);
        logger.debug("URL:{}\n{}\n", url, response.getStatusLine());
    }

}
