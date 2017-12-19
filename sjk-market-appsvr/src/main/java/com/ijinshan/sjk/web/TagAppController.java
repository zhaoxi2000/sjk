package com.ijinshan.sjk.web;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.annotation.Cacheable;
import com.ijinshan.sjk.service.TagAppService;
import com.ijinshan.sjk.service.TagService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.AppTopic;
import com.ijinshan.sjk.vo.pc.SimpleTag;
import com.ijinshan.sjk.vo.pc.abstracttag.TagApps;

@Controller
@RequestMapping("/app/api/cdn/")
public class TagAppController {
    private static final Logger logger = LoggerFactory.getLogger(TagAppController.class);

    private static final int defaultCacheTime = 10 * 60;// 默认单位为秒

    @Resource(name = "emptyArray")
    private List<?> emptyArray;

    @Resource(name = "jsonStyle")
    private JSONStyle jsonStyle;

    @Autowired
    private TagAppService service;

    @Resource(name = "tagServiceImpl")
    private TagService tagService;

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/tagapp/topic/{catalog}/{tagId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appTagTopic(@PathVariable short catalog, @PathVariable int tagId) {

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            Set<AppTopic> list = service.getAppTopic(tagId, catalog);
            output.put("result", server);
            output.put("data", list);
            output.put("total", list == null ? 0 : list.size());
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/topic-apps/{tagId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String apps4Topic(@PathVariable int tagId, @RequestParam int page, @RequestParam int rows) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            JSONObject data = new JSONObject();
            output.put("data", data);
            List<TagApps> list = service.getApps4Topic(tagId);
            if (list != null && !list.isEmpty()) {
                int pid = list.get(0).getPid();
                if (pid > 0) {
                    SimpleTag tag = tagService.get(pid);
                    data.put("topic", tag);
                } else {
                    data.put("topic", null);
                }
                data.put("topic-apps", list);
            } else {
                data.put("topic", null);
                data.put("topic-apps", emptyArray);
            }
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
