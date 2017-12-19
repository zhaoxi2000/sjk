package com.ijinshan.sjk.web;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class TestTagAppController extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(TestTagAppController.class);

    @Test
    public void testEdit() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8080/sjk-market-appsvr/app/api/cdn/tagapp/tagtopic/0/9.json";
        URIBuilder urlb = new URIBuilder(url);
        // 参数
        urlb.setParameter("tabId", "0");
        urlb.setParameter("tagId", "9");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlb.build());
        HttpResponse response = httpClient.execute(httpGet);
        logger.debug("URL:{}\n{}\n{}", url, response.getStatusLine(), response.getEntity());
    }
}
