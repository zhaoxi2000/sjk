package com.ijinshan.sjk.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.MoFeaturedDao;
import com.ijinshan.sjk.po.MoFeatured;
import com.ijinshan.sjk.service.MoAttachmentService;
import com.ijinshan.util.HttpSupport;
import com.ijinshan.util.IOUtils;
import com.ijinshan.util.PathUtils;

@Service
public class MoAttachmentServiceImpl implements MoAttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(MoAttachmentServiceImpl.class);
    public static final String dateTimeFormatter = "yyyyMMddHHmmssSSS";

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "moFeaturedDaoImpl")
    private MoFeaturedDao moFeaturedDao;

    @Override
    public String saveMoMixFeatured(int id, MultipartFile file) throws IllegalStateException, IOException {
        if (file == null) {
            throw new NullPointerException();
        }
        String filename = newDateTimeString();
        StringBuilder pathSb = new StringBuilder(24).append(id).append("/").append(filename);
        String extension = getExtensionFromContentType(file.getContentType());
        pathSb.append(".").append(extension);
        File dest = new File(PathUtils.concate(appConfig.getDestMomixFeaturedUploadDir(), pathSb.toString()));
        PathUtils.makeSureDir(dest.getParentFile());
        file.transferTo(dest);
        String path = PathUtils.concate(appConfig.getDestMomixFeaturedUploadBaseurl(), pathSb.toString());
        return path;
    }

    @Override
    public String saveMoTagFile(int id, MultipartFile file) throws IllegalStateException, IOException {
        if (file == null) {
            throw new NullPointerException();
        }
        String filename = newDateTimeString();
        StringBuilder pathSb = new StringBuilder(24).append(id).append("/").append(filename);
        String extension = getExtensionFromContentType(file.getContentType());
        pathSb.append(".").append(extension);
        File dest = new File(PathUtils.concate(appConfig.getDestMobileTagUploadDir(), pathSb.toString()));
        PathUtils.makeSureDir(dest.getParentFile());
        file.transferTo(dest);
        String path = PathUtils.concate(appConfig.getDestMobileTagUploadBaseurl(), pathSb.toString());
        return path;
    }

    public String saveMoFeaturedFile(int id, MultipartFile file) throws IllegalStateException, IOException {
        if (file == null) {
            throw new NullPointerException();
        }
        String filename = newDateTimeString();
        StringBuilder path = new StringBuilder(30).append(id).append("/").append(filename);
        String extension = getExtensionFromContentType(file.getContentType());
        path.append(".").append(extension);
        File dest = new File(PathUtils.concate(appConfig.getDestMobileFeaturedUploadDir(), path.toString()));
        PathUtils.makeSureDir(dest.getParentFile());
        file.transferTo(dest);
        return path.toString();
    }

    private String saveMoFeaturedFile(int id, String remoteUrl) throws IOException {
        StringBuilder path = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(remoteUrl);
            conn = (HttpURLConnection) url.openConnection();

            String filename = newDateTimeString();
            path = new StringBuilder(30).append(id).append("/").append(filename);
            String extension = null;
            // 先连接,后取contentType
            inputStream = HttpSupport.getMethod(conn);
            extension = getExtensionFromContentType(conn.getContentType());
            path.append(".").append(extension);
            File dest = new File(PathUtils.concate(appConfig.getDestMobileFeaturedUploadDir(), path.toString()));
            FileUtils.copyInputStreamToFile(inputStream, dest);
        } finally {
            IOUtils.closeQuietly(inputStream);
            conn.disconnect();
        }
        return path.toString();
    }

    public static String newDateTimeString() {
        try {
            // 如果同一个form保存多个截图,那会有并发生成图片文件名问题是相同的.
            Thread.sleep(1);
            logger.debug("sleeping 1 ms...");
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
        Date date = new Date();
        return new java.text.SimpleDateFormat(dateTimeFormatter).format(date);
    }

    private String getExtensionFromContentType(String contentType) {
        if (contentType == null) {
            return "";
        }
        if (contentType.contains("png")) {
            return "png";
        } else if (contentType.contains("jpeg") || contentType.contains("jpg")) {
            return "jpg";
        }
        return "";
    }

    @Override
    public void deleteFile(String url) {
        final String subPath = url.replace(appConfig.getDestUploadBaseurl(), "");
        String path = PathUtils.concate(appConfig.getDestUploadDir(), subPath);
        File file = new File(path);
        if (file.exists()) {
            Assert.isTrue(file.isFile(), "File must be a file");
            logger.info("Deleting... {}", url);
            file.delete();
        }
    }

    @Override
    public void saveOrUpdateMoFeaturedImageFile(MoFeatured moFeatured, MultipartHttpServletRequest multipartReq)
            throws URISyntaxException, IOException {
        String[] oldImageUrls = multipartReq.getParameter("strImageUrls").split(",");
        // 可能要删除的文件,我们的服务器.
        Set<String> deleteImageUrlsInlocalServer = new HashSet<String>();
        if (oldImageUrls != null) {
            for (String s : oldImageUrls) {
                if (s.contains(appConfig.getDestMobileFeaturedUploadBaseurl())) {
                    deleteImageUrlsInlocalServer.add(s.trim());
                }
            }
        }
        // now imageFiles and imageUrls
        String[] imageUrl = null;
        List<MultipartFile> imageFile = null;
        String[] imageUrl1 = multipartReq.getParameterValues("pics");
        List<MultipartFile> imageFile1 = multipartReq.getFiles("picsFile");// bigPicsFile
        String[] imageUrl2 = multipartReq.getParameterValues("bigPics");
        List<MultipartFile> imageFile2 = multipartReq.getFiles("bigPicsFile");
        if (moFeatured.getPicType() == 3) {
            imageUrl = imageUrl2;
            imageFile = imageFile2;
        } else {
            imageUrl = imageUrl1;
            imageFile = imageFile1;
        }
        // 它方的服务器图片.
        Set<String> remoteImageUrlSet = new HashSet<String>();
        if (imageUrl != null) {
            for (String s : imageUrl) {
                if (StringUtils.isNotBlank(s)) {
                    // 如果是本地服务器的.这个地址保留下来不要删除.
                    if (s.contains(appConfig.getDestMobileFeaturedUploadBaseurl())) {
                        deleteImageUrlsInlocalServer.remove(s);
                    } else {
                        remoteImageUrlSet.add(s);
                    }
                }
            }
        }
        // 预判断大小
        int nowImageCount = (imageUrl == null ? 0 : imageUrl.length) + (imageFile == null ? 0 : imageFile.size());
        List<String> nowImagePaths = new ArrayList<String>(nowImageCount);
        // 处理最后要保存的图片,添加到集合
        boolean needToUpdate = false;
        if (imageUrl != null) {
            for (String url : imageUrl) {
                if (StringUtils.isNotBlank(url)) {
                    if (!url.contains(appConfig.getDestMobileFeaturedUploadBaseurl())) {
                        String subPath = saveMoFeaturedFile(moFeatured.getId(), url);
                        String path = PathUtils.concate(appConfig.getDestMobileFeaturedUploadBaseurl(), subPath);
                        nowImagePaths.add(path);
                        // 新增URL.
                        needToUpdate = true;
                    } else {
                        nowImagePaths.add(url);
                    }
                }
            }
        }
        if (imageFile != null) {
            for (MultipartFile file : imageFile) {
                if (!file.isEmpty()) {
                    String subPath = saveMoFeaturedFile(moFeatured.getId(), file);
                    String path = PathUtils.concate(appConfig.getDestMobileFeaturedUploadBaseurl(), subPath);
                    nowImagePaths.add(path);
                    // 新上传文件.
                    needToUpdate = true;
                }
            }
        }

        // StrImageUrls 多图片 URL的逗号分隔拼接.所有要保存的图片.
        StringBuilder imagesToModel = new StringBuilder(100);
        if (nowImagePaths != null && !nowImagePaths.isEmpty()) {
            for (String s : nowImagePaths) {
                if (StringUtils.isNotEmpty(s) && StringUtils.isNotEmpty(s.trim())) {
                    imagesToModel.append(s).append(",");
                }
            }
        }
        if (imagesToModel.length() > 1) {
            imagesToModel.deleteCharAt(imagesToModel.length() - 1);
        }
        // if (!deleteImageUrlsInlocalServer.isEmpty()) {
        // needToUpdate = true;
        // }
        if (needToUpdate) {
            String pics = StringUtils.defaultIfEmpty(imagesToModel.toString(), "").replace(",,", "");
            if (moFeatured.getPicType() == 3) {
                moFeatured.setBigPics(StringUtils.stripEnd(StringUtils.stripStart(pics, ","), ","));
            } else {
                moFeatured.setPics(StringUtils.stripEnd(StringUtils.stripStart(pics, ","), ","));
            }
            moFeaturedDao.update(moFeatured);
        }
        // 删除最后执行.避开出错从而导致数据丢失问题.
        if (!deleteImageUrlsInlocalServer.isEmpty()) {
            for (String url : deleteImageUrlsInlocalServer) {
                deleteFile(url);
            }
        }
    }

}
