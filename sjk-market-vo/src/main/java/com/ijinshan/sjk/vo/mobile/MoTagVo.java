package com.ijinshan.sjk.vo.mobile;

import java.util.ArrayList;
import java.util.List;

public class MoTagVo {
    private Integer pid;
    private String pname;
    private List<TagListVo> tagList = new ArrayList<TagListVo>();
    
    
    public MoTagVo() {
        super();
    }
   
    
    
    public MoTagVo(Integer pid, String pname, List<TagListVo> tagList) {
        super();
        this.pid = pid;
        this.pname = pname;
        this.tagList = tagList;
    }



    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    
    
    
    public String getPname() {
        return pname;
    }



    public void setPname(String pname) {
        this.pname = pname;
    }



    public List<TagListVo> getTagList() {
        return tagList;
    }
    public void setTagList(List<TagListVo> tagList) {
        this.tagList = tagList;
    }
    
    
}
