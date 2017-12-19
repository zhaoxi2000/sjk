package com.ijinshan.sjk.config;

public class SmsConfig {

    private boolean smsDisable;
    private String urlApi = "http://send.sms.kingsoft.com:8080/sender/sendMessage";
    private String userName = "sjzMonitor";
    private String md5Key = "95tr24iuca3b5e6i6";
    private String smsContact;

    /** 判断是否禁用发短信 */
    public boolean isSmsDisable() {
        return smsDisable;
    }

    public String getSmsContact() {
        return smsContact;
    }

    /** 短信提醒中的联系电话 */
    public void setSmsContact(String smsContact) {
        this.smsContact = smsContact;
    }

    public void setSmsDisable(boolean smsDisable) {
        this.smsDisable = smsDisable;
    }

    /** 判断是否禁用发短信 */
    public String getUrlApi() {
        return urlApi;
    }

    public void setUrlApi(String urlApi) {
        this.urlApi = urlApi;
    }

    /** 短信网关账号 */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 短信网关Md5Key */
    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

}
