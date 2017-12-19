package com.ijinshan.sjk.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.EnumCatalog;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.service.QuickTipsService;
import com.ijinshan.util.IOUtils;
import com.ijinshan.util.PathUtils;

/**
 * @author LinZuXiong
 */
@Service
public class QuickTipsServiceImpl implements QuickTipsService {
    private static final Logger logger = LoggerFactory.getLogger(QuickTipsServiceImpl.class);

    private static final Logger dbLogger = LoggerFactory.getLogger("db.search");

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "sessionFactory")
    private SessionFactory sessions;
    @Resource(name = "appDaoImpl")
    private AppDao appDao;

    private final Version luceneVersion = Version.LUCENE_36;

    @Resource(name = "ikAnalyzer")
    private Analyzer analyzer;

    private IndexReader quickTipsIndexReader = null;
    private IndexSearcher quickTipsSearcher = null;

    @Resource(name = "fieldName")
    private String fieldName;

    @PreDestroy
    public void destory() {
        IOUtils.closeQuietly(quickTipsSearcher, quickTipsIndexReader);
    }

    @Override
    public String[] quickTips(String q) throws IOException {
        if (StringUtils.isEmpty(q) || q.length() > appConfig.getKeywordMaxLength()) {
            logger.error("empty keywords or over-length! {}", q);
            return null;
        }
        ScoreDoc[] docs = this.prefixSearch(q);
        if (docs != null) {
            Set<String> tipsSet = new LinkedHashSet<String>();
            for (ScoreDoc hit : docs) {
                int docID = hit.doc;
                Document doc = quickTipsSearcher.doc(docID);
                String name = doc.get(fieldName);
                // 去重的提示.
                if (name != null && !name.isEmpty()) {
                    tipsSet.add(name);
                }
                if (tipsSet.size() >= appConfig.getQuickTipsNum()) {
                    break;
                }
            }
            String[] tips = tipsSet.toArray(new String[tipsSet.size()]);
            tipsSet.clear();
            return tips;
        }
        return null;
    }

    @Override
    public synchronized void reset() throws IOException {
        if (appConfig.isInitIndex()) {
            createQuickTipsIndex();
        }
        resetClassFields();
    }

    private void resetClassFields() throws IOException {
        resetQuickTips();
    }

    private void resetQuickTips() throws IOException {
        String indexDir = appConfig.getOldAllQuickTipsIndex();
        Directory directory = FSDirectory.open(new File(indexDir));
        if (!IndexReader.indexExists(directory)) {
            logger.info("Please reset index firstly!");
            return;
        }
        Directory ram = new RAMDirectory(directory);
        this.quickTipsIndexReader = IndexReader.open(ram);
        logger.info("IndexReader has numDos: {}", this.quickTipsIndexReader.numDocs());

        IndexSearcher preIndexSearcher = this.quickTipsSearcher;
        this.quickTipsSearcher = new IndexSearcher(quickTipsIndexReader);
        IOUtils.closeQuietly(preIndexSearcher);
    }

    /**
     * 创建QuickTips索引
     * 
     * @throws IOException
     */
    private synchronized void createQuickTipsIndex() throws IOException {
        logger.info("createQuickTipsIndex...");
        IndexWriter indexerWriter = null;
        long count = 0;

        Session session = sessions.openSession();
        try {
            session.setDefaultReadOnly(true);
            count = appDao.countName(session);
        } finally {
            session.close();
        }

        final int pageSize = 10000;
        int totalPage = (int) (count / pageSize + 1);
        int currentPage = 1;
        try {
            File quickTipsIndexDir = new File(appConfig.getOldAllQuickTipsIndex());
            PathUtils.makeSureDir(quickTipsIndexDir);
            Directory directory = FSDirectory.open(quickTipsIndexDir);
            IndexWriterConfig iwc = new IndexWriterConfig(luceneVersion, null);
            iwc.setOpenMode(OpenMode.CREATE);
            iwc.setRAMBufferSizeMB(IndexWriterConfig.DEFAULT_RAM_BUFFER_SIZE_MB);
            indexerWriter = new IndexWriter(directory, iwc);
            pager: for (; currentPage <= totalPage; currentPage++) {
                List<App> list = null;
                session = sessions.openSession();
                try {
                    session.setDefaultReadOnly(true);
                    list = appDao.getForQuickTipsIndex(session, currentPage, pageSize);
                    if (list != null && !list.isEmpty()) {
                        addAppsToIndexerWriter(list, indexerWriter);
                        list.clear();
                        indexerWriter.commit();
                    } else {
                        break pager;
                    }
                } finally {
                    session.close();
                }
            } // end loop get all
            logger.info("createQuickTipsIndex done! IndexWriter has {} numDocs! ", indexerWriter.numDocs());
        } finally {
            IOUtils.closeQuietly(indexerWriter);
        }
    }

    private void addAppsToIndexerWriter(List<App> list, IndexWriter indexerWriter) {
        Field name = new Field(fieldName, "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
        NumericField catalog = new NumericField("catalog", Field.Store.NO, true);
        NumericField downloadRank = new NumericField("downloadRank", Field.Store.NO, true);
        for (App a : list) {
            try {
                Document doc = new Document();
                name.setValue(a.getName().toLowerCase());
                doc.add(name);
                downloadRank.setIntValue(a.getDownloadRank());
                doc.add(downloadRank);
                catalog.setIntValue(a.getCatalog());
                doc.add(catalog);
                indexerWriter.addDocument(doc);
            } catch (Exception e) {
                logger.error("Exception", e);
            }
        }
    }

    @Override
    public ScoreDoc[] prefixSearch(String keywords) throws IOException {
        if (StringUtils.isEmpty(keywords) || keywords.length() > appConfig.getKeywordMaxLength()) {
            logger.error("empty keywords or over-length! {}", keywords);
            return null;
        }
        Sort sort = new Sort(new SortField("downloadRank", SortField.INT, true));

        Term nameFldTerm = new Term(fieldName, keywords);
        PrefixQuery nameFldQuery = new PrefixQuery(nameFldTerm);

        NumericRangeQuery<Integer> catalogQuery = NumericRangeQuery.newIntRange("catalog",
                (int) EnumCatalog.SOFT.getCatalog(), (int) EnumCatalog.GAME.getCatalog(), true, true);
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(catalogQuery, Occur.MUST);
        booleanQuery.add(nameFldQuery, Occur.MUST);

        TopDocs topDocs = quickTipsSearcher.search(booleanQuery, appConfig.getQuickTipsNum() * 2, sort);
        ScoreDoc[] docs = topDocs.scoreDocs;
        return docs;
    }
}
