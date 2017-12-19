/**
 * 
 */
package com.ijinshan.sjk.ntxservice.impl;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.ntxservice.DownloadStatService;
import com.ijinshan.sjk.service.DownloadService;
import com.ijinshan.sjk.util.IntegerSerializer;

/**
 * @author LinZuXiong
 */
@Service
public class DownloadStatServiceImpl implements DownloadStatService {
    private static final Logger logger = LoggerFactory.getLogger(DownloadStatServiceImpl.class);

    @Resource(name = "downloadServiceImpl")
    private DownloadService downloadService;

    @Resource(name = "redisTemplate")
    private RedisTemplate<Integer, Integer> template;

    @Resource(name = "valueOperations")
    private ValueOperations<Integer, Integer> valueOps;

    private Set<Integer> keys = new ConcurrentSkipListSet<Integer>();

    @PostConstruct
    public void init() {
        template.setKeySerializer(IntegerSerializer.INSTANCE);
        template.setValueSerializer(IntegerSerializer.INSTANCE);
    }

    @Override
    public void add(int pk) {
        Integer key = Integer.valueOf(pk);
        // long val =
        valueOps.increment(key, 1L);
        keys.add(key);
    }

    @Override
    public void updateFlushIncrementToDB() {
        long start = System.currentTimeMillis();
        try {
            int count = 0;
            Iterator<Integer> it = keys.iterator();
            while (it.hasNext()) {
                Integer key = it.next();
                if (key == null) {
                    continue;
                }
                Integer delta = valueOps.get(key);
                if (null != delta) {
                    count++;
                    try {
                        downloadService.update(key.intValue(), delta);
                        template.delete(key);
                        keys.remove(key);
                    } catch (Exception ex) {
                        logger.error("Exception", ex);
                    }
                }
            }
            logger.info("count {} to DB", count);
        } finally {
        }
        logger.info("Flush to DB costs {} ms.", String.valueOf(System.currentTimeMillis() - start));
    }

    @PreDestroy
    @Override
    public void destroy() {
        logger.info("Destroy...Preparing to flushIncrementToDB...");
        this.updateFlushIncrementToDB();
        keys.clear();
        keys = null;
        logger.info("FlushIncrementToDB done!");
    }
}
