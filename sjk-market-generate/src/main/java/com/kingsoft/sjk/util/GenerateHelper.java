package com.kingsoft.sjk.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.Permissions;
import com.kingsoft.sjk.po.AppTags;
import com.kingsoft.sjk.po.Catalog;

public class GenerateHelper {
    private static final Logger logger = LoggerFactory.getLogger(GenerateHelper.class);

    public static final String templateDir = null;;

    public static void createParentDir(File f) {
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
    }

    // 添加一个大游戏列表List<BigGamePack>
    public static void generateAppDetailByTemplate(File file, App app, Template template, Catalog catalog,
            Map<Integer, Permissions> permissionsMap, List<AppTags> appTags, List<AppTags> AppTopics,
            List<BigGamePack> bigGameList) {
        Map<String, Object> context = new HashMap<String, Object>();

        context.put("App", app);
        context.put("Catalog", catalog);
        context.put("AppTags", appTags);
        context.put("AppTopics", AppTopics);
        context.put("AppTagNames", getTagAppName(appTags));
        context.put("AppTopicsNames", getTagAppName(AppTopics));
        context.put("GigGameList", bigGameList);
        String permissionsStr = null;
        StringBuilder sb = null;
        String arr[] = null;
        sb = new StringBuilder();
        // permissionsStr = app.getPermissions();
        // arr = permissionsStr.split(",");

        // for (String string : arr) {
        // if (StringUtils.isNotBlank(string) &&
        // NumberUtils.isNumber(string.trim()) && "0".equals(string)) {
        //
        // if(null!=permissionsMap.get(Integer.valueOf(string))){
        // sb.append(permissionsMap.get(Integer.valueOf(string)).getName() +
        // "<br/>");
        // }
        //
        // }
        // }
        // 加入 手机权限信息
        context.put("PermissionsDesc", sb.toString());
        // String AdActionTypes =
        // StringUtils.defaultIfBlank(app.getAdActionTypes(), "");
        // String AdPopupTypes =
        // StringUtils.defaultIfBlank(app.getAdPopupTypes(), "");
        // // 判断是否是恶意广告
        // if (AdActionTypes.contains("1") || AdActionTypes.contains("3") ||
        // AdPopupTypes.contains("1")) {
        // context.put("UnFriendAd", true);
        //
        // } else {
        // context.put("UnFriendAd", false);
        // }

        VelocityContext tmplContext = new VelocityContext(context);

        createParentDir(file);

        BufferedWriter writer = null;
        try {

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 以utf-8编码保存
            template.merge(tmplContext, writer);
            file.setLastModified(System.currentTimeMillis());
            // logger.info("generate pages..{}", file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            logger.error("exception:", e);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            logger.error("exception:", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }

    }

    private static String getTagAppName(List<AppTags> appTags) {
        StringBuffer sb = new StringBuffer("");
        if (appTags != null && appTags.size() > 0) {
            Map<String, String> mapTagNames = new HashMap<String, String>(appTags.size());
            for (AppTags appTag : appTags) {
                mapTagNames.put(appTag.getTagName(), appTag.getTagName());
            }
            for (String str : mapTagNames.values()) {
                sb.append(str);
                sb.append(",");
            }
        }
        if (sb.toString().length() > 0) {
            return sb.delete(sb.toString().length() - 1, sb.toString().length()).toString();
        } else {
            return "";
        }
    }

}
