package com.ijinshan.sjk.service.impl;

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
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.service.TagAppService;
import com.ijinshan.sjk.vo.AppTopic;
import com.ijinshan.sjk.vo.TagApps;

@Service
public class TagAppServiceImpl implements TagAppService {
    private static final Logger logger = LoggerFactory.getLogger(TagAppServiceImpl.class);

    @Resource(name = "tagAppMapper")
    private TagAppMapper tagAppMapper;

    @Override
    public List<com.ijinshan.sjk.vo.pc.abstracttag.TagApps> getApps4Topic(int tagId) {
        return tagAppMapper.getApps4Topic(tagId);
    }

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
            mapApps.put(tag.getTag().getId().toString(), new AppTopic(tag.getTag().getPid(), tag.getTag().getId(), tag
                    .getTag().getName(), tag.getTag().getTagDesc(), tag.getTag().getImgUrl()));
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

}