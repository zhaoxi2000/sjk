/**
 * 
 */
package com.ijinshan.sjk.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.service.KeywordService;

/**
 * @author Linzuxiong
 */
@Controller
@RequestMapping(value = "/admin/keyword")
public class KeywordController {
    private static final Logger logger = LoggerFactory.getLogger(KeywordController.class);

    @Resource(name = "keywordServiceImpl")
    private KeywordService keywordService;

    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(@RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
        List<Keyword> list = keywordService.search(page, rows, q, sort, order);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("rows", list);
        output.put("total", keywordService.countForSearching(q));
        return output.toJSONString();
    }

    @RequestMapping(value = "/save.d")
    public String saveOrUpdate(Keyword keyword, Model model) {
        try {
            keywordService.saveOrUpdate(keyword);
            model.addAttribute("rstCode", 0);
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
            logger.error("Exception", e);
        }
        return "/save";
    }

    @RequestMapping(value = "/{id}.d")
    public String get(@PathVariable int id, Model model) {
        Keyword keyword = keywordService.get(id);
        model.addAttribute("keyword ", keyword);
        return "/keyword/add-edit";
    }

    @RequestMapping(value = "/{id}.del.d")
    @ResponseBody
    public String del(@PathVariable int id) {
        List<Integer> list = new ArrayList<Integer>(1);
        list.add(Integer.valueOf(id));
        boolean del = keywordService.delete(list);
        JSONObject output = new JSONObject();
        output.put("result", del);
        return output.toJSONString();
    }

}
