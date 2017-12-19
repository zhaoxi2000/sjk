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
    public void testMain(){
       
        
//        System.out.println((int)'A'  + "=="+(char)65);
        
        
//        HashMap<String,List<String>> map=new HashMap<String, List<String>>();
//        List<String> list = new ArrayList<String>();
//        list.add("test");
//        list.add("test2");
//        map.put("A", list);
//        map.get("A").add("fff");
//        System.out.println(map.toString() + "==" + map.get("A"));
//        HashMap<String,String> map=new HashMap<String, String>();
//        map.put("B","11");
//        map.put("A", "22");
//        map.put("D", "33");
//        for (Entry<String,String> entry: map.entrySet()) {
//         System.out.println("排序之前:"+entry.getKey()+" 值"+entry.getValue());
//
//        }
//        System.out.println("======================================================");
//        SortedMap<String,String> sort=new TreeMap<String,String>(map);
//        Set<Entry<String,String>> entry1=sort.entrySet();
//        Iterator<Entry<String,String>> it=entry1.iterator();
//
//        while(it.hasNext())
//        {
//         Entry<String,String> entry=it.next();
//         System.out.println("排序之后:"+entry.getKey()+" 值"+entry.getValue());
//        }
    }

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
