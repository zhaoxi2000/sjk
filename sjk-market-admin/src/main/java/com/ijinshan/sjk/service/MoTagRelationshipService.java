package com.ijinshan.sjk.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.MoAppAndTag;
import com.ijinshan.sjk.po.MoTag;
import com.ijinshan.sjk.po.MoViewTagApps;
import com.ijinshan.sjk.po.marketmerge.MoTagTopic;

/**
 * 标签的管理是一个抽象概念的,包括专题,普通标签.
 * 
 * @author LinZuXiong
 */
public interface MoTagRelationshipService {
    List<MoTag> list(TagType tagType);

    boolean delete(int id);

    MoTag get(int id);

    void saveOrUpdate(MultipartHttpServletRequest multipartReq, MoTag moTag);

    boolean saveByName(short catalog, List<String> names, short tagType);

    void saveMoAppAndTag(MoAppAndTag moAppAndTag);

    void saveMoAppAndTag(List<MoAppAndTag> moAppAndTag);

    void saveMoAppAndTag(Integer[] appIdArray, Integer[] tagIdArray, String[] tagNameArray, Short[] tagTypeArray);

    List<String> listTags(List<App> list);

    List<MoAppAndTag> getMoAppAndTagsByAppId(int appId);

    /* MoAppAndTag分页查询 */
    List<MoViewTagApps> searchMoAppAndTag(Integer tagId, Integer catalog, Short tagType, int page, int rows,
            String keywords, String sort, String order);

    /* MoAppAndTag分页返回行数 */
    long countMoAppAndTagForSearching(Integer tagId, Integer catalog, Short tagType, String keywords);

    // 分页统计记录条目
    long countMoTagForSearching(Short pId, Short tagType, String keywords);

    // 分页查询搜索
    List<MoTag> searchMoTag(Short pId, String keywords, int page, int rows, String sort, String order);

    List<MoTagTopic> searchTagList(Short pid, Short tagType, String keywords, int page, int rows, String sort,
            String order);

    /* AppAndTag删除操作 */
    boolean deleteMoTagApp(int andTagId);

    /* AppAndTag 修改操作 */
    void updateMoAppAndTag(MoAppAndTag moAppAndTag);

    /* 修改排序 */
    boolean updateMoAppAndTagSort(Integer[] ids, Integer[] ranks);

    MoAppAndTag getMoAppAndTag(int id);

    // List<TagTopic> listTopic(Integer pid, String keywords);

    int deleteByAppId(Session session, int appId);
}
