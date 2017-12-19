package com.ijinshan.sjk.po.marketmerge;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MoTagTopic implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer pid = 0;
    private String pname = "无";
    private String tagDesc;
    private short catalog;
    private short tagType;
    private String imgUrl;
    private Integer rank = 0;

    private String bigPics;
    private String mediumPics;
    private String smallPics;

    public MoTagTopic() {
    }

    @Override
    public String toString() {
        return "MoTagTopic [id=" + id + ", name=" + name + ", pid=" + pid + ", pname=" + pname + ", tagDesc=" + tagDesc
                + ", catalog=" + catalog + ", tagType=" + tagType + ", imgUrl=" + imgUrl + ", rank=" + rank
                + ", bigPics=" + bigPics + ", mediumPics=" + mediumPics + ", smallPics=" + smallPics + "]";
    }

    public MoTagTopic(String name, short catalog, short tagType) {
        this.name = name;
        this.catalog = catalog;
        this.tagType = tagType;
    }

    public MoTagTopic(String name, String tagDesc, short catalog, short tagType, String imgUrl, Integer rank) {
        this.name = name;
        this.tagDesc = tagDesc;
        this.catalog = catalog;
        this.tagType = tagType;
        this.imgUrl = imgUrl;
        this.rank = rank;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PId", nullable = false)
    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Column(name = "TagDesc", nullable = false)
    public String getTagDesc() {
        return this.tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    @Column(name = "Catalog", nullable = false)
    public short getCatalog() {
        return this.catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

    @Column(name = "TagType", nullable = false)
    public short getTagType() {
        return this.tagType;
    }

    public void setTagType(short tagType) {
        this.tagType = tagType;
    }

    @Column(name = "ImgUrl", nullable = false)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "PName", nullable = false)
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Column(name = "Rank")
    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Column(name = "BigPics", nullable = false)
    public String getBigPics() {
        return bigPics;
    }

    public void setBigPics(String bigPics) {
        this.bigPics = bigPics;
    }

    @Column(name = "MediumPics", nullable = false)
    public String getMediumPics() {
        return mediumPics;
    }

    public void setMediumPics(String mediumPics) {
        this.mediumPics = mediumPics;
    }

    @Column(name = "SmallPics", nullable = false)
    public String getSmallPics() {
        return smallPics;
    }

    public void setSmallPics(String smallPics) {
        this.smallPics = smallPics;
    }

}
