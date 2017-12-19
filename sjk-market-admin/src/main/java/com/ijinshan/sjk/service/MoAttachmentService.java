package com.ijinshan.sjk.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.MoFeatured;

public interface MoAttachmentService {

    /**
     * @param id
     * @param multipart
     * @return 保存后的路径
     * @throws IOException
     * @throws IllegalStateException
     */
    String saveMoTagFile(int id, MultipartFile file) throws IllegalStateException, IOException;

    /**
     * @param id
     * @param multipart
     * @return 保存后的路径
     * @throws IOException
     * @throws IllegalStateException
     */
    String saveMoMixFeatured(int id, MultipartFile file) throws IllegalStateException, IOException;

    void deleteFile(String url);

    /**
     * 广告图片.
     * 
     * @param Metro
     *            Win8色块广告
     * @param multipartReq
     * @throws URISyntaxException
     * @throws IOException
     */
    void saveOrUpdateMoFeaturedImageFile(MoFeatured moFeatured, MultipartHttpServletRequest multipartReq)
            throws URISyntaxException, IOException;

}
