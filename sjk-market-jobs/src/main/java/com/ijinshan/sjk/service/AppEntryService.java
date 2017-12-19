package com.ijinshan.sjk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.Viapp;
import com.ijinshan.sjk.vo.KeyScoreParis;

public interface AppEntryService {
    // KeyScoreParis : key , score
    KeyScoreParis getKeySocrePair(Viapp app);
}
