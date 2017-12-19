package com.ijinshan.sjk.web;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.MoFeatured;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.MoFeaturedService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/featuredapp")
public class MoFeaturedController {
    private static final Logger logger = LoggerFactory.getLogger(MoFeaturedController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "moFeaturedServiceImpl")
    private MoFeaturedService moFeaturedService;

    @RequestMapping(value = "/save.d", produces = "application/json;charset=UTF-8")
    public String saveOrUpdate(MultipartHttpServletRequest multipartReq, MoFeatured moFeatured, Model model) {
        try {
            if (moFeatured.getPicType() == 3) {
                String bigPics = StringUtils.defaultIfEmpty(moFeatured.getBigPics(), "").replace(",,", "");
                moFeatured.setBigPics(StringUtils.stripEnd(StringUtils.stripStart(bigPics, ","), ","));
            } else {
                String pics = StringUtils.defaultIfEmpty(moFeatured.getPics(), "").replace(",,", "");
                moFeatured.setPics(StringUtils.stripEnd(StringUtils.stripStart(pics, ","), ","));
            }
            moFeaturedService.saveOrUpdate(multipartReq, moFeatured);
            model.addAttribute("rstCode", 0);
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
        }
        return "/moFeatured/save";
    }

    /* 【搜索】 */
    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam(required = false) Short type,
            @RequestParam(required = false) Boolean hidden, @RequestParam(required = false) Boolean deleted,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<MoFeatured> list = moFeaturedService.search(type, hidden, deleted, keywords, page, rows, sort, order);
            Long count = moFeaturedService.countForSearching(type, hidden, deleted, keywords);
            output.put("result", server);
            output.put("rows", list);
            output.put("total", count == null ? 0 : count);
            return output.toJSONString();
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/{id}.del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String del(@PathVariable int id, @RequestParam String type) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = moFeaturedService.delete(id, type);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/hide.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hide(@RequestParam Integer[] id, @RequestParam String type) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (id == null || id.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }

        try {
            boolean result = moFeaturedService.updateHide(Arrays.asList(id), type);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", "数目不对");
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/deleted.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateDeleted(@RequestParam Integer[] id, @RequestParam String type, @RequestParam Boolean deleted) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (id == null || id.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            boolean result = moFeaturedService.updateDeleted(Arrays.asList(id), type, deleted);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", "数目不对");
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/show.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String show(@RequestParam Integer[] id, @RequestParam String type) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (id == null || id.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            boolean result = moFeaturedService.updateShow(Arrays.asList(id), type);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", "数目不对");
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/sort.json", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String sort(@RequestParam Integer[] ids, @RequestParam Integer[] ranks) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = moFeaturedService.updateSort(ids, ranks);
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
