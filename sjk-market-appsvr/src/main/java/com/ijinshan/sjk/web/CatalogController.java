package com.ijinshan.sjk.web;

import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.annotation.Cacheable;
import com.ijinshan.sjk.po.CatalogInfo;
import com.ijinshan.sjk.service.CatalogService;
import com.ijinshan.sjk.util.SvrResult;

//@Controller
//@RequestMapping("/app/api/cdn/catalog")
public class CatalogController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    private static final int defaultCacheTime = 20 * 60;// 默认单位为秒
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "catalogServiceImpl")
    private CatalogService service;

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/list.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(@RequestParam int catalog, @RequestParam(required = false) Integer noAds,
            @RequestParam(required = false) Integer official) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        List<CatalogInfo> list = service.listCatalogInfo(catalog, noAds, official);
        output.put("rows", list);
        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/all.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String all() {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        List<CatalogInfo> list = service.listCatalogInfo();
        output.put("rows", list);
        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

}
