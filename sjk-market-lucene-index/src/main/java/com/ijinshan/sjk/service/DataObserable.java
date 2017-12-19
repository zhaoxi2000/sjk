/**
 * 
 */
package com.ijinshan.sjk.service;

/**
 * Design Pattern : Observer Pattern
 * 
 * @author Linzuxiong
 */
public interface DataObserable {

    /**
     * job or request
     * 
     * @throws Exception
     */
    boolean createAll();

    /**
     * job or request
     * 
     * @return TODO
     * @throws Exception
     */
    boolean updateIncrement();

    void registerObservers(IndexUpdater index);
}
