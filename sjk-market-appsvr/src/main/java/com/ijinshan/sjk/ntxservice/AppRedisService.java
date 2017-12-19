package com.ijinshan.sjk.ntxservice;


import java.util.List;

import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;
import com.ijinshan.util.Pager;

public interface AppRedisService {

    Pager<LatestApp> getLatest(int subCatalog,Long date, int currentPage, int pageSize, int noAds, int noVirus, int official);

    Pager<String> getLatestDates(int subCatalog, int currentPage, int pageSize, int noAds, int noVirus, int official);


    <T> Pager<T> getPager(ListCallback<T> cb, int subCatalog, int currentPage, int pageSize, int noAds, int noVirus,
            int official);

    Pager<App4Summary> getHotDownload(int subCatalog, int currentPage, int pageSize, int sortType, int noAds, int noVirus, int official);

    List<SimpleRankApp> getSimpleRankApp(int subCatalog, int page, int rows, int sortType, int noAds, int noVirus, int official);

}
