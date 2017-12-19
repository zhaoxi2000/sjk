package com.ijinshan.sjk.vo.pc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagList {
    private Integer id;
    private String name;
    private Integer pid;
    private String tagDesc;
    private String imgUrl;
    private List<App4TagList> applist = new ArrayList<App4TagList>();
    
    private TagList() {
        super();
    }

    private TagList(Integer id, String name, Integer pid, String tagDesc, String imgUrl, List<App4TagList> applist) {
        super();
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.tagDesc = tagDesc;
        this.imgUrl = imgUrl;
        this.applist = applist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<App4TagList> getApplist() {
        return applist;
    }

    public void setApplist(List<App4TagList> applist) {
        this.applist = applist;
    }
}
