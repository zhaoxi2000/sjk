package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.mapper.TagAppMapper;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.MoAppAndTag;
import com.ijinshan.sjk.service.TagAppService;
import com.ijinshan.sjk.vo.AppTopic;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.TagApps;
import com.ijinshan.sjk.vo.TagMobileVo;
import com.ijinshan.sjk.vo.mobile.MoTagVo;
import com.ijinshan.sjk.vo.mobile.TagListVo;
import com.ijinshan.util.HibernateHelper;
import com.ijinshan.util.Pager;

@Service
public class TagAppServiceImpl implements TagAppService {
    public static final int MAX_ROWS = 30;
    private static final Logger logger = LoggerFactory.getLogger(TagAppServiceImpl.class);
    public static final int PAGE_SIZE = 20;
    public static final int CURRENT_PAGE = 1;

    @Resource(name = "tagAppMapper")
    private TagAppMapper tagAppMapper;

    // @Autowired
    // private TagAppDao dao;

    @Override
    public List<AppAndTag> getTags(int tagId, short catalog) {
        Assert.isTrue(tagId > 0, "invalid tagId");
        Assert.isTrue(catalog > 0, "invalid catalog");
        return tagAppMapper.getTags(tagId, catalog);

    }

    @Override
    public Set<AppTopic> getAppTopic(int tagId, short catalog) {
        Assert.isTrue(tagId > 0, "invalid tagId");
        Assert.isTrue(catalog > 0, "invalid catalog");

        List<AppAndTag> list = tagAppMapper.getTags(tagId, catalog);
        Set<AppTopic> outputList = new LinkedHashSet<AppTopic>();
        Map<String, AppTopic> mapApps = new LinkedHashMap<String, AppTopic>();
        if (list != null) {
            setAppTopic(list, mapApps);
            for (AppTopic appTopic : mapApps.values()) {
                setTagApps(list, appTopic);
                outputList.add(appTopic);
            }
        }
        return outputList;
    }

    private void setAppTopic(List<AppAndTag> list, Map<String, AppTopic> mapApps) {
        for (AppAndTag tag : list) {
            // Assert.isTrue(tag.getTag()!=null,"tag is null.");
            mapApps.put(tag.getTag().getId().toString(), new AppTopic(tag.getTag().getPid(), tag.getTag().getId(), tag
                    .getTag().getName(), tag.getTag().getTagDesc(), tag.getTag().getImgUrl()));
        }
    }

    private void setMobileAppTopic(List<MoAppAndTag> list, Map<String, AppTopic> mapApps) {
        for (MoAppAndTag tag : list) {
            mapApps.put(tag.getMoTag().getId().toString(), new AppTopic(tag.getMoTag().getPid(),
                    tag.getMoTag().getId(), tag.getMoTag().getName(), tag.getMoTag().getTagDesc(), tag.getMoTag()
                            .getImgUrl()));
        }
    }

    private void setTagApps(List<AppAndTag> list, AppTopic topic) {
        Set<TagApps> tagApps = new LinkedHashSet<TagApps>();
        for (AppAndTag appAndTag : list) {
            if (topic.getId() == appAndTag.getTag().getId()) {
                tagApps.add(new TagApps(appAndTag.getId(), appAndTag.getRank(), "", appAndTag.getApp()));
            }
        }
        topic.setTagApps(tagApps);
    }

    private void setMobileTagApps(List<MoAppAndTag> list, AppTopic topic) {
        Set<TagApps> tagApps = new LinkedHashSet<TagApps>();
        for (MoAppAndTag app : list) {
            if (topic.getId() == app.getMoTag().getId()) {
                tagApps.add(new TagApps(app.getId(), app.getRank(), app.getShortDesc(), app.getApp()));
            }
        }
        topic.setTagApps(tagApps);
    }

    @Override
    public Set<AppTopic> getMobileAppTopic(int tagId, int currentPage, int pageSize) {
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Pager<MoAppAndTag> pager = new Pager<MoAppAndTag>();
        pager.setResult(tagAppMapper.getTagsByTagId(tagId, offset, pageSize));
        List<MoAppAndTag> list = pager.getResult();
        Set<AppTopic> outputList = new LinkedHashSet<AppTopic>();
        Map<String, AppTopic> mapApps = new LinkedHashMap<String, AppTopic>();
        if (list != null) {
            setMobileAppTopic(list, mapApps);
            for (AppTopic appTopic : mapApps.values()) {
                setMobileTagApps(list, appTopic);
                outputList.add(appTopic);
            }
        }
        return outputList;
    }

