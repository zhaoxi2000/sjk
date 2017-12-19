package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.mapper.BigGamePackMapper;
import com.ijinshan.sjk.mapper.PhoneInfoMapper;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.PhoneBasicInfo;
import com.ijinshan.sjk.service.BigGamePackService;
import com.ijinshan.sjk.vo.pc.biggame.Apk;
import com.ijinshan.sjk.vo.pc.biggame.AppBigGamePacks;
import com.ijinshan.sjk.vo.pc.biggame.BigGamePacks;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:17:12
 * </pre>
 */

@Service
public class BigGamePackServiceImpl implements BigGamePackService {
    private static final Logger logger = LoggerFactory.getLogger(BigGamePackServiceImpl.class);

    // @Resource(name = "appDaoImpl")
    // private AppDao appDao;

    // @Resource(name = "appConfig")
    // private AppConfig appConfig;
    //
    // @Resource(name = "appMapper")
    // private AppMapper appMapper;

    @Resource(name = "bigGamePackMapper")
    private BigGamePackMapper bigGamePackMapper;

    @Resource(name = "phoneInfoMapper")
    private PhoneInfoMapper phoneInfoMapper;

    // @Resource(name = "cdnCacheImpl")
    // private CDNCache cdnCache;

    @Override
    public List<BigGamePack> getBigGameByMarkAppId(int marketAppId) {
        return bigGamePackMapper.getBigGameByMarkAppId(marketAppId);
    }

    @Override
    public List<BigGamePack> findBigPackListByParams(int marketAppId, String product, String brand) {
        List<BigGamePack> gameList = null;
        List<PhoneBasicInfo> phoneInfo = phoneInfoMapper.getPhoneList(product, brand);
        List<BigGamePack> biggameList = bigGamePackMapper.findBigPackListByParams(marketAppId, product, brand);
        if (null != phoneInfo && phoneInfo.size() > 0 && null != biggameList && biggameList.size() > 0) {
            for (PhoneBasicInfo phonebase : phoneInfo) {
                for (BigGamePack biggame : biggameList) {
                    if ((null == biggame.getUnsupportPhoneType())
                            || (null != biggame.getUnsupportPhoneType() && !(biggame.getUnsupportPhoneType()
                                    .contains(phonebase.getId() + ";")))) {
                        gameList = new ArrayList<BigGamePack>();
                        gameList.add(biggame);
                    }
                }
            }
        }

        return gameList;
    }

    @Override
    public List<BigGamePack> findBigGamePackByParams(int marketAppId, int phoneId, int cputype) {
        Assert.isTrue(marketAppId > 0);
        Assert.isTrue(phoneId > 0);
        Assert.isTrue(cputype > 0);
        List<BigGamePack> game = null;
        List<BigGamePack> biggameList = bigGamePackMapper.findBigGamePackList(marketAppId, cputype);
        if (null != biggameList && biggameList.size() > 0) {
            for (BigGamePack biggame : biggameList) {
                if ((null == biggame.getUnsupportPhoneType())
                        || (null != biggame.getUnsupportPhoneType() && !(biggame.getUnsupportPhoneType()
                                .contains(phoneId + ";")))) {
                    game = new ArrayList<BigGamePack>();
                    game.add(biggame);
                }
            }
        }

        return game;
    }

    @Override
    public List<BigGamePacks> getBigGamePakcList() {
        List<AppBigGamePacks> list = bigGamePackMapper.getBigGamePakcList();
        List<BigGamePacks> bigpacklist = null;
        if (list.size() <= 0) {// 无记录的时候返回null
            return null;
        } else if (list.size() == 1) { // 当只有一条记录的时候
            AppBigGamePacks aps = list.get(0);
            bigpacklist = new ArrayList<BigGamePacks>();
            BigGamePacks bp = new BigGamePacks();
            setAppBigGame(aps, bp);
            bigpacklist.add(bp);
        } else {
            bigpacklist = new ArrayList<BigGamePacks>();
            BigGamePacks bp = new BigGamePacks();
            setAppBigGame(list.get(0), bp);
            bigpacklist.add(bp);

            int x = 0;// 用来统计封装后bigpacklist的长度

            for (int i = 1; i < list.size(); i++) {
                // if (list.get(i).getId() == list.get(i-1).getId()) {
                if (list.get(i).getMarketAppId() == list.get(i - 1).getMarketAppId()) {
                    AppBigGamePacks agp = list.get(i);
                    Apk apk = new Apk();
                    apk.setBigGamePackId(agp.getBigGamePackId());
                    apk.setCputype(agp.getCputype());
                    apk.setFreeSize(agp.getFreeSize());
                    apk.setMarketAppId(agp.getMarketAppId());
                    apk.setMarketUpdateTime(agp.getMarketUpdateTime());
                    apk.setSize(agp.getSize());
                    apk.setUnsupportPhoneType(agp.getUnsupportPhoneType());
                    apk.setUrl(agp.getUrl());
                    // System.out.println("id=" + agp.getId() + " bid=" +
                    // agp.getBigGamePackId());
                    bigpacklist.get(x).getApkPackList().add(apk);
                } else {
                    BigGamePacks bpk = new BigGamePacks();
                    setAppBigGame(list.get(i), bpk);
                    bigpacklist.add(bpk);
                    x++;
                    // System.out.println("x =" + x);
                }
            }
        }

        return bigpacklist;
    }

    private void setAppBigGame(AppBigGamePacks aps, BigGamePacks bp) {
        bp.setId(aps.getId());
        bp.setName(aps.getName());
        bp.setMarketName(aps.getMarketName());
        bp.setLogoUrl(aps.getLogoUrl());
        bp.setPageUrl(aps.getPageUrl());
        bp.setSubCatalog(aps.getSubCatalog());
        bp.setPkname(aps.getPkname());
        bp.setVersion(aps.getVersion());
        bp.setDownloadRank(aps.getDownloadRank());
        bp.setVersionCode(aps.getVersionCode());
        bp.setSignatureSha1(aps.getSignatureSha1());
        bp.setOfficialSigSha1(aps.getOfficialSigSha1());
        Apk bgame = new Apk();
        bgame.setBigGamePackId(aps.getBigGamePackId());
        bgame.setCputype(aps.getCputype());
        bgame.setFreeSize(aps.getFreeSize());
        bgame.setMarketAppId(aps.getMarketAppId());
        bgame.setMarketUpdateTime(aps.getMarketUpdateTime());
        bgame.setUnsupportPhoneType(aps.getUnsupportPhoneType());
        bp.getApkPackList().add(bgame);
    }

    @Override
    public List<BigGamePacks> getAllBigGameList() {
        return bigGamePackMapper.getAllBigGameListV1();
    }

    @Override
    public List<BigGamePacks> getApplistByParams(Integer cputype, String phoneId, String subCatalog) {
        
        return bigGamePackMapper.getBigGameListByCputypeOrCatalog(cputype, subCatalog,phoneId);
    }

}
