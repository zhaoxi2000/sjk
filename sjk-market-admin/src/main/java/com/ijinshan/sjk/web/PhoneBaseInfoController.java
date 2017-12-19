package com.ijinshan.sjk.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.po.PhoneBasicInfo;
import com.ijinshan.sjk.po.PhoneRelations;
import com.ijinshan.sjk.service.PhoneInfoService;
import com.ijinshan.sjk.service.PhoneRelationsService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.PhoneAdminVo;
import com.ijinshan.sjk.vo.PhoneInfoVo;

@Controller
@RequestMapping("/admin/phone")
public class PhoneBaseInfoController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBaseInfoController.class);

    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "phoneInfoServiceImpl")
    private PhoneInfoService phoneInfoService;

    @Resource(name = "phoneRelationsServiceImpl")
    private PhoneRelationsService phoneRelationsService;

    /*
     * 品牌列表(当不带参数时查询所有的品牌，当带brand时查询对应的列表，当带product时查询对应的一款手机)
     */
    @RequestMapping(value = "/phone-info.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPhoneBrandInfo(@RequestParam(required = false) String brand,
            @RequestParam(required = false) String product) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();

        if (StringUtils.isEmpty(brand) && StringUtils.isEmpty(product)) {
            List<String> brandList = phoneInfoService.findAllPhoneBrand();
            output.put("data", brandList);
        } else {
            List<PhoneAdminVo> adminList = phoneInfoService.findPhoneListByParams(brand, product);
            output.put("data", adminList);
        }
        output.put("result", server);
        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    /**
     * 根据手机id来查询对应的手机信息
     * 
     * @param phoneId
     * @return
     */
    @RequestMapping(value = "/get-phone.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPhone(@RequestParam String phoneIds) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        List<PhoneBasicInfo> infoVoList = phoneInfoService.getPhoneBaseInfoById(phoneIds);
        List<PhoneInfoVo> pvoList = new ArrayList<PhoneInfoVo>();
        if (infoVoList.size() > 0) {
            for (PhoneBasicInfo infoVo : infoVoList) {
                if (null != infoVo) {
                    List<PhoneRelations> phoneRelList = phoneRelationsService.findPhoneRelationsByParam(
                            infoVo.getProductId(), infoVo.getId());
                    if (phoneRelList.size() > 0) {
                        PhoneInfoVo pvo = new PhoneInfoVo();
                        pvo.setBrand(infoVo.getBrand());
                        pvo.setCpu(phoneRelList.get(0).getCpu());
                        pvo.setPhoneType(infoVo.getProduct());
                        pvo.setPhoneId(infoVo.getId());
                        pvoList.add(pvo);
                    }
                }
            }
        }
        output.put("data", pvoList);
        output.put("result", server);
        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }
}
