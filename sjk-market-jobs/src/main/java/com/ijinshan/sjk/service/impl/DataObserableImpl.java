package com.ijinshan.sjk.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.Viapp;
import com.ijinshan.sjk.service.CommonRedis;
import com.ijinshan.sjk.service.DataObserable;

@Service("dataObserableImpl")
public class DataObserableImpl implements DataObserable {
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(DataObserableImpl.class);

    @Resource(name = "appConfig")
    protected AppConfig appConfig;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;


    @Resource(name = "jedisPool")
    private JedisPool jedisPool;
    
    
    @Resource(name = "commonRedisImpl")
    private CommonRedis commonRedis;

    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    
    @Override
    public void updateAll() {
        
        try {
            final List<Viapp> list = appDao.getAllApp();
            logger.info("BeginNewAddAppToRedis...");
            long start = System.currentTimeMillis();
            redisTemplate.execute(new RedisCallback<Object>() {  
                @Override  
                public Object doInRedis(RedisConnection connection)  
                        throws DataAccessException {
                    connection.flushDb();
                    //connection.multi();
                    commonRedis.saveCommonRedis(list,connection);
                   // connection.exec();
                    return null;  
                }  
            });  
            logger.info("redis init finish cost:{}ms!", System.currentTimeMillis() - start);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
