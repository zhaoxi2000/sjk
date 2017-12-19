package com.ijinshan.sjk.jsonpo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "package" })
public class HiapkMarketApp {
    // 项目编号
    private int softcode;
    // 软件编号
    private int apkid;
    private String packagename;
    private String apkname;
    private int apksize;
    private String platform;
    private String versionname;
    private int versioncode;
    private short minsdkversion;
    private float totalstar;
    private float star;
    private String description;
    private String iconurl;
    private String downurl;
    private String sourceurl;
    private Date createtime;
    private Date publishtime;
    private String language;
    private String developername;
    private int totaldownload;
    private int download;
    private int cid;
    private short pid;
    private String cname;
    private String pay;
    private String dpi;
    private String iscertify;
    private String isfirstpublish;
    private String updatedescription;
    private String qrcodeurl;
    private String imageurl;
    private String status;

    public int getSoftcode() {
        return softcode;
    }

    public void setSoftcode(int softcode) {
        this.softcode = softcode;
    }

    public int getApkid() {
        return apkid;
    }

    public void setApkid(int apkid) {
        this.apkid = apkid;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public int getApksize() {
        return apksize;
    }

    public void setApksize(int apksize) {
        this.apksize = apksize;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public short getMinsdkversion() {
        return minsdkversion;
    }

    public void setMinsdkversion(short minsdkversion) {
        this.minsdkversion = minsdkversion;
    }

    public float getTotalstar() {
        return totalstar;
    }

    public void setTotalstar(float totalstar) {
        this.totalstar = totalstar;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDevelopername() {
        return developername;
    }

    public void setDevelopername(String developername) {
        this.developername = developername;
    }

    public int getTotaldownload() {
        return totaldownload;
    }

    public void setTotaldownload(int totaldownload) {
        this.totaldownload = totaldownload;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public short getPid() {
        return pid;
    }

    public void setPid(short pid) {
        this.pid = pid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getIscertify() {
        return iscertify;
    }

    public void setIscertify(String iscertify) {
        this.iscertify = iscertify;
    }

    public String getIsfirstpublish() {
        return isfirstpublish;
    }

    public void setIsfirstpublish(String isfirstpublish) {
        this.isfirstpublish = isfirstpublish;
    }

    public String getUpdatedescription() {
        return updatedescription;
    }

    public void setUpdatedescription(String updatedescription) {
        this.updatedescription = updatedescription;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
