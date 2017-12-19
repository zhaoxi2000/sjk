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
import com.ijinshan.sjk.dao.MetroDao;
import com.ijinshan.sjk.po.Metro;
import com.ijinshan.sjk.service.AttachmentService;
import com.ijinshan.sjk.service.MetroService;

@Service
public class MetroServiceImpl implements MetroService {
    private static final Logger logger = LoggerFactory.getLogger(MetroServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "attachmentServiceImpl")
    private AttachmentService attachmentService;

    @Resource(name = "metroDaoImpl")
    private MetroDao metroDao;

    @Override
    public void saveOrUpdate(MultipartHttpServletRequest multipartReq, Metro metro) throws MalformedURLException,
            URISyntaxException, IOException {
        metro.setOpTime(new Date());
        if (metro.getId() > 0) {
            metroDao.update(metro);
        } else {
            Integer id = (Integer) metroDao.save(metro);
            metro.setId(id);
        }
        attachmentService.saveOrUpdateMetroImageFile(metro, multipartReq);
    }

    @Override
    public List<Metro> search(String type, Boolean hidden, Boolean deleted) {
        return metroDao.search(type, hidden, deleted);
    }

    @Override
    public boolean delete(int id, String type) throws UnsupportedOperationException {
        long redundant = metroDao.count(type);
        boolean allowDelete = false;
        Metro metro = metroDao.get(id);
        if ("1".equals(type)) {
            allowDelete = checkMetro(redundant, allowDelete, metro, 1);
        }
        if ("2".equals(type)) {
            allowDelete = checkMetro(redundant, allowDelete, metro, 4);
        }
        if ("3".equals(type)) {
            allowDelete = checkMetro(redundant, allowDelete, metro, 6);
        }
        if (allowDelete) {
            return metroDao.delete(id) == 1;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean checkMetro(long redundant, boolean allowDelete, Metro metro, int num) {
        if (redundant == num && metro.isHidden()) {
            allowDelete = true;
        } else if (redundant > num) {
            allowDelete = true;
        }
        return allowDelete;
    }

    @Override
    public boolean updateHide(List<Integer> ids, String type) {
        long redundant = metroDao.count(type);
        boolean allow = false;
        long count = redundant - ids.size();
        if ("1".equals(type) && count >= 1) {
            allow = true;
        }
        if ("2".equals(type) && count >= 4) {
            allow = true;
        }
        if ("3".equals(type) && count >= 6) {
            allow = true;
        }
        if (allow) {
            return metroDao.updateHide(ids) == ids.size();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean allowOperate(String type, List<Integer> ids) {
        long redundant = metroDao.count(type);
        boolean allow = false;
        long count = redundant - ids.size();
        if ("1".equals(type) && count > 1) {
            allow = true;
        }
        if ("2".equals(type) && count > 4) {
            allow = true;
        }
        if ("3".equals(type) && count > 6) {
            allow = true;
        }
        return allow;
    }

    @Override
    public boolean updateShow(List<Integer> ids, String type) {
        return metroDao.updateShow(ids) == ids.size();
    }

    @Override
    public boolean updateDeleted(List<Integer> ids, String type, boolean deleted) {
        long redundant = metroDao.count(type);
        boolean allowDelete = false;
        long count = redundant - ids.size();
        if ("1".equals(type) && count >= 1) {
            allowDelete = true;
        }
        if ("2".equals(type) && count >= 4) {
            allowDelete = true;
        }
        if ("3".equals(type) && count >= 6) {
            allowDelete = true;
        }
        if (deleted) {
            if (allowDelete) {
                return metroDao.updateDeleted(ids, deleted);
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            return metroDao.updateDeleted(ids, deleted);
        }

    }

}
