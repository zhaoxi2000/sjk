/**
 * 
 */
package com.ijinshan.sjk.service.impl;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.mapper.AppMapper;
import com.ijinshan.sjk.service.DownloadStatService;
import com.ijinshan.sjk.util.IntegerSerializer;

/**
 * @author LinZuXiong
 */
@Service
public class DownloadStatServiceImpl implements DownloadStatService {
    private static final Logger logger = LoggerFactory.getLogger(DownloadStatServiceImpl.class);
    @Resource(name = "sessionFactory")
    private SessionFactory sessions;
    @Resource(name = "appDaoImpl")
    private AppDao appDao;
    @Resource(name = "appMapper")
    private AppMapper appMapper;

    @Resource(name = "redisTemplate")
    private RedisTemplate<Integer, Integer> template;

    // @Autowired
    @Resource(name = "valueOperations")
    private ValueOperations<Integer, Integer> valueOps;

    private Set<Integer> keys = new ConcurrentSkipListSet<Integer>();

    @PostConstruct
    public void init() {
        template.setKeySerializer(IntegerSerializer.INSTANCE);
        template.setValueSerializer(IntegerSerializer.INSTANCE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.service.DownloadStatService#add(int)
     */
    @Override
    public void add(int pk) {
        Integer key = Integer.valueOf(pk);
        long val = valueOps.increment(key, 1L);
        keys.add(key);
        logger.debug("{} has {}", key, val);
    }

    @Override
    public void updateFlushIncrementToDB() {
        long start = System.currentTimeMillis();
        Session session = sessions.openSession();
        try {
            int count = 0;
            Iterator<Integer> it = keys.iterator();
            while (it.hasNext()) {
                Integer key = it.next();
                if (key == null) {
                    continue;
                }
                Integer delta = valueOps.get(key);
                if (delta != null) {
                    try {
                        // appDao.updateIncrementDownload(session,
                        // key.intValue(), delta);
                        appMapper.updateIncrementDownload(key.intValue(), delta);
                        // valueOps.set(key, 0);
                        template.delete(key);
                        keys.remove(key);
                    } catch (Exception ex) {
                        logger.error("Exception", ex);
                    }
                    if (++count % AppConfig.BATCH_SIZE == 0) {
                        session.flush();
                        session.clear();
                    }
                }
            }
            logger.info("count {} to DB", count);
        } finally {
            if (null != session) {
                session.flush();
                session.clear();
                session.close();
            }
        }
        logger.info("Flush to DB costs {} ms.", String.valueOf(System.currentTimeMillis() - start));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kingsoft.sjk.service.DownloadStatService#destroy()
     */
    @PreDestroy
    @Override
    public void destroy() {
        logger.info("Destroy...Preparing to flushIncrementToDB...");
        // template.exec();
        this.updateFlushIncrementToDB();
        keys.clear();
        keys = null;
        logger.info("FlushIncrementToDB done!");
    }
}
