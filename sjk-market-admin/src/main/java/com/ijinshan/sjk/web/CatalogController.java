package com.ijinshan.sjk.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.service.CatalogService;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/catalog")
public class CatalogController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "catalogServiceImpl")
    private CatalogService service;

    @RequestMapping(value = "/list.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String list(@RequestParam Short pid) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        pid = pid == -1 ? null : pid;
        List<Catalog> list = service.list(pid);
        output.put("rows", list);
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        short catalog = Short.parseShort(StringUtils.defaultString(request.getParameter("catalog"), "0"));
        short subCatalog = Short.parseShort(StringUtils.defaultString(request.getParameter("subCatalog"), "0"));
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<Catalog> list = null;
        list = service.search(catalog, subCatalog, keywords, page, rows, sort, order);
        output.put("rows", list);
        output.put("total", service.countForSearching(catalog, subCatalog, keywords));
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = { "/edit.json", "add.json" }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String editCatalogConvertor(Catalog entity) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        if (entity.getId() == null) {
            server.put("msg", "参数编号错误！");
            return output.toJSONString(jsonStyle);
        }
        boolean result = service.saveOrUpdate(entity);
        if (result) {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } else {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "数目不对");
        }

        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/del.json", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean delete = service.deleteById(id);
            if (delete) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
        } catch (Exception e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
        }
        return output.toJSONString();
    }
}
