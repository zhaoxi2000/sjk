package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.CatalogInfo;

public interface CatalogMapper {
    public final String CATALOG_COLUMNS = " Id,PId,Name";

    @Select(value = { "select ", CATALOG_COLUMNS, " from Catalog" })
    List<Catalog> list();

    @Select(value = { "select ", CATALOG_COLUMNS, " from Catalog", "where pid = #{catalog}", "order by rank ASC" })
    List<Catalog> listByCatalog(@Param(value = "catalog") int catalog);

    List<CatalogInfo> listCatalogInfoByFilters(@Param(value = "catalog") int catalog,
            @Param(value = "noAds") Boolean noAds, @Param(value = "official") Boolean official);

    @Select("SELECT x.id AS subCatalog , x.pid AS catalog, x.name FROM Catalog x ORDER BY x.rank ASC")
    List<CatalogInfo> listCatalogInfo();

}
