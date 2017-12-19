package com.ijinshan.sjk.vo;

import java.util.Set;

public class AppTopic {

    private static final long serialVersionUID = 2L;

    private Integer id;
    private String name;
    private Integer pid;
    private String tagDesc;
    private String imgUrl;
    private Set<TagApps> tagApps;

    public AppTopic(Integer pid, Integer id, String name, String tagDesc, String imgUrl) {
        super();
        this.pid = pid;
        this.id = id;
        this.name = name;
        this.tagDesc = tagDesc;
        this.imgUrl = imgUrl;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Set<TagApps> getTagApps() {
        return tagApps;
    }

    public void setTagApps(Set<TagApps> tagApps) {
        this.tagApps = tagApps;
    }

}
