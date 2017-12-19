package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.BigGamePack;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:16:38
 * </pre>
 */
public interface BigGamePackService {

    List<BigGamePack> getBigGameByMarkAppId(int marketAppId);

}
