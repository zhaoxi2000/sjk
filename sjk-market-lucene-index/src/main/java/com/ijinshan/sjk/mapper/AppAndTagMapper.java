/**
 * 
 */
package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ijinshan.sjk.vo.index.Tags4App;

/**
 * @author Linzuxiong
 */
public interface AppAndTagMapper {
    /**
     * 按appIs列表搜索，modify by lujunsheng
     * @param appIds
     * @return
     */
    
    List<Tags4App> getTags4App(@Param(value = "appIds") List<Integer> appIds);

}
