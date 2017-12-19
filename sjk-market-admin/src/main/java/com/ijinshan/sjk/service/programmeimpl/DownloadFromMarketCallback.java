package com.ijinshan.sjk.service.programmeimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.service.UpdateDownloadCallback;
import com.ijinshan.sjk.vo.Downloads;

public class DownloadFromMarketCallback implements UpdateDownloadCallback {
    private static final Logger logger = LoggerFactory.getLogger(DownloadFromMarketCallback.class);

    private Map<String, Market> marketMap;
    private long allDownloads;

    public DownloadFromMarketCallback(Map<String, Market> marketMap, long allDownloads) {
        this.marketMap = marketMap;
        this.allDownloads = allDownloads;
    }

    @Override
    public int doIn(Session session, App a, AppDao appDao) {
        int downloadRank = 0;
        double ratio = 0.0d;
        List<Downloads> downloadsList = appDao.getDownloads(a.getPkname());
        Map<String, Downloads> downloadsMap = new HashMap<String, Downloads>();
        int downloadsOfApp = 0;
        if (downloadsList != null && !downloadsList.isEmpty()) {
            for (Downloads down : downloadsList) {
                downloadsMap.put(down.getMarketName(), down);
            }
            for (Downloads down : downloadsList) {
                // ratio += down.getMarketAppDown() /
                // (marketMap.get(down.getMarketName()).getDownloads() * 1.0d);
                downloadsOfApp += down.getMarketAppDown();
            }
            downloadRank = (int) (ratio * allDownloads / (downloadsList.size() * 1.0d));
            return appDao.updateDownloads(session, a.getId(), downloadRank, downloadsOfApp);
        }
        return 0;
    }
}
