package com.kingsoft.sjk.service;

import java.util.List;

import com.kingsoft.sjk.po.App;

public interface AppRankService {

    /**
     * 类别下排行
     * 
     * @param parentId
     *            l
     * @param subCatalog
     * @param top
     * @return
     */

    List<App> getAppCategoryRank(final int parentId, final int subCatalog, final int top);

    /**
     * 默认专题下的排行
     * 
     * @param typeId
     * @param top
     * @return
     */
    List<App> getAppDefultRank(final int typeId, final int top);
}
