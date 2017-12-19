package com.ijinshan.sjk.dao;

import java.text.ParseException;

import com.ijinshan.sjk.po.MarketApp;

public interface TextParser {
    MarketApp parser(String marketName, String one) throws ParseException;
}
