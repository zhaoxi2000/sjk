package com.ijinshan.sjk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.Viapp;

public interface DataObserable {

    /**
     * 同步数据到Redis    
     */
    void updateAll();
}
