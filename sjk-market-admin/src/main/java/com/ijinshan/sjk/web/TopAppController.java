package com.ijinshan.sjk.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ijinshan.sjk.po.TopApp;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.CatalogService;
import com.ijinshan.sjk.service.MarketAppService;
import com.ijinshan.sjk.service.RollinfoService;
import com.ijinshan.sjk.service.TagRelationshipService;
import com.ijinshan.sjk.service.TopAppService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/top")
public class TopAppController {
    private static final Logger logger = LoggerFactory.getLogger(TopAppController.class);

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

    @Resource(name = "topAppServiceImpl")
    private TopAppService topAppService;

    @RequestMapping(value = "/seach.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String topSearch(@RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) Integer id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        long count = topAppService.getCounts();
        List<TopApp> list = topAppService.findTopAppList(keywords, id, page, rows);
        if (list.size() > 0) {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            output.put("total", count);
            output.put("rows", list);
        } else {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "数目不对");
        }
        output.put("result", server);
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/{id}.del.d")
    @ResponseBody
    public String del(@PathVariable int id, Model model) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        List<Integer> list = new ArrayList<Integer>(1);
        try {
            list.add(Integer.valueOf(id));
            boolean del = topAppService.delete(list);
            model.addAttribute("rstCode", del ? 0 : 1);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            logger.error("Exception", e);
        }
        output.put("result", server);
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/{id}.merge.d")
    @ResponseBody
    public String merge(@PathVariable int id, Model model) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        TopApp topapp = topAppService.get(id);
        try {
            if (topapp != null) {
                model.addAttribute("topapp", topapp);
            }
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            logger.error("Exception", e);
        }
        output.put("result", server);
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/save.d")
    @ResponseBody
    public String saveOrUpdate(TopApp topApp, Model model) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            topAppService.saveOrUpdate(topApp);
            model.addAttribute("rstCode", 0);
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            logger.error("Exception", e);
        }
        output.put("result", server);
        return output.toJSONString(jsonStyle);
    }
    
    @RequestMapping(value = "/change-state.d")
    @ResponseBody
    public String updateState(@RequestParam List<Integer> ids,@RequestParam byte state){
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            topAppService.updateState(ids,state);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        output.put("result", server);
        return output.toJSONString(jsonStyle);
        
    }

}