    public Set<AppTopic> getMobileAppTopics(int tagId) {
        List<MoAppAndTag> list = tagAppMapper.getTagsByTagIds(tagId);
        Set<AppTopic> outputList = new LinkedHashSet<AppTopic>();
        Map<String, AppTopic> mapApps = new LinkedHashMap<String, AppTopic>();
        if (list != null) {
            setMobileAppTopic(list, mapApps);
            for (AppTopic appTopic : mapApps.values()) {
                setMobileTagApps(list, appTopic);
                outputList.add(appTopic);
            }
        }
        return outputList;
    }

    @Override
    public List<MobileSearchApp> getTopicList(int tagId) {
        List<MobileSearchApp> appOrderList = new ArrayList<MobileSearchApp>();
        Set<AppTopic> topset = getMobileAppTopics(tagId);
        Iterator<AppTopic> it = topset.iterator();
        while (it.hasNext()) {
            AppTopic topic = it.next();
            if (topic != null) {
                Set<TagApps> tagApps = topic.getTagApps();
                if (tagApps.size() > 0) {
                    List<MobileSearchApp> appList = getAppList(tagApps);
                    for (MobileSearchApp applists : appList) {
                        appOrderList.add(applists);
                    }
                }

            }

        }

        return appOrderList;
    }

    public List<MobileSearchApp> getAppList(Set<TagApps> tagApps) {
        List<MobileSearchApp> appOrderList = new ArrayList<MobileSearchApp>();
        if (tagApps.size() > 0) {
            Iterator<TagApps> it = tagApps.iterator();
            while (it.hasNext()) {
                TagApps tagApp = it.next();
                if (tagApp != null) {
                    App app = tagApp.getApp();
                    if (app != null) {
                        MobileSearchApp applist = new MobileSearchApp();
                        applist.setId(app.getId());
                        applist.setAdActionTypes(app.getAdActionTypes());
                        applist.setAdPopupTypes(app.getAdPopupTypes());
                        applist.setCatalog(app.getCatalog());
                        applist.setDownloadRank(app.getDownloadRank());
                        applist.setDownloadUrl(app.getDownloadUrl());
                        applist.setLastUpdateTime(app.getLastUpdateTime());
                        applist.setLogoUrl(app.getLogoUrl());
                        applist.setMarketname(app.getMarketName());
                        applist.setName(app.getName());
                        applist.setOfficialSigSha1(app.getOfficialSigSha1());
                        applist.setPkname(app.getPkname());
                        applist.setSignatureSha1(app.getSignatureSha1());
                        applist.setSize(app.getSize());
                        applist.setVersion(app.getVersion());
                        applist.setVersionCode(app.getVersionCode());
                        applist.setVirusKind(app.getVirusKind());
                        appOrderList.add(applist);
                    }
                }

            }
        }

        return appOrderList;
    }

    @Override
    public Pager<MobileSearchApp> getMobileAppTopicList(int currentPage, int pageSize) {
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");

        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Pager<MobileSearchApp> pager = new Pager<MobileSearchApp>();
        pager.setResult(tagAppMapper.getMobileAppTopicList(offset, pageSize));
        return pager;
    }

    @Override
    public Pager<MobileSearchApp> getMobileTagList(int tagId, int currentPage, int pageSize) {
        Assert.isTrue(pageSize > 0 && pageSize <= MAX_ROWS, "pageSize invalid!");
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Pager<MobileSearchApp> pager = new Pager<MobileSearchApp>();
        pager.setResult(tagAppMapper.getMobileTagAppList(tagId, offset, pageSize));
        return pager;
    }

    @Override
    public TagMobileVo getTagByTagId(int tagId) {
        TagMobileVo tagVo = tagAppMapper.getTagByTagId(tagId);
        return tagVo;
    }

    @Override
    public Pager<TagListVo> getTagListPage(Integer tagType, Integer currentPage, Integer pageSize) {
        if (currentPage == null && pageSize == null && tagType == null) {
            tagType = 1;
            currentPage = CURRENT_PAGE;
            pageSize = PAGE_SIZE;
        }
        
        final int offset = HibernateHelper.firstResult(currentPage, pageSize);
        Pager<TagListVo> pager = new Pager<TagListVo>();
        long rows = tagAppMapper.getCountTagListPage(tagType);
        pager.setRows(rows);
        pager.setResult(tagAppMapper.getTagListPage(tagType, offset, pageSize));
        return pager;
    }

    @Override
    public long getMobileAppTopic() {
        return tagAppMapper.getCountMobileAppTopic();
    }

}