package com.ijinshan.sjk.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.service.AbastractIndexUpdater;
import com.ijinshan.sjk.service.DataObserable;
import com.ijinshan.util.IOUtils;
import com.ijinshan.util.PathUtils;

@Service
public class SpellCheckerIndexUpdaterImpl extends AbastractIndexUpdater {
    private static final Logger logger = LoggerFactory.getLogger(SpellCheckerIndexUpdaterImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "dataObserableImpl")
    private DataObserable dataObserable;

    @Override
    @PostConstruct
    public void registerToObserable() {
        Assert.notNull(dataObserable);
        dataObserable.registerObservers(this);
    }

    @Override
    public void beginAll() throws IOException {
        super.beginAll();
    }

    @Override
    public void createAll(List<App> apps, boolean append) throws IOException {
        synchronized (lock) {
            if (CollectionUtils.isEmpty(apps)) {
                return;
            }

            Set<String> names = new HashSet<String>(apps.size() + 2);
            final String path = appConfig.getAllNameDict();
            for (App a : apps) {
                names.add(a.getName());
            }
            writeToFile(path, apps.size(), names.iterator(), append);
        }
    }

    @Override
    public void endAll() {
        synchronized (lock) {
            consumerCount--;
        }
        synchronized (lock) {
            Directory spellcheckDir = null;
            SpellChecker spellChecker = null;
            try {
                final String dicPath = appConfig.getAllNameDict();
                File dicFile = new File(dicPath);
                if (dicFile.exists()) {
                    final String strSpellCheckIndexDir = appConfig.getAllSpellcheckDir();
                    File spellCheckIndexDir = new File(strSpellCheckIndexDir);
                    PathUtils.makeSureDir(spellCheckIndexDir);
                    spellcheckDir = FSDirectory.open(spellCheckIndexDir);

                    IndexWriterConfig iwc = new IndexWriterConfig(luceneVersion, analyzer);
                    iwc.setOpenMode(OpenMode.CREATE);

                    spellChecker = new SpellChecker(spellcheckDir);
                    spellChecker.indexDictionary(new PlainTextDictionary(dicFile), iwc, true);
                    spellChecker.setStringDistance(new JaroWinklerDistance());
                    spellChecker.setAccuracy(0.7f);
                }
            } catch (Exception e) {
                logger.error("Exception", e);
            } finally {
                IOUtils.closeQuietly(spellcheckDir, spellChecker);
            }
            logger.info("close SpellChecker indexWriter!");
        }
    }

    @Override
    public void beginIncrement() throws IOException {
        super.beginIncrement();
    }

    @Override
    public void updateIncrement(List<App> apps) throws IOException {
    }

    @Override
    public void addDocucmentToIndexer(List<App> apps) {
    }

    private void writeToFile(String path, int lines, Iterator<String> iterator, boolean append) {
        synchronized (lock) {
            if (iterator == null) {
                return;
            }
            OutputStream out = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            try {
                out = new FileOutputStream(new File(path), append);
                osw = new OutputStreamWriter(out, "UTF-8");
                bw = new BufferedWriter(osw, lines * 5);
                while (iterator.hasNext()) {
                    bw.write(iterator.next());
                    bw.write("\n");
                }
            } catch (Exception e) {
                logger.error("Exception", e);
            } finally {
                IOUtils.closeQuietly(bw, osw, out);
            }
        }
    }

    @Override
    public void delDoucmentFromIndex(List<Integer> appIds) throws IOException {
        synchronized (lock) {

        }
    }
}
