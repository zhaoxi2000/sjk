package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ijinshan.sjk.po.BigGamePack;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:15:38
 * </pre>
 */
public interface BigGamePackMapper {

    List<BigGamePack> getBigGameByMarkAppId(@Param(value = "marketAppId") int marketAppId);

    List<BigGamePack> getBigGameByMarkAppIds(@Param(value = "ids") List<Integer> ids);

}
