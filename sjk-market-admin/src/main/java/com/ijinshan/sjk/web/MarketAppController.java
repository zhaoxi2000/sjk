package com.ijinshan.sjk.web;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.po.BigGamePack;
import com.ijinshan.sjk.po.MarketApp;
import com.ijinshan.sjk.service.BigGamePackService;
import com.ijinshan.sjk.service.MarketAppService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.ArrayParamVO;
import com.ijinshan.util.DateString;
import com.ijinshan.util.DefaultDateTime;

/**
 * 这个controller , 金山虚拟市场.
 * 
 * @author luohuijun
 */
@Controller
@RequestMapping(value = "/admin/marketapp")
public class MarketAppController {
    private static final Logger logger = LoggerFactory.getLogger(MarketAppController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "marketAppServiceImpl")
    private MarketAppService marketAppService;
     
    
    @Resource(name = "bigGamePackServiceImpl")
    private BigGamePackService bigGamePackService;

    @RequestMapping(value = "/edit-catalog.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String editCatalog(@RequestParam short catalog, @RequestParam int subCatalog, @RequestParam Integer[] ids,
            @RequestParam String subCatalogName) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        if (ids == null || ids.length < 1) {
            server.put("msg", "Don't do that!The ids is empty!!!");
            return output.toJSONString(jsonStyle);
        }
        boolean edit = marketAppService.editCatalog(Arrays.asList(ids), catalog, subCatalog, subCatalogName);
        if (edit) {
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } else {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "数目不对");
        }
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/{catalog}.add.d")
    public String add(@PathVariable int catalog, Model model) {
        if (catalog == 100) {
            return "/market-app/addBigGame";
        } else {
            return "/market-app/add";
        }
    }

    @RequestMapping(value = "/add.d")
    public String add(Model model) {
        return "/market-app/add";
    }

    @RequestMapping(value = "/{id}.edit.d")
    public String edit(@PathVariable int id, Model model) {
        MarketApp marketApp = marketAppService.get(id);
        if (null != marketApp.getId()) {
            List<BigGamePack> bigGamePakcList = bigGamePackService.getBigGamePacksByMarketAppid(marketApp.getId());
            logger.debug("id:{}", id);
            model.addAttribute("bigGamePack", bigGamePakcList);
        }
        model.addAttribute("marketApp", marketApp);
        return "/market-app/edit";
    }

    @RequestMapping(value = { "/save.d", "edit.d" })
    public String saveOrUpdate(MultipartHttpServletRequest multipartReq, MarketApp marketApp, Model model,
            ArrayParamVO paramVo) {
        try {
            String reqPath = multipartReq.getServletPath();
            if (reqPath.endsWith("/edit.d")) {
                setMarketModels(marketApp);
            }
            marketApp.setLastUpdateTime(new Date());
            marketApp.setMarketUpdateTime(new Date()); // 新增更新时间
            marketApp.setAppFetchTime(new Date());
            if (marketApp.getMarketApkScanTime() == null) {
                marketApp.setMarketApkScanTime(DefaultDateTime.getDefaultDateTime());
            }
            if (marketApp.getId() <= 0 && marketAppService.searchExists(EnumMarket.SHOUJIKONG, marketApp.getPkname())) {
                model.addAttribute("rstCode", 1);
                model.addAttribute("rstMsg", "同一市场下PkName重复了。");
                return "/market-app/save";
            }
            marketAppService.saveOrUpdateForIjinshan(multipartReq, marketApp, paramVo);
            model.addAttribute("rstCode", 0);
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            model.addAttribute("rstMsg", "系统异常。");
            logger.error("Exception", e);
        }
        return "/save";
    }

    @RequestMapping(value = "/del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delete(@RequestParam Integer[] id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            int rows = marketAppService.deleteByIds(Arrays.asList(id));
            int bigrows = bigGamePackService.deleteByMarketAppIds(Arrays.asList(id));
            boolean del = rows == id.length;
            boolean delbigs = bigrows == 1;
            if (del && delbigs) {
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

    /**
     * @RequestParam catalog
     * @RequestParam subCatalog
     * @RequestParam page
     * @RequestParam rows
     * @RequestParam keywords
     * @RequestParam sort 排序列字段名
     * @RequestParam order 排序方式, 可以是 'asc' 或者 'desc'
     * @return
     */
    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(@RequestParam(required = false) Short catalog,
            @RequestParam(required = false) Integer subCatalog, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order,
            @RequestParam(required = false) Integer cputype, @RequestParam(required = false) String startdatestr,
            @RequestParam(required = false) String enddatestr) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<MarketApp> list = null;
        Date startDate = null;
        Date endDate = null;
        if (StringUtils.isNotEmpty(startdatestr)) {
            startDate = DateString.StrToDate(startdatestr);
        }
        if (StringUtils.isNotEmpty(enddatestr)) {
            endDate = DateString.StrToDate(enddatestr);
        }
        list = marketAppService.search(EnumMarket.SHOUJIKONG, catalog, subCatalog, page, rows, keywords, id, cputype,
                sort, order, startDate, endDate);
        output.put("rows", list);
        output.put("total", marketAppService.countForSearching(EnumMarket.SHOUJIKONG, catalog, subCatalog, keywords,
                id, startDate, endDate));
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/shift-to-ijinshan.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appShiftIjinshan(@RequestParam Integer[] ids) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            marketAppService.updateAppShiftIjinshan(Arrays.asList(ids));
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        output.put("result", server);
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/biggame-to-marketapp.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String virtualMarket() {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            marketAppService.updateVirtualMarket();
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());

        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        output.put("result", server);
        return output.toJSONString(jsonStyle);
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
            marketAppService.updateKeyWords(id, keywords);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "系统错误");
        }
        return output.toJSONString(jsonStyle);
    }

    private void setMarketModels(MarketApp marketApp) {
        if (marketApp.getId() > 0) {
            MarketApp marketAppOld = marketAppService.get(marketApp.getId());
            if (marketAppOld != null) {
                if (marketAppOld.getMarketApkScanTime() != null) {
                    marketApp.setMarketApkScanTime(marketAppOld.getMarketApkScanTime());
                }
                if (marketAppOld.getMarketApkScanTime() != null) {
                    marketApp.setMarketUpdateTime(marketAppOld.getMarketUpdateTime());
                }
                if (marketAppOld.getMarketApkScanTime() != null) {
                    marketApp.setAppFetchTime(marketAppOld.getAppFetchTime());
                }
            }
        }
    }
}
