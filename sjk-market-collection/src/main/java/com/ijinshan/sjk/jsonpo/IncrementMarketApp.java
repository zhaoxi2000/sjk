package com.ijinshan.sjk.jsonpo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ijinshan.sjk.po.MarketApp;

//@JsonIgnoreProperties(value = { "totalPages", "currentPage", "pageSize" })
@JsonIgnoreProperties(value = { "downloads" })
public class IncrementMarketApp extends PaginationMarketApp {
    private String marketName;
    private int totals;
    protected List<MarketApp> data;

    private IncrementMarketApp() {
        super();
    }

    private IncrementMarketApp(String marketName, int totals, List<MarketApp> data) {
        super();
        this.marketName = marketName;
        this.totals = totals;
        this.data = data;
    }

    @Override
    public String getMarketName() {
        return marketName;
    }

    @Override
    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Override
    public int getTotals() {
        return totals;
    }

    @Override
    public void setTotals(int totals) {
        this.totals = totals;
    }

    @Override
    public List<MarketApp> getData() {
        return data;
    }

    @Override
    public void setData(List<MarketApp> data) {
        this.data = data;
    }

}
