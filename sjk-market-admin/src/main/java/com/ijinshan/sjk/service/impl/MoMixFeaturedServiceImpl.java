package com.ijinshan.sjk.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.MoMixFeaturedDao;
import com.ijinshan.sjk.po.MoMixFeatured;
import com.ijinshan.sjk.service.MoAttachmentService;
import com.ijinshan.sjk.service.MoMixFeaturedService;

@Service
public class MoMixFeaturedServiceImpl implements MoMixFeaturedService {
    private static final Logger logger = LoggerFactory.getLogger(MoMixFeaturedServiceImpl.class);

    @Autowired
    private MoMixFeaturedDao dao;

    @Autowired
    private AppConfig config;

    @Resource(name = "moAttachmentServiceImpl")
    private MoAttachmentService attachmentService;

    @Override
    public List<MoMixFeatured> search(String type, Boolean hidden) {
        return dao.search(type, hidden);
    }

    @Override
    public long count(String type) {
        return dao.count();
    }

    @Override
    public long countForSearching(Short type, Short typePic, Boolean hidden, String keywords) {
        return dao.countForSearching(type, typePic, hidden, keywords);
    }

    @Override
    public List<MoMixFeatured> search(Short type, Short picType, Boolean hidden, String keywords, int page, int rows,
            String sort, String order) {
        return dao.search(type, picType, hidden, keywords, page, rows, sort, order);
    }

    @Override
    public boolean delete(int appId) {
        return dao.delete(appId) > 0;
    }

    @Override
    public boolean updateHide(List<Integer> ids) {
        return dao.updateHide(ids) == ids.size();
    }

    @Override
    public boolean updateShow(List<Integer> ids) {
        return dao.updateShow(ids) == ids.size();
    }

    @Override
    public boolean updateSort(Integer[] ids, Integer[] ranks) {
        for (int i = 0; i < ids.length; i++) {
            dao.updateSort(ids[i], ranks[i]);
        }
        return true;
    }

    @Override
    public void saveOrUpdate(MultipartHttpServletRequest multipartReq, MoMixFeatured entity) {
        entity.setBigPics(StringUtils.isEmpty(entity.getBigPics()) == true ? "" : entity.getBigPics());
        entity.setMediumPics(StringUtils.isEmpty(entity.getMediumPics()) == true ? "" : entity.getMediumPics());
        entity.setSmallPics(StringUtils.isEmpty(entity.getSmallPics()) == true ? "" : entity.getSmallPics());
        entity.setOpTime(new Date());
        if (entity.getId() > 0) {
            dao.update(entity);
        } else {
            dao.save(entity);
        }
        updateBigPicsUrl(multipartReq, entity);
        updateMediumPicsUrl(multipartReq, entity);
        updateSmallPicsUrl(multipartReq, entity);
    }

    private void updateBigPicsUrl(MultipartHttpServletRequest multipartReq, MoMixFeatured entity) {
        MultipartFile file = multipartReq.getFile("fileBigPicsUrl");
        String oldImgUrl = multipartReq.getParameter("oldBigPicsUrl");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveMoMixFeatured(entity.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (config.isDeleteUploadImageFile() && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(config.getDestMomixFeaturedUploadBaseurl())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        entity.setBigPics(subPath);
        dao.update(entity);
    }

    private void updateMediumPicsUrl(MultipartHttpServletRequest multipartReq, MoMixFeatured entity) {
        MultipartFile file = multipartReq.getFile("fileMediumPicsUrl");
        String oldImgUrl = multipartReq.getParameter("oldMediumPicsUrl");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveMoMixFeatured(entity.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (config.isDeleteUploadImageFile() && config.isDeleteUploadImageFile()
                    && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(config.getDestMomixFeaturedUploadBaseurl())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        entity.setMediumPics(subPath);
        dao.update(entity);
    }

    private void updateSmallPicsUrl(MultipartHttpServletRequest multipartReq, MoMixFeatured entity) {
        MultipartFile file = multipartReq.getFile("fileSmallPicsUrl");
        String oldImgUrl = multipartReq.getParameter("oldSmallPicsUrl");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveMoMixFeatured(entity.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (config.isDeleteUploadImageFile() && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(config.getDestMomixFeaturedUploadBaseurl())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        entity.setSmallPics(subPath);
        dao.update(entity);
    }
}
