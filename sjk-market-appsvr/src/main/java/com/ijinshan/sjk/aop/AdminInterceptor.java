package com.ijinshan.sjk.aop;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ijinshan.sjk.config.AppConfig;

/**
 * @author Linzuxiong
 */
@Aspect
@Component
public class AdminInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Pointcut("execution(public * com.ijinshan.sjk.web.DataAdminController.*(..))")
    private void pointCutMethod() {
    }

    @Around("pointCutMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest req = (HttpServletRequest) pjp.getArgs()[0];
        // IP valid
        String ip = getClientIP(req);
        if (validIP(ip)) {
            return pjp.proceed();
        } else {
            logger.error("Warning : under attack from {}", ip);
            return "403 Forbidden! Your ip is " + ip;
        }
    }

    private String getClientIP(HttpServletRequest req) {
        String ip = req.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty()) {
            ip = req.getRemoteAddr();
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getHeader("X-Forwarded-For");
        }
        return ip;
    }

    private boolean validIP(String ip) {
        Set<String> ips = appConfig.getTrustIPs();
        Iterator<String> it = ips.iterator();
        String trustIp = null;
        while (it.hasNext()) {
            trustIp = it.next();
            if (trustIp.equals(ip)) {
                return true;
            }
        }
        return false;
    }
}
