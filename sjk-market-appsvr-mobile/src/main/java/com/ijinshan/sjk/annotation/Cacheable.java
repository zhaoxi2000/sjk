package com.ijinshan.sjk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * @author Hu Youzhi
 * 
 * 2012-4-26 下午12:00:31
 * </pre>
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
    // 缓存的key,默认是方法签名和方法 参数做key
    String value() default "";

    // 缓存的时间,默认一小时
    int exp() default 3600;
}
