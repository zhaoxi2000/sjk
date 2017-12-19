package com.ijinshan.sjk.vo.pc.abstracttag;

import java.util.List;

import com.ijinshan.sjk.vo.pc.App4Summary;

public class TagApps {
    private int id; // this tag id.
    private int pid; // the parent tag id of this tag
    private String name; // tag name
    private String tagDesc;// this tag description
    private String imgUrl;// this tag image url
    private List<App4Summary> tagApps = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<App4Summary> getTagApps() {
        return tagApps;
    }

    public void setTagApps(List<App4Summary> tagApps) {
        this.tagApps = tagApps;
    }

}
