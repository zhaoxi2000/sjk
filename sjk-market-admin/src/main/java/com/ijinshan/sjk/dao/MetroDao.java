package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.Metro;

public interface MetroDao extends BaseDao<Metro> {

    List<Metro> search(String type, Boolean hidden, Boolean deleted);

    long count(String type);

    int delete(int appId);

    int updateHide(List<Integer> appIds);

    int updateShow(List<Integer> appIds);

    /*
     * 逻辑删除操作， deleted=true 删除操作 deleted=false 还原操作
     */
    Boolean updateDeleted(List<Integer> ids, boolean deleted);

}
