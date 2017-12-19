package com.ijinshan.sjk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;

import com.ijinshan.sjk.po.Viapp;

public interface CommonRedis {

    void saveCommonRedis(List<Viapp> app,RedisConnection connection);

}
