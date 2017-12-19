package com.ijinshan.sjk.web;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.Metro;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.MetroService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/metroapp")
public class MetroController {
    private static final Logger logger = LoggerFactory.getLogger(MetroController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "metroServiceImpl")
    private MetroService metroService;

    @RequestMapping(value = "/save.d", produces = "application/json;charset=UTF-8")
    // @ResponseBody
    public String saveOrUpdate(MultipartHttpServletRequest multipartReq, Metro metro) {
        logger.debug("receiving...metro save.");
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            String pics = StringUtils.defaultIfEmpty(metro.getPics(), "").replace(",,", "");
            metro.setPics(StringUtils.stripEnd(StringUtils.stripStart(pics, ","), ","));
            metroService.saveOrUpdate(multipartReq, metro);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return "/metro/save";
    }

    @RequestMapping(value = "/search.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(@RequestParam(required = false) String type, @RequestParam(required = false) Boolean hidden,
            @RequestParam(required = false) Boolean deleted) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<Metro> list = metroService.search(type, hidden, deleted);
            output.put("data", list);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
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
            boolean del = metroService.delete(id, type);
            if (del) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", "没有足够的记录可以删除.");
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
            boolean hide = metroService.updateHide(Arrays.asList(id), type);
            if (hide) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", "数目不对");
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", "没有足够的记录可以隐藏。");
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
            boolean hide = metroService.updateDeleted(Arrays.asList(id), type, deleted);
            if (hide) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", "数目不对");
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", SvrResult.NO_DATA.getCode());
            server.put("msg", "没有足够的记录可以删除。");
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
            boolean hide = metroService.updateShow(Arrays.asList(id), type);
            if (hide) {
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
}
