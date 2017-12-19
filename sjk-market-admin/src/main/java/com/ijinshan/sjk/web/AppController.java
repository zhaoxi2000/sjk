package com.ijinshan.sjk.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.AppAdmin;
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.po.Rollinfo;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.CatalogService;
import com.ijinshan.sjk.service.MarketAppService;
import com.ijinshan.sjk.service.RollinfoService;
import com.ijinshan.sjk.service.TagRelationshipService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.util.DateString;
import com.ijinshan.util.DefaultDateTime;

@Controller
@RequestMapping(value = "/admin/app")
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);
    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "marketAppServiceImpl")
    private MarketAppService marketAppService;

    @Resource(name = "tagRelationshipServiceImpl")
    private TagRelationshipService tagRelationshipService;

    @Resource(name = "catalogServiceImpl")
    private CatalogService catalogService;

    @Resource(name = "rollinfoServiceImpl")
    private RollinfoService rollinfoService;

    @RequestMapping(value = "/edit-catalog.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String editCatalog(@RequestParam short catalog, @RequestParam int subCatalog, @RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        if (ids == null || ids.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        boolean edit = appService.updateBatchCatalog(Arrays.asList(ids), catalog, subCatalog);

        if (edit) {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } else {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "数目不对");
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/hide.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hide(@RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (ids == null || ids.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        boolean hide = appService.updateHide(Arrays.asList(ids));
        if (hide) {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } else {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "数目不对");
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/show.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String show(@RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (ids == null || ids.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        StringBuilder sbOutputMessage = new StringBuilder();
        try {
            appService.updateShow(Arrays.asList(ids), sbOutputMessage);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", sbOutputMessage.toString());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "系统错误");
        }

        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/listbase.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listApp(@RequestParam short catalog, @RequestParam Integer subCatalog, @RequestParam int page,
            @RequestParam int rows) {
        // page=1&rows=20
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        List<App> list = appService.list(catalog, subCatalog, page, rows);
        output.put("rows", list);
        output.put("total", appService.count(catalog, subCatalog));
        return output.toJSONString(jsonStyle);
    }

    /**
     * @RequestParam catalog
     * @RequestParam subCatalog
     * @RequestParam page
     * @RequestParam rows
     * @RequestParam keywords
     * @RequestParam sort 排序列字段名
     * @RequestParam order 排序方式, 可以是 'asc' 或者 'desc'
     * @return
     */
    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(@RequestParam(required = false) Short catalog,
            @RequestParam(required = false) Integer subCatalog, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order,
            @RequestParam(required = false) String startdatestr, @RequestParam(required = false) String enddatestr,
            @RequestParam(required = false) Boolean official, @RequestParam(required = false) Integer audit) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<AppAdmin> list = null;
        Date startDate = null;
        Date endDate = null;
        if (StringUtils.isNotEmpty(startdatestr)) {
            startDate = DateString.StrToDate(startdatestr);
        }
        if (StringUtils.isNotEmpty(enddatestr)) {
            endDate = DateString.StrToDate(enddatestr);
        }
        list = appService.search(catalog, subCatalog, page, rows, keywords, id, sort, order, startDate, endDate,
                official, audit);
        output.put("rows", list);
        output.put("total",
                appService.countForSearching(catalog, subCatalog, keywords, id, startDate, endDate, official, audit));
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/rolling/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchForRolling(@RequestParam(required = false) Short catalog,
            @RequestParam(required = false) Integer subCatalog, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<Rollinfo> list = null;
        list = appService.searchForRolling(catalog, subCatalog, page, rows, keywords, sort, order);
        output.put("rows", list);
        output.put("total", appService.countForSearchingRolling(catalog, subCatalog, keywords));
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/rolling/del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delRolling(@RequestParam Integer[] id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            int rows = rollinfoService.deleteByAppIds(Arrays.asList(id));
            boolean del = rows == id.length;
            if (del) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.PART_DATA.getCode());
                server.put("msg", SvrResult.PART_DATA.getMsg());
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/rolling/set-recommand.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String setRecommand(@RequestParam Integer[] id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            int rows = rollinfoService.updateRecommand(Arrays.asList(id));
            boolean del = rows == id.length;
            if (del) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.PART_DATA.getCode());
                server.put("msg", SvrResult.PART_DATA.getMsg());
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/rolling/set-unrecommand.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String setUnRecommand(@RequestParam Integer[] id, Model model) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            int rows = rollinfoService.updateUnRecommand(Arrays.asList(id));
            boolean del = rows == id.length;
            if (del) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.PART_DATA.getCode());
                server.put("msg", SvrResult.PART_DATA.getMsg());
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/rolling/upd-logo.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateLogo(MultipartHttpServletRequest multipartReq, @RequestParam int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            appService.updateLogo(multipartReq, id);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/rolling/add.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addRolling(@RequestParam int appId, @RequestParam short catalog) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<Integer> appIds = new ArrayList<Integer>(1);
            appIds.add(appId);
            rollinfoService.save(appIds, catalog);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/{id}.del.d")
    public String del(@PathVariable int id, Model model) {
        List<Integer> list = new ArrayList<Integer>(1);
        list.add(Integer.valueOf(id));
        boolean del = appService.delete(list);
        model.addAttribute("rstCode", del ? 0 : 1);
        return "/save";
    }

    @RequestMapping(value = "/{id}.merge.d")
    public String merge(@PathVariable int id, Model model) {
        App app = appService.get(id);
        MarketApp marketApp = null;
        List<AppAndTag> listAppTag = null;
        if (app != null) {
            marketApp = marketAppService.getTop(app.getPkname());
            listAppTag = tagRelationshipService.getAppAndTagsByAppId(app.getId());
        }
        model.addAttribute("app", app);
        model.addAttribute("listAppTag", listAppTag);
        model.addAttribute("marketApp", marketApp);
        return "/app/merge";
    }

    @RequestMapping(value = "/{id}.getapp.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getApp(@PathVariable int id) {
        App app = appService.get(id);
        JSONObject output = new JSONObject();
        try {
            app = appService.get(id);
            output.put("data", app);
        } catch (Exception e) {
            logger.error("Exception", e);
            output.put("data", null);
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/save.d")
    public String saveOrUpdate(MultipartHttpServletRequest multipartReq, App app, Model model) {
        try {
            if (app.getApkScanTime() == null) {
                app.setApkScanTime(DefaultDateTime.getDefaultDateTime());
            }
            appService.saveOrUpdate(multipartReq, app);
            model.addAttribute("rstCode", 0);
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            logger.error("Exception", e);
        }
        return "/save";
    }

    @RequestMapping(value = "/download/update.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateDownload(@RequestParam Integer[] id, @RequestParam(required = false) Integer realDownload,
            @RequestParam int deltaDownload) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            appService.updateDownload(Arrays.asList(id), realDownload, deltaDownload);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/audit.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String audit(@RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (ids == null || ids.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            appService.updateAudit(Arrays.asList(ids));
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "系统错误");
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/off-audit.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String auditNo(@RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (ids == null || ids.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            appService.updateNoAudit(Arrays.asList(ids));
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "系统错误");
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/need-audit.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String auditNeed(@RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (ids == null || ids.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            appService.updateNoNeedAudit(Arrays.asList(ids));
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "系统错误");
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/edit-keywords.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateKeyWords(@RequestParam Integer id, @RequestParam String keywords) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        if (id == null) {
            server.put("msg", "Don't do that!The id is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            App app = appService.get(id);
            app.setKeywords(keywords);
            appService.saveOrUpdate(app);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "系统错误");
        }
        return output.toJSONString(jsonStyle);
    }
}
