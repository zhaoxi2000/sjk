package com.ijinshan.sjk.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.dao.MoAppAndTagDao;
import com.ijinshan.sjk.dao.MoTagDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.MoAppAndTag;
import com.ijinshan.sjk.po.MoTag;
import com.ijinshan.sjk.po.MoViewTagApps;
import com.ijinshan.sjk.po.marketmerge.MoTagTopic;
import com.ijinshan.sjk.service.MoAttachmentService;
import com.ijinshan.sjk.service.MoTagRelationshipService;

@Service
public class MoTagRelationshipServiceImpl implements MoTagRelationshipService {
    private static final Logger logger = LoggerFactory.getLogger(MoTagRelationshipServiceImpl.class);

    @Resource(name = "moTagDaoImpl")
    private MoTagDao moTagDao;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "moAppAndTagDaoImpl")
    private MoAppAndTagDao moAppAndTagDao;

    @Resource(name = "moAttachmentServiceImpl")
    private MoAttachmentService attachmentService;

    @Override
    public List<MoTag> list(TagType tagType) {
        return moTagDao.list(tagType);
    }

    @Override
    public boolean delete(int tagId) {
        List<Integer> ids = new ArrayList<Integer>(1);
        if (countMoAppAndTag(tagId) > 0) {
            throw new UnsupportedOperationException("不可以删除,在该标签下存在展现的软件!");
        }
        ids.add(Integer.valueOf(tagId));
        int effect = moTagDao.deleteByIds(ids);
        ids.clear();
        return effect == 1;
    }

    @Override
    public boolean deleteMoTagApp(int andTagId) {
        MoAppAndTag tagApp = moAppAndTagDao.get(andTagId);
        if (tagApp != null)
            moAppAndTagDao.delete(tagApp);
        else
            throw new UnsupportedOperationException("很抱歉,在该专题应用不存在。");
        return true;
    }

    public long countMoAppAndTag(int tagId) {
        return moAppAndTagDao.countMoAppAndTag(tagId);
    }

    @Override
    public MoTag get(int id) {
        return moTagDao.get(Integer.valueOf(id));
    }

    @Override
    public void saveOrUpdate(MultipartHttpServletRequest multipartReq, MoTag tag) {
        tag.setImgUrl(StringUtils.isEmpty(tag.getImgUrl()) == true ? "" : tag.getImgUrl());
        tag.setBigPics(StringUtils.isEmpty(tag.getBigPics()) == true ? "" : tag.getBigPics());
        tag.setMediumPics(StringUtils.isEmpty(tag.getMediumPics()) == true ? "" : tag.getMediumPics());
        tag.setSmallPics(StringUtils.isEmpty(tag.getSmallPics()) == true ? "" : tag.getSmallPics());
        if (tag.getId() == 0) {
            moTagDao.save(tag);
        } else {
            moTagDao.update(tag);
        }
        // updateTagImgUrl(multipartReq, tag);
        updateBigPicsUrl(multipartReq, tag);
        updateSmallPicsUrl(multipartReq, tag);
        updateMediumPicsUrl(multipartReq, tag);
    }

    /** 原截图 */
    private void updateTagImgUrl(MultipartHttpServletRequest multipartReq, MoTag tag) {
        MultipartFile file = multipartReq.getFile("fileImgUrl");
        String oldImgUrl = multipartReq.getParameter("oldImgUrl");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveMoTagFile(tag.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (appConfig.isDeleteUploadImageFile() && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(appConfig.getDestMobileTagUploadBaseurl())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        tag.setImgUrl(subPath);
        moTagDao.update(tag);
    }

    /** 大图 */
    private void updateBigPicsUrl(MultipartHttpServletRequest multipartReq, MoTag tag) {
        MultipartFile file = multipartReq.getFile("fileBigPicsUrl");
        String oldImgUrl = multipartReq.getParameter("oldBigPicsUrl");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveMoTagFile(tag.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (appConfig.isDeleteUploadImageFile() && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(appConfig.getDestMobileTagUploadBaseurl())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        tag.setBigPics(subPath);
        moTagDao.update(tag);
    }

    /** 中图 */
    private void updateMediumPicsUrl(MultipartHttpServletRequest multipartReq, MoTag tag) {
        MultipartFile file = multipartReq.getFile("fileMediumPicsUrl");
        String oldImgUrl = multipartReq.getParameter("oldMediumPicsUrl");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveMoTagFile(tag.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (appConfig.isDeleteUploadImageFile() && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(appConfig.getDestMobileTagUploadBaseurl())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        tag.setMediumPics(subPath);
        moTagDao.update(tag);
    }

    /** 小图 */
    private void updateSmallPicsUrl(MultipartHttpServletRequest multipartReq, MoTag tag) {
        MultipartFile file = multipartReq.getFile("fileSmallPicsUrl");
        String oldImgUrl = multipartReq.getParameter("oldSmallPicsUrl");
        String subPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveMoTagFile(tag.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (appConfig.isDeleteUploadImageFile() && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(appConfig.getDestMobileTagUploadBaseurl())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        tag.setSmallPics(subPath);
        moTagDao.update(tag);
    }

    @Override
    public boolean saveByName(short catalog, List<String> names, short tagType) {
        int count = 0;
        for (String name : names) {
            MoTag e = new MoTag();
            e.setName(name.trim());
            e.setTagType(tagType);
            e.setCatalog(catalog);
            e.setTagDesc("");
            Serializable id = moTagDao.save(e);
            if (id != null) {
                count++;
            }
        }
        return count == names.size();
    }

    @Override
    public void updateMoAppAndTag(MoAppAndTag moAppAndTag) {
        moAppAndTagDao.update(moAppAndTag);
    }

    @Override
    public void saveMoAppAndTag(MoAppAndTag moAppAndTag) {
        moAppAndTagDao.save(moAppAndTag);
    }

    @Override
    public void saveMoAppAndTag(List<MoAppAndTag> mAppAndTagList) {
        for (MoAppAndTag t : mAppAndTagList) {
            saveMoAppAndTag(t);
        }
    }

    @Override
    public void saveMoAppAndTag(Integer[] appIdArray, Integer[] tagIdArray, String[] tagNameArray, Short[] tagTypeArray) {
        if (appIdArray.length < 1 || tagIdArray.length < 1 || tagNameArray.length < 1 || tagTypeArray.length < 1) {
            return;
        }
        List<MoAppAndTag> updateList = new ArrayList<MoAppAndTag>();
        List<MoAppAndTag> insertList = new ArrayList<MoAppAndTag>();
        for (Integer appId : appIdArray) {
            for (int index = 0; index < tagIdArray.length; index++) {
                MoAppAndTag entity = moAppAndTagDao.getMoAppTag(appId, tagIdArray[index]);
                if (entity != null) {
                    entity.setTagId(tagIdArray[index]);
                    entity.setTagName(tagNameArray[index]);
                    entity.setTagType(tagTypeArray[index]);
                    updateList.add(entity);
                } else {
                    entity = new MoAppAndTag();
                    entity.setAppId(appId);
                    entity.setRank(0);
                    entity.setTagId(tagIdArray[index]);
                    entity.setTagName(tagNameArray[index]);
                    entity.setTagType(tagTypeArray[index]);
                    entity.setShortDesc("");
                    insertList.add(entity);
                }
            }
        }
        saveMoAppAndTag(updateList);
        saveMoAppAndTag(insertList);
    }

    @Override
    public List<String> listTags(List<App> list) {
        List<Integer> appIds = new ArrayList<Integer>();
        for (App app : list) {
            appIds.add(app.getId());
        }
        moAppAndTagDao.listTagByApp(appIds);
        return null;
    }

    @Override
    public List<MoAppAndTag> getMoAppAndTagsByAppId(int appId) {
        Assert.isTrue(appId > 0, "Invalid appId!");
        List<Integer> appIds = new ArrayList<Integer>(1);
        appIds.add(appId);
        List<MoAppAndTag> list = moAppAndTagDao.listTagByApp(appIds);
        return list;
    }

    @Override
    public MoAppAndTag getMoAppAndTag(int id) {
        return moAppAndTagDao.get(id);
    }

    /* 修改排序 */
    @Override
    public boolean updateMoAppAndTagSort(Integer[] ids, Integer[] ranks) {
        boolean result = true;
        Integer i = 0;
        do {
            result = moAppAndTagDao.updateSort(ids[i], ranks[i]);
            i++;
        } while (i < ids.length && result);
        return result;
    }

    @Override
    public int deleteByAppId(Session session, int appId) {
        return moAppAndTagDao.deleteByAppId(session, appId);
    }

    @Override
    public long countMoTagForSearching(Short pId, Short tagType, String keywords) {
        return moTagDao.countForSearching(pId, tagType, keywords);
    }

    @Override
    public List<MoTag> searchMoTag(Short pId, String keywords, int page, int rows, String sort, String order) {
        return moTagDao.search(pId, keywords, page, rows, sort, order);
    }

    @Override
    public List<MoTagTopic> searchTagList(Short pid, Short tagType, String keywords, int page, int rows, String sort,
            String order) {
        return moTagDao.searchTagList(pid, tagType, keywords, page, rows, sort, order);
    }

    @Override
    public List<MoViewTagApps> searchMoAppAndTag(Integer tagId, Integer catalog, Short tagType, int page, int rows,
            String keywords, String sort, String order) {
        return moAppAndTagDao.search(tagId, catalog, tagType, page, rows, keywords, sort, order);
    }

    @Override
    public long countMoAppAndTagForSearching(Integer tagId, Integer catalog, Short tagType, String keywords) {
        return moAppAndTagDao.countForSearching(tagId, catalog, tagType, keywords);
    }

}
