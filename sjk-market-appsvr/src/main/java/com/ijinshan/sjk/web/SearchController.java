package com.ijinshan.sjk.web;

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
import com.ijinshan.sjk.ntxservice.QuickTipsService;
import com.ijinshan.sjk.ntxservice.SearchService1;
import com.ijinshan.sjk.ntxservice.SpellCheckerService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.pc.SearchApp;
import com.ijinshan.util.Pager;

@Controller
@RequestMapping("/app/api/")
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private static final int defaultCacheTime = 6 * 60;// 默认单位为秒

    @Resource(name = "jsonStyle")
    private JSONStyle jsonStyle;

    @Resource(name = "emptyArray")
    private List<?> emptyArray;

    @Resource(name = "quickTipsServiceImpl")
    private QuickTipsService quickTipsService;

    @Resource(name = "searchService1Impl")
    private SearchService1 search;

    @Resource(name = "spellCheckerServiceImpl")
    private SpellCheckerService spellCheckerService;

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/s/search.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(@RequestParam String q, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) Integer noAds, @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            Pager<SearchApp> pager = search.search(q, page, rows, noAds, official);
            if (pager != null && pager.getRows() > 0) {
                List<SearchApp> list = pager.getResult();
                output.put("total", pager.getRows());
                if (list != null && !list.isEmpty()) {
                    output.put("data", list);
                    output.put("count", list.size());
                } else {
                    output.put("data", emptyArray);
                }
            } else {
                output.put("total", 0);
                if (spellCheckerService != null) {
                    String[] spellSuggestions = spellCheckerService.checkSpell(q);
                    output.put("spellSuggestions", spellSuggestions);
                } else {
                    output.put("spellSuggestions", null);
                }
            }
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/s/quickTips.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchBox(@RequestParam String q) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        String[] tips = null;
        try {
            tips = quickTipsService.quickTips(q);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        if (tips != null && tips.length > 0) {
            output.put("data", tips);
        } else {
            output.put("data", emptyArray);
        }
        return output.toJSONString(jsonStyle);
    }

    /*
     * 方法作废 // @Cacheable(exp = defaultCacheTime)
     * 
     * @RequestMapping(value = "/cdn/s/searchTag.json", method =
     * RequestMethod.GET, produces = "application/json;charset=UTF-8")
     * 
     * @ResponseBody public String searchTag(@RequestParam String tagName,
     * 
     * @RequestParam int page, @RequestParam int rows) { JSONObject output = new
     * JSONObject(); JSONObject server = new JSONObject(); output.put("result",
     * server); try { Pager<SearchApp> pager = search.searchAppByTag(tagName,
     * page, rows); if (pager != null && pager.getRows() > 0) { List<SearchApp>
     * list = pager.getResult(); output.put("total", pager.getRows()); if (list
     * != null && !list.isEmpty()) { output.put("data", list);
     * output.put("count", list.size()); } else { output.put("data",
     * emptyArray); } } else { output.put("total", 0); String[] spellSuggestions
     * = null; output.put("spellSuggestions", spellSuggestions); }
     * server.put("code", SvrResult.OK.getCode()); server.put("msg",
     * SvrResult.OK.getMsg()); } catch (Exception e) { server.put("code",
     * SvrResult.ERROR.getCode()); server.put("msg", SvrResult.ERROR.getMsg());
     * logger.error("Exception", e); } return output.toJSONString(jsonStyle); }
     */
}
