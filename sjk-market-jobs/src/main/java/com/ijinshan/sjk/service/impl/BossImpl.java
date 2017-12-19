package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.service.Boss;

public class BossImpl implements Boss {
    private static final Logger logger = LoggerFactory.getLogger(BossImpl.class);

    @Resource(name = "appConfig")
    protected AppConfig appConfig;

    @Resource(name = "jedisPool")
    protected JedisPool jedisPool;

    long start = System.currentTimeMillis();
    ExecutorService threads = Executors.newFixedThreadPool(20);
    List<Jedis> jedisClientList = new ArrayList<Jedis>();

    private AtomicInteger idleCount = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        threads = Executors.newFixedThreadPool(30);
    }

    @Override
    public boolean canSubmit() {
        synchronized (jedisClientList) {
            if (jedisClientList.isEmpty()) {
                try {
                    jedisClientList.add(jedisPool.getResource());
                } catch (Exception e) {

                }
            }
        }
        return false;
    }

    @Override
    public void submit(Callable<Boolean> task, int index) {
    }

}
