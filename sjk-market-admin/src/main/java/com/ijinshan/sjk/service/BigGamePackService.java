/**
 * 
 */
package com.ijinshan.sjk.service;

import java.io.Serializable;
import java.util.List;

import com.ijinshan.sjk.po.BigGamePack;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-1-10 上午11:49:50
 * </pre>
 */
public interface BigGamePackService {

    void saveBatch(List<BigGamePack> bigGamePacks);

    Serializable save(BigGamePack bigGamePack);

    void update(BigGamePack bigGamePack);

    void updateBatch(List<BigGamePack> bigGamePackss);

    List<BigGamePack> getBigGamePacksByMarketAppid(Integer marketAppId);

    void saveOrUpdateBatch(List<BigGamePack> bigGamePacks);

    int deleteByMarketAppIds(List<Integer> asList);

    int deleteByIds(List<Integer> ids);

}
