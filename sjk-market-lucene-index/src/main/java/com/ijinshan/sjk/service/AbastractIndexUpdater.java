package com.ijinshan.sjk.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.vo.index.LuceneFieldCollection;
import com.ijinshan.util.IOUtils;

public abstract class AbastractIndexUpdater implements IndexUpdater {
    private static final Logger logger = LoggerFactory.getLogger(AbastractIndexUpdater.class);

    @Resource(name = "appConfig")
    protected AppConfig appConfig;

    @Resource(name = "dataObserableImpl")
    protected DataObserable dataObserable;

    protected final Version luceneVersion = Version.LUCENE_36;
    @Resource(name = "ikAnalyzer")
    protected Analyzer analyzer;

    final protected LuceneFieldCollection luceneFlds = new LuceneFieldCollection();
    protected IndexWriter indexWriter = null;

    protected Object lock = new Object();
    /**
     * job is a consumer
     */
    protected volatile int consumerCount = 0;

    @PreDestroy
    private void destroy() {
        IOUtils.closeQuietly(indexWriter, analyzer);
        if (indexWriter != null) {
            indexWriter = null;
        }
        if (analyzer != null) {
            analyzer = null;
        }
    }

    @Override
    public void beginAll() throws IOException {
        synchronized (lock) {
            consumerCount++;
        }
    }

    @Override
    public void endAll() {
        synchronized (lock) {
            consumerCount--;
        }
        while (consumerCount > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        synchronized (lock) {
            if (indexWriter != null) {
                try {
                    indexWriter.commit();
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
            }
            IOUtils.closeQuietly(indexWriter);
            if (indexWriter != null) {
                indexWriter = null;
            }
        }
    }

    @Override
    public void beginIncrement() throws IOException {
        synchronized (lock) {
            consumerCount++;
        }
    }

    @Override
    public void endIncrement() {
        synchronized (lock) {
            consumerCount--;
        }
        while (consumerCount > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        synchronized (lock) {
            if (indexWriter != null) {
                try {
                    indexWriter.commit();
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
            }
            IOUtils.closeQuietly(indexWriter);
            if (indexWriter != null) {
                indexWriter = null;
            }
        }
    }

    @Override
    @PostConstruct
    public void registerToObserable() {
        Assert.notNull(dataObserable);
        dataObserable.registerObservers(this);
    }

}
