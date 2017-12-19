package com.ijinshan.sjk.aop;

import java.util.List;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
public class AppHistory4IndexInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AppHistory4IndexInterceptor.class);

    // @Resource(name = "appHistory4IndexDaoImpl")
    // private AppHistory4IndexDao appHistory4IndexDaoImpl;

    // @Pointcut("execution(public * com.ijinshan.sjk.service.impl.AppServiceImpl.save*(..))")

    /**
     * 拦截 新增加 的app
     */
    @Pointcut("execution(public * com.ijinshan.sjk.service.programmeimpl.MergeServiceImpl.createAppFromMarketApp(..))")
    private void createAppPointCut() {
    }

    /**
     * 拦截更新
     */
    @Pointcut("execution(public * com.ijinshan.sjk.service.programmeimpl.MergeServiceImpl.updateAppIfNeedUpdate(..))")
    private void updateAppPointcut() {
    }

    /**
     * 拦截隐藏
     */
    @Pointcut("execution(public * com.ijinshan.sjk.dao.impl.AppDaoImpl.updateHide(..))")
    private void updateAppShowPointcut() {
    }

    /**
     * 拦截显示
     */
    @Pointcut("execution(public * com.ijinshan.sjk.dao.impl.AppDaoImpl.updateShow(..))")
    private void updateAppHidePointcut() {
    }

    @Before(value = "createAppPointCut()")
    public void createHistoryBefore() {
        System.out.println("before   create ....");
        logger.info("hello...");
    }

    @After(value = "createAppPointCut() && args(jp)")
    public void createHistory(Joinpoint jp) {
        logger.debug("begin createAppPointCut ");
        //
        // App app = null;
        // try {
        // app = (App) jp.proceed();
        // } catch (Throwable e) {
        // logger.error("throwable:", e);
        // }
        // if (app != null) {
        // appHistory4IndexDaoImpl.saveOrUpdate(app.getId());
        // }
    }

    @After(value = "updateAppPointcut() && args(jp)")
    public void updateHistory(Joinpoint jp) {
        logger.debug("begin updateAppPointcut ");
        // App app = null;
        // try {
        // app = (App) jp.proceed();
        // } catch (Throwable e) {
        // logger.error("throwable:", e);
        // }
        // if (app != null) {
        // appHistory4IndexDaoImpl.saveOrUpdate(app.getId());
        // }
    }

    // @AfterReturning(value = "updateAppHidePointcut()", argNames = "ids")
    public void hideApp(List<Integer> ids) {
        // if (ids == null || ids.size() == 0) {
        // return;
        // }
        // appHistory4IndexDaoImpl.updateAppStatus2Del(ids);
    }

    // @AfterReturning(value = "updateAppShowPointcut()", argNames = "ids")
    public void showApp(List<Integer> ids) {
        // if (ids == null || ids.size() == 0) {
        // return;
        // }
        // for (Integer appId : ids) {
        // appHistory4IndexDaoImpl.saveOrUpdate(appId);
        // }
    }

}
