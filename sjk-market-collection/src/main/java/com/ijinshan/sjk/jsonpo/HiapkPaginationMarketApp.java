package com.ijinshan.sjk.jsonpo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "error_code" })
public class HiapkPaginationMarketApp {
    private static final Logger logger = LoggerFactory.getLogger(HiapkPaginationMarketApp.class);
    private String action;
    private int totals;
    private String sc;
    private String chkcode;
    private String respmsg;
    private String code;
    private List<HiapkMarketApp> data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getChkcode() {
        return chkcode;
    }

    public void setChkcode(String chkcode) {
        this.chkcode = chkcode;
    }

    public String getRespmsg() {
        return respmsg;
    }

    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<HiapkMarketApp> getData() {
        return data;
    }

    public void setData(List<HiapkMarketApp> data) {
        this.data = data;
    }

    public static Logger getLogger() {
        return logger;
    }

}
