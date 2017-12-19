package com.kingsoft.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.BigGamePack;

public interface BigGamePackDao {

    List<BigGamePack> getBigGameByMarkAppId(Integer marketAppId);

}
