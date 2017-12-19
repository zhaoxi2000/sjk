package com.ijinshan.sjk.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppHistory4Index;
import com.ijinshan.sjk.service.DataObserable;
import com.ijinshan.sjk.service.IndexUpdater;
import com.ijinshan.sjk.txservice.AppHistory4IndexService;
import com.ijinshan.sjk.txservice.AppService;
import com.ijinshan.util.Pager;

/**
 * @author Linzuxiong
 */
@Service
public class DataObserableImpl implements DataObserable {
    private static final Logger logger = LoggerFactory.getLogger(DataObserableImpl.class);
    public final int pageSize = 10000;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "appHistory4IndexServiceImpl")
    private AppHistory4IndexService appHistory4indexService;

    private Set<IndexUpdater> indexUpdaters = new HashSet<IndexUpdater>(10);

    @Override
    public boolean createAll() {
        if (!appConfig.isInitIndex()) {
            return true;
        }
        try {
            long start = System.currentTimeMillis();
            final long count = appService.count();
            try {
                if (count > 0) {
                    for (IndexUpdater indexUpdater : indexUpdaters) {
                        indexUpdater.beginAll();
                    }
                }
                // get apps .loop pagination.
                Pager<App> pager = new Pager<App>(pageSize, count);
                final int totalPage = pager.getTotalPage();
                pager: for (; pager.getCurrentPage() <= totalPage; pager.setCurrentPage(pager.getCurrentPage() + 1)) {
                    List<App> apps = appService.getApps(pager.getCurrentPage(), pageSize);
                    if (apps == null || apps.isEmpty()) {
                        break pager;
                    }
                    for (IndexUpdater indexUpdater : indexUpdaters) {
                        logger.info("Creat all indexes... Please wait a moment!**********************");
                        if (pager.getCurrentPage() <= 1) {
                            indexUpdater.createAll(apps, false);
                        } else {
                            indexUpdater.createAll(apps, true);
                        }
                    } // end one list
                    apps.clear();
                }
                // end loop pagination
            } finally {
                for (IndexUpdater indexUpdater : indexUpdaters) {
                    indexUpdater.endAll();
                }
            }
            logger.info("Creat all indexes cost time : " + (System.currentTimeMillis() - start) / 1000 + " s!");
            return true;
        } catch (Exception e) {
            logger.error("Exception", e);
            return false;
        }
    }

    @Override
    public boolean updateIncrement() {
        try {
            long start = System.currentTimeMillis();
            long count = appHistory4indexService.count();
            logger.info("begin updateIncrement.");

            List<AppHistory4Index> appHistory4indexList = null;
            boolean begining = false;
            try {
                if (count < 0)
                    return false;
                // 开始增加量的准备
                appHistory4indexList = appHistory4indexService.getApps();
                if (appHistory4indexList == null || appHistory4indexList.size() == 0) {
                    return false;
                }

                begining = true;
                for (IndexUpdater indexUpdater : indexUpdaters) {
                    indexUpdater.beginIncrement();
                }

                // 从该 集合中分离出需要删除 的集合和，新增加，更新的
                List<Integer> appIdsForUpdate = new ArrayList<Integer>();
                List<Integer> appIdsForDel = new ArrayList<Integer>();

                /**
                 * @author HuYouzhi LinZuxiong
                 */
                for (AppHistory4Index appHistory4index : appHistory4indexList) {
                    if (appHistory4index.getAppStatus().intValue() == 3) {// 删除
                        appIdsForDel.add(appHistory4index.getAppId());
                    } else {
                        appIdsForUpdate.add(appHistory4index.getAppId());
                    }
                }

                List<App> appListForUpdate = null;
                if (!CollectionUtils.isEmpty(appIdsForUpdate)) {
                    appListForUpdate = appService.getApps(appIdsForUpdate);
                }

                // 先更新后删除
                for (IndexUpdater indexUpdater : indexUpdaters) {
                    indexUpdater.updateIncrement(appListForUpdate);// 处理更新
                }
                for (IndexUpdater indexUpdater : indexUpdaters) {
                    indexUpdater.delDoucmentFromIndex(appIdsForDel);// 处删除
                }

                // 删除 AppHistoryForIndex中的数据，
                appHistory4indexService.delAppHistory4index(appIdsForDel);
                // AppHistoryForIndex 状态更新
                appHistory4indexService.updateAppHistory4indeToIndexed(appIdsForUpdate);

                if (!CollectionUtils.isEmpty(appListForUpdate)) {
                    appListForUpdate.clear();
                }
                appListForUpdate = null;

                if (!CollectionUtils.isEmpty(appIdsForDel)) {
                    appIdsForDel.clear();
                }
                appIdsForDel = null;
            } finally {
                if (begining) {
                    for (IndexUpdater indexUpdater : indexUpdaters) {
                        indexUpdater.endIncrement();
                    }
                }
                logger.info("end updateIncrement,cost:{}s", (System.currentTimeMillis() - start) / 1000);
            }
            return true;
        } catch (Exception e) {
            logger.error("Exception", e);
            return false;
        }
    }

    @Override
    public void registerObservers(IndexUpdater index) {
        if (!indexUpdaters.contains(index)) {
            indexUpdaters.add(index);
        }
    }

}
