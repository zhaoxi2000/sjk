package com.ijinshan.sjk.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.Viapp;

public interface AppDao {

    List<Viapp> getAllApp();
}
