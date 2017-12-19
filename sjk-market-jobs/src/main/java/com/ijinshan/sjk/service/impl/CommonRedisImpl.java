package com.ijinshan.sjk.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.ijinshan.sjk.po.Viapp;
import com.ijinshan.sjk.service.CommonRedis;

@Service
public class CommonRedisImpl implements CommonRedis {
    private static final Logger logger = LoggerFactory.getLogger(CommonRedisImpl.class);

    // @Resource(name = "shardedJedisPool")
    // private ShardedJedisPool shardedJedisPool;
    @Resource
    private RedisTemplate<Serializable, Serializable> template;

    @Override
    public void saveCommonRedis(List<Viapp> list, RedisConnection connection) {
        try {

            String k1, k2, k3, k4, k5, k6, k7, k8, k9, k10;
            String k11, k12, k13, k14, k15, k16, k17, k18, k19, k20;
            String k21, k22, k23, k24, k25, k26, k27, k28, k29, k30;
            String k31, k32, k33, k34, k35, k36, k37, k38, k39, k40;
            for (int i = 0; i < list.size(); i++) {
                Viapp vp = list.get(i);
                String Id = vp.getId().toString();
                double lastUpdateTime = vp.getLastUpdateTime().getTime();
                double downloadRank = vp.getDownloadRank();
                double lastDayDelta = vp.getLastDayDelta();
                double lastWeekDelta = vp.getLastWeekDelta();
                double realDownload = vp.getRealDownload();
                // 0 time
                k1 = String.format("key_%d_%d_0_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
                k2 = String.format("key_%d_%d_0_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());
                // 1 download
                k3 = String.format("key_%d_%d_1_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
                k4 = String.format("key_%d_%d_1_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());

                // 2 lastDayDelta
                k5 = String.format("key_%d_%d_2_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
                k6 = String.format("key_%d_%d_2_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());

                // 3 lastWeekDelta
                k7 = String.format("key_%d_%d_3_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
                k8 = String.format("key_%d_%d_3_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());

                // 4 realDownload
                k9 = String.format("key_%d_%d_4_%d", vp.getNoAds(), vp.getOfficial(), vp.getCatalog());
                k10 = String.format("key_%d_%d_4_%d", vp.getNoAds(), vp.getOfficial(), vp.getSubCatalog());

                addRedis(k1, Id, lastUpdateTime, connection);
                addRedis(k2, Id, lastUpdateTime, connection);
                addRedis(k3, Id, downloadRank, connection);
                addRedis(k4, Id, downloadRank, connection);
                addRedis(k5, Id, lastDayDelta, connection);
                addRedis(k6, Id, lastDayDelta, connection);
                addRedis(k7, Id, lastWeekDelta, connection);
                addRedis(k8, Id, lastWeekDelta, connection);
                addRedis(k9, Id, realDownload, connection);
                addRedis(k10, Id, realDownload, connection);

                if (vp.getNoAds() == 1) {
                    k11 = String.format("key_0_%d_0_%d", vp.getOfficial(), vp.getCatalog());
                    k12 = String.format("key_0_%d_0_%d", vp.getOfficial(), vp.getSubCatalog());
                    // 1 download
                    k13 = String.format("key_0_%d_1_%d", vp.getOfficial(), vp.getCatalog());
                    k14 = String.format("key_0_%d_1_%d", vp.getOfficial(), vp.getSubCatalog());
                    // 2 lastDayDelta
                    k15 = String.format("key_0_%d_2_%d", vp.getOfficial(), vp.getCatalog());
                    k16 = String.format("key_0_%d_2_%d", vp.getOfficial(), vp.getSubCatalog());
                    // 3 lastWeekDelta
                    k17 = String.format("key_0_%d_3_%d", vp.getOfficial(), vp.getCatalog());
                    k18 = String.format("key_0_%d_3_%d", vp.getOfficial(), vp.getSubCatalog());
                    // 4 realDownload
                    k19 = String.format("key_0_%d_4_%d", vp.getOfficial(), vp.getCatalog());
                    k20 = String.format("key_0_%d_4_%d", vp.getOfficial(), vp.getSubCatalog());

                    addRedis(k11, Id, lastUpdateTime, connection);
                    addRedis(k12, Id, lastUpdateTime, connection);
                    addRedis(k13, Id, downloadRank, connection);
                    addRedis(k14, Id, downloadRank, connection);
                    addRedis(k15, Id, lastDayDelta, connection);
                    addRedis(k16, Id, lastDayDelta, connection);
                    addRedis(k17, Id, lastWeekDelta, connection);
                    addRedis(k18, Id, lastWeekDelta, connection);
                    addRedis(k19, Id, realDownload, connection);
                    addRedis(k20, Id, realDownload, connection);
                }

                if (vp.getOfficial() == 1) {
                    k21 = String.format("key_%d_0_0_%d", vp.getNoAds(), vp.getCatalog());
                    k22 = String.format("key_%d_0_0_%d", vp.getNoAds(), vp.getSubCatalog());
                    // 1 download
                    k23 = String.format("key_%d_0_1_%d", vp.getNoAds(), vp.getCatalog());
                    k24 = String.format("key_%d_0_1_%d", vp.getNoAds(), vp.getSubCatalog());
                    // 2 lastDayDelta
                    k25 = String.format("key_%d_0_2_%d", vp.getNoAds(), vp.getCatalog());
                    k26 = String.format("key_%d_0_2_%d", vp.getNoAds(), vp.getSubCatalog());
                    // 3 lastWeekDelta
                    k27 = String.format("key_%d_0_3_%d", vp.getNoAds(), vp.getCatalog());
                    k28 = String.format("key_%d_0_3_%d", vp.getNoAds(), vp.getSubCatalog());
                    // 4 realDownload
                    k29 = String.format("key_%d_0_4_%d", vp.getNoAds(), vp.getCatalog());
                    k30 = String.format("key_%d_0_4_%d", vp.getNoAds(), vp.getSubCatalog());

                    addRedis(k21, Id, lastUpdateTime, connection);
                    addRedis(k22, Id, lastUpdateTime, connection);
                    addRedis(k23, Id, downloadRank, connection);
                    addRedis(k24, Id, downloadRank, connection);
                    addRedis(k25, Id, lastDayDelta, connection);
                    addRedis(k26, Id, lastDayDelta, connection);
                    addRedis(k27, Id, lastWeekDelta, connection);
                    addRedis(k28, Id, lastWeekDelta, connection);
                    addRedis(k29, Id, realDownload, connection);
                    addRedis(k30, Id, realDownload, connection);
                }
                if (vp.getNoAds() == 1 && vp.getOfficial() == 1) {
                    k31 = String.format("key_0_0_0_%d", vp.getCatalog());
                    k32 = String.format("key_0_0_0_%d", vp.getSubCatalog());
                    // 1 download
                    k33 = String.format("key_0_0_1_%d", vp.getCatalog());
                    k34 = String.format("key_0_0_1_%d", vp.getSubCatalog());
                    // 2 lastDayDelta
                    k35 = String.format("key_0_0_2_%d", vp.getCatalog());
                    k36 = String.format("key_0_0_2_%d", vp.getSubCatalog());
                    // 3 lastWeekDelta
                    k37 = String.format("key_0_0_3_%d", vp.getCatalog());
                    k38 = String.format("key_0_0_3_%d", vp.getSubCatalog());
                    // 4 realDownload
                    k39 = String.format("key_0_0_4_%d", vp.getCatalog());
                    k40 = String.format("key_0_0_4_%d", vp.getSubCatalog());

                    addRedis(k31, Id, lastUpdateTime, connection);
                    addRedis(k32, Id, lastUpdateTime, connection);
                    addRedis(k33, Id, downloadRank, connection);
                    addRedis(k34, Id, downloadRank, connection);
                    addRedis(k35, Id, lastDayDelta, connection);
                    addRedis(k36, Id, lastDayDelta, connection);
                    addRedis(k37, Id, lastWeekDelta, connection);
                    addRedis(k38, Id, lastWeekDelta, connection);
                    addRedis(k39, Id, realDownload, connection);
                    addRedis(k40, Id, realDownload, connection);
                }

            }
        } catch (Exception e) {
            logger.error("saveRedisERROR:" + e);
        }
        // Executors.new

    }

    private void addRedis(String key, String id, double score, RedisConnection connection) {
        connection.zAdd(template.getStringSerializer().serialize(key), score, template.getStringSerializer().serialize(id));
        // redis.zadd(key, score, id);
        // shardedJredis.zadd(key, score, id);// 小类
        // redis.disconnect();
    }
}
