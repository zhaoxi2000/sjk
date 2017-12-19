package com.ijinshan.sjk.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.MoMixFeatured;
import com.ijinshan.sjk.service.MoMixFeaturedService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/mixfeatured")
public class MixFeaturedController {
    private static final Logger logger = LoggerFactory.getLogger(MixFeaturedController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Autowired
    private MoMixFeaturedService service;

    @RequestMapping(value = "/save.d", produces = "application/json;charset=UTF-8")
    public String saveOrUpdate(MultipartHttpServletRequest multipartReq, MoMixFeatured entity, Model model) {
        try {
            service.saveOrUpdate(multipartReq, entity);
            model.addAttribute("rstCode", 0);
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            model.addAttribute("rstMsg", "同一类别下ID重复。");
        }
        return "/mixfeatured/save";
    }

    /* 【搜索】 */
    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam(required = false) Short type,
            @RequestParam(required = false) Short picType, @RequestParam(required = false) Boolean hidden,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<MoMixFeatured> list = service.search(type, picType, hidden, keywords, page, rows, sort, order);
            Long count = service.countForSearching(type, picType, hidden, keywords);
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
    public String del(@PathVariable int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = service.delete(id);
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
    public String hide(@RequestParam Integer[] id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (id == null || id.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }

        try {
            boolean result = service.updateHide(Arrays.asList(id));
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
    public String show(@RequestParam Integer[] id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (id == null || id.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            boolean result = service.updateShow(Arrays.asList(id));
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
            boolean result = service.updateSort(ids, ranks);
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
