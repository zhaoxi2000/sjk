package com.ijinshan.sjk.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.po.CatalogConvertor;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.service.CatalogConvertorService;
import com.ijinshan.sjk.service.CatalogService;
import com.ijinshan.sjk.service.MarketSecurityService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.MarketCatalog;

@Controller
@RequestMapping(value = "/admin")
public class CatalogConvertorController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogConvertorController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "catalogConvertorServiceImpl")
    private CatalogConvertorService service;

    @Autowired
    private MarketSecurityService marketSecurityService;
    @Autowired
    private CatalogService catalogService;

    /* 【搜索】 */
    @RequestMapping(value = "/catalogconvertor/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam(required = false) String marketName,
            @RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        short catalog = Short.parseShort(StringUtils.defaultString(request.getParameter("catalog"), "0"));
        output.put("result", server);
        List<CatalogConvertor> list = null;
        list = service.search(marketName, catalog, keywords, page, rows, sort, order);
        output.put("rows", list);
        output.put("total", service.countForSearching(catalog, marketName, keywords));
        return output.toJSONString(jsonStyle);
    }

    /* 【单个类别修】 */
    @RequestMapping(value = "/catalogconvertor/edit.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String editCatalogConvertor(CatalogConvertor entity) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        if (entity.getId() == null || entity.getId() < 1) {
            server.put("msg", "参数编号错误！");
            return output.toJSONString(jsonStyle);
        }
        if (validateModels(entity)) {
            boolean result = service.saveOrUpdate(entity);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", "数目不对");
            }
        } else {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "数据错误，请确认数据完整性！");
        }

        return output.toJSONString(jsonStyle);
    }

    /* 【批了修改】 */
    @RequestMapping(value = "/catalogconvertor/edit.list.d")
    public String saveOreditCatalogConvertors(HttpServletRequest request, Model model) {
        try {
            List<CatalogConvertor> list = catalogStrToList(request);
            boolean result = service.saveOrEditCatalogConvertors(list);
            if (result) {
                model.addAttribute("rstCode", 0);
            } else {
                model.addAttribute("rstCode", 1);
            }
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            logger.error("Exception", e);
        }
        return "/save";
    }

    @RequestMapping(value = "/catalogconvertor/del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delRolling(@RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean del = service.deleteByIds(Arrays.asList(ids));
            if (del) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.PART_DATA.getCode());
                server.put("msg", SvrResult.PART_DATA.getMsg());
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }
        return output.toJSONString(jsonStyle);
    }

    /* 【进入类别对于关系页面】 */
    @RequestMapping(value = { "/catalogconvertor/{marketName}.convertor.d" })
    public String addOrUpateconvertor(@PathVariable String marketName, Model model) {
        List<MarketCatalog> marketCatalogs = service.searchMarketCatalogs(marketName);
        model.addAttribute("marketName", marketName);
        model.addAttribute("marketCatalogs", marketCatalogs);
        return "/catalog-convertor/convertor";
    }

    /* 【获取市场列表】 */
    @RequestMapping(value = "/catalogconvertor/market.list.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getMarkets() {
        JSONObject jsonData = new JSONObject();
        List<Market> makets = marketSecurityService.findMarkets();
        Map<Integer, String> mapList = new HashMap<Integer, String>();
        for (Market market : makets) {
            mapList.put(market.getId(), market.getMarketName());
        }
        jsonData.put("data", mapList);
        return jsonData.toJSONString();
    }

    // 把参数字符串数组数据转换成对象集合
    private List<CatalogConvertor> catalogStrToList(HttpServletRequest request) throws Exception {
        String[] ids = request.getParameterValues("id");
        String[] marketNames = request.getParameterValues("marketName");
        String[] catalogs = request.getParameterValues("catalog");
        String[] subCatalogs = request.getParameterValues("subCatalog");
        String[] subCatalogNames = request.getParameterValues("subCatalogName");
        String[] targetCatalogs = request.getParameterValues("targetCatalog");
        String[] targetSubCatalogs = request.getParameterValues("targetSubCatalog");
        int len = 0;
        if (ids != null)
            len = ids.length;
        List<CatalogConvertor> list = new ArrayList<CatalogConvertor>();
        for (int i = 0; i < len; i++) {
            if (Integer.valueOf(targetSubCatalogs[i]) > 0 && Short.valueOf(targetCatalogs[i]) > 0) {
                CatalogConvertor catalog = new CatalogConvertor(marketNames[i], Short.parseShort(catalogs[i]),
                        Integer.parseInt(subCatalogs[i]), subCatalogNames[i], Short.parseShort(targetCatalogs[i]),
                        Integer.parseInt(targetSubCatalogs[i]));

                catalog.setId(Integer.valueOf(StringUtils.defaultIfBlank(ids[i], "0")));
                if (validateModels(catalog)) {
                    list.add(catalog);
                } else {
                    throw new Exception("数据错误，确认数据完整性！");
                }
            }
        }
        return list;
    }

    /* 验证类别是否正确 */
    private boolean validateModels(CatalogConvertor catalog) {
        if (catalog.getId() == null || StringUtils.isBlank(catalog.getMarketName())
                || StringUtils.isBlank(catalog.getSubCatalogName()) || catalog.getCatalog() <= 0
                || catalog.getSubCatalog() <= 0 || catalog.getSubCatalog() <= 0 || catalog.getTargetSubCatalog() <= 0) {
            return false;
        } else {
            return true;

        }
    }
}
