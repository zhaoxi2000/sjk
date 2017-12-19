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

public class CatalogConvertorControllerTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CatalogConvertorControllerTest.class);

    @Test
    public void testEdit() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8080/sjk-market-admin/admin/catalogconvertor/edit.json";
        URIBuilder urlb = new URIBuilder(url);

        // 参数
        urlb.setParameter("id", "1");
        urlb.setParameter("marketName", "eoemarket");
        urlb.setParameter("catalog", "1");
        urlb.setParameter("subCatalog", "15");
        urlb.setParameter("subCatalogName", "系统工具1");
        urlb.setParameter("targetCatalog", "1");
        urlb.setParameter("targetSubCatalog", "14");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlb.build());
        HttpResponse response = httpClient.execute(httpPost);
        logger.debug("URL:{}\n{}\n{}", url, response.getStatusLine(), response.getEntity());
    }

    @Test
    public void testEditList() throws URISyntaxException, ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8080/sjk-market-admin/admin/catalogconvertor/edit.list.d";
        URIBuilder urlb = new URIBuilder(url);

        // 参数
        urlb.setParameter("id", "1,2");
        urlb.setParameter("marketName", "eoemarket,eoemarket");
        urlb.setParameter("catalog", "1,1");
        urlb.setParameter("subCatalog", "15,8");
        urlb.setParameter("subCatalogName", "系统工具,生活娱乐1");
        urlb.setParameter("targetCatalog", "1,1");
        urlb.setParameter("targetSubCatalog", "14,9");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlb.build());
        HttpResponse response = httpClient.execute(httpPost);
        logger.debug("URL:{}\n{}\n{}", url, response.getStatusLine(), response.getEntity());
    }

}
