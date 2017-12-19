package com.ijinshan.sjk.web;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class MarketAppControllerTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MarketAppControllerTest.class);

    @Test
    public void testEdit() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8080/sjk-market-admin/admin/ijinshan/shift-to-ijinshan.json";
        URIBuilder urlb = new URIBuilder(url);
        // 318840 378460 hiapk 雨夜壁纸桌面主题
        // 318839 378435 hiapk APO极限滑板

        urlb.setParameter("ids", "318840,318839");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlb.build());
        HttpResponse response = httpClient.execute(httpPost);
        logger.debug("URL:{}\n{}\n{}", url, response.getStatusLine(), response.getEntity());
    }

    public MarketAppControllerTest() {
    }
}
