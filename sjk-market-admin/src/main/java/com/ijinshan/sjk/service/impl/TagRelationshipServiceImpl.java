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
import com.ijinshan.sjk.dao.AppAndTagDao;
import com.ijinshan.sjk.dao.AppHistory4IndexDao;
import com.ijinshan.sjk.dao.TagDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.po.ViewTagApps;
import com.ijinshan.sjk.po.marketmerge.TagTopic;
import com.ijinshan.sjk.service.AttachmentService;
import com.ijinshan.sjk.service.TagRelationshipService;

@Service
public class TagRelationshipServiceImpl implements TagRelationshipService {
    private static final Logger logger = LoggerFactory.getLogger(TagRelationshipServiceImpl.class);

    @Resource(name = "tagDaoImpl")
    private TagDao tagDao;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appAndTagDaoImpl")
    private AppAndTagDao appAndTagDao;

    @Resource(name = "appHistory4IndexDaoImpl")
    private AppHistory4IndexDao appHistory4IndexDao;

    @Resource(name = "attachmentServiceImpl")
    private AttachmentService attachmentService;

    @Override
    public List<Tag> list(TagType tagType) {
        return tagDao.list(tagType);
    }

    @Override
    public boolean delete(int tagId) {
        List<Integer> ids = new ArrayList<Integer>(1);
        if (countAppAndTag(tagId) > 0) {
            throw new UnsupportedOperationException("不可以删除,在该标签下存在展现的软件!");
        }
        ids.add(Integer.valueOf(tagId));
        int effect = tagDao.deleteByIds(ids);
        ids.clear();
        return effect == 1;
    }

    @Override
    public boolean deleteTagApp(int andTagId) {
        AppAndTag tagApp = appAndTagDao.get(andTagId);
        if (tagApp != null)
            appAndTagDao.delete(tagApp);
        else
            throw new UnsupportedOperationException("很抱歉,在该专题应用不存在。");
        return true;
    }

    public long countAppAndTag(int tagId) {
        return appAndTagDao.countAppAndTag(tagId);
    }

    @Override
    public Tag get(int id) {
        return tagDao.get(Integer.valueOf(id));
    }

    @Override
    public void saveOrUpdate(MultipartHttpServletRequest multipartReq, Tag tag) {
        if (tag.getTagType() == TagType.TOPIC.getVal()) {
            saveOrUpdateTag(multipartReq, tag);
        } else {
            tagDao.saveOrUpdate(tag);
        }
    }

    private void saveOrUpdateTag(MultipartHttpServletRequest multipartReq, Tag tag) {

        MultipartFile file = multipartReq.getFile("fileImgUrl");
        String oldImgUrl = multipartReq.getParameter("oldImgUrl");
        String subPath = "";
        if (tag.getId() == 0) {
            tagDao.save(tag);
        } else {
            tagDao.update(tag);
        }
        if (file != null && !file.isEmpty()) {
            try {
                subPath = attachmentService.saveTagFile(tag.getId(), file);
            } catch (IllegalStateException e) {
                logger.error("upload tag img error", e);
            } catch (IOException e) {
                logger.error("upload tag img error", e);
            }
        }
        if (StringUtils.isNotBlank(subPath)) {
            if (appConfig.isDeleteUploadImageFile() && StringUtils.isNotBlank(oldImgUrl)
                    && oldImgUrl.contains(appConfig.getDestTagUploadDir())) {
                attachmentService.deleteFile(oldImgUrl);
            }
        } else {
            subPath = oldImgUrl;
        }
        tag.setImgUrl(subPath);
        tagDao.update(tag);

    }

    @Override
    public boolean saveByName(short catalog, List<String> names, short tagType) {
        int count = 0;
        for (String name : names) {
            Tag e = new Tag();
            e.setName(name.trim());
            e.setTagType(tagType);
            e.setCatalog(catalog);
            e.setTagDesc("");
            Serializable id = tagDao.save(e);
            if (id != null) {
                count++;
            }
        }
        return count == names.size();
    }

