package com.ijinshan.sjk.web;

import java.util.List;
import java.util.Set;

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
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.AppTopic;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.sjk.vo.TagForMobileVo;
import com.ijinshan.sjk.vo.TagMobileVo;
import com.ijinshan.sjk.vo.mobile.MoTagVo;
import com.ijinshan.sjk.vo.mobile.TagListVo;
import com.ijinshan.util.Pager;

@Controller
@RequestMapping("/app/api/cdn/tagapp")
public class TagAppController {
    private static final Logger logger = LoggerFactory.getLogger(TagAppController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);
    private static final int defaultCacheTime = 5 * 60;// 默认单位为秒

    @Autowired
    private TagAppService service;

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/topic/{catalog}/{tagId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appTagTopic(@PathVariable short catalog, @PathVariable int tagId) {

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            Set<AppTopic> list = service.getAppTopic(tagId, catalog);
            // List<AppAndTag> list = service.getTags(tagId, catalog);
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

    /**
     * 移动应用手机专题 接口
     * 
     * @param tagId
     * @return
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/topic/mobile/{tagId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String mobileAppTagTopic(@PathVariable int tagId, @RequestParam int page, @RequestParam int rows) {

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            Set<AppTopic> list = service.getMobileAppTopic(tagId, page, rows);
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

    /**
     * 首页专题列表
     * 
     * @param page
     * @param rows
     * @return
     */

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/topic/mobile/tag-list.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String mobileAppTagTopicList(@RequestParam int page, @RequestParam int rows) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
            Pager<MobileSearchApp> pager = service.getMobileAppTopicList(page, rows);
            long count = service.getMobileAppTopic();
            output.put("result", server);
            output.put("data", pager.getResult().size() > 0 ? pager.getResult(): null);
            output.put("total", count);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
            
            
            
            
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    /**
     * 首页专题列表
     * 
     * @param page
     * @param rows
     * @return
     */

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/topic/tag/mobile/{tagId}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String mobileTagIdAppList(@PathVariable int tagId, @RequestParam int page, @RequestParam int rows) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        Pager<MobileSearchApp> pager = new Pager<MobileSearchApp>();
        TagForMobileVo tagMobile = new TagForMobileVo();
        try {
            TagMobileVo tagVo = service.getTagByTagId(tagId);
            if (tagVo != null) {
                pager = service.getMobileTagList(tagId, page, rows);
                tagMobile.setTag(tagVo);
                if (pager.getResult().size() > 0) {
                    tagMobile.setAppList(pager.getResult());
                }
            }
            output.put("data", tagMobile);
            output.put("result", server);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    /**
     * 手机专题 或标签列表
     * 
     * @param tagType
     * @param page
     * @param rows
     * @return
     */

//     @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/tag/mobile/labelortag-list.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String mobileTagIdAppList(@RequestParam(required = false) Integer tagType,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer rows) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        try {
             Pager<TagListVo> list = service.getTagListPage(tagType, page, rows);
            output.put("data", list.getResult().size() > 0 ? list.getResult(): null); 
            output.put("total", list.getRows());
            output.put("result", server);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    /**
     * 目前接口需求不明
     */
    /*
     * @Cacheable(exp = defaultCacheTime)
     * 
     * @RequestMapping(value = "/topic/mobile/{catalog}/{tagType}.json", method
     * = RequestMethod.GET, produces = "application/json;charset=UTF-8")
     * 
     * @ResponseBody public String mobileAppTagTopicList(@RequestParam int
     * tagType, @RequestParam(required = false) int catalog,
     * 
     * @RequestParam(required = false) int subCatalog) { JSONObject output = new
     * JSONObject(); JSONObject server = new JSONObject(); try { // List<> list
     * = service.getMoTagTopicList(tagType,); // output.put("data", list);
     * server.put("code", SvrResult.OK.getCode()); server.put("msg",
     * SvrResult.OK.getMsg()); } catch (Exception e) { server.put("code",
     * SvrResult.ERROR.getCode()); server.put("msg", SvrResult.ERROR.getMsg());
     * logger.error("Exception", e); } return output.toJSONString(jsonStyle); }
     */

}
