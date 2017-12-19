package com.kingsoft.sjk.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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

import com.kingsoft.sjk.annotation.Cacheable;
import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.po.AppDetail;
import com.kingsoft.sjk.po.AppType;
import com.kingsoft.sjk.po.ExtendData;
import com.kingsoft.sjk.po.Keywords;
import com.kingsoft.sjk.po.Topic;
import com.kingsoft.sjk.service.AppRankService;
import com.kingsoft.sjk.service.AppsService;
import com.kingsoft.sjk.service.KeywordsService;
import com.kingsoft.sjk.service.SearchService;
import com.kingsoft.sjk.util.SvrResult;

/**
 * @author LinZuXiong
 */
@Controller
@RequestMapping(value = "/apps")
public class AppsController {
    private static final Logger logger = LoggerFactory.getLogger(AppsController.class);

    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    private static final int defaultCacheTime = 30 * 60;// 默认单位为秒

    @Resource(name = "appConfig")
    private AppConfig config;

    @Resource(name = "searchServiceImpl")
    private SearchService searchService;

    @Resource(name = "appsServiceImpl")
    private AppsService appsService;

    @Resource(name = "appRankServiceImpl")
    private AppRankService appRankService;

    @Resource(name = "keywordsServiceImpl")
    private KeywordsService keywordsService;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    /**
     * 按类别翻页
     * 
     * @param parentId
     * @param subTypeId
     * @param pageNumber
     * @param orderByColumnId
     *            约定排序的列
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/list/{parentId}/{subTypeId}/{pageNumber}/{pageSize}/{orderByColumnId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(@PathVariable int parentId, @PathVariable int subTypeId, @PathVariable int pageNumber,
            @PathVariable int pageSize, @PathVariable int orderByColumnId) {
        List<App> list = appsService.list(parentId, subTypeId, pageNumber, pageSize, orderByColumnId);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    /**
     * 推荐的软件/游戏
     * 
     * @param parentId
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/recommend/{parentId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String recommendList(@PathVariable int parentId) {
        List<App> list = appsService.recommend(parentId);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/types/{parentId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAppTypes(@PathVariable int parentId) {
        List<AppType> list = appsService.getAppTypes(parentId);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    /**
     * 单个软件的详细信息.
     * 
     * @param appId
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/app/{appId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getApp(@PathVariable int appId) {
        AppDetail app = appsService.getAppDetail(appId);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        if (app == null) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        }
        res.put("data", app);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    /**
     * 默认大类别的排行榜
     * 
     * @param parentId
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/rank/{parentId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appDefaultRank(@PathVariable int parentId) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<App> list = appRankService.getAppDefultRank(parentId, appConfig.getAppsRankTopNum());
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    /**
     * 单分类下的排行榜
     * 
     * @param parentId
     * @param subCatalog
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/categoryrank/{parentId}/{subCatalog}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appCategoryRank(@PathVariable int parentId, @PathVariable int subCatalog) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<App> list = appRankService.getAppCategoryRank(parentId, subCatalog, appConfig.getAppsRankTopNum());
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/search.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(@RequestParam String keyword, @RequestParam String typeId) {
        keyword = keyword.trim();
        int appTypeId = Integer.parseInt(typeId);
        List<App> list = searchService.search(appTypeId, keyword);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        if (list == null || list.size() == 0) {
            List<String> suggestions = searchService.spellChecker(appTypeId, keyword);
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
            server.put("len", 0);
            res.put("keywordTips", suggestions);
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/searchtips.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchTips(@RequestParam String keyword, @RequestParam String typeId) {
        keyword = keyword.trim();
        int appTypeId = Integer.parseInt(typeId);
        List<String> list = searchService.getSpellSuggestion(appTypeId, keyword);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        if (list == null || list.size() == 0) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/initindex/{typeId}/{subTypeId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String initIndex(@PathVariable int typeId, @PathVariable int subTypeId) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            this.searchService.initAppIndex(typeId == -1 ? null : typeId, subTypeId == -1 ? null : subTypeId);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        }
        logger.debug("#initIndex typeId: {}  subTypeId: {}", typeId, subTypeId);
        res.put("data", "");
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/inittrietree/{typeId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String initIndex(@PathVariable String typeId) {
        logger.debug("#initIndex");
        int appTypeId = Integer.parseInt(typeId);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            this.searchService.initTrieDict(appTypeId);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        }
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/keywords/hot.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hotKeywords() {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<Keywords> list = keywordsService.getHotKeywords();
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/ext-data/{appId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String extendData(@PathVariable int appId) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<ExtendData> list = appsService.getAppExtendDatas(appId);
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    // /apps/power/tuijian/{typeId}/{start}/{count}.json
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/power/tuijian/{typeId}/{start}/{count}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerTuiJian(@PathVariable int typeId, @PathVariable int start, @PathVariable int count) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<App> list = appsService.getPowerTuiJian(typeId, start, count);
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    // /apps/power/tuijian/{typeId}/{start}/{count}.json
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/powerchannel/tuijian/{typeId}/{start}/{count}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerTuiJianChannel(@PathVariable int typeId, @PathVariable int start, @PathVariable int count) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<App> list = appsService.getPowerChannelTuiJian(typeId, start, count);
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/power/tuijian/topic/{topicid}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerTuiJianTopic(@PathVariable int topicid) {
        Topic topic = appsService.getPowerTuiJianTopic(topicid);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        if (topic == null) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        }
        res.put("data", topic);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/power/tuijian/topic/apps/{topicid}/{start}/{count}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerTuiJianTopicApps(@PathVariable int topicid, @PathVariable int start, @PathVariable int count) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<App> list = appsService.getPowerTuiJianTopicApps(topicid, start, count);
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/power/tuijian/topics/{status}/{start}/{count}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerTuiJianTopics(@PathVariable int status, @PathVariable int start, @PathVariable int count) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<Topic> list = appsService.getPowerTuiJianTopics(status, start, count);

        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/powerchannel/tuijian/topic/{topicid}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerChannelTuiJianTopic(@PathVariable int topicid) {
        Topic topic = appsService.getPowerChannelTuiJianTopic(topicid);
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();

        if (topic == null) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        }
        res.put("data", topic);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/powerchannel/tuijian/topic/apps/{topicid}/{start}/{count}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerChannelTuiJianTopicApps(@PathVariable int topicid, @PathVariable int start,
            @PathVariable int count) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<App> list = appsService.getPowerChannelTuiJianTopicApps(topicid, start, count);
        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/powerchannel/tuijian/topics/{status}/{start}/{count}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String powerChannelTuiJianTopics(@PathVariable int status, @PathVariable int start, @PathVariable int count) {
        JSONObject res = new JSONObject();
        JSONObject server = new JSONObject();
        List<Topic> list = appsService.getPowerChannelTuiJianTopics(status, start, count);

        if (list == null || list.isEmpty()) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", SvrResult.NO_DATA.getMsg());
        } else {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            // for server developers debugger.
            server.put("len", list.size());
        }
        res.put("data", list);
        res.put("result", server);
        return res.toJSONString(jsonStyle);
    }

    // statistics
    @RequestMapping(value = "/power/package/statistics/{appId}/{opKind}.c", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void powerClickPackage(@PathVariable int appId, @PathVariable int opKind, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Cache-Control", "max-age=0");
        resp.setStatus(HttpURLConnection.HTTP_OK);
        resp.flushBuffer();
        resp.getWriter().flush();
        resp.getWriter().close();

        boolean gather = appsService.gatherStat(appId, opKind);
        if (gather) {
        } else {
            logger.error("Save to db fail.");
        }
    }
}
