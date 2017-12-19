/**
 * 
 */
package com.ijinshan.sjk.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.BigGamePackDao;
import com.ijinshan.sjk.dao.MarketAppDao;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.service.BigGamePackService;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-1-10 下午2:21:59
 * </pre>
 */
@Service
public class BigGamePackServiceImpl implements BigGamePackService {
    private static final Logger logger = LoggerFactory.getLogger(BigGamePackServiceImpl.class);

    @Autowired
    private BigGamePackDao bigGamePackDao;
    @Autowired
    private MarketAppDao marketAppDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ijinshan.sjk.service.BigGamePackService#saveBatch(java.util.List)
     */
    @Override
    public void saveBatch(List<BigGamePack> bigGamePacks) {

        for (BigGamePack bigGamePack : bigGamePacks) {
            bigGamePackDao.save(bigGamePack);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ijinshan.sjk.service.BigGamePackService#save(com.ijinshan.sjk.po.
     * BigGamePack)
     */
    @Override
    public Serializable save(BigGamePack bigGamePack) {
        return bigGamePackDao.save(bigGamePack);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ijinshan.sjk.service.BigGamePackService#update(com.ijinshan.sjk.po
     * .BigGamePack)
     */
    @Override
    public void update(BigGamePack bigGamePack) {
        bigGamePackDao.update(bigGamePack);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ijinshan.sjk.service.BigGamePackService#updateBatch(java.util.List)
     */
    @Override
    public void updateBatch(List<BigGamePack> bigGamePackss) {

        for (BigGamePack bigGamePack : bigGamePackss) {
            bigGamePackDao.update(bigGamePack);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ijinshan.sjk.service.BigGamePackService#getBigGamePacksByMarketAppid
     * (java.lang.Integer)
     */
    @Override
    public List<BigGamePack> getBigGamePacksByMarketAppid(Integer marketAppId) {

        return bigGamePackDao.getBigGamePacksByMarketAppid(marketAppId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ijinshan.sjk.service.BigGamePackService#saveOrUpdateBatch(java.util
     * .List)
     */
    @Override
    public void saveOrUpdateBatch(List<BigGamePack> bigGamePacks) {

        for (BigGamePack bigGamePack : bigGamePacks) {
            bigGamePackDao.saveOrUpdate(bigGamePack);
        }
    }

    @Override
    public int deleteByMarketAppIds(List<Integer> asList) {

        if (asList != null && asList.size() > 0) {
            for (Integer id : asList) {
                List<BigGamePack> bigGameList = bigGamePackDao.getBigGamePacksByMarketAppid(id);
                if (null != bigGameList && bigGameList.size() > 0) {
                    for (BigGamePack bg : bigGameList) {
                        bigGamePackDao.delete(bg);
                    }
                }
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteByIds(List<Integer> asList) {

        if (asList != null && asList.size() > 0) {
            for (Integer id : asList) {
                BigGamePack bp = bigGamePackDao.get(id);
                if (null != bp) {
                    bigGamePackDao.delete(bp);
                }
            }
            return 1;
        }
        return 0;
    }
}
