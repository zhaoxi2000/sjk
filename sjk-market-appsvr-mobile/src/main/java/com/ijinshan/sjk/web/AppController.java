package com.ijinshan.sjk.web;

import static com.ijinshan.sjk.util.SvrResult.ERROR;
import static com.ijinshan.sjk.util.SvrResult.OK;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

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
import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.BigGamePackService;
import com.ijinshan.sjk.service.KeywordService;
import com.ijinshan.sjk.service.MetroService;
import com.ijinshan.sjk.service.SearchService;
import com.ijinshan.sjk.service.SysDictionaryService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.AppVo;
import com.ijinshan.sjk.vo.MetroMobileParamVO;
import com.ijinshan.sjk.vo.MetroOldMobileVO;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.RecommendWordVo;
import com.ijinshan.sjk.vo.pc.App4Summary;
import com.ijinshan.sjk.vo.pc.LatestApp;
import com.ijinshan.util.Pager;

@Controller
@RequestMapping("/app/api")
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    private static final int defaultCacheTime = 5 * 60;// 默认单位为秒

    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "searchServiceImpl")
    private SearchService searchService;

    @Resource(name = "metroServiceImpl")
    private MetroService metroService;

    @Resource(name = "sysDictionaryServiceImpl")
    private SysDictionaryService sysDictionaryService;

    @Resource(name = "bigGamePackServiceImpl")
    private BigGamePackService bigGamePackService;

    @Resource(name = "keywordServiceImpl")
    private KeywordService keywordService;

    @Resource(name = "emptyArray")
    private List<?> emptyArray;

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/search.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(@RequestParam String q, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows, @RequestParam(required = false) Integer noAds,
            @RequestParam(required = false) Integer official) {
        q = q.trim().toLowerCase();

        List<MobileSearchApp> list = null;
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        try {
            String targetQ = q;
            Keyword sourceKeywordModel = searchService.getTargetKeyword(targetQ);
            if (sourceKeywordModel != null && sourceKeywordModel.getTargetKeyword() != null
                    && !sourceKeywordModel.getTargetKeyword().isEmpty()) {
                targetQ = sourceKeywordModel.getTargetKeyword();
            }

            // System.currentTimeMillis();
            Pager<MobileSearchApp> pager = searchService.search(targetQ, sourceKeywordModel, page, rows, noAds,
                    official);
            list = pager.getResult();
            // System.currentTimeMillis() - startTime;
            if (list == null || list.isEmpty()) {
            } else {
                server.put("count", list.size());
            }
            output.put("total", pager.getRows());

            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (IllegalArgumentException e) {
            server.put("code", SvrResult.CLIENT_PARAMS_ERROR.getCode());
            server.put("msg", SvrResult.CLIENT_PARAMS_ERROR.getMsg());
            logger.error("Exception", e);
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        // output.put("costTime", cost / 1000.0d);
        output.put("data", list);
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/quicktips.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchBox(@RequestParam String q) {
        q = q.trim().toLowerCase();
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        String[] tips = null;
        try {
            tips = searchService.quickTips(q);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        output.put("data", tips);
        return output.toJSONString(jsonStyle);
    }

    /**
     * 分类排序列表
     * 
     * @param catalog
     * @param subCatalog
     * @param page
     * @param rows
     * @param downloads
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/app-catalog-order.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appCatalog(@RequestParam short catalog, @RequestParam(required = false) Integer subCatalog,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) Integer sortType) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            Pager<MobileSearchApp> pager = appService.getSearchAppListByParams(catalog, subCatalog, page, rows,
                    sortType);
            // if(null != pager)
            // output.put("total", pager.getRows());
            output.put("data", pager.getResult());
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (IllegalArgumentException e) {
            server.put("code", SvrResult.CLIENT_PARAMS_ERROR.getCode());
            server.put("msg", SvrResult.CLIENT_PARAMS_ERROR.getMsg());
            logger.error("Exception", e);
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    /**
     * 根据id查询对应的APP
     * 
     * @param id
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/app/{id}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appForMobile(@PathVariable int id) {
        AppVo app = appService.getAppVoById(id);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("data", app);

        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    /**
     * 首页按下载量统计排序APP列表接口
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
    @RequestMapping(value = "/cdn/countdownload.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String latest(@RequestParam(required = false)  Short catalog, @RequestParam(required = false) Integer subCatalog,
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
     * 移动应用的色块接口
     * 
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/mobile-metroinfo.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String mobileMetroinfo() {
        List<MetroOldMobileVO> list = metroService.getMoFeaturedlist();

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("data", list);
        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    /**
     * 移动应用的色块新定义接口
     * 
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = { "/mobile/mobile-metroinfolist.json", "/cdn/mobile/mobile-metroinfolist.json" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String newMobileMetroinfo() {
        Map<Integer, MetroMobileParamVO> map = metroService.getMetroMobileList();

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("A", map.get(1));
        output.put("B", map.get(2));
        output.put("C", map.get(3));
        output.put("D", map.get(4));

        output.put("result", server);
        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
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
    public String searchRecommend(@RequestParam int page, @RequestParam int rows) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            final long count = page == 1 ? keywordService.getRecommendWordVosCount() : -1;
            List<RecommendWordVo> list = keywordService.getRecommendWordVos(page, rows);
    
            output.put("total", count);
            
            if (list != null && !list.isEmpty()) {
                output.put("data", list);
            } else {
                output.put("data", emptyArray);
            }
            
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
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

    @RequestMapping(value = "/cdn/increment.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String incrementSort(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows, @RequestParam(required = false) Short catalog) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            Pager<App4Summary> pager = appService.getIncrement(page, rows, catalog);
            if (null != pager && pager.getResult() != null)
                output.put("total", pager.getRows());
            output.put("data", pager.getResult());
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (IllegalArgumentException e) {
            server.put("code", SvrResult.CLIENT_PARAMS_ERROR.getCode());
            server.put("msg", SvrResult.CLIENT_PARAMS_ERROR.getMsg());
            logger.error("Exception", e);
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }
}
