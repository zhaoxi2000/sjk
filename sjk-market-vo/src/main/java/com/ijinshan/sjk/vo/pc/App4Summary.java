package com.ijinshan.sjk.vo.pc;

public class App4Summary extends SimpleRankApp {

    private static final long serialVersionUID = 3956095230611872969L;

    private String indexImgUrl = "";
    private String adActionTypes = "";
    private String adPopupTypes = "";
    private short virusKind = 0;
    private String shortDesc = "";
    private String description = "";
    private long freeSize = 0;

    public String getIndexImgUrl() {
        return indexImgUrl;
    }

    public void setIndexImgUrl(String indexImgUrl) {
        if (indexImgUrl == null) {
            return;
        }
        this.indexImgUrl = indexImgUrl;
    }

    public String getAdActionTypes() {
        return adActionTypes;
    }

    public void setAdActionTypes(String adActionTypes) {
        if (adActionTypes == null) {
            return;
        }
        this.adActionTypes = adActionTypes;
    }

    public String getAdPopupTypes() {
        return adPopupTypes;
    }

    public void setAdPopupTypes(String adPopupTypes) {
        if (adPopupTypes == null) {
            return;
        }
        this.adPopupTypes = adPopupTypes;
    }

    public final short getVirusKind() {
        return virusKind;
    }

    public final void setVirusKind(short virusKind) {
        this.virusKind = virusKind;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        if (shortDesc == null) {
            return;
        }
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public long getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(long freeSize) {
        this.freeSize = freeSize;
    }

    public void setDescription(String description) {
        if (description != null && !description.isEmpty()) {
            description = description.replaceAll("\\<.*?>", "");
            description = description.replaceAll("\\</.+>", "");
            description = description.replaceAll("\\</.+", "");
            description = description.replaceAll("\\<.+", "");
            final int len = description.length() > 50 ? 50 : description.length();
            this.description = description.substring(0, len);
        }
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
