/**
 * 
 */
package com.kingsoft.sjk.game.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.MarketApp;
import com.kingsoft.sjk.game.dao.impl.BigGamePackDaoImpl;
import com.kingsoft.sjk.game.dao.impl.MarketAppDaoImpl;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-2-26 上午10:19:38
 * </pre>
 */
@Repository
public class BigGameDataHandle {
    private static final Logger logger = LoggerFactory.getLogger(BigGameDataHandle.class);

    @Resource(name = "marketAppDaoImpl")
    private MarketAppDaoImpl marketAppDaoImpl;

    @Resource(name = "bigGamePackDaoImpl")
    private BigGamePackDaoImpl bigGamePackDaoImpl;

    public void handleData() {

        /**
         * 1:获取所有大游戏数据，从MarketApp表中 2：取其中的一些字段，封装成BigGamePack
         * 3:插入数据到BigGamePack中
         */

        List<MarketApp> list = marketAppDaoImpl.getAllBigGameFromMarketApp();
        BigGamePack bigGamePack = null;
        for (MarketApp marketApp : list) {
            int marketAppId = marketApp.getId();
            String urlString = marketApp.getDownloadUrl();
            int size = marketApp.getSize();
            long freeSize = marketApp.getFreeSize();
            byte cputype = 1;// 全部统一设置为1,默认为高通

            bigGamePack = new BigGamePack();
            bigGamePack.setMarketAppId(marketAppId);
            bigGamePack.setUrl(urlString);
            bigGamePack.setSize(size);
            bigGamePack.setFreeSize(freeSize);
            bigGamePack.setCputype(cputype);
            bigGamePack.setMarketUpdateTime(new Date());

            bigGamePackDaoImpl.save(bigGamePack);

        }

    }

}
