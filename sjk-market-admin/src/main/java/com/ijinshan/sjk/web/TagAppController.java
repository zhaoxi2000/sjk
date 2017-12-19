package com.ijinshan.sjk.web;

import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.po.AppAndTag;
import com.ijinshan.sjk.po.ViewTagApps;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.RollinfoService;
import com.ijinshan.sjk.service.TagRelationshipService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/tagapp")
public class TagAppController {
    private static final Logger logger = LoggerFactory.getLogger(TagAppController.class);
    @Resource(name = "rollinfoServiceImpl")
    private RollinfoService rollinfoService;

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "tagRelationshipServiceImpl")
    private TagRelationshipService tagRelationshipService;

    // @RequestMapping(value = "/tagapps.d", produces =
    // "application/json;charset=UTF-8")
    // public String search(@RequestParam short catalog, @RequestParam(required
    // = false) Integer subCatalog,
    // @RequestParam int page, @RequestParam int rows, @RequestParam(required =
    // false) String keywords,
    // @RequestParam(required = false) String sort, @RequestParam(required =
    // false) String order) {
    // appService.search(catalog, subCatalog, page, rows, keywords, sort,
    // order);
    // return null;
    // }

    @RequestMapping(value = "/normaltag-search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String normaltagSearch(@RequestParam(required = false) Integer tagId,
            @RequestParam(required = false) Integer catalog, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        Short tagType = (short) TagType.NormalTag.getVal();
        List<ViewTagApps> list = tagRelationshipService.searchAppAndTag(tagId, catalog, tagType, page, rows, keywords,
                sort, order);
        Long count = tagRelationshipService.countForSearchingAppAndTag(tagId, catalog, tagType, keywords);
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("rows", list);
        output.put("total", count == null ? 0 : count);
        return output.toJSONString();
    }

    @RequestMapping(value = "/topic-search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String topicSearch(@RequestParam(required = false) Integer tagId, @RequestParam int page,
            @RequestParam int rows, @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        Short tagType = (short) TagType.TOPIC.getVal();
        List<ViewTagApps> list = tagRelationshipService.searchAppAndTag(tagId, null, tagType, page, rows, keywords,
                sort, order);
        Long count = tagRelationshipService.countForSearchingAppAndTag(tagId, null, tagType, keywords);
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("rows", list);
        output.put("total", count == null ? 0 : count);
        return output.toJSONString();
    }

    @RequestMapping(value = "/tags/{appId}.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String tagsByAppId(@PathVariable int appId) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<AppAndTag> list = tagRelationshipService.getAppAndTagsByAppId(appId);
            output.put("data", list);
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString();
    }

    @RequestMapping(value = "/save-tagapp.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveAppAndTag(@RequestParam Integer[] appId, @RequestParam Integer[] tagId,
            @RequestParam String[] tagName, @RequestParam Short[] tagType) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {

            tagRelationshipService.saveAppAndTag(appId, tagId, tagName, tagType);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString();
    }

    @RequestMapping(value = "/save.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveTagApp(AppAndTag tagApp) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            if (tagApp.getId() > 0) {
                tagRelationshipService.updateAppAndTag(tagApp);
            } else {
                tagRelationshipService.saveAppAndTag(tagApp);
            }
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString();
    }

    @RequestMapping(value = "/del.json", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = tagRelationshipService.deleteTagApp(id);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
        }
        return output.toJSONString();
    }

    @RequestMapping(value = "/sort.json", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String sort(@RequestParam Integer[] ids, @RequestParam Integer[] ranks) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = tagRelationshipService.updateAppAndTagSort(ids, ranks);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
        }
        return output.toJSONString();
    }

}
