package com.ijinshan.sjk.service;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.Viapp;

public interface SynRedisData {

    /**
     * 同步数据到Redis
     */
    void synRedisByApp();

}
