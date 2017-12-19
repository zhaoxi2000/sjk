package com.ijinshan.sjk;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);

    @Test
    public void testbrokenLink() throws IOException, URISyntaxException {

        JSONObject object = new JSONObject();
        object.put("key", "sprSCKKWf8xUeXxEo6Bv0lE1sSjWRDkO");
        object.put("marketName", "eoemarket");
        object.put("count", 1);
        JSONArray data = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("id", -1);
        o.put("link", "http://testsssssss");
        o.put("statusCode", 404);
        data.add(o);
        object.put("data", data);

        Reader input = new StringReader(object.toJSONString());
        byte[] binaryData = IOUtils.toByteArray(input, "UTF-8");
        String encodeBase64 = Base64.encodeBase64String(binaryData);

        String url = "http://localhost:8080/sjk-market/market/brokenLink.d";
        url = "http://app-t.sjk.ijinshan.com/market/brokenLink.d";
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("c", encodeBase64);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(builder.build());
        HttpResponse response = httpclient.execute(httpPost);
        logger.debug("URI: {} , {}", url, response.getStatusLine());

        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        // be convinient to debug
        String rspJSON = IOUtils.toString(is, "UTF-8");
        System.out.println(rspJSON);
    }
}
