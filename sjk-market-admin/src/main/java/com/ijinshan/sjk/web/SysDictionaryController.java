package com.ijinshan.sjk.web;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.po.SysDictionary;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.SysDictionaryService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/sys")
public class SysDictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(SysDictionaryController.class);

    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Autowired
    private SysDictionaryService dicService;

    @RequestMapping(value = "/save.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveOrUpdate(SysDictionary entity) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            dicService.saveOrUpdate(entity);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString();
    }

    @RequestMapping(value = "/dic-value-save.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateDicValue(@RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer intValue) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            dicService.updateDicValue(id, intValue);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        output.put("result", server);
        return output.toJSONString();
    }

    @RequestMapping(value = "/dic-name-save.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateDicName(@RequestParam(required = false) String name,
            @RequestParam(required = false) Integer intValue) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            dicService.updateDicValue(name, intValue);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        output.put("result", server);
        return output.toJSONString();
    }

    @RequestMapping(value = "/dict.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDicValue(@RequestParam(required = false) Integer id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            SysDictionary dic = dicService.get(id);
            server.put("data", dic);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("data", null);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        output.put("result", server);
        return output.toJSONString();
    }
}
