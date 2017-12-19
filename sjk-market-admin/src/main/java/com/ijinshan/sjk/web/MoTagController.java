/**
 * 
 */
package com.ijinshan.sjk.web;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.config.TagType;
import com.ijinshan.sjk.po.MoTag;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.po.marketmerge.MoTagTopic;
import com.ijinshan.sjk.service.MoTagRelationshipService;
import com.ijinshan.sjk.util.SvrResult;

/**
 * @author luohuijun
 */

@Controller
@RequestMapping(value = "/admin/motag")
public class MoTagController {
    private static final Logger logger = LoggerFactory.getLogger(MoTagController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "moTagRelationshipServiceImpl")
    private MoTagRelationshipService service;

    @RequestMapping(value = "/normaltag-list.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listNormalTag() {
        List<MoTag> list = service.list(TagType.NormalTag);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("rows", list);
        return output.toJSONString();
    }

    @RequestMapping(value = "/topic-list.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listTopic() {
        List<MoTag> list = service.list(TagType.TOPIC);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("rows", list);
        return output.toJSONString();
    }

    @RequestMapping(value = "/{id}.del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delete(@PathVariable int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = service.delete(id);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
        }
        return output.toJSONString();
    }

    @RequestMapping(value = "/{id}.d")
    public String read(@PathVariable int id, Model model) {
        MoTag mTag = service.get(id);
        model.addAttribute("tag", mTag);
        return "/motag/edit";
    }

    @RequestMapping(value = "/get.{id}.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getMoTag(@PathVariable int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            MoTag moTag = service.get(id);
            if (moTag != null) {
                output.put("data", moTag);
            } else {
                output.put("data", null);
            }
        } catch (UnsupportedOperationException e) {
            output.put("data", null);
        }
        return output.toJSONString();
    }

    @RequestMapping(value = { "/save.d", "/edit.d" })
    public String saveOrUpdate(MultipartHttpServletRequest multipartReq, MoTag mTag, Model model) {
        try {
            service.saveOrUpdate(multipartReq, mTag);
            model.addAttribute("rstCode", 0);
        } catch (Exception e) {
            model.addAttribute("rstCode", 1);
        }
        return "/motag/save";
    }

    @RequestMapping(value = "/add-names.d")
    @ResponseBody
    public String saveByName(@RequestParam short catalog, @RequestParam String names, @RequestParam short tagType) {
        String[] nameArray = names.split(",|，");
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        if (nameArray != null && nameArray.length > 0) {
            try {
                boolean result = service.saveByName(catalog, Arrays.asList(nameArray), tagType);
                if (result) {
                    server.put("code", SvrResult.OK.getCode());
                    server.put("msg", SvrResult.OK.getMsg());
                } else {
                    server.put("code", SvrResult.ERROR.getCode());
                    server.put("msg", SvrResult.ERROR.getMsg());
                }
            } catch (ConstraintViolationException e) {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", "您添加的专题名称重复！");
            }

        } else {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", "数目不对!");
        }
        return output.toJSONString();
    }

    /* 【搜索】 */
    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam(required = false) Short pid,
            @RequestParam(required = false) Short tagType, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<MoTagTopic> list = service.searchTagList(pid, tagType, keywords, page, rows, sort, order);
        output.put("rows", list);
        output.put("total", service.countMoTagForSearching(pid, tagType, keywords));
        return output.toJSONString(jsonStyle);
    }

    @RequestMapping(value = "/del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delRolling(@RequestParam int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = service.delete(id);
            if (result) {
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

    // 查询条件
    private void searchWhere(String keywords, List<Tag> list, List<Tag> outputList) {
        if (StringUtils.isEmpty(keywords)) {
            if (list != null && list.size() > 0) {
                for (Tag tag : list) {
                    outputList.add(tag);
                }
            }
        } else {
            if (list != null && list.size() > 0) {
                for (Tag tag : list) {
                    if (StringUtils.isNotBlank(keywords) && tag.getName().contains(keywords)) {
                        outputList.add(tag);
                    }
                }
            }
        }
    }

}
