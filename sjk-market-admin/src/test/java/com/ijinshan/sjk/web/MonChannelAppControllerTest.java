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

public class MonChannelAppControllerTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MonChannelAppControllerTest.class);

    @Test
    public void testSaveAndUpdate() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8080/sjk-market-admin/market/admni/sava.json";
        URIBuilder urlb = new URIBuilder(url);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost();

        // HttpGet httpget = new HttpGet(urlb.build());
        HttpResponse response = httpClient.execute(httpPost);
        logger.debug("URL:{}\n{}\n{}", url, response.getStatusLine(), response.getEntity());

    }
}
