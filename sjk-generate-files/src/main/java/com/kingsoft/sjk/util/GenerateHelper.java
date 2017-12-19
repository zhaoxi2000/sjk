package com.kingsoft.sjk.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.ScreenImage;

public class GenerateHelper {
    private static final Logger logger = LoggerFactory.getLogger(GenerateHelper.class);
    // 参数：nAppId,strAppName,strVersion,strIconUrl,strApkUrl, nApkSize
    private static String jsDownApktemp = "window.external.DownApk(%1d,'%2s','%3s','%4s','%5s',%6d)";
    // 参数：nAppId,strAppName,strVersion,strIconUrl,strApkUrl, strPackUrl,
    // nApkSize, nPackSize
    private static String jsDownGamePack = "window.external.DownGamePack(%1d,'%2s','%3s','%4s','%5s','%6s',%7d,%8d)";
    public static final String templateDir = null;;

    // static {
    // GenerateHelper.class.getClass().getClassLoader();
    // URL url = ClassLoader.getSystemResource(".");
    // File file = new File(url.getPath());
    // logger.debug("load template path,{}", file.getAbsolutePath());
    // templateDir = file.getAbsolutePath() + "\\template";
    // }

    // public static String getTemplatePath() {
    // URL url =
    // GenerateHelper.class.getClass().getClassLoader().getSystemResource(".");
    // File file = new File(url.getPath());
    // logger.debug("load template path,{}",file.getAbsolutePath() );
    // return file.getAbsolutePath() + "\\template";
    // }

    public static void createParentDir(File f) {
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
    }

