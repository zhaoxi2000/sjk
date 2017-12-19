/**
 * 
 */
package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.BigGamePack;

/**
 * <pre>
 * @author HuYouzhi
 * Create on 2013-1-15 下午5:42:50
 * </pre>
 */
public interface BigGamePackDao extends BaseDao<BigGamePack> {

    public List<BigGamePack> getBigGamePacksByMarketAppid(Integer marketAppId);

}
