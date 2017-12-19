package com.ijinshan.sjk.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.po.Viapp;
import com.ijinshan.sjk.service.AppEntryService;
import com.ijinshan.sjk.vo.KeyScoreParis;

@Service
public class AppEntryServiceImpl implements AppEntryService {
    private static final Logger logger = LoggerFactory.getLogger(AppEntryServiceImpl.class);

    @Override
    public KeyScoreParis getKeySocrePair(Viapp app) {
        int[] noAdsKeys, officailKeys, sortTypeKeys, abstractCatalogKeys;
        if (app.getNoAds() == 1) {
            // 无广告.关心选项，只关心1 ， 转化为可以查询0 ， 1 
            noAdsKeys = new int[] { 0, 1 };
        } else {
            // 有广告.转化为可以查询 0
            noAdsKeys = new int[] { 0 };
        }

        if (app.getOfficial() == 1) {
            // 官方版.关心选项，只关心1 ， 转化为可以查询0 ， 1 
            officailKeys = new int[] { 0, 1 };
        } else {
            // 非官方版.转化为可以查询 0
            officailKeys = new int[] { 0 };
        }

        abstractCatalogKeys = new int[] { app.getCatalog(), app.getSubCatalog() };
        // sortType , 0-日期，1-总下载，2-日下载，3-周下载
        sortTypeKeys = new int[] { 0, 1, 2, 3 };
        int count = noAdsKeys.length * officailKeys.length * sortTypeKeys.length * abstractCatalogKeys.length;
        String[] keys = new String[count];
        long[] scores = new long[count];
        int i = 0;
        String key = null;
        long score = 0;
        for (int noAdsKey : noAdsKeys) {
            for (int officailKey : officailKeys) {
                for (int sortTypeKey : sortTypeKeys) {
                    for (int abstractCatalogKey : abstractCatalogKeys) {
                        key = String.format("key_%d_%d_%d_%d", noAdsKey, officailKey, sortTypeKey, abstractCatalogKey);
                        // sortType , 0-日期，1-总下载，2-日下载，3-周下载
                        switchFlag: switch (sortTypeKey) {
                            case 0: {
                                // TODO keep yyyy-MM-dd
                                score = app.getLastUpdateTime().getTime();
                                break switchFlag;
                            }
                            case 1: {
                                score = app.getDownloadRank();
                                break switchFlag;
                            }
                            case 3: {
                            }
                            default: {
                                score = app.getDownloadRank();
                                break switchFlag;
                            }
                        }
                        scores[i] = score;
                        keys[i++] = key;
                    }
                }
            }
        }
        KeyScoreParis paris = new KeyScoreParis();
        paris.setKeys(keys);
        paris.setScore(scores);
        return paris;
    }
}
