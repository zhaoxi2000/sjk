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

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.service.DownloadStatService;
import com.ijinshan.sjk.service.SearchService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping("/admin")
public class DataAdminController {
    private static final Logger logger = LoggerFactory.getLogger(DataAdminController.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;
    @Resource(name = "searchServiceImpl")
    private SearchService searchService;

    @Resource(name = "downloadStatServiceImpl")
    private DownloadStatService downloadStatService;

    @RequestMapping(value = "/reset.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String reset(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        logger.info("Accept the quest to reset index!");
        searchService.reset();

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("cost", (System.currentTimeMillis() - start) / 1000 + " s!");

        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString();
    }

}
