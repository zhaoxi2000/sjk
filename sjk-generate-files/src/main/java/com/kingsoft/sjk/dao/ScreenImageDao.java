package com.kingsoft.sjk.dao;

import java.util.List;

import com.kingsoft.sjk.po.ScreenImage;

public interface ScreenImageDao {
    List<ScreenImage> getAppImages(int softid);

    List<ScreenImage> findAll();
}
