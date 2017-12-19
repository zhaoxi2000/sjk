package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.AppAndTag;

public interface TagAppDao {
    List<AppAndTag> getTags(int tagId, short catalog);

}
