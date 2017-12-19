package com.ijinshan.com.sjk.sms;

import java.util.Vector;

import org.apache.xmlrpc.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.config.SmsConfig;

public class SmsBase {
    private static final Logger logger = LoggerFactory.getLogger(SmsBase.class);

    /*
     * 短信发送接口
     */
    public static Integer sendMessage(String userNumber, String sendMessage, SmsConfig smsConfig) {
        Integer result = null;
        try {

            logger.info("send message:{}", sendMessage);
            // 外网正式地址
            String url = smsConfig.getUrlApi().trim();
            BaseXmlRpc xmlRpc = new BaseXmlRpc(url);
            // 接口参数
            Vector<String> params = new Vector<String>();
            // 先转化GBK编码，再Base64编码
            byte[] sendBytes = Base64.encode(sendMessage.getBytes("GBK"));
            String msg = new String(sendBytes);
            // md5(userName+userNumber+message +私钥)
            String content = new StringBuilder().append(smsConfig.getUserName().trim()).append(userNumber).append(msg)
                    .append(smsConfig.getMd5Key().trim()).toString();
            String md5Str = MD5.toMD5(content);
            params.add(smsConfig.getUserName().trim());
            params.add(userNumber);
            params.add(msg);
            params.add(md5Str);
            result = xmlRpc.intExcute("sender.send", params);
            logger.info("send message to {}  status:{}", userNumber, result);
            logger.info("status description:{}", xmlRpc.getStatusDesc(result));
        } catch (Exception e) {
            // Unexpected Response from Server: Not Found
            // [短信平台故障]:从服务器意外的反应：没有找到
            logger.error("sendMessage Exception:", e);
            result = -11;
        }
        return result;
    }
}
