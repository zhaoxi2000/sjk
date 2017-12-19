package com.ijinshan.sjk.service;

import javax.servlet.http.HttpServletResponse;

public interface CDNCache {
    long getCurrentTimeCloseToPrevInterval();

    void setCacheScanSoftIfMiss(HttpServletResponse resp);

    void setCacheScanSoftIfHit(HttpServletResponse resp);
}
