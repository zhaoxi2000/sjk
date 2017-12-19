package com.ijinshan.sjk.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.service.AbastractIndexUpdater;
import com.ijinshan.sjk.vo.index.LuceneFieldCollection;

@Service
public class QuickTipsIndexUpdaterImpl extends AbastractIndexUpdater {
    private static final Logger logger = LoggerFactory.getLogger(QuickTipsIndexUpdaterImpl.class);

    @Override
    public void beginAll() throws IOException {
        super.beginAll();
        synchronized (lock) {
            try {
                if (indexWriter == null) {
                    final String dir = appConfig.getAllQuickTipsIndexDir();
                    Directory directory = FSDirectory.open(new File(dir));

                    IndexWriterConfig iwc = new IndexWriterConfig(luceneVersion, analyzer);
                    iwc.setRAMBufferSizeMB(IndexWriterConfig.DEFAULT_RAM_BUFFER_SIZE_MB * 3);
                    iwc.setOpenMode(OpenMode.CREATE);

                    indexWriter = new IndexWriter(directory, iwc);
                }
            } catch (IOException e) {
                consumerCount--;
                logger.error("IOException", e);
                throw e;
            }
        }
    }

    @Override
    public void createAll(List<App> apps, boolean append) throws IOException {
        synchronized (lock) {
            if (CollectionUtils.isEmpty(apps)) {
                return;
            }
            addDocucmentToIndexer(apps);
           // indexWriter.commit();
        }
    }

    @Override
    public void endAll() {
        super.endAll();
        logger.info("close quickTips indexWriter!");
    }

    @Override
    public void beginIncrement() throws IOException {
        super.beginIncrement();
        synchronized (lock) {
            try {
                if (indexWriter == null) {
                    final String dir = appConfig.getAllQuickTipsIndexDir();
                    Directory directory = FSDirectory.open(new File(dir));
                    Assert.isTrue(IndexReader.indexExists(directory), "Invalid quickTips index!");

                    IndexWriterConfig iwc = new IndexWriterConfig(luceneVersion, analyzer);
                    iwc.setRAMBufferSizeMB(IndexWriterConfig.DEFAULT_RAM_BUFFER_SIZE_MB * 3);
                    iwc.setOpenMode(OpenMode.APPEND);

                    indexWriter = new IndexWriter(directory, iwc);
                }
            } catch (IOException e) {
                consumerCount--;
                logger.error("IOException", e);
                throw e;
            }
        }
    }

    @Override
    public void updateIncrement(List<App> apps) throws IOException {
        synchronized (lock) {
            if (CollectionUtils.isEmpty(apps)) {
                return;
            }
            logger.info("Before updating .QuickTips' indexWriter has numDos: {}", indexWriter.numDocs());
            Document doc = null;
            for (App app : apps) {
                int id = app.getId().intValue();
                NumericRangeQuery<Integer> idQuery = NumericRangeQuery.newIntRange(
                        LuceneFieldCollection.ColumnName.ID.getName(), id, id, true, true);
                indexWriter.deleteDocuments(idQuery);
            }
            indexWriter.commit();
            logger.info("After deleting .QuickTips' indexWriter has numDos: {}", indexWriter.numDocs());

            for (App app : apps) {
                doc = newDocument(app);// 构造文档并 添加域
                indexWriter.addDocument(doc);
            }
            indexWriter.commit();
            logger.info("After adding .QuickTips' indexWriter has numDos: {} \n", indexWriter.numDocs());
        }
    }

    private Document newDocument(App app) {
        Document doc = new Document();
        luceneFlds.getIdFld().setIntValue((app.getId().intValue()));
        luceneFlds.getNameFld().setValue(app.getName());
        luceneFlds.getDownloadRankFld().setIntValue(app.getDownloadRank());

        doc.add(luceneFlds.getIdFld());
        doc.add(luceneFlds.getNameFld());
        doc.add(luceneFlds.getDownloadRankFld());
        return doc;
    }

    @Override
    public void addDocucmentToIndexer(List<App> apps) {
        synchronized (lock) {
            Document doc = null;
            for (App app : apps) {
                try {
                    doc = newDocument(app);
                    indexWriter.addDocument(doc);
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
            }
        }
    }

    @Override
    public void delDoucmentFromIndex(List<Integer> appIds) throws IOException {
        synchronized (lock) {
            logger.info("Before deleting history .Search's indexWriter has numDos: {}", indexWriter.numDocs());
            if (CollectionUtils.isEmpty(appIds)) {
                return;
            }
            logger.info("prepare delete ids:{}", appIds);

            NumericRangeQuery<Integer> idQuery = null;
            for (Integer id : appIds) {
                idQuery = NumericRangeQuery.newIntRange(LuceneFieldCollection.ColumnName.ID.getName(), id, id, true,
                        true);
                indexWriter.deleteDocuments(idQuery);
            }
            indexWriter.commit();
        }
    }
}
