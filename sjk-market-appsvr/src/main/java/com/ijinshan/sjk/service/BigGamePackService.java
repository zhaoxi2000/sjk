package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.vo.pc.biggame.BigGamePacks;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:16:38
 * </pre>
 */
public interface BigGamePackService {

    List<BigGamePack> getBigGameByMarkAppId(int marketAppId);

    List<BigGamePack> findBigPackListByParams(int marketAppId, String product, String brand);

    List<BigGamePack> findBigGamePackByParams(int marketAppId, int phoneId, int cputype);

    List<BigGamePacks> getBigGamePakcList();

    List<BigGamePacks> getAllBigGameList();

    List<BigGamePacks> getApplistByParams(Integer cputype, String phoneId, String subCatalog);

}
