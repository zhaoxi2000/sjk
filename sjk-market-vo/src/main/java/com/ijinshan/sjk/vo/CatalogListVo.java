package com.ijinshan.sjk.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CatalogListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private short pid;

    private List<CatalogVo> catalog = new ArrayList<CatalogVo>();

    public short getPid() {
        return pid;
    }

    public void setPid(short pid) {
        this.pid = pid;
    }

    public List<CatalogVo> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<CatalogVo> catalog) {
        this.catalog = catalog;
    }

}
