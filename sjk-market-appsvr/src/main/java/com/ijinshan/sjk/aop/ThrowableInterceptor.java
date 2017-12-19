package com.ijinshan.sjk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * @author Hu Youzhi
 * 
 * 2012-5-10 下午4:00:16
 * 捕获所有web Controller层的异常
 * </pre>
 */

@Aspect
@Component
public class ThrowableInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ThrowableInterceptor.class);

    // 定义一个切入点,名称为pointCutMethod(),拦截类的所有方法
    @Pointcut("execution(public * com.ijinshan.*.web.*.*(..))")
    private void errorPointCutMethod() {
    }

    @AfterThrowing(pointcut = "errorPointCutMethod()", throwing = "throwable")
    public void doAfterException(JoinPoint jp, Throwable throwable) {
        logger.error("invoke method: {}", jp.getSignature().getName());
        Object[] args = jp.getArgs();
        for (Object object : args) {
            if (object != null) {
                logger.error("parameter value: {},parameter type: {}", object, object.getClass().getName());
            } else {
                logger.error("arg is null");
            }
        }
        logger.error("Throwable", throwable);
    }

}
