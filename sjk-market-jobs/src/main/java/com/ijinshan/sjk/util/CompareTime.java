package com.ijinshan.sjk.util;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.Viapp;

public class CompareTime implements Comparator {
    private static final Logger logger = LoggerFactory.getLogger(CompareTime.class);

    @Override
    public int compare(Object o, Object o2) {
        Viapp v = (Viapp) o;
        Viapp v2 = (Viapp) o2;
        
        if (v.getLastUpdateTime().before(v2.getLastUpdateTime())) {
            return -1;
        }else if(v.getLastUpdateTime().after(v2.getLastUpdateTime())){
            return 1;
        }
        return 0;
    }

}
