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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.po.MonUserChannelApp;
import com.ijinshan.sjk.service.MonChannelAppService;
import com.ijinshan.sjk.service.RunMonChannelService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/monchannel/")
public class MonChannelAppController {
    private static final Logger logger = LoggerFactory.getLogger(MonChannelAppController.class);

    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Autowired
    private MonChannelAppService service;

    @Autowired
    private RunMonChannelService monService;

    @RequestMapping(value = "/del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delete(@RequestParam("id") int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = service.deleteByIds(Arrays.asList(id));
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (Exception e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
            logger.error("Exception:", e);
        }
        return output.toJSONString();
    }

    @RequestMapping(value = { "/save.d", "/edit.d" })
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request, MonUserChannelApp app) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            MonUserChannelApp oldEntity = null;
            if (app.getId() > 0) {
                oldEntity = service.get(app.getId());
            }
            boolean result = service.saveAndUpdate(app, oldEntity);
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

    /* 【搜索】 */
    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keyword, @RequestParam(required = false) Boolean autoCover,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<MonUserChannelApp> list = service.queryList(keyword, autoCover, page, rows, order, sort);
        long count = service.countForSearching(keyword, autoCover);
        output.put("rows", list);
        output.put("total", count);
        return output.toJSONString();
    }

    /**
     * 发送短信
     * 
     * @return
     */
    @RequestMapping(value = "/sms.d", method = RequestMethod.POST)
    public @ResponseBody
    String smsUserChannelUpdate(@RequestParam(required = false) Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {

            List<MonUserChannelApp> list = service.queryList(Arrays.asList(ids));
            if (list == null || list.size() < 1) {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
            boolean result = service.updateSmsUserChannelUpdate(list);
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

    /**
     * 监控渠道包
     * 
     * @return
     */
    @RequestMapping(value = "/mon-all.d", method = RequestMethod.GET)
    public @ResponseBody
    String runMonChannelAppSms() {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            monService.updateMonChannelApp();
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (UnsupportedOperationException e) {
            logger.error("runMonChannelAppSms error:", e);
            server.put("code", -1.);
            server.put("msg", e.getMessage());
        }
        return output.toJSONString();
    }
}