    @Override
    public void updateAppAndTag(AppAndTag appAndTag) {
        appAndTagDao.update(appAndTag);
    }

    @Override
    public void saveAppAndTag(AppAndTag appAndTag) {
        appAndTagDao.save(appAndTag);
    }

    @Override
    public void saveAppAndTag(List<AppAndTag> appAndTagList) {
        for (AppAndTag t : appAndTagList) {
            saveAppAndTag(t);
        }
    }

    @Override
    public void saveAppAndTag(Integer[] appIdArray, Integer[] tagIdArray, String[] tagNameArray, Short[] tagTypeArray) {
        if (appIdArray.length < 1 || tagIdArray.length < 1 || tagNameArray.length < 1 || tagTypeArray.length < 1) {
            return;
        }
        List<AppAndTag> updateList = new ArrayList<AppAndTag>();
        List<AppAndTag> insertList = new ArrayList<AppAndTag>();
        for (Integer appId : appIdArray) {
            for (int index = 0; index < tagIdArray.length; index++) {
                AppAndTag entity = appAndTagDao.getAppTag(appId, tagIdArray[index]);
                if (entity != null) {
                    entity.setAppId(appId);
                    entity.setTagId(tagIdArray[index]);
                    entity.setTagName(tagNameArray[index]);
                    entity.setTagType(tagTypeArray[index]);
                    updateList.add(entity);
                } else {
                    entity = new AppAndTag();
                    entity.setAppId(appId);
                    entity.setRank(0);
                    entity.setTagId(tagIdArray[index]);
                    entity.setTagName(tagNameArray[index]);
                    entity.setTagType(tagTypeArray[index]);
                    insertList.add(entity);
                }
            }
            // 标签或专题管理更新时，需要记录索引修改记录；
            appHistory4IndexDao.saveOrUpdate(appId);
        }
        if (updateList != null) {
            saveAppAndTag(updateList);
            updateList.clear();
        }
        if (insertList != null) {
            saveAppAndTag(insertList);
            insertList.clear();
        }
    }

    @Override
    public List<String> listTags(List<App> list) {
        List<Integer> appIds = new ArrayList<Integer>();
        for (App app : list) {
            appIds.add(app.getId());
        }
        appAndTagDao.listTagByApp(appIds);
        return null;
    }

    @Override
    public List<AppAndTag> getAppAndTagsByAppId(int appId) {
        Assert.isTrue(appId > 0, "Invalid appId!");
        List<Integer> appIds = new ArrayList<Integer>(1);
        appIds.add(appId);
        List<AppAndTag> list = appAndTagDao.listTagByApp(appIds);
        return list;
    }

    @Override
    public List<ViewTagApps> searchAppAndTag(Integer tagId, Integer catalog, Short tagType, int page, int rows,
            String keywords, String sort, String order) {
        return appAndTagDao.search(tagId, catalog, tagType, page, rows, keywords, sort, order);
    }

    @Override
    public long countForSearchingAppAndTag(Integer tagId, Integer catalog, Short tagType, String keywords) {
        return appAndTagDao.countForSearching(tagId, catalog, tagType, keywords);
    }

    @Override
    public AppAndTag getAppAndTag(int id) {
        return appAndTagDao.get(id);
    }

    /* 修改排序 */
    @Override
    public boolean updateAppAndTagSort(Integer[] ids, Integer[] ranks) {
        boolean result = true;
        Integer i = 0;
        do {
            result = appAndTagDao.updateSort(ids[i], ranks[i]);
            i++;
        } while (i < ids.length && result);
        return result;
    }

    @Override
    public List<TagTopic> searchTopicList(Integer pid, String keywords, int page, int rows, String sort, String order) {
        List<TagTopic> list = tagDao.searchTopicList(pid, keywords, page, rows, sort, order);
        return list;
    }

    @Override
    public int deleteByAppId(Session session, int appId) {
        return appAndTagDao.deleteByAppId(session, appId);
    }

    @Override
    public long countForSearchingTag(Integer pid, TagType tagType, String keywords) {
        return tagDao.countForSearching(pid, tagType, keywords);
    }

}
