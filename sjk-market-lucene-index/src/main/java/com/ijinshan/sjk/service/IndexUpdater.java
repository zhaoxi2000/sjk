package com.ijinshan.sjk.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;

import com.ijinshan.sjk.po.App;

/**
 * Design Pattern : Observer Pattern
 * 
 * @author Linzuxiong
 */
public interface IndexUpdater {

    void registerToObserable();

    void beginAll() throws IOException;

    /**
     * 如果不是追加的,那整个apps是新建的索引.
     * 
     * @param apps
     * @param append
     * @throws IOException
     * @throws CorruptIndexException
     */
    void createAll(List<App> apps, boolean append) throws IOException;

    void endAll();

    void beginIncrement() throws IOException;

    void updateIncrement(List<App> apps) throws IOException;

    void delDoucmentFromIndex(List<Integer> appIds) throws IOException;

    void addDocucmentToIndexer(List<App> apps);

    void endIncrement();

}
