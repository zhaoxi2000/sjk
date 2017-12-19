package com.ijinshan.sjk.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.EnumCatalog;
import com.ijinshan.sjk.config.SearchRankType;
import com.ijinshan.sjk.dao.AppDao;
import com.ijinshan.sjk.dao.TagDao;
import com.ijinshan.sjk.mapper.AppMapper;
import com.ijinshan.sjk.mapper.CatalogMapper;
import com.ijinshan.sjk.mapper.KeywordMapper;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.service.QuickTipsService;
import com.ijinshan.sjk.service.SearchService;
import com.ijinshan.sjk.util.ListSort;
import com.ijinshan.sjk.vo.MobileSearchApp;
import com.ijinshan.util.IOUtils;
import com.ijinshan.util.Pager;

@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    private static final Logger dbLogger = LoggerFactory.getLogger("db.search");

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appDaoImpl")
    private AppDao appDao;
    @Resource(name = "appMapper")
    private AppMapper appMapper;

    @Resource(name = "catalogMapper")
    private CatalogMapper catalogMapper;
    @Resource(name = "tagDaoImpl")
    private TagDao tagDao;

    @Resource(name = "keywordMapper")
    private KeywordMapper keywordMapper;

    @Resource(name = "sessionFactory")
    private SessionFactory sessions;

    private final Version luceneVersion = Version.LUCENE_36;
    @Resource(name = "ikAnalyzer")
    private Analyzer analyzer;

    private IndexReader indexReader = null;
    private IndexSearcher indexSearcher = null;

    private Map<Integer, Catalog> catalogs = new HashMap<Integer, Catalog>();
    private Map<Integer, Tag> tags = new HashMap<Integer, Tag>();

    @Resource(name = "quickTipsServiceImpl")
    private QuickTipsService quickTipsService;

    @Resource(name = "fieldName")
    private String fieldName;

    @PreDestroy
    public void destroy() {
        IOUtils.closeQuietly(this.indexSearcher, this.indexReader);
    }

    @Override
    public String[] quickTips(String keywords) throws IOException {
        return quickTipsService.quickTips(keywords);
    }

    @Override
    public synchronized void reset() {
        final boolean init = appConfig.isInitIndex();
        appConfig.setInitIndex(true);
        init();
        appConfig.setInitIndex(init);
    }

    @Override
    @PostConstruct
    public synchronized void init() {
        long startTime = System.currentTimeMillis();
        Session session = sessions.openSession();
        try {
            Thread searchSuggestionThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // call the another class
                        quickTipsService.reset();
                    } catch (IOException e) {
                        logger.error("Exception!Fatal Error!", e);
                    }
                }
            });
            searchSuggestionThread.start();

            session.setDefaultReadOnly(true);
            /** 初始化catalog and tags */
            if (catalogs != null) {
                catalogs.clear();
            }
            List<Catalog> list = catalogMapper.list();
            if (list != null) {
                for (Catalog c : list) {
                    catalogs.put(c.getId(), c);
                }
                list.clear();
            }
            if (tags != null) {
                tags.clear();
            }
            List<Tag> taglist = tagDao.getList(session);
            if (taglist != null) {
                for (Tag tag : taglist) {
                    tags.put(tag.getId(), tag);
                }
                taglist.clear();
            }

            if (appConfig.isInitIndex()) {
                resetIndex();
            }
            resetClassFields();

            searchSuggestionThread.join();

            if (this.indexSearcher == null) {
                logger.error("Initailizer indexSearcher must not be null!");
            }
            logger.info("Initializer cost time {} ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.error("Exception!Fatal Error!", e);
        } finally {
            session.close();
        }
    }

    private synchronized void resetIndex() throws IOException {
        logger.info("resetIndex...");
        long start = System.currentTimeMillis();
        IndexWriter indexerWriter = null;

        long count = 0;
        Session session = sessions.openSession();
        try {
            session.setDefaultReadOnly(true);
            count = appDao.count(session);
        } finally {
            session.close();
        }

        final int pageSize = 10000;
        int totalPage = (int) (count / pageSize + 1);
        int currentPage = 1;
        try {
            Directory directory = FSDirectory.open(new File(appConfig.getOldAllIndexDir()));
            IndexWriterConfig iwc = new IndexWriterConfig(luceneVersion, analyzer);
            iwc.setOpenMode(OpenMode.CREATE);
            iwc.setRAMBufferSizeMB(IndexWriterConfig.DEFAULT_RAM_BUFFER_SIZE_MB * 3);
            indexerWriter = new IndexWriter(directory, iwc);
            pager: for (; currentPage <= totalPage; currentPage++) {
                session = sessions.openSession();
                try {
                    session.setDefaultReadOnly(true);
                    List<App> list = appDao.getForIndex(session, null, null, currentPage, pageSize);
                    if (list != null && !list.isEmpty()) {
                        addAppsToIndexer(session, list, indexerWriter);
                        list.clear();
                        indexerWriter.commit();
                    } else {
                        break pager;
                    }
                } finally {
                    session.close();
                }
            } // end loop all
            logger.info("resetIndex done! IndexerWriter has {} documents! Costs time: {}", indexerWriter.numDocs(),
                    System.currentTimeMillis() - start);
        } finally {
            IOUtils.closeQuietly(indexerWriter);
        }
    }

    private void addAppsToIndexer(Session session, List<App> list, IndexWriter indexWriter) {
        NumericField idFld = new NumericField("id", Field.Store.YES, true);
        Field nameFld = new Field(fieldName, "", Field.Store.NO, Field.Index.NOT_ANALYZED);
        Field analyzedNameFld = new Field("analyzedName", "", Field.Store.NO, Field.Index.ANALYZED);
        Field keywordsFld = new Field("keywords", "", Field.Store.NO, Field.Index.NOT_ANALYZED);
        Field catalogNameFld = new Field("catalogName", "", Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS);
        Field analyzedCatalogNameFld = new Field("analyzedCatalogName", "", Field.Store.NO, Field.Index.ANALYZED);
        Field tagsFld = new Field("tagsName", "", Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS);
        NumericField catalogNumFld = new NumericField("catalog", Field.Store.NO, true);
        NumericField officialNumFld = new NumericField("official", Field.Store.NO, true);
        NumericField downloadRankFld = new NumericField("downloadRank", Field.Store.YES, true);

        for (App a : list) {
            try {
                Document doc = new Document();
                idFld.setIntValue(a.getId());
                doc.add(idFld);

                String appName = a.getName().toLowerCase();
                nameFld.setValue(appName);
                nameFld.setBoost(5.0f);
                doc.add(nameFld);
                analyzedNameFld.setValue(appName);
                analyzedNameFld.setBoost(3.0f);
                doc.add(analyzedNameFld);

                String keywordsVal = a.getKeywords() == null ? "" : a.getKeywords().toLowerCase();
                keywordsFld.setValue(keywordsVal);
                doc.add(keywordsFld);

                Catalog catalog = catalogs.get(a.getSubCatalog());
                String catalogValue = catalog == null ? "" : catalog.getName().toLowerCase();
                catalogNameFld.setValue(catalogValue);
                doc.add(catalogNameFld);

                analyzedCatalogNameFld.setValue(catalogValue);
                analyzedCatalogNameFld.setBoost(1f);
                doc.add(analyzedCatalogNameFld);

                List<Integer> tagIds = tagDao.getPkByAppId(session, a.getId());
                StringBuilder tagsValue = new StringBuilder(10).append("");
                if (tagIds != null && !tagIds.isEmpty()) {
                    for (Integer tagId : tagIds) {
                        Tag tag = tags.get(tagId);
                        String tagName = tag == null ? "" : tag.getName();
                        tagsValue.append(tagName.toLowerCase()).append(",");
                    }
                }
                tagsFld.setValue(tagsValue.toString());

                catalogNumFld.setIntValue(a.getCatalog());
                doc.add(catalogNumFld);

                // advertisement
                NumericField noAdsNum = new NumericField("noAds", Field.Store.NO, true);
                String adPopupTypes = a.getAdPopupTypes();
                String adActionTypes = a.getAdActionTypes();
                if (adPopupTypes != null) {
                    adPopupTypes = adPopupTypes.trim().replace(",,", ",");
                }
                if (adActionTypes != null) {
                    adActionTypes = adActionTypes.trim().replace(",,", ",");
                }
                if ((adPopupTypes == null || adPopupTypes.isEmpty())
                        && (adActionTypes == null || adActionTypes.isEmpty())) {
                    noAdsNum.setIntValue(1);
                } else {
                    noAdsNum.setIntValue(0);
                }
                doc.add(noAdsNum);

                // official
                boolean official = false;
                if (a.getSignatureSha1() != null && a.getSignatureSha1().equals(a.getOfficialSigSha1())) {
                    official = true;
                }
                if (official) {
                    officialNumFld.setIntValue(1);
                } else {
                    officialNumFld.setIntValue(0);
                }
                doc.add(officialNumFld);

                downloadRankFld.setIntValue(a.getDownloadRank());
                doc.add(downloadRankFld);

                indexWriter.addDocument(doc);
            } catch (Exception e) {
                logger.error("Exception", e);
            }
        }
    }

    @Override
    public Pager<MobileSearchApp> search(final String q, Keyword keywordModel, Integer page, Integer rows,
            Integer noAds, Integer official) throws Exception {
        if (StringUtils.isEmpty(q) || q.length() > appConfig.getKeywordMaxLength()) {
            logger.error("empty keywords or over-length! {}", q);
            return new Pager<MobileSearchApp>();
        }
        Assert.isTrue(rows > 0 && rows <= 30, "invalid rows " + rows);
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }

        TopDocs topDocs = getSearchTotalhits(q, keywordModel, null, bNoAds, bOfficial);
        Pager<MobileSearchApp> pager = searchInTotalhitsByQuery(q, page, rows, this.indexSearcher, topDocs);
        return pager;
    }

    private Pager<MobileSearchApp> searchInTotalhitsByQuery(String keywords, Integer page, int rows,
            IndexSearcher searcher, TopDocs topDocs) throws CorruptIndexException, IOException {
        List<Integer> searchIds;
        int queryNum = 0;
        int start = 0;
        if (page != null && page.intValue() > 0) {
            // pagination
            start = (page - 1) * rows;
            queryNum = start + rows;
        } else {
            queryNum = rows;
        }
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        int hitCount = scoreDocs == null ? 0 : scoreDocs.length;
        List<Integer> mutileSearchIds = null;
        if (hitCount > 0) {
            final int validSize = Math.min(queryNum, hitCount) - start;
            if (validSize > 0) {
                mutileSearchIds = new ArrayList<Integer>(validSize);
                for (int i = start; i < queryNum && i < hitCount; i++) {
                    ScoreDoc hit = scoreDocs[i];
                    int docID = hit.doc;
                    Document doc = searcher.doc(docID);
                    int id = Integer.parseInt(doc.get("id"));
                    mutileSearchIds.add(id);
                }
            }
        }
        searchIds = mutileSearchIds;
        List<MobileSearchApp> list = null;
        if (searchIds != null && !searchIds.isEmpty()) {
            list = appMapper.getByIds(searchIds, null, null);
            ListSort.sort(searchIds, list, true);
        }
        dbLogger.info("Keyword: {}\t HitCount: {}.", keywords, hitCount);
        Pager<MobileSearchApp> pager = new Pager<MobileSearchApp>();
        pager.setResult(list);
        pager.setRows(hitCount);
        return pager;
    }

    @Override
    public void resetClassFields() throws IOException {
        Directory directory = FSDirectory.open(new File(appConfig.getOldAllIndexDir()));
        if (!IndexReader.indexExists(directory)) {
            logger.error("Please reset index firstly!");
            return;
        }
        Directory ram = new RAMDirectory(directory);
        IndexReader preIndexReader = this.indexReader;
        this.indexReader = IndexReader.open(ram);
        logger.info("IndexReader has numDos: {}", this.indexReader.numDocs());

        IndexSearcher preIndexSearcher = this.indexSearcher;
        this.indexSearcher = new IndexSearcher(indexReader);
        IOUtils.closeQuietly(preIndexSearcher, preIndexReader);
    }

    @Override
    public long searchCount(String keywords, Keyword keywordModel, Short catalog, Boolean noAds, Boolean official)
            throws Exception {
        TopDocs topDocs = getSearchTotalhits(keywords, keywordModel, catalog, noAds, official);
        return topDocs.totalHits;
    }

    private TopDocs getSearchTotalhits(String q, Keyword keywordModel, Short catalog, Boolean noAds, Boolean official)
            throws ParseException, IOException {
        NumericRangeQuery<Integer> catalogNumQuery = null;
        if (catalog != null) {
            if (catalog.shortValue() != EnumCatalog.SOFT.getCatalog()) {
                catalogNumQuery = NumericRangeQuery.newIntRange("catalog", (int) EnumCatalog.GAME.getCatalog(),
                        (int) EnumCatalog.BIGGAME.getCatalog(), true, true);
            } else {
                catalogNumQuery = NumericRangeQuery.newIntRange("catalog", (int) catalog, (int) catalog, true, true);
            }
        } else {
            catalogNumQuery = NumericRangeQuery.newIntRange("catalog", (int) EnumCatalog.SOFT.getCatalog(),
                    (int) EnumCatalog.GAME.getCatalog(), true, true);
        }

        final String chSpacesReg = " |　";
        String[] phrases = q.split(chSpacesReg);
        if (phrases.length >= 2) {
            // phrases = escapeKeywords.replaceAll(chSpacesReg, " OR ");
            // Query analyzedNameQuery = new QueryParser(luceneVersion,
            // "analyzedName", analyzer).parse(phrases);
            return mutipPhraseSearch(phrases, catalogNumQuery, catalog);
        }

        final String keywordsWithWildcard = new StringBuilder("*").append(q).append("*").toString();
        final String escapeKeywords = QueryParser.escape(q);
        Query nameQuery = new WildcardQuery(new Term(fieldName, keywordsWithWildcard));
        Query analyzedNameQuery = new QueryParser(luceneVersion, "analyzedName", analyzer).parse(escapeKeywords);

        Query keywordsQuery = new WildcardQuery(new Term("keywords", keywordsWithWildcard));
        Query catalogQuery = new WildcardQuery(new Term("catalogName", escapeKeywords));
        Query tagsQuery = new WildcardQuery(new Term("tagsName", keywordsWithWildcard));

        BooleanQuery query1 = new BooleanQuery();

        BooleanQuery query1WithCatalog = new BooleanQuery();
        query1WithCatalog.add(query1, Occur.MUST);
        if (catalog != null) {
            query1WithCatalog.add(catalogNumQuery, Occur.MUST);
        }
        if (noAds != null && noAds.booleanValue()) {
            NumericRangeQuery<Integer> noAdsQuery = NumericRangeQuery.newIntRange("noAds", 1, 1, true, true);
            query1WithCatalog.add(noAdsQuery, Occur.MUST);
        }
        if (official != null && official.booleanValue()) {
            NumericRangeQuery<Integer> officialQuery = NumericRangeQuery.newIntRange("official", 1, 1, true, true);
            query1WithCatalog.add(officialQuery, Occur.MUST);
        }

        // Sort sort = new Sort(new SortField("official", SortField.INT, true),
        // new SortField("downloadRank",
        // SortField.INT, true));

        Sort sort = new Sort(new SortField("downloadRank", SortField.INT, true));

        Keyword keyword = null;
        if (keywordModel != null && q.equals(keywordModel.getTargetKeyword())) {
            keyword = keywordMapper.get(q);
        } else {
            keyword = keywordModel;
        }

        TopDocs topDocs = null;
        if (keyword != null && SearchRankType.valueOf(keyword.getRankType().toUpperCase()) == SearchRankType.DOCUMENT) {
            query1.add(nameQuery, Occur.SHOULD);
            nameQuery.setBoost(5);
            query1.add(analyzedNameQuery, Occur.SHOULD);
            analyzedNameQuery.setBoost(2);
            topDocs = this.indexSearcher.search(query1WithCatalog, Integer.MAX_VALUE);
        } else {
            query1.add(nameQuery, Occur.MUST);
            topDocs = this.indexSearcher.search(query1WithCatalog, Integer.MAX_VALUE, sort);
        }
        // 分词搜索
        if (topDocs.totalHits <= 0) {
            query1.add(analyzedNameQuery, Occur.SHOULD);
            topDocs = this.indexSearcher.search(query1WithCatalog, Integer.MAX_VALUE);
        }

        // 搜索 keywords 不靠谱的.
        if (topDocs.totalHits <= 0) {
            BooleanQuery query2 = new BooleanQuery();
            BooleanQuery query2WithCatalog = new BooleanQuery();
            query2WithCatalog.add(query2, Occur.MUST);
            if (catalog != null) {
                query2WithCatalog.add(catalogNumQuery, Occur.MUST);
            }
            query2.add(keywordsQuery, Occur.SHOULD);
            query2.add(catalogQuery, Occur.SHOULD);
            query2.add(tagsQuery, Occur.SHOULD);

            if (noAds != null && noAds.booleanValue()) {
                NumericRangeQuery<Integer> noAdsQuery = NumericRangeQuery.newIntRange("noAds", 1, 1, true, true);
                query2WithCatalog.add(noAdsQuery, Occur.MUST);
            }
            if (official != null && official.booleanValue()) {
                NumericRangeQuery<Integer> officialQuery = NumericRangeQuery.newIntRange("official", 1, 1, true, true);
                query2WithCatalog.add(officialQuery, Occur.MUST);
            }

            topDocs = this.indexSearcher.search(query2WithCatalog, Integer.MAX_VALUE, sort);
        }
        return topDocs;
    }

    private TopDocs mutipPhraseSearch(String[] phrases, NumericRangeQuery<Integer> catalogNumQuery, Short catalog)
            throws ParseException, IOException {
        TopDocs topDocs = null;
        BooleanQuery query1 = new BooleanQuery();

        int len = phrases.length, index = 0;
        float factor = 0.25f;
        for (String q : phrases) {
            // 每个词的长度越长则可信度越高.
            final float f = 1 - index * factor + (q.length() - 1) * 0.3f;
            final String keywordsWithWildcard = new StringBuilder("*").append(q).append("*").toString();
            Query nameQuery = new WildcardQuery(new Term(fieldName, keywordsWithWildcard));
            nameQuery.setBoost(f * 5 * len);
            query1.add(nameQuery, Occur.SHOULD);
            index++;
        }

        BooleanQuery query1WithCatalog = new BooleanQuery();
        query1WithCatalog.add(query1, Occur.MUST);
        if (catalog != null) {
            query1WithCatalog.add(catalogNumQuery, Occur.MUST);
        }
        Sort sort = new Sort(new SortField("downloadRank", SortField.INT, true));
        topDocs = this.indexSearcher.search(query1WithCatalog, Integer.MAX_VALUE, sort);
        if (topDocs.totalHits < 1) {
            index = 0;
            for (String q : phrases) {
                final float f = 1 - index * factor;
                final String escapeQ = QueryParser.escape(q);
                Query analyzedNameQuery = new QueryParser(luceneVersion, "analyzedName", analyzer).parse(escapeQ);
                analyzedNameQuery.setBoost(f * 3 * len);
                query1.add(analyzedNameQuery, Occur.SHOULD);

                Query analyzedCatalogNameQuery = new QueryParser(luceneVersion, "analyzedCatalogName", analyzer)
                        .parse(escapeQ);
                analyzedCatalogNameQuery.setBoost(4 * len);
                query1.add(analyzedCatalogNameQuery, Occur.SHOULD);
                index++;
            }
            topDocs = this.indexSearcher.search(query1WithCatalog, Integer.MAX_VALUE);
        }
        return topDocs;
    }

    @Override
    public Keyword getTargetKeyword(String q) {
        return keywordMapper.get(q);
    }

}
