package com.ijinshan.sjk.vo.mobile;

/**
 * 提供所有的专题列表
 * <pre>
 * @author Du WangXi
 * Create on 2013-4-19 下午4:29:11
 * </pre>
 */
public class TagListVo {
    private Integer id;
    private String name = "";
    private String tagDesc = "";
    private short catalog = 0;
    private String bigPics = "";
    private String mediumPics ="";
    private String smallPics ="";
    private String pname = "";
    private Integer pid =0;
    public TagListVo() {
        super();
    }
    public TagListVo(Integer id, String name, String tagDesc, short catalog, String bigPics, String mediumPics,
            String smallPics, String pname, Integer pid) {
        super();
        this.id = id;
        this.name = name;
        this.tagDesc = tagDesc;
        this.catalog = catalog;
        this.bigPics = bigPics;
        this.mediumPics = mediumPics;
        this.smallPics = smallPics;
        this.pname = pname;
        this.pid = pid;
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
    public short getCatalog() {
        return catalog;
    }
    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }
    public String getBigPics() {
        return bigPics;
    }
    public void setBigPics(String bigPics) {
        this.bigPics = bigPics;
    }
    public String getMediumPics() {
        return mediumPics;
    }
    public void setMediumPics(String mediumPics) {
        this.mediumPics = mediumPics;
    }
    public String getSmallPics() {
        return smallPics;
    }
    public void setSmallPics(String smallPics) {
        this.smallPics = smallPics;
    }
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    
}
