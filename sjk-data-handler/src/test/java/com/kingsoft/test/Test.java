package com.kingsoft.test;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingsoft.sjk.util.HttpSupport;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // String dateStr = DateFormatUtils.format(new Date(),
        // "yyyyMMddHHmmssS");
        // System.out.println(dateStr);
        //
        // String url =
        // "http://img03.muzhiwan.com/2011/10/30/4eacb858991e7_124.png";
        // String filename = url.substring(url.lastIndexOf("/") + 1);
        // filename = filename.substring(filename.lastIndexOf("."));
        //
        // System.out.println(filename);

        // downloadImg();
        String str = "http://img03.muzhiwan.com/2011/12/25/com.ea.game.realracing2_row/com.ea.game.realracing2_row4ef68758a9e34_sp.jpg   http://img03.muzhiwan.com/2011/12/25/com.ea.game.realracing2_row/com.ea.game.realracing2_row4ef68758a9e34_sp.jpg";
        parseUrl(str);

    }

    public static String parseUrl(String str) {
        String[] arr = str.split("http://");
        System.out.println(arr.length);
        String url = null;
        for (String s : arr) {
            if (StringUtils.isNotEmpty(s)) {
                url = s;
                break;
            }
        }
        return url;
    }

    public static void downloadImg() {
        URL url = null;
        HttpURLConnection conn = null;

        String logo = "http://img02.muzhiwan.com/2011/12/13/4ee6dbffd1d83_124.jpg";

        String store = "/data/apps/static-web/sjk/market/img/";

        File img = new File(store + "a.jpg");
        InputStream input = null;
        try {
            url = new URL(logo);
            conn = (HttpURLConnection) url.openConnection();
            input = HttpSupport.getMethod(conn);
            FileUtils.writeByteArrayToFile(img, IOUtils.toByteArray(input));
        } catch (Exception e) {
            logger.error("exception:", e);
        } finally {
            IOUtils.closeQuietly(input);
            conn.disconnect();
        }

        logger.debug("path:{}", img.getAbsoluteFile());
    }
}
