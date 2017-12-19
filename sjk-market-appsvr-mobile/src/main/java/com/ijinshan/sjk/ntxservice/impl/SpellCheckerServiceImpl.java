package com.ijinshan.sjk.ntxservice.impl;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.ntxservice.SpellCheckerService;
import com.ijinshan.util.IOUtils;

@Service
public class SpellCheckerServiceImpl implements SpellCheckerService {
    private static final Logger logger = LoggerFactory.getLogger(SpellCheckerServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    private SpellChecker spellChecker = null;

    @PostConstruct
    public void reset() {
        String indexDir = appConfig.getAllSpellCheckerDir();
        try {
            Directory spellcheckDir = FSDirectory.open(new File(indexDir));
            if (!IndexReader.indexExists(spellcheckDir)) {
                logger.info("Please reset index firstly!");
                return;
            }
            SpellChecker newSpellChecker = new SpellChecker(spellcheckDir);
            newSpellChecker.setStringDistance(new JaroWinklerDistance());
            newSpellChecker.setAccuracy(0.7f);
            if (spellChecker == null) {
                spellChecker = newSpellChecker;
            } else {
                final Closeable preSpellChecker = spellChecker;
                spellChecker = newSpellChecker;
                IOUtils.closeQuietly(preSpellChecker);
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    @Override
    public String[] checkSpell(String q) {
        if (q == null || q.isEmpty() || q.length() > appConfig.getKeywordMaxLength()) {
            logger.error("empty keywords or over-length! {}", q);
            return null;
        }
        try {
            return spellChecker.suggestSimilar(q, appConfig.getSpellCheckerNum());
        } catch (IOException e) {
            return null;
        }
    }
}
