/**
 * 
 */
package com.ijinshan.sjk.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;

import com.ijinshan.sjk.po.Viapp;
import com.ijinshan.sjk.vo.KeyScoreParis;

/**
 * @author songyi
 */
public class RedisCommitJob implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RedisCommitJob.class);
    public static final String DEFAULT_CHARSET = "UTF-8";

    private Viapp app;
    private Jedis redis;

    private KeyScoreParis keyScoreParis;

    @Override
    public void run() {
        try {
            int count = keyScoreParis.getKeys().length;
            String[] keys = keyScoreParis.getKeys();
            long[] scores = keyScoreParis.getScore();
            Assert.isTrue(keys.length == scores.length, "Not a pair!!!!");
            for (int i = 0; i < count; i++) {
                byte[] key = keys[i].getBytes(DEFAULT_CHARSET);
                byte[] member = app.getId().toString().getBytes(DEFAULT_CHARSET);
                redis.zadd(key, scores[i], member);
            }
        } catch (UnsupportedEncodingException e) {
        }
    }

    public final Viapp getApp() {
        return app;
    }

    public final void setApp(Viapp app) {
        this.app = app;
    }

    public final Jedis getRedis() {
        return redis;
    }

    public final void setRedis(Jedis redis) {
        this.redis = redis;
    }

    public final KeyScoreParis getKeyScoreParis() {
        return keyScoreParis;
    }

    public final void setKeyScoreParis(KeyScoreParis keyScoreParis) {
        this.keyScoreParis = keyScoreParis;
    }

    public static final String getDefaultCharset() {
        return DEFAULT_CHARSET;
    }

}
