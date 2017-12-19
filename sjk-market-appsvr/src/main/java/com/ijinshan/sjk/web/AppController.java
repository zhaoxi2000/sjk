package com.ijinshan.sjk.web;

import static com.ijinshan.sjk.util.SvrResult.ERROR;
import static com.ijinshan.sjk.util.SvrResult.OK;

import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.annotation.Cacheable;
import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.BigGamePackService;
import com.ijinshan.sjk.service.KeywordService;
import com.ijinshan.sjk.service.MetroService;
import com.ijinshan.sjk.service.SysDictionaryService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.AppAndBigGamesVo;
import com.ijinshan.sjk.vo.LatestDate;
import com.ijinshan.sjk.vo.MetroVO;
import com.ijinshan.sjk.vo.RecommendWordVo;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.sjk.vo.pc.SimpleRankApp;
import com.ijinshan.sjk.vo.pc.biggame.BigGamePacks;

@Controller
@RequestMapping("/app/api")
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private static final int defaultCacheTime = 5 * 60;// 默认单位为秒

    @Resource(name = "emptyArray")
    private List<?> emptyArray;

    @Resource(name = "jsonStyle")
    private JSONStyle jsonStyle;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "metroServiceImpl")
    private MetroService metroService;

    @Resource(name = "sysDictionaryServiceImpl")
    private SysDictionaryService sysDictionaryService;

    @Resource(name = "bigGamePackServiceImpl")
    private BigGamePackService bigGamePackService;

    @Resource(name = "keywordServiceImpl")
    private KeywordService keywordService;
    
    @Cacheable(exp = 20 * 60)
    @RequestMapping(value = "/cdn/rollinfo.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String rollinfo() {
        List<Rollinfo> list = appService.getRollinfo();
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("data", list);
        output.put("recommendCount", sysDictionaryService.getAppsRollRecommendNum());

        server.put("code", OK.getCode());
        server.put("msg", OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/{id}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String app(@PathVariable int id) {
        App app = appService.get(id);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("data", app);

        server.put("code", OK.getCode());
        server.put("msg", OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

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
    @RequestMapping(value = "/cdn/hot.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hot(@RequestParam short catalog, @RequestParam(required = false) Integer subCatalog,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) Integer sortType,
            @RequestParam(required = false) Integer noAds, @RequestParam(required = false) Integer noVirus,
            @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            final long count = page == 1 ? appService
                    .getHotDownloadCount(catalog, subCatalog, noAds, noVirus, official) : -1;
            output.put("total", count);

            final List<App4Summary> list = appService.getHotDownload(catalog, subCatalog, page, rows, sortType, noAds,
                    noVirus, official);
            if (list != null && !list.isEmpty()) {
                output.put("data", list);
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
    @RequestMapping(value = "/cdn/rank.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String rank(@RequestParam(required = false) Integer catalog,
            @RequestParam(required = false) Integer subCatalog, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) Integer sortType, @RequestParam(required = false) Integer noAds,
            @RequestParam(required = false) Integer noVirus, @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<SimpleRankApp> list = appService.getSimpleRankApp(catalog, subCatalog, page, rows, sortType, noAds,
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
    @RequestMapping(value = "/cdn/latest.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String latest(@RequestParam short catalog, @RequestParam(required = false) Integer subCatalog,
            @RequestParam(required = false) Long date, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) Integer noAds, @RequestParam(required = false) Integer noVirus,
            @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            final long count = page == 1 ? appService.getLatestCount(catalog, subCatalog, date, noAds, noVirus,
                    official) : -1;
            output.put("total", count);

            final List<LatestApp> list = appService.getLatest(catalog, subCatalog, date, page, rows, noAds, noVirus,
                    official);
            if (list != null && !list.isEmpty()) {
                output.put("data", list);
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
     * 最新的日期列表,降序排列.
     * 
     * @param catalog
     * @param subCatalog
     * @param page
     * @param rows
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/latest-dates.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String latestDate(@RequestParam short catalog, @RequestParam(required = false) Integer subCatalog,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) Integer noAds,
            @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            final long count = page == 1 ? appService.getLatestDatesCount(catalog, subCatalog, noAds, official) : -1;
            output.put("total", count);

            final List<String> list = appService.getLatestDates(catalog, subCatalog, page, rows, noAds, official);
            if (list != null && !list.isEmpty()) {
                output.put("data", list);
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
     * 取出所有分类的最近一天日期
     * 
     * @return
     */
    @Cacheable(exp = 30 * 60)
    @RequestMapping(value = "/cdn/last-date.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String lastDate(@RequestParam(required = false) Integer noAds,
            @RequestParam(required = false) Integer official) {
        List<LatestDate> list = appService.getLatestDate(noAds, official);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("data", list);

        server.put("code", OK.getCode());
        server.put("msg", OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = 10 * 60)
    @RequestMapping(value = "/cdn/metroinfo.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String metroinfo() {
        List<MetroVO> list = metroService.list();

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("metro", list);
        output.put("version", "1");

        server.put("code", OK.getCode());
        server.put("msg", OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    /**
     * 一个App对应多个安装包
     * 
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/big-game.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appToManyIntallPackages(@RequestParam(required = true) short catalog,
            @RequestParam(required = false) Integer subCatalog) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<AppAndBigGamesVo> list = appService.getAppListByCatalog(catalog, subCatalog);
            output.put("data", list);
            server.put("code", OK.getCode());
            server.put("msg", OK.getMsg());
        } catch (Exception e) {
            server.put("code", ERROR.getCode());
            server.put("msg", ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    /**
     * 根据cpu类型,phoneId,subCatalog新增包列表
     * 
     * @param cputype
     * @param phoneId
     * @param brand
     * @param product
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = { "/apppack-list.json", "/cdn/apppack-list.json" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPackageListByCpu(@RequestParam(required = false) Integer cputype,
            @RequestParam(required = false) String phoneId, @RequestParam(required = false) String subCatalog) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            if (null == cputype && null == phoneId && null == subCatalog && null == subCatalog) {
                // List<AppForAllBigGameVo> appList =
                // appService.getAllAppList();
                // List<BigGamePacks> getAllBigGameList =
                // bigGamePackService.getAllBigGameList(); // mybaties
                List<BigGamePacks> getAllBigGameList = bigGamePackService.getBigGamePakcList(); // list查询后进行封装
                output.put("data", getAllBigGameList);
            } else {
                // List<AppForAllBigGameVo> applist =
                // appService.getApplistByParams(cputype, phoneId, subCatalog);
                List<BigGamePacks> applist = bigGamePackService.getApplistByParams(cputype, phoneId, subCatalog);
                output.put("data", applist);
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

    /**
     * 根据marketAppId,brand(品牌),phoneId查询对应的安装包
     * 
     * @param cputype
     * @param phoneId
     * @param brand
     * @param product
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = { "/installpack-list.json", "/cdn/installpack-list.json" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getInstallPackageByParam(@RequestParam int marketAppId, @RequestParam String product,
            @RequestParam String brand) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<BigGamePack> bigGamePakcList = bigGamePackService.findBigPackListByParams(marketAppId, product, brand);
            output.put("data", bigGamePakcList);
            server.put("code", OK.getCode());
            server.put("msg", OK.getMsg());
        } catch (Exception e) {
            server.put("code", ERROR.getCode());
            server.put("msg", ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    /**
     * 热门搜索推荐列表
     * 
     * @return
     */
    @Cacheable(exp = 30 * 60)
    @RequestMapping(value = "/cdn/searchrecommend.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchRecommend(@RequestParam int rows) {
        List<RecommendWordVo> list = keywordService.getRecommendWordVos(rows);

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("data", list);
        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }
    
    /**
     * 根据marketAppId,cputype(cpu类型),phoneId查询对应的安装包
     * 
     * @param marketAppId
     * @param phoneId
     * @param cputype
     * @return
     */
    @RequestMapping(value = { "/install-pack.json", "/cdn/install-pack.json" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getInstallPackByType(@RequestParam int marketAppId, @RequestParam int phoneId,
            @RequestParam int cputype) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<BigGamePack> bigGamePack = bigGamePackService.findBigGamePackByParams(marketAppId, phoneId, cputype);
            output.put("data", bigGamePack);
            server.put("code", OK.getCode());
            server.put("msg", OK.getMsg());
        } catch (Exception e) {
            server.put("code", ERROR.getCode());
            server.put("msg", ERROR.getMsg());
            logger.error("Exception", e);
        }

        return output.toJSONString(jsonStyle);
    }

    /**
     * 按上升变化量降序排序
     * 
     * @param catalog
     * @param subCatalog
     * @param page
     * @param rows
     * @return
     */
    @Cacheable(exp = 20 * 60)
    @RequestMapping(value = "/cdn/increment.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String incrementSort(@RequestParam(required = false) Short catalog) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<App4Summary> list = appService.getIncrement(catalog);

        if (CollectionUtils.isNotEmpty(list)) {
            output.put("data", list);
        } else {
            output.put("data", emptyArray);
        }

        server.put("code", OK.getCode());
        server.put("msg", OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

}
