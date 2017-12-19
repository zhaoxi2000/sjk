package com.ijinshan.sjk.util;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.Viapp;

public class CompareDowld implements Comparator {
    private static final Logger logger = LoggerFactory.getLogger(CompareDowld.class);

    @Override
    public int compare(Object o, Object o2) {
        Viapp v = (Viapp) o;
        Viapp v2 = (Viapp) o2;
        
        if (v.getDownloadRank() < v2.getDownloadRank()) {
            return 1;
        }else if(v.getDownloadRank() > v2.getDownloadRank()){
            return -1;
        }
        return 0;
    }

}
