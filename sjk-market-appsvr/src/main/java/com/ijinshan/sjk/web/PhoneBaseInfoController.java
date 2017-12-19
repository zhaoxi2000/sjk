package com.ijinshan.sjk.web;

import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.annotation.Cacheable;
import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.service.PhoneInfoService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.BrandListVo;
import com.ijinshan.sjk.vo.PhoneVo;

@Controller
@RequestMapping("/app/api/phone")
public class PhoneBaseInfoController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBaseInfoController.class);

    private static final int defaultCacheTime = 30 * 60;// 默认单位为秒

    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "phoneInfoServiceImpl")
    private PhoneInfoService phoneInfoService;

    /*
     * 品牌信息列表接口
     */
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/phone-info.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String biggameByBrand(@RequestParam String brand) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<PhoneVo> phoneList = phoneInfoService.findPhoneInfoList(brand);
            output.put("data", phoneList);
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
        }

        return output.toJSONString(jsonStyle);
    }

    /*
     * 品牌列表
     */
//    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/brand-list.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPhoneBrand() {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<BrandListVo> list = phoneInfoService.findPhoneLetterList();
            output.put("data", list);
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
