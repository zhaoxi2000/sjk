package com.ijinshan.sjk.jsonpo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.MarketApp;

public class PaginationMarketApp {
    private static final Logger logger = LoggerFactory.getLogger(PaginationMarketApp.class);
    protected String marketName;
    protected int totalPages;
    protected int currentPage;
    protected int totals;
    protected int pageSize;
    protected List<MarketApp> data;

    public PaginationMarketApp() {
        super();
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<MarketApp> getData() {
        return data;
    }

    public void setData(List<MarketApp> data) {
        this.data = data;
    }

    public static Logger getLogger() {
        return logger;
    }

}
