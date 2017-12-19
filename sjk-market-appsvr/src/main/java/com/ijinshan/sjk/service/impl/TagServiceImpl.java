package com.ijinshan.sjk.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.mapper.TagMapper;
import com.ijinshan.sjk.service.TagService;
import com.ijinshan.sjk.vo.pc.SimpleTag;

@Service
public class TagServiceImpl implements TagService {
    private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    @Resource(name = "tagMapper")
    private TagMapper tagMapper;

    @Override
    public SimpleTag get(int id) {
        return tagMapper.get(id);
    }
}
