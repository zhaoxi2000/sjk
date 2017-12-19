package com.ijinshan.sjk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;

public interface AppRedisMapper {

    List<LatestApp> getLatest(@Param(value = "ids") List<Integer> ids);

    List<App4Summary> getApps4Summary(@Param(value = "ids") List<Integer> ids);

    List<SimpleRankApp> getSimpleRankApp(@Param(value = "ids") List<Integer> ids);

    List<String> getLatestDates(@Param(value = "ids") List<Integer> ids);

}