    public static void generateAppDetailByTemplate(File file, App app, Template template) {
        Map<String, App> context = new HashMap<String, App>();
        String postDate = "";
        String editDate = "";
        String updateDate = "";
        StringBuffer sbImages = new StringBuffer("");
        if (app.getScreenImages() != null) {
            for (ScreenImage img : app.getScreenImages()) {
                sbImages.append(img.getImageUrl());
                sbImages.append(",");
            }
        }
        if (app.getPostDate() != null) {

            postDate = DateFormatUtils.format(app.getPostDate(), "yyyy-MM-dd");
        }
        if (app.getEditDate() != null) {
            editDate = DateFormatUtils.format(app.getEditDate(), "yyyy-MM-dd");
        }
        if (app.geteUpdateDate() != null) {
            updateDate = DateFormatUtils.format(app.geteUpdateDate(), "yyyy-MM-dd");
        }
        app.setPostDateStr(postDate);
        app.setEditDateStr(editDate);
        app.seteUpdateDateStr(updateDate);
        app.setImages(sbImages.toString());
        context.put("App", app);
        VelocityContext tmplContext = new VelocityContext(context);
        createParentDir(file);
        BufferedWriter writer = null;
        try {

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));// 以utf-8编码保存
            template.merge(tmplContext, writer);
        } catch (FileNotFoundException e) {
            logger.error("exception:", e);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /* 生成应用详情页面 */
    public static void generateAppDetail(File file, App app, com.kingsoft.sjk.util.Template template, AppConfig config) {
        String contentTemplate = null;
        contentTemplate = template.getDetailContent();

        contentTemplate = contentTemplate.replace("{@id@}", String.valueOf(app.getId()));
        contentTemplate = contentTemplate.replace("{@name@}", app.getName());
        contentTemplate = contentTemplate.replace("{@originalName@}", app.getOriginalName());
        contentTemplate = contentTemplate.replace("{@otherNames@}", app.getOtherNames());
        contentTemplate = contentTemplate.replace("{@publisherShortName@}", app.getPublisherShortName());
        contentTemplate = contentTemplate.replace("{@catalog@}", String.valueOf(app.getCatalog()));
        contentTemplate = contentTemplate.replace("{@subCatalog@}", String.valueOf(app.getSubCatalog()));
        if (app.getUpdateDate() != null) {
            contentTemplate = contentTemplate.replace("{@updateDate@}",
                    DateFormatUtils.format(app.getUpdateDate(), "yyyy-MM-dd") + "");
        }

        contentTemplate = contentTemplate.replace("{@version@}", app.getVersion());
        String fileSize = String.format("%.2fMB", (app.getSize() / 1024d / 1024d));
        if ((app.getOneKeyPackSize() - app.getSize()) > 0) {
            fileSize = String.format("%.2fMB", (app.getOneKeyPackSize() / 1024d / 1024d));
        }
        contentTemplate = contentTemplate.replace("{@size@}", fileSize);
        contentTemplate = contentTemplate.replace("{@language@}", app.getLanguage());
        contentTemplate = contentTemplate.replace("{@oSVersion@}", app.getOSVersion());
        contentTemplate = contentTemplate.replace("{@resolution@}", app.getResolution());
        contentTemplate = contentTemplate.replace("{@webBaseUrl@}", config.getWebCssOrJsBaseUrl());
        StringBuffer sbDesc = new StringBuffer();
        // 描述
        if (app.getDescription().length() > 180) {
            sbDesc.append("<div id=\"jianjie_desc\" class=\"desc\" style=\"display: block; \">");
            sbDesc.append(app.getDescription().substring(0, 180) + "...");
            sbDesc.append("<a style=\"color:#0066CC\" href=\"javascript:void(0)\" onclick=\"ShowMoreDesc(1,1)\">更多&gt;&gt;</a> </div>");
            sbDesc.append("<div id=\"jianjie_moredesc\" style=\"display: none; \">");

            sbDesc.append("    <div class=\"desc\"> ");
            sbDesc.append(app.getDescription());
            sbDesc.append("    </div>");
            sbDesc.append("    <div class=\"moreshouqi\">");
            sbDesc.append("    <a style=\"color:#0066CC\" href=\"javascript:void(0)\" onclick=\"ShowMoreDesc(-1,1)\">收起&gt;&gt;</a>");
            sbDesc.append("    </div>");
            sbDesc.append("</div>");

        } else {
            if (!StringUtils.isEmpty(app.getDescription())) {
                sbDesc.append("<div id=\"jianjie_desc\" class=\"desc\">");
                sbDesc.append(app.getDescription());
                sbDesc.append("</div>");
            }
        }

        contentTemplate = contentTemplate.replace("{@Description@}", sbDesc.toString());
        StringBuffer sbUpdate = new StringBuffer("");
        sbUpdate.append("<div class=\"gengxin\">");
        sbUpdate.append("" + app.getVersion() + "版更新信息");
        sbUpdate.append("</div>");
        // 更新信息
        if (app.getUpdateInfo().length() > 120) {

            sbUpdate.append("<div id=\"gengxin_desc\" class=\"desc\" style=\"display: block; \">");
            sbUpdate.append(app.getUpdateInfo().substring(0, 120) + "...");
            sbUpdate.append("    <a style=\"color:#0066CC\" href=\"javascript:void(0)\" onclick=\"ShowMoreDesc(1,2)\">更多&gt;&gt;</a> ");
            sbUpdate.append("</div> ");

            sbUpdate.append("<div id=\"gengxin_moredesc\" style=\"display: none; \">");
            sbUpdate.append("    <div class=\"desc\"> ");
            sbUpdate.append(app.getUpdateInfo());
            sbUpdate.append("    </div>");
            sbUpdate.append("    <div class=\"moreshouqi\">");
            sbUpdate.append("        <a style=\"color:#0066CC\" href=\"javascript:void(0)\" onclick=\"ShowMoreDesc(-1,2)\">收起&gt;&gt;</a>");
            sbUpdate.append("    </div>");
            sbUpdate.append("</div>");
        } else {
            if (!StringUtils.isEmpty(app.getUpdateInfo())) {
                sbUpdate.append("<div id=\"gengxin_desc\" class=\"desc\">");
                sbUpdate.append(app.getUpdateInfo());
                sbUpdate.append("</div>");
            } else {
                sbUpdate.delete(0, sbUpdate.length());
            }
        }
        contentTemplate = contentTemplate.replace("{@UpdateInfo@}", sbUpdate.toString());

        contentTemplate = contentTemplate.replace("{@associateIDs@}", app.getAssociateIDs());
        contentTemplate = contentTemplate.replace("{@rank@}", String.valueOf(app.getRank()));
        contentTemplate = contentTemplate.replace("{@logoUrl@}", app.getLogoUrl());
        if (app.getPostDate() != null) {
            contentTemplate = contentTemplate.replace("{@postDate@}",
                    DateFormatUtils.format(app.getPostDate(), "yyyy-MM-dd") + "");
        }
        if (app.getEditDate() != null) {
            contentTemplate = contentTemplate.replace("{@editDate@}",
                    DateFormatUtils.format(app.getEditDate(), "yyyy-MM-dd") + "");
        }
        contentTemplate = contentTemplate.replace("{@downloadUrl@}", app.getDownloadUrl());
        contentTemplate = contentTemplate.replace("{@permit@}", app.getPermit());
        // contentTemplate = contentTemplate.replace("{@downloadUrl@}",
        // app.getDownloadUrl());
        if (app.geteUpdateDate() != null) {
            contentTemplate = contentTemplate.replace("{@eUpdateDate@}",
                    DateFormatUtils.format(app.geteUpdateDate(), "yyyy-MM-dd") + "");
        }
        contentTemplate = contentTemplate.replace("{@isGray@}", String.valueOf(app.getIsGray()));
        contentTemplate = contentTemplate.replace("{@oSVersionGroupName@}", app.getOSVersionGroupName());

        StringBuffer sbTabImg = new StringBuffer();
        StringBuffer sbTabNub = new StringBuffer();
        if (app.getScreenImages() != null) {
            ScreenImage img = null;
            int count = app.getScreenImages().size();
            contentTemplate = contentTemplate.replace("{@imageCount@}", String.valueOf(count));
            for (int i = 1; i <= count; i++) {
                img = app.getScreenImages().get(i - 1);
                sbTabImg.append("<div class=\"Tab_con_c104_2\" id=\"con_two_" + i + "\" style=\"display:"
                        + (i == 1 ? "block " : "none") + ";\">");
                sbTabImg.append("      <img src=\"");
                sbTabImg.append(img.getImageUrl());
                sbTabImg.append("\" />");
                sbTabImg.append("</div>");

                sbTabNub.append("<span>");
                sbTabNub.append("    <a href=\"javascript:sejietu('two'," + i + "," + count + ")\">");
                if (i == 0) {
                    sbTabNub.append("        <img id=\"two" + i + "\" src=\"../../images/tab01_ico01.jpg\">");
                } else {
                    sbTabNub.append("        <img id=\"two" + i + "\" src=\"../../images/tab01_ico02.jpg\">");
                }
                sbTabNub.append("    </a>");
                sbTabNub.append("</span>");
            }
        }
        contentTemplate = contentTemplate.replace("{@tabimgsHtml@}", sbTabImg.toString());
        contentTemplate = contentTemplate.replace("{@tabnumHtml@}", sbTabNub.toString());

        String jsDown = String.format(jsDownApktemp, app.getId(), app.getName(), app.getVersion(), app.getLogoUrl(),
                app.getDownloadUrl(), app.getSize());
        int haveData = app.getHaveData();
        if (app.getHaveData() == 1) {
            if (!StringUtils.isEmpty(app.getOneKeyPackUrl())) {
                jsDown = String.format(jsDownGamePack, app.getId(), app.getName(), app.getVersion(), app.getLogoUrl(),
                        app.getDownloadUrl(), app.getOneKeyPackUrl(), app.getSize(), app.getOneKeyPackSize());
            } else {
                haveData = 0;
            }
        }

        contentTemplate = contentTemplate.replace("{@haveData@}", String.valueOf(haveData));
        // if (app.getHaveData() == 1) {
        // // 游戏
        // if (app.getExtendDataList() != null) {
        // if (app.getExtendDataList().size() == 1) {
        // ExtendData data = app.getExtendDataList().get(0);
        // if (data != null) {
        // jsDown = "window.external.DownApkAndData('" + app.getLogoUrl() +
        // "','" + app.getName() + "', '"
        // + app.getDownloadUrl() + "','" + data.getDownloadUrl() + "', '"
        // + StringUtils.defaultIfEmpty(data.getSDPath(), "") + "')";
        // }
        // }
        // }
        // }
        contentTemplate = contentTemplate.replace("{@jsdownfunction@}", jsDown);
        logger.debug("begin write file ...");
        try {
            FileUtils.writeStringToFile(file, contentTemplate, "UTF-8");
        } catch (IOException e) {
            logger.error("generate file error:", e);
        }
    }
}
