package com.ijinshan.sjk.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.po.ViewTagApps;
import com.ijinshan.sjk.po.marketmerge.TagTopic;

/**
 * 标签的管理是一个抽象概念的,包括专题,普通标签.
 * 
 * @author LinZuXiong
 */
public interface TagRelationshipService {
    List<Tag> list(TagType tagType);

    boolean delete(int id);

    Tag get(int id);

    void saveOrUpdate(MultipartHttpServletRequest multipartReq, Tag tag);

    boolean saveByName(short catalog, List<String> names, short tagType);

    void saveAppAndTag(AppAndTag appAndTag);

    void saveAppAndTag(List<AppAndTag> appAndTag);

    void saveAppAndTag(Integer[] appIdArray, Integer[] tagIdArray, String[] tagNameArray, Short[] tagTypeArray);

    List<String> listTags(List<App> list);

    List<AppAndTag> getAppAndTagsByAppId(int appId);

    /* AppAndTag分页查询 */
    List<ViewTagApps> searchAppAndTag(Integer tagId, Integer catalog, Short tagType, int page, int rows,
            String keywords, String sort, String order);

    /* AppAndTag分页返回行数 */
    long countForSearchingAppAndTag(Integer tagId, Integer catalog, Short tagType, String keywords);

    /* AppAndTag删除操作 */
    boolean deleteTagApp(int andTagId);

    /* AppAndTag 修改操作 */
    void updateAppAndTag(AppAndTag appAndTag);

    /* 修改排序 */
    boolean updateAppAndTagSort(Integer[] ids, Integer[] ranks);

    AppAndTag getAppAndTag(int id);

    List<TagTopic> searchTopicList(Integer pid, String keywords, int page, int rows, String sort, String order);

    long countForSearchingTag(Integer pid, TagType tagType, String keywords);

    int deleteByAppId(Session session, int appId);
}
