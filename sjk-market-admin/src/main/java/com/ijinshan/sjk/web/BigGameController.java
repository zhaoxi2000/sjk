/**
 * 
 */
package com.ijinshan.sjk.web;

import java.util.Date;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.CatalogService;
import com.ijinshan.sjk.util.SvrResult;

/**
 * @author LinZuXiong
 */
@Controller
@RequestMapping(value = "/admin/biggame")
public class BigGameController {
    private static final Logger logger = LoggerFactory.getLogger(BigGameController.class);

    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);
    @Resource(name = "appServiceImpl")
    private AppService appService;

    @Resource(name = "catalogServiceImpl")
    private CatalogService catalogService;

    @RequestMapping(value = "/add.d")
    public String add() {
        return "/big-game/add";
    }

    @RequestMapping(value = "/save.d")
    public String saveOrUpdate(MultipartHttpServletRequest multipartReq, App app, Model model) {
        try {
            setAppModels(app);
            app.setLastUpdateTime(new Date());
            appService.saveOrUpdateForBiggame(app, multipartReq);
            model.addAttribute("rstCode", 0);
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            logger.error("Exception", e);
        }
        return "/save";
    }

    private void setAppModels(App app) {
        if (app.getId() > 0) {
            App appOld = appService.get(app.getId());
            if (appOld != null) {
                if (appOld.getLastFetchTime() != null) {
                    app.setLastFetchTime(appOld.getLastFetchTime());
                }
                if (appOld.getApkScanTime() != null) {
                    app.setApkScanTime(appOld.getApkScanTime());
                }
            }
        }
    }

    @RequestMapping(value = "/{id}.d")
    public String read(@PathVariable int id, Model model) {
        try {
            App app = appService.get(id);
            model.addAttribute("app", app);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return "/big-game/edit";
    }

    @RequestMapping(value = "/edit-keywords.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateKeyWords(@RequestParam Integer id, @RequestParam String keywords) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        if (id == null) {
            server.put("msg", "Don't do that!The id is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        try {
            App app = appService.get(id);
            app.setKeywords(keywords);
            appService.saveOrUpdate(app);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "系统错误");
        }
        return output.toJSONString(jsonStyle);
    }

}
