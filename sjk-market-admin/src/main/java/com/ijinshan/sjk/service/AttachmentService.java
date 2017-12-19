package com.ijinshan.sjk.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.po.Metro;

public interface AttachmentService {

    /**
     * @param id
     * @param multipart
     * @return 保存后的路径
     * @throws IOException
     * @throws IllegalStateException
     */
    String saveFile(int id, MultipartFile multipart) throws IllegalStateException, IOException;

    /**
     * @param id
     * @param remoteUrl
     * @return 保存后的路径. 如果异常,无法下载.
     * @throws MalformedURLException
     * @throws IOException
     * @throws URISyntaxException
     */
    String saveFile(int id, String remoteUrl) throws IOException;

    /**
     * 保存MarketApp
     * 
     * @param id
     * @param multipart
     * @return 保存后的路径
     * @throws IOException
     * @throws IllegalStateException
     */
    String saveMarketAppFile(int id, MultipartFile file) throws IllegalStateException, IOException;

    /**
     * 保存MarketApp
     * 
     * @param id
     * @param remoteUrl
     * @return 保存后的路径. 如果异常,无法下载.
     * @throws MalformedURLException
     * @throws IOException
     * @throws URISyntaxException
     */
    String saveMarketAppFile(int id, String remoteUrl) throws IOException;

    /**
     * @param id
     * @param multipart
     * @return 保存后的路径
     * @throws IOException
     * @throws IllegalStateException
     */
    String saveTagFile(int id, MultipartFile file) throws IllegalStateException, IOException;

    void deleteFile(String url);

    /**
     * 处理截图.
     * 
     * @param app
     * @param multipartReq
     * @throws URISyntaxException
     * @throws IOException
     */
    void saveOrUpdateImageFile(App app, MultipartHttpServletRequest multipartReq) throws URISyntaxException,
            IOException;

    /**
     * 广告图片.
     * 
     * @param Metro
     *            Win8色块广告
     * @param multipartReq
     * @throws URISyntaxException
     * @throws IOException
     */
    void saveOrUpdateMetroImageFile(Metro metro, MultipartHttpServletRequest multipartReq) throws URISyntaxException,
            IOException;

    /**
     * ijinshan 广告图片.
     * 
     * @param Metro
     *            Win8色块广告
     * @param multipartReq
     * @throws URISyntaxException
     * @throws IOException
     */
    void saveOrUpdateImageFile(MarketApp app, MultipartHttpServletRequest multipartReq) throws URISyntaxException,
            IOException;
}
