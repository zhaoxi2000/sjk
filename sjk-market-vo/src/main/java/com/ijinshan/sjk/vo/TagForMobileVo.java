package com.ijinshan.sjk.vo;

import java.util.ArrayList;
import java.util.List;


public class TagForMobileVo {
    
    private TagMobileVo tag;
    private List<MobileSearchApp> appList  = new ArrayList<MobileSearchApp>();
    
    public TagForMobileVo() {
        super();
    }
    public TagMobileVo getTag() {
        return tag;
    }
    public void setTag(TagMobileVo tag) {
        this.tag = tag;
    }
    public List<MobileSearchApp> getAppList() {
        return appList;
    }
    public void setAppList(List<MobileSearchApp> appList) {
        this.appList = appList;
    }
    
    
    
    
}
