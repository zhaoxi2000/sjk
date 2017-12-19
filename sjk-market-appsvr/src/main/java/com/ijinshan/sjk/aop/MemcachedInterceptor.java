package com.ijinshan.sjk.aop;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ijinshan.sjk.annotation.Cacheable;

/**
 * <pre>
 * @author Hu Youzhi
 * 
 * 2012-4-26 下午12:00:41
 * </pre>
 */

@Aspect
@Component
public class MemcachedInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MemcachedInterceptor.class);

    @Resource(name = "memcachedClient")
    private MemcachedClient memcachedClient;

    // 定义一个切入点,名称为pointCutMethod(),拦截类的所有方法
    @Pointcut("execution(public * com.ijinshan.sjk.web.*.*(..))")
    private void pointCutMethod() {
    }

    // 定义环绕通知
    @Around("pointCutMethod() && @annotation(cacheable)")
    public Object aroundMethod(ProceedingJoinPoint pjp, Cacheable cacheable) throws Throwable {
        String key = cacheable.value();
        StringBuilder sb = null;
        if (StringUtils.isEmpty(key)) {
            sb = new StringBuilder(100);
            sb.append(pjp.getSignature().getName());

            Object[] args = pjp.getArgs();
            for (Object object : args) {
                sb.append(object);
                if (object != null) {
                    sb.append(object.getClass().getName());
                } else {
                    sb.append("null");
                }
            }
            key = DigestUtils.shaHex(sb.toString());
        }
        // 获取方法签名，构造 key，根据key从memcache中获取
        String memString = null;
        // 异常必须捕获
        try {
            memString = memcachedClient.get(key);
        } catch (MemcachedException e) {
            logger.error("Client Exception");
            if (memcachedClient.isShutdown()) {
                logger.error("Xmemcached is stopped! Client shutdown!");
            }
        } catch (Exception e) {
            logger.error("error", e);
        }

        if (StringUtils.isNotEmpty(memString)) {
            logger.debug("Cache hit!\tMethod: {}", pjp.getSignature().getName());
            return memString;
        }

        memString = (String) pjp.proceed();
        try {
            memcachedClient.setWithNoReply(key, cacheable.exp(), memString);
        } catch (MemcachedException e) {
            logger.error("Client Exception");
            if (memcachedClient.isShutdown()) {
                logger.error("Xmemcached is stopped! Client shutdown!");
            }
        } catch (Exception e) {
            logger.error("error", e);
        }

        return memString;
    }
}
