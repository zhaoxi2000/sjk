package com.ijinshan.sjk.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
public class AppHistory4IndexInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AppHistory4IndexInterceptor.class);

    // @Resource(name = "appHistory4IndexImpl")
    // private AppHistory4Index appHistory4Index;

    @Pointcut("execution(public * com.ijinshan.sjk.service.impl.AppServiceImpl.save*(..))")
    private void createAppPointCut() {
    }

    @Pointcut("execution(public * com.ijinshan.sjk.service.impl.AppServiceImpl.update*(..))")
    private void updateAppPointcut() {
    }

    @AfterReturning(value = "createAppPointCut()")
    public void createHistory() {
        System.out.println("create ....");
    }

    @AfterReturning(value = "updateAppPointcut()")
    public void updateHistory() {
        System.out.println("update ....");
    }
}
