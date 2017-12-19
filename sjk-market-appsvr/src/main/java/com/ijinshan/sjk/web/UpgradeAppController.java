package com.ijinshan.sjk.web;

import java.util.List;

import javax.annotation.Resource;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.annotation.Cacheable;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.util.SvrResult;
import com.ijinshan.sjk.vo.pc.ScanApks;
import com.ijinshan.sjk.vo.pc.ScanApp;

@Controller
@RequestMapping("/app/api")
public class UpgradeAppController {
    private static final Logger logger = LoggerFactory.getLogger(UpgradeAppController.class);

    private static final int defaultCacheTime = 5 * 60;// 默认单位为秒

    @Resource(name = "jsonStyle")
    private JSONStyle jsonStyle;
    @Resource(name = "emptyArray")
    private List<?> emptyArray;

    @Resource(name = "appServiceImpl")
    private AppService appService;

    // 手机控 2.0.4.1133 之前的版本使用这个，之后的版本已经淘汰了。
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/pkname/{pkname}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appByPackageName(@PathVariable String pkname) {
        ScanApp app = appService.getAppByPkame(pkname);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        output.put("data", app);

        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    // 手机控 2.0.4.1133 之前的版本使用这个，之后的版本已经淘汰了。
    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/apk/{pkname}/{signaturesha1}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appByApk(@PathVariable String pkname, @PathVariable String signaturesha1) {
        ScanApp app = appService.getAppByApk(pkname, signaturesha1);
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        if (app != null) {
            output.put("signature", true);
            output.put("data", app);
        } else {
            output.put("signature", false);
            ScanApp appByPkname = appService.getAppByPkame(pkname);
            output.put("data", appByPkname);
        }

        server.put("code", SvrResult.OK.getCode());
        server.put("msg", SvrResult.OK.getMsg());
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/apks/{pkname}/{versionCode}.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String apks(@PathVariable String pkname, @PathVariable long versionCode) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<ScanApp> apps = appService.getApks(pkname, versionCode);
            if (apps != null && !apps.isEmpty()) {
                output.put("data", apps);
            } else {
                output.put("data", emptyArray);
            }
            server.put("code", SvrResult.OK.getCode());
            server.put("msg", SvrResult.OK.getMsg());
        } catch (IllegalArgumentException e) {
            server.put("code", SvrResult.CLIENT_PARAMS_ERROR.getCode());
            server.put("msg", SvrResult.CLIENT_PARAMS_ERROR.getMsg());
            StringBuilder sb = new StringBuilder(pkname.length() + 100);
            sb.append("apks method.");
            sb.append(" IllegalArgumentException: ").append(e.getMessage());
            sb.append("\npkname:").append(pkname).append("\t").append("versionCode:").append(versionCode);
            logger.error(sb.toString());
        } catch (Exception e) {
            server.put("code", SvrResult.ERROR.getCode());
            server.put("msg", SvrResult.ERROR.getMsg());
            logger.error("Exception", e);
        }
        return output.toJSONString(jsonStyle);
    }

    @Cacheable(exp = defaultCacheTime)
    @RequestMapping(value = "/cdn/apks/top-n.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String apksTop() {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            List<ScanApks> data = appService.getScanAppTopN();
            if (data != null && !data.isEmpty()) {
                output.put("data", data);
            } else {
                output.put("data", emptyArray);
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
