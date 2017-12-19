package com.kingsoft.sjk.game.service;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.App;
import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.game.dao.impl.AppDaoImpl;
import com.kingsoft.sjk.game.vo.GameInfo;
import com.kingsoft.sjk.util.HttpSupport;

@Repository
public class AppDaoService {
    private static final Logger logger = LoggerFactory.getLogger(AppDaoService.class);

    @Resource(name = "appconfig")
    private AppConfig appconfig;

    @Resource(name = "appDaoImpl")
    private AppDaoImpl appDaoImpl;

    private static final Integer pid = 100;

    private final static Map<String, Integer> map;
    static {
        map = new HashMap<String, Integer>();

        map.put("大游戏", pid);
        map.put("赛车游戏", 101);
        map.put("RPG角色扮演", 102);
        map.put("FPS第一人称射击游", 103);
        map.put("FPS第一人称射击", 103);
        map.put("体育竞技", 104);
        map.put("竞技体育", 104);
        map.put("其他", 105);

    }

    public Integer saveAppAndGenerateInfo(GameInfo game) {

        App app = new App();
        app.setName(game.getGameName());
        app.setCatalog((short) 100);
        app.setSubCatalog(map.get(game.getCatalogName().trim()));
        app.setMarketName("ijinshan");
        app.setMarketAppId(0);

        app.setAuditCatalog(false);
        app.setDescription(game.getGameDesc());
        app.setDetailUrl("");

        app.setDownloadUrl("");
        app.setEnumStatus("update");
        app.setHidden(false);
        app.setNotice(game.getGameNote());

        app.setLanguage(game.getLanguage());
        app.setOfficeHomepage(game.getGameWebPage());

        app.setKeywords("");
        app.setLastUpdateTime(new Date());
        app.setLogoUrl(null);
        app.setMinsdkversion((short) 0);
        app.setModels("");
        app.setOsversion(game.getAndroidVersion());
        app.setPageUrl("");

        app.setPkname(System.currentTimeMillis() + "");
        app.setPrice(0);
        app.setPublisherShortName("");
        app.setScreens("");
        app.setShortDesc("");
        app.setStrImageUrls("");

        app.setSize(0);
        app.setStarRating(0);
        app.setStrImageUrls("");
        app.setSupportpad((short) 0);
        app.setUpdateInfo("");
        app.setVersion(game.getGameVersion());
        app.setVersionCode(0);
        app.setViewCount(0);
        app.setLogoUrl("");
        app.setIndexImgUrl("");

        Integer pkId = appDaoImpl.save(app);
        // 得到 id后，开始处理图片，处理截图
        // 下载 截图
        File logo = downloadImg(game.getLogo(), pkId);

        if (logo != null && logo.exists()) {

            // 开始设置 logo
            app.setLogoUrl(String.format(appconfig.getAppImgBaseurl(), pkId, logo.getName()));
        }
        // 开始下载截图
        File screen = downloadImg(game.getSceern(), pkId);
        if (screen != null && screen.exists()) {
            app.setIndexImgUrl(String.format(appconfig.getAppImgBaseurl(), pkId, screen.getName()));
        }

        appDaoImpl.update(app);

        // 生成 结果信息
        return pkId;
    }

    public File downloadImg(String urlStr, Integer pkId) {
        if (StringUtils.isEmpty(urlStr)) {
            return null;
        }
        urlStr = urlStr.trim();
        urlStr = parseUrl(urlStr);

        URL url = null;
        HttpURLConnection conn = null;

        logger.debug("urlStr:{}", urlStr);

        String filename = urlStr.substring(urlStr.lastIndexOf("/") + 1);
        filename = filename.substring(filename.lastIndexOf("."));

        String dateStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS");
        File img = new File(String.format(appconfig.getAppImgStorePath(), pkId, dateStr + filename));
        InputStream input = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(36000);
            conn.setReadTimeout(36000);

            input = HttpSupport.getMethod(conn);
            FileUtils.writeByteArrayToFile(img, IOUtils.toByteArray(input));

        } catch (Exception e) {
            logger.error("exception:", e);
            return null;
        } finally {
            IOUtils.closeQuietly(input);
            conn.disconnect();
        }
        return img;
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
        return "http://" + url;
    }
}
