package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.vo.AppForBigGamePacksVo;
import com.ijinshan.sjk.vo.pc.biggame.AppBigGamePacks;
import com.ijinshan.sjk.vo.pc.biggame.BigGamePacks;

/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-21 下午4:15:38
 * </pre>
 */
public interface BigGamePackMapper {

    List<BigGamePack> getBigGameByMarkAppId(@Param(value = "marketAppId") int marketAppId);

    List<BigGamePack> getBigGameByMarkAppIds(@Param(value = "ids") List<Integer> ids);

    List<BigGamePack> getBigGameList(@Param(value = "cputype") Integer cputype);

    List<AppForBigGamePacksVo> getAllBigGameList();

    List<BigGamePack> findBigPackListByParams(@Param(value = "marketAppId") int marketAppId,
            @Param(value = "product") String product, @Param(value = "brand") String brand);

    List<BigGamePack> findBigGamePackList(@Param(value = "marketAppId") Integer marketAppId,
            @Param(value = "cputype") Integer cputype);

    List<BigGamePacks> getBigGameListByCputypeOrCatalog(@Param(value = "cputype") Integer cputype,
            @Param(value = "subCatalog") String subCatalog,@Param(value = "phoneId") String phoneId);

    List<BigGamePacks> getAllBigGameListV1();

    List<AppBigGamePacks> getBigGamePakcList();

}
