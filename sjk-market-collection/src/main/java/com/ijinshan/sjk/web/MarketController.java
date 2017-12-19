package com.ijinshan.sjk.web;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ijinshan.sjk.config.JsonObjectMapperFactory;
import com.ijinshan.sjk.jsonpo.BrokenLink;
import com.ijinshan.sjk.service.BaseMarketService;
import com.ijinshan.sjk.service.MarketAppService;
import com.ijinshan.sjk.service.MarketSecurityService;
import com.ijinshan.sjk.service.programmeimpl.NotifyDataServiceImpl;
import com.ijinshan.sjk.util.SvrResult;

/**
 * 这个controller , 供合作商访问.
 * 
 * @author LinZuXiong
 */
@Controller
@RequestMapping(value = "/market")
public class MarketController {
    private static final Logger logger = LoggerFactory.getLogger(MarketController.class);
    private static final Logger dblogger = LoggerFactory.getLogger("db.brokenlink");

    @Resource(name = "marketAppServiceImpl")
    private MarketAppService marketAppService;

    @Resource(name = "marketSecurityServiceImpl")
    private MarketSecurityService marketSecurityService;

    @Resource(name = "notifyDataServiceImpl")
    private BaseMarketService notifyDataServiceImpl;

    @RequestMapping(value = "/brokenLink.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String brokenLink(HttpServletRequest req, @RequestParam String c,
            @RequestParam(required = false) String marketName) {
        dblogger.debug("Someone requests brokenLink! c: {}", c);
        byte[] json = Base64.decodeBase64(c);

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        BrokenLink brokenLink = null;
        try {
            brokenLink = JsonObjectMapperFactory.getInstance().getObjectMapper()
                    .readValue(json, new TypeReference<BrokenLink>() {
                    });
            dblogger.info("From brokenlink.MarketName: {}\t Count: {}", brokenLink.getMarketName(),
                    brokenLink.getCount());
        } catch (Exception e) {
            server.put("msg", SvrResult.INVALID_JSON.getMsg());
            server.put("code", SvrResult.INVALID_JSON.getCode());
            String s = "";
            try {
                s = new String(Base64.decodeBase64(c), "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                logger.error("UnsupportedEncodingException", e1);
            }
            if (marketName != null) {
                logger.error("IP: {}\t marketName: {}\n decode: {}", getClientIP(req), marketName, s);
            }
            logger.error("Exception", e);
        }
        if (brokenLink != null) {
            try {
                if (brokenLink.getCount() > 0) {
                    boolean login = marketSecurityService.allowAccess(brokenLink.getMarketName(), brokenLink.getKey());
                    if (login) {
                        dblogger.info("{} valid login!", brokenLink.getMarketName());
                        marketAppService.delete(brokenLink.getMarketName(), brokenLink.getData());
                        server.put("msg", SvrResult.OK.getMsg());
                        server.put("code", SvrResult.OK.getCode());
                    } else {
                        dblogger.info("{} invalid login!", brokenLink.getMarketName());
                        server.put("msg", SvrResult.CLIENT_PARAMS_ERROR.getMsg());
                        server.put("code", SvrResult.CLIENT_PARAMS_ERROR.getCode());
                    }
                }
            } catch (Exception e) {
                server.put("msg", SvrResult.ERROR.getMsg());
                server.put("code", SvrResult.ERROR.getCode());
                logger.error("Exception", e);
            }
        }
        logger.info("End brokenLink!");
        return output.toJSONString();
    }

    /**
     * 市场主动护送数据,按下载URL去拉取一个文件
     * 127.0.0.1:9080/sjk-market-collection/market/yingyongso
     * /gen.d?marketName=shoujikong_channel
     * &key=bzHqw2V1MhXXkUddQl77qFa5slxYKhYf&
     * download=http://www.yingyong.so/shoujikong
     * /ijinshan_shoujikongchannel_f855087b4eaf423fb7b2be5413825abb.csv
     * 
     * @param c
     * @return
     */
    @RequestMapping(value = "/yingyongso/gen.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String gen(@RequestParam final String marketName, @RequestParam String key,
            @RequestParam final String download) {
        logger.info("Someone requests gen a file!");

        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);

        boolean login = marketSecurityService.allowAccess(marketName, key);
        if (login) {
            server.put("msg", "Login Ok! Handle in deamon...");
            server.put("code", SvrResult.OK.getCode());
            dblogger.info("URL of download: {}", download);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ((NotifyDataServiceImpl) notifyDataServiceImpl).importByUrl(marketName, download);
                }
            }).start();
        } else {
            dblogger.info("{} invalid login!", marketName);
            server.put("msg", SvrResult.CLIENT_ILLEGAL_LOGIN.getMsg());
            server.put("code", SvrResult.CLIENT_ILLEGAL_LOGIN.getCode());
        }
        return output.toJSONString();
    }

    private String getClientIP(HttpServletRequest req) {
        String ip = req.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty()) {
            ip = req.getRemoteAddr();
        }
        if (ip == null) {
            ip = req.getHeader("x-forwarded-for");
        }
        if (ip == null) {
            ip = req.getHeader("X-Forwarded-For");
        }
        return ip;
    }
}
