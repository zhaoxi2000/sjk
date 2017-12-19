package com.ijinshan.sjk.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.MoFeaturedDao;
import com.ijinshan.sjk.po.MoFeatured;
import com.ijinshan.sjk.service.MoAttachmentService;
import com.ijinshan.sjk.service.MoFeaturedService;

@Service
public class MoFeaturedServiceImpl implements MoFeaturedService {
    private static final Logger logger = LoggerFactory.getLogger(MoFeaturedServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "moAttachmentServiceImpl")
    private MoAttachmentService attachmentService;

    @Resource(name = "moFeaturedDaoImpl")
    private MoFeaturedDao moFeaturedDao;

    @Override
    public void saveOrUpdate(MultipartHttpServletRequest multipartReq, MoFeatured moFeatured)
            throws MalformedURLException, URISyntaxException, IOException {
        moFeatured.setOpTime(new Date());
        if (moFeatured.getId() > 0) {
            moFeaturedDao.update(moFeatured);
        } else {
            Integer id = (Integer) moFeaturedDao.save(moFeatured);
            moFeatured.setId(id);
        }
        attachmentService.saveOrUpdateMoFeaturedImageFile(moFeatured, multipartReq);
    }

    @Override
    public List<MoFeatured> search(String type, Boolean hidden, Boolean deleted) {
        return moFeaturedDao.search(type, hidden, deleted);
    }

    @Override
    public boolean delete(int id, String type) throws UnsupportedOperationException {
        return moFeaturedDao.delete(id) == 1;
    }

    @Override
    public boolean updateHide(List<Integer> ids, String type) {
        return moFeaturedDao.updateHide(ids) == ids.size();
    }

    @Override
    public boolean updateShow(List<Integer> ids, String type) {
        return moFeaturedDao.updateShow(ids) == ids.size();
    }

    @Override
    public boolean updateDeleted(List<Integer> ids, String type, boolean deleted) {
        return moFeaturedDao.updateDeleted(ids, deleted);
    }

    @Override
    public long countForSearching(Short type, Boolean hidden, Boolean deleted, String keywords) {
        return moFeaturedDao.countForSearching(type, hidden, deleted, keywords);
    }

    @Override
    public List<MoFeatured> search(Short type, Boolean hidden, Boolean deleted, String keywords, int page, int rows,
            String sort, String order) {
        return moFeaturedDao.search(type, hidden, deleted, keywords, page, rows, sort, order);
    }

    @Override
    public boolean updateSort(Integer[] ids, Integer[] ranks) {
        boolean result = true;
        Integer i = 0;
        do {
            result = moFeaturedDao.updateSort(ids[i], ranks[i]);
            i++;
        } while (i < ids.length && result);
        return result;
    }

}
