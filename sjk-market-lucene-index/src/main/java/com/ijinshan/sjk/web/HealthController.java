package com.ijinshan.sjk.web;

import javax.annotation.PostConstruct;

import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @RequestMapping(value = "/health.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String health() {
        JSONObject server = new JSONObject();
        server.put("msg", "It's running...");
        return server.toJSONString();
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("Server programmer bootstrap is OK!");
    }
}
