package com.ijinshan.sjk.vo;

import java.util.ArrayList;
import java.util.List;

public class MetroMobileParamVO {

    private List<MetroMobileVO> metrolist = new ArrayList<MetroMobileVO>();
    public MetroMobileParamVO() {
        super();
    }
    public List<MetroMobileVO> getMetrolist() {
        return metrolist;
    }
    public void setMetrolist(List<MetroMobileVO> metrolist) {
        this.metrolist = metrolist;
    }
    
}
