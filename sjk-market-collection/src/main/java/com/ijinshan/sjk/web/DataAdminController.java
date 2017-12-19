package com.ijinshan.sjk.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.service.BaseMarketService;
import com.ijinshan.sjk.service.MergeService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/data")
public class DataAdminController {
    private static final Logger logger = LoggerFactory.getLogger(DataAdminController.class);

    @Resource(name = "eoemarketServiceImpl")
    private BaseMarketService eoemarketService;

    @Resource(name = "appChinaServiceImpl")
    private BaseMarketService appChinaServiceImpl;

    @Resource(name = "yingyongsoServiceImpl")
    private BaseMarketService yingyongsoServiceImpl;

    @Resource(name = "m91ServiceImpl")
    private BaseMarketService m91ServiceImpl;

    @Resource(name = "hiapkServiceImpl")
    private BaseMarketService hiapkServiceImpl;

    @Resource(name = "mergeServiceImpl")
    private MergeService mergeService;

    // @RequestMapping(value = "/hiapk-full.d", method = RequestMethod.GET,
    // produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String doHiapkFull(HttpServletRequest request) {
    // logger.info("Accept the quest for hiapk-full!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // hiapkServiceImpl.importFull();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }
    //
    // @RequestMapping(value = "/eoe-full.d", method = RequestMethod.GET,
    // produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String doEoeFull(HttpServletRequest request) {
    // logger.info("Accept the quest for eoe-full!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // eoemarketService.importFull();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }
    //
    // @RequestMapping(value = "/eoe-goon.d", method = RequestMethod.GET,
    // produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String goonEoe(HttpServletRequest request) {
    // logger.info("Accept the quest for goon!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // eoemarketService.importFull();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }
    //
    // @RequestMapping(value = "/appchina-full.d", method = RequestMethod.GET,
    // produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String doAppChinaFull(HttpServletRequest request) {
    // logger.info("Accept the quest for appchina-full!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // appChinaServiceImpl.importFull();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }
    //
    // @RequestMapping(value = "/91-full.d", method = RequestMethod.GET,
    // produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String doM91Full(HttpServletRequest request) {
    // logger.info("Accept the quest for 91!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // m91ServiceImpl.importFull();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }
    //
    // @RequestMapping(value = "/yingyongso-full.d", method = RequestMethod.GET,
    // produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String doYingyongsoFull(HttpServletRequest request) {
    // logger.info("Accept the quest for yingyongso-full!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // yingyongsoServiceImpl.importFull();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }
    //
    // @RequestMapping(value = "/appchina-increment.d", method =
    // RequestMethod.GET, produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String doAppChinaIncrement(HttpServletRequest request) {
    // logger.info("Accept the quest for appchina-increment!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // appChinaServiceImpl.importIncrement();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }
    //
    // @RequestMapping(value = "/appchina-off.d", method = RequestMethod.GET,
    // produces = "application/json;charset=UTF-8")
    // @ResponseBody
    // public String doAppChinaOff(HttpServletRequest request) {
    // logger.info("Accept the quest for appchina-off!");
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // ((FileImporterMarketServiceImpl) appChinaServiceImpl).doDelete();
    // }
    // }).start();
    //
    // JSONObject output = new JSONObject();
    // JSONObject server = new JSONObject();
    // output.put("result", server);
    //
    // server.put("code", SvrResult.OK.getCode());
    // server.put("msg", SvrResult.OK.getMsg());
    // return output.toJSONString();
    // }

    @RequestMapping(value = "/app.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String app(HttpServletRequest request) {
        logger.info("Accept the quest for merge app!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mergeService.mergeToApp();
            }
        }).start();

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString();
    }

}
