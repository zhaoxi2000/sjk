package com.ijinshan.com.sjk.sms;

import java.util.Hashtable;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcClientLite;

/**
 * Description:
 * 
 * @param
 * @return
 */
public class BaseXmlRpc {
    /* 服务提供地址 */
    String strXmlRpcUrl = null;

    public BaseXmlRpc(String xmlrpcurl) {
        strXmlRpcUrl = xmlrpcurl;
    }

    public int intExcute(String strMethod, Vector<String> params) {
        try {
            XmlRpcClient xmlrpc = new XmlRpcClient(strXmlRpcUrl);
            Object obj = xmlrpc.execute(strMethod, params);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public float floatExcute(String strMethod, Vector<String> params) {
        try {
            XmlRpcClient xmlrpc = new XmlRpcClient(strXmlRpcUrl);
            Object obj = xmlrpc.execute(strMethod, params);
            if (obj instanceof Integer) {
                return ((Integer) obj).floatValue();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public Hashtable hashExcute(String strMethod, Vector<String> params) {
        try {
            XmlRpcClientLite xmlrpc = new XmlRpcClientLite(strXmlRpcUrl);
            Object obj = xmlrpc.execute(strMethod, params);
            if (obj instanceof Hashtable) {
                return (Hashtable) (obj);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String strExcute(String strMethod, Vector<String> params) {
        try {
            XmlRpcClientLite xmlrpc = new XmlRpcClientLite(strXmlRpcUrl);
            Object obj = xmlrpc.execute(strMethod, params);
            if (obj instanceof String) {
                return String.valueOf(obj);
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public Object remoteExcute(String strMethod, Vector<String> params) {
        try {
            XmlRpcClientLite xmlrpc = new XmlRpcClientLite(strXmlRpcUrl);
            Object obj = xmlrpc.execute(strMethod, params);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStatusDesc(int status) {
        String desc = null;
        switch (status) {
            case 0:
                desc = "[发送成功]:所有内部操作都正常";
                break;
            case 1:
                desc = "[发送成功但写日志出错]:属于系统内部附加操作，不影响短信正常发送";
                break;
            case -1:
                desc = "[无效用户名]:userName不在允许的用户列表内";
                break;
            case -2:
                desc = "[加密认证错误]:加密结果错误";
                break;
            case -3:
                desc = "[无效ip]:调用接口的程序所在服务器不在允许的ip范围内";
                break;
            case -4:
                desc = "[达到当日发送上限]: 当前用户当日允许向指定用户发送最多N条短信";
                break;
            case -5:
                desc = "[短信平台故障]";
                break;
            case -6:
                desc = "[非法手机号码]:目标手机号码不符合匹配规则";
                break;
            case -10:
                desc = "[短信平台故障]:从服务器意外的反应：没有找到";
                break;
            default:
                desc = "";
                break;
        }
        return desc;
    }
}
