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

import com.ijinshan.sjk.po.MoAppAndTag;
import com.ijinshan.sjk.po.MoViewTagApps;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.MoTagRelationshipService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/mo-tagapp")
public class MoTagAppController {
    private static final Logger logger = LoggerFactory.getLogger(MoTagAppController.class);

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "moTagRelationshipServiceImpl")
    private MoTagRelationshipService moTagRelationshipService;

    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Search(@RequestParam(required = false) Short tagType, @RequestParam(required = false) Integer tagId,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();

        List<MoViewTagApps> list = moTagRelationshipService.searchMoAppAndTag(tagId, null, tagType, page, rows,
                keywords, sort, order);
        Long count = moTagRelationshipService.countMoAppAndTagForSearching(tagId, null, tagType, keywords);
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
            List<MoAppAndTag> list = moTagRelationshipService.getMoAppAndTagsByAppId(appId);
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
            moTagRelationshipService.saveMoAppAndTag(appId, tagId, tagName, tagType);
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
    public String saveTagApp(MoAppAndTag mTagApp) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            if (mTagApp.getId() > 0) {
                moTagRelationshipService.updateMoAppAndTag(mTagApp);
            } else {
                moTagRelationshipService.saveMoAppAndTag(mTagApp);
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
            boolean result = moTagRelationshipService.deleteMoTagApp(id);
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
            boolean result = moTagRelationshipService.updateMoAppAndTagSort(ids, ranks);
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
