package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.po.marketmerge.ComparableBaseApp;

/**
 * 多市场应用在合并比较时的逻辑.
 * 
 * @author LinZuXiong
 */
public interface MergeAppComparator {
    /**
     * 依次比较市场的重要性,包版本号,更新日期 , App入库的时间.<br />
     * 在比较MarketApp 和App时, 把MarketApp表数据当作是新数据.
     * 
     * @param mApp
     * @param app
     * @param currentMarket
     * @param appOwnerMarket
     *            旧数据所属的market
     * @return
     */
    int compare(MarketApp mApp, App app, Market currentMarket, Market appOwnerMarket);

    /**
     * 1.对于市场的数据更新,每次从市场来的数据都是新数据.<br/>
     * 2.在比较MarketApp 和App时, 把MarketApp表数据当作是新数据.
     * 
     * @param newApp
     * @param bakApp
     * @param currentMarket
     * @param bakAppOwnerMarket
     *            旧数据所属的market
     * @return
     */
    int compare(ComparableBaseApp newApp, ComparableBaseApp bakApp, Market currentMarket, Market bakAppOwnerMarket);

    /**
     * 比较list中所有的元素, 取出比较条件后最高版本(或者说是最好的一个版本的包).
     * 
     * @param list
     * @return
     */
    MarketApp getTop(List<MarketApp> list);
}
