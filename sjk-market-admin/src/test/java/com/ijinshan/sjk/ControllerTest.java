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

        String test = "eyJjb3VudCI6IDEwLCAibWFya2V0TmFtZSI6ICJBcHBDaGluYSIsICJkYXRhIjogW3sibGluayI6ICJodHRwOi8vd3d3LmFwcGNoaW5hLmNvbS9hcHAvY29tLmdvb2dsZS5hbmRyb2lkLmFwcHMubWFwcyIsICJpZCI6IDEsICJzdGF0dXNDb2RlIjogNDA0fSwgeyJsaW5rIjogImh0dHA6Ly93d3cuYXBwY2hpbmEuY29tL2FwcC9jb20ud2VhdGhlci5XZWF0aGVyIiwgImlkIjogMiwgInN0YXR1c0NvZGUiOiA0MDR9LCB7ImxpbmsiOiAiaHR0cDovL3d3dy5hcHBjaGluYS5jb20vYXBwL2NvbS5zdHlsZW0ud2FsbHBhcGVycyIsICJpZCI6IDQsICJzdGF0dXNDb2RlIjogNDA0fSwgeyJsaW5rIjogImh0dHA6Ly93d3cuYXBwY2hpbmEuY29tL2FwcC9jb20uc2hhemFtLmVuY29yZS5hbmRyb2lkIiwgImlkIjogNSwgInN0YXR1c0NvZGUiOiA0MDR9LCB7ImxpbmsiOiAiaHR0cDovL3d3dy5hcHBjaGluYS5jb20vYXBwL2NvbS5yaW5nZHJvaWQiLCAiaWQiOiA2LCAic3RhdHVzQ29kZSI6IDQwNH0sIHsibGluayI6ICJodHRwOi8vd3d3LmFwcGNoaW5hLmNvbS9hcHAvY29tLnAxLmNob21wc21zIiwgImlkIjogNywgInN0YXR1c0NvZGUiOiA0MDR9LCB7ImxpbmsiOiAiaHR0cDovL3d3dy5hcHBjaGluYS5jb20vYXBwL2NvbS5oYW5kY2VudC5uZXh0c21zIiwgImlkIjogOCwgInN0YXR1c0NvZGUiOiA0MDR9LCB7ImxpbmsiOiAiaHR0cDovL3d3dy5hcHBjaGluYS5jb20vYXBwL2NvbS5mYWNlYm9vay5rYXRhbmEiLCAiaWQiOiA5LCAic3RhdHVzQ29kZSI6IDQwNH0sIHsibGluayI6ICJodHRwOi8vd3d3LmFwcGNoaW5hLmNvbS9hcHAvY29tLmNvZGUuaS5tdXNpYyIsICJpZCI6IDEwLCAic3RhdHVzQ29kZSI6IDQwNH0sIHsibGluayI6ICJodHRwOi8vd3d3LmFwcGNoaW5hLmNvbS9hcHAvY29tLmJpZ2d1LnNob3BzYXZ2eSIsICJpZCI6IDExLCAic3RhdHVzQ29kZSI6IDQwNH1dLCAia2V5IjogImpqRzhMa0MzTUh5RjlYY3NWS2g2Rkh4bXRMQ05ZdE14In0=";
        Reader input = new StringReader(object.toJSONString());
        byte[] binaryData = IOUtils.toByteArray(input, "UTF-8");
        String encodeBase64 = Base64.encodeBase64String(binaryData);
        System.out.println(encodeBase64);

        String url = "http://localhost:9080/sjk-market-admin/market/brokenLink.d";
        url = "http://app.sjk.ijinshan.com/market/brokenLink.d";
        URIBuilder builder = new URIBuilder(url);
        builder.setParameter("c", test);
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
