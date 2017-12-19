package com.ijinshan.sjk.web;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping("/app/api")
public class ActivityController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private static final int defaultCacheTime = 24 * 60 * 60;// 默认单位为秒

    @Resource(name = "jsonStyle")
    private JSONStyle jsonStyle;

    @RequestMapping(value = "/activity/status.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String activity() {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            output.put("ok", false);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

}
