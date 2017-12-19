package com.ijinshan.sjk.web;

import static com.ijinshan.sjk.util.SvrResult.ERROR;
import static com.ijinshan.sjk.util.SvrResult.OK;

import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.annotation.Cacheable;
import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.ntxservice.AppRedisService;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;
import com.ijinshan.util.Pager;

@Controller
@RequestMapping("/app/api")
public class AppRedisController {
    private static final Logger logger = LoggerFactory.getLogger(AppRedisController.class);
    private static final int defaultCacheTime = 5 * 60;// 默认单位为秒

    @Resource(name = "emptyArray")
    private List<?> emptyArray;

    @Resource(name = "jsonStyle")
    private JSONStyle jsonStyle;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "appRedisServiceImpl")
    private AppRedisService appRedisService;

    
    /**
     * 按下载量排名的"最热门"应用列表.
     * 
     * @param catalog
     * @param subCatalog
     * @param page
     * @param rows
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/r/hot.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hotRedis(@RequestParam short catalog, @RequestParam(required = false) Integer subCatalog,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) Integer sortType,
            @RequestParam(required = false) Integer noAds, @RequestParam(required = false) Integer noVirus,
            @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            if (null == subCatalog) subCatalog = (int) catalog;
            if (null == noAds) noAds = 0;
            if (null == noVirus) noVirus = 0;
            if (null == official)  official = 0;
            if (null == sortType)  sortType = 0;
          //noVirus 参数暂时不用
            final Pager<App4Summary> pager = appRedisService.getHotDownload(subCatalog, page, rows, sortType, noAds,
                    noVirus, official);
            
            if (pager != null) {
                output.put("total", pager.getRows());
                output.put("data", pager.getResult());
            } else {
                output.put("data", emptyArray);
            }

            server.put("code", OK.getCode());
            server.put("msg", OK.getMsg());
        } catch (IllegalArgumentException e) {
            server.put("code", SvrResult.CLIENT_PARAMS_ERROR.getCode());
            server.put("msg", SvrResult.CLIENT_PARAMS_ERROR.getMsg());
            logger.error("Exception", e);
        } catch (Exception e) {
            server.put("code", ERROR.getCode());
            server.put("msg", ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }
    
    /**
     * 最小的信息输出排行榜. <br/>
     * 今日热门. 下载排行.
     * 
     * @param catalog
     * @param subCatalog
     * @param page
     * @param rows
     * @param sortType
     * @param noAds
     * @param noVirus
     * @param official
     * @return
     */
    @Cacheable(exp = 2 * 60 * 60)
    @RequestMapping(value = "/cdn/r/rank.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String rankRedis(@RequestParam(required = false) Integer catalog,
            @RequestParam(required = false) Integer subCatalog, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) Integer sortType, @RequestParam(required = false) Integer noAds,
            @RequestParam(required = false) Integer noVirus, @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            if (null == subCatalog) subCatalog = (int) catalog;
            if (null == noAds) noAds = 0;
            if (null == official)  official = 0;
            if (null == noVirus) noVirus = 0;
            if(null == sortType) sortType = 0;
            
            List<SimpleRankApp> list = appRedisService.getSimpleRankApp(subCatalog, page, rows, sortType, noAds,
                    noVirus, official);
            output.put("data", list);
            server.put("code", OK.getCode());
            server.put("msg", OK.getMsg());
        } catch (Exception e) {
            server.put("code", ERROR.getCode());
            server.put("msg", ERROR.getMsg());
            logger.error("Exception", e.getMessage());
        }
        return output.toJSONString(jsonStyle);
    }

    /**
     * 最新应用的列表<br />
     * 控制小量的时间缓存.
     * 
     * @param catalog
     * @param subCatalog
     * @param date
     * @param page
     * @param rows
     * @param noAds
     * @param noVirus
     * @param official
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/r/latest.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String latestRedis(@RequestParam short catalog, @RequestParam(required = false) Integer subCatalog,
            @RequestParam(required = false) Long date,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) Integer noAds,
            @RequestParam(required = false) Integer noVirus, @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            if (null == subCatalog) subCatalog = (int) catalog;
            if (null == noAds) noAds = 0;
            if (null == noVirus) noVirus = 0;
            if (null == official)  official = 0;

            Pager<LatestApp> pager = appRedisService.getLatest(subCatalog,date, page, rows, noAds, noVirus, official);
            if (pager != null) {
                output.put("total", pager.getRows());
                if (pager.getResult() != null && !pager.getResult().isEmpty()) {
                    output.put("data", pager.getResult());
                } else {
                    output.put("data", emptyArray);
                }
            } else {
                output.put("data", emptyArray);
            }

            server.put("code", OK.getCode());
            server.put("msg", OK.getMsg());
        } catch (Exception e) {
            server.put("code", ERROR.getCode());
            server.put("msg", ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

//    /**
//     * 最新的日期列表,降序排列.
//     * 
//     * @param catalog
//     * @param subCatalog
//     * @param page
//     * @param rows
//     * @return
//     */
//    @Cacheable(exp = defaultCacheTime)
//    @RequestMapping(value = "/cdn/r/latest-dates.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String latestDate(@RequestParam short catalog, @RequestParam(required = false) Integer subCatalog,
//            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) Integer noAds,
//            @RequestParam(required = false) Integer noVirus, @RequestParam(required = false) Integer official) {
//        JSONObject output = new JSONObject();
//        JSONObject server = new JSONObject();
//        output.put("result", server);
//        try {
//            if (null == subCatalog) {
//                subCatalog = (int) catalog;
//            }
//            if (null == noAds)
//                noAds = 0;
//            if (null == noVirus)
//                noVirus = 0;
//            if (null == official)
//                official = 0;
//
//            Pager<String> pager = appRedisService.getLatestDates(subCatalog, page, rows, noAds, noVirus, official);
//            if (pager != null) {
//                output.put("total", pager.getRows());
//                if (pager.getResult() != null && !pager.getResult().isEmpty()) {
//                    output.put("data", pager.getResult());
//                } else {
//                    output.put("data", emptyArray);
//                }
//            } else {
//                output.put("data", emptyArray);
//            }
//
//            server.put("code", OK.getCode());
//            server.put("msg", OK.getMsg());
//        } catch (Exception e) {
//            server.put("code", ERROR.getCode());
//            server.put("msg", ERROR.getMsg());
//            logger.error("Exception", e);
//        }
//        return output.toJSONString(jsonStyle);
//    }

}
