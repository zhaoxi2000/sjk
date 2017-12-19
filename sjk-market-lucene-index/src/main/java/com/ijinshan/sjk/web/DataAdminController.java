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
import com.ijinshan.sjk.service.DataObserable;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping("/admin")
public class DataAdminController {
    private static final Logger logger = LoggerFactory.getLogger(DataAdminController.class);

    @Resource(name = "dataObserableImpl")
    private DataObserable dataObserable;

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    /**
     * 只做全量索引重置.
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/reset.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public synchronized String resetIndex(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        logger.info("Accept the quest to reset index!");

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        final boolean previous = appConfig.isInitIndex();
        try {
            appConfig.setInitIndex(true);
            dataObserable.createAll();
            output.put("result", server);
            output.put("cost", (System.currentTimeMillis() - start) / 1000 + " s!");
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        } finally {
            appConfig.setInitIndex(previous);
        }
        return output.toJSONString();
    }

}
