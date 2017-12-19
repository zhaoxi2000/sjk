package com.ijinshan.sjk.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.BigGamePack;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:16:02
 * </pre>
 */
public class AppAndBigGamesVo extends App implements java.io.Serializable {

    private static final long serialVersionUID = 7005163795031372280L;

    private List<BigGamePack> bigGamePackList = new ArrayList<BigGamePack>();

    public AppAndBigGamesVo() {
        super();
    }

    public AppAndBigGamesVo(int marketAppId, String marketName, String name, String engName, short catalog,
            int subCatalog, int size, long freeSize, String downloadUrl, String logoUrl, String description,
            String updateInfo, String publisherShortName, Short minsdkversion, long versionCode, String osversion,
            String pkname, Date lastUpdateTime, Date lastFetchTime, String detailUrl, float price, String screens,
            String models, String keywords, float starRating, int viewCount, int downloadRank, short supportpad,
            String enumStatus, String strImageUrls, boolean auditCatalog, boolean hidden, String pageUrl,
            String shortDesc, String indexImgUrl, String notice, String officeHomepage, String language, boolean audit,
            byte pathStatus, String permissions, String signatureSha1, String officialSigSha1, Date apkScanTime,
            String advertises, String adActionTypes, String adPopupTypes, byte adRisk, int realDownload,
            int deltaDownload, int lastDayDownload, int lastDayDelta, int lastWeekDownload, int lastWeekDelta,
            byte virusKind, String virusBehaviors, String virusName, String md5, int appId, int apkId, String logo1url,
            Integer status, boolean autoCover,int scSta) {
        super(marketAppId, marketName, name, engName, catalog, subCatalog, size, freeSize, downloadUrl, logoUrl,
                description, updateInfo, publisherShortName, minsdkversion, versionCode, osversion, pkname,
                lastUpdateTime, lastFetchTime, detailUrl, price, screens, models, keywords, starRating, viewCount,
                downloadRank, supportpad, enumStatus, strImageUrls, auditCatalog, hidden, pageUrl, shortDesc,
                indexImgUrl, notice, officeHomepage, language, audit, pathStatus, permissions, signatureSha1,
                officialSigSha1, apkScanTime, advertises, adActionTypes, adPopupTypes, adRisk, realDownload,
                deltaDownload, lastDayDownload, lastDayDelta, lastWeekDownload, lastWeekDelta, virusKind,
                virusBehaviors, virusName, md5, appId, apkId, logo1url, status, autoCover,scSta);
    }

    public AppAndBigGamesVo(int id, String name) {
        super(id, name);
    }

    public AppAndBigGamesVo(List<BigGamePack> bigGamePackList) {
        super();
        this.bigGamePackList = bigGamePackList;
    }

    public List<BigGamePack> getBigGamePackList() {
        return bigGamePackList;
    }

    public void setBigGamePackList(List<BigGamePack> bigGamePackList) {
        this.bigGamePackList = bigGamePackList;
    }

}
