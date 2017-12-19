package com.ijinshan.sjk.ntxservice.impl;

import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.ANALYZED_DESCRIPTION;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.ANALYZED_KEYWORDS;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.ANALYZED_NAME;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.CATALOG_NAME;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.DOWNOLOAD_RANK;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.MARKET_RANK;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.NAME;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.OFFICIAL;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.TAG_NAME;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.SearchRankType;
import com.ijinshan.sjk.ntxservice.SearchService1;
import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.service.AppService;
import com.ijinshan.sjk.service.KeywordService;
import com.ijinshan.sjk.util.ListSort;
import com.ijinshan.sjk.util.TopDocsUtil;
import com.ijinshan.sjk.vo.pc.SearchApp;
import com.ijinshan.util.IOUtils;
import com.ijinshan.util.Pager;

/**
 * @author Linzuxiong
 */
@Service
public class SearchService1Impl implements SearchService1 {
    private static final Logger logger = LoggerFactory.getLogger(SearchService1Impl.class);
    public final Pager<SearchApp> emptyResultPager = new Pager<SearchApp>();
    public final Sort sortByDownloadRank = new Sort(new SortField(DOWNOLOAD_RANK.getName(), SortField.INT, true),
            new SortField(MARKET_RANK.getName(), SortField.INT, false));
    public final Sort sortByManual = new Sort(new SortField(OFFICIAL.getName(), SortField.INT, true), new SortField(
            MARKET_RANK.getName(), SortField.INT, false), new SortField(DOWNOLOAD_RANK.getName(), SortField.INT, true));
    public final float factor = 0.25f;
    public final int MAX_TOP = 1000;

    /**
     * 按约定将连续或是中文空格将全部换成单个英文空格.
     */
    public final String chSpacesRegex = "( |　)+";
    public final String qqSearchRegex = ".*qq ?[1-9]?\\d*";
    public final String space = " ";

    @Resource(name = "appConfig")
    private AppConfig appConfig;
    @Resource(name = "appServiceImpl")
    private AppService appService;
    @Resource(name = "keywordServiceImpl")
    private KeywordService keywordService;

    protected final Version luceneVersion = Version.LUCENE_36;
    @Resource(name = "ikAnalyzer")
    protected Analyzer analyzer;

    private IndexReader indexReader = null;
    private IndexSearcher indexSearcher = null;

    @PostConstruct
    public void reset() {
        final String dirPath = appConfig.getAllIndexDir();
        try {
            Directory directory = FSDirectory.open(new File(dirPath));
            if (!IndexReader.indexExists(directory)) {
                logger.error("Please reset index firstly! The path: {}", dirPath);
                return;
            }
            Directory ram = new RAMDirectory(directory);
            if (this.indexReader == null) {
                reopenIndexSearcher(ram);
            } else {
                // changed
                logger.info("Search's new indexReader!");
                final IndexReader preIndexReader = this.indexReader;
                final IndexSearcher preIndexSearcher = this.indexSearcher;
                reopenIndexSearcher(ram);
                IOUtils.closeQuietly(preIndexSearcher, preIndexReader);
                logger.info("Reload search index!");
            }
            logger.info("Search's IndexReader has numDos: {}", this.indexReader.numDocs());
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    private void reopenIndexSearcher(Directory ram) throws CorruptIndexException, IOException {
        this.indexReader = IndexReader.open(ram);
        this.indexSearcher = new IndexSearcher(this.indexReader);
    }

    @Override
    public Pager<SearchApp> search(String q, int page, int rows, Integer noAds, Integer official) throws Exception {
        Assert.isTrue(page > 0, "invalid page " + page);
        Assert.isTrue(rows > 0 && rows <= appConfig.getSearchMaxNum(), "invalid rows " + rows);

        if (q != null) {
            q = q.trim().toLowerCase();
        }
        if (q == null || q.isEmpty() || q.length() > appConfig.getKeywordMaxLength()) {
            logger.error("empty keywords or over-length! {}", q);
            return emptyResultPager;
        }
        q = q.replaceAll(chSpacesRegex, space);
        Boolean bNoAds = null, bOfficial = null;
        if (noAds != null && noAds.intValue() == 1) {
            bNoAds = Boolean.TRUE;
        }
        if (official != null && official.intValue() == 1) {
            bOfficial = Boolean.TRUE;
        }
        TopDocs topDocs = getSearchTotalhits(q, bNoAds, bOfficial);
        Pager<SearchApp> pager = searchInTotalhitsByQuery(topDocs, page, rows);

        return pager;
    }

    @Override
    public Pager<SearchApp> searchInTotalhitsByQuery(TopDocs topDocs, int page, int rows) throws Exception {
        final Pager<SearchApp> pager = new Pager<SearchApp>();
        if (topDocs == null) {
            pager.setRows(0);
            return pager;
        }
        // pagination
        // start : offset of the ScoreDoc[]
        // queryNum : the end position of the ScoreDoc[]
        final int start = (page - 1) * rows, queryNum = start + rows;

        final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        final int hitCount = topDocs.totalHits;
        pager.setRows(hitCount);
        if (hitCount > 0) {
            final int validSize = Math.min(queryNum, hitCount) - start;
            if (validSize > 0) {
                final List<Integer> mutileSearchIds = new ArrayList<Integer>(validSize);
                for (int i = start; i < queryNum && i < hitCount; i++) {
                    ScoreDoc hit = scoreDocs[i];
                    final int docID = hit.doc;
                    Document doc = indexSearcher.doc(docID);
                    int id = Integer.parseInt(doc.get("id"));
                    mutileSearchIds.add(id);
                } // end loop
                final List<SearchApp> list = appService.getSearchAppByIds(mutileSearchIds);
                ListSort.sort(mutileSearchIds, list, true);
                mutileSearchIds.clear();
                pager.setResult(list);
            }
        }
        return pager;
    }

    @Override
    public TopDocs getSearchTotalhits(final String q, final Boolean bNoAds, final Boolean bOfficial) throws Exception {
        final String[] qs = q.split(chSpacesRegex);
        return getSearchTotalhitsByOriginalPhrase(q, qs, bNoAds, bOfficial);
    }

    private TopDocs getSearchTotalhitsByOriginalPhrase(String q, String[] qs, Boolean bNoAds, Boolean bOfficial)
            throws Exception, IOException {
        // avoid the dead loop
        final Keyword manualKeyword = keywordService.getByName(q);
        SearchRankType searchRankType = null;
        String[] targetQs = null;
        if (manualKeyword == null) {
            // TODO 最好做qq过滤
            if (q.matches(qqSearchRegex)) {

            } else if (qs.length < 2) {
                targetQs = new String[] { q };
            } else {
                return getSearchTotalhitsByMultiplyPhrase(q, qs, bNoAds, bOfficial);
            }
        } else if (manualKeyword.getTargetKeyword() != null) {
            if (!manualKeyword.getTargetKeyword().isEmpty()) {
                targetQs = manualKeyword.getTargetKeyword().split(",");
            } else {
            }
            searchRankType = SearchRankType.valueOf(manualKeyword.getRankType().toUpperCase());
        }
        // 根据搜索的特点,热门和长度做处理
        if (searchRankType == null) {
            if (q.matches(qqSearchRegex)) {
                searchRankType = SearchRankType.ONLY_NAME_DOWNLOAD;
                q = "qq";
            } else {
                // TODO 看需求. 百度是有查询其它字段的.并做分词搜索.
                // if (q.length() > 3) {
                // // searchRankType = SearchRankType.DOCUMENT;
                // searchRankType = SearchRankType.ONLY_NAME_DOWNLOAD;
                // } else if (q.length() == 3) {
                // // searchRankType = SearchRankType.ONLY_NAME_DOCUMENT;
                // searchRankType = SearchRankType.ONLY_NAME_DOWNLOAD;
                // } else if (q.length() == 2) {
                // // 不对搜索词进行分词分析的匹配.
                // searchRankType = SearchRankType.ONLY_NAME_DOWNLOAD;
                if (q.length() > 1) {
                    searchRankType = SearchRankType.ONLY_NAME_DOWNLOAD;
                } else {
                    searchRankType = SearchRankType.DOWNLOAD;
                }
            }
        }
        if (targetQs == null) {
            return getSearchTotalhitsBySinglePhrase(q, searchRankType, bNoAds, bOfficial);
            // TODO 不适合现在做.
            // if (docs.totalHits < 1 && q.length() > 3) {
            // searchRankType = SearchRankType.DOWNLOAD;
            // return getSearchTotalhitsBySinglePhrase(q, searchRankType,
            // catalog, bNoAds, bOfficial);
            // }
        } else if (targetQs.length <= 1) {
            return getSearchTotalhitsBySinglePhrase(targetQs[0], searchRankType, bNoAds, bOfficial);
        }
        // has munual search words , multi phrases
        final TopDocs[] topDocsArray = new TopDocs[targetQs.length];
        for (int i = 0; i < targetQs.length; i++) {
            topDocsArray[i] = getSearchTotalhitsBySinglePhrase(targetQs[i], searchRankType, bNoAds, bOfficial);
        }
        TopDocs topDocs = null;
        if (searchRankType == SearchRankType.DOWNLOAD || searchRankType == SearchRankType.ONLY_NAME_DOWNLOAD) {
            topDocs = TopDocs.merge(sortByDownloadRank, Integer.MAX_VALUE, topDocsArray);
        } else {
            topDocs = TopDocs.merge(null, Integer.MAX_VALUE, topDocsArray);
        }
        return TopDocsUtil.mergeDuplicateDocId(topDocs);
    }

    @Override
    public TopDocs getSearchTotalhitsBySinglePhrase(String targetQ, SearchRankType searchRankType, Boolean bNoAds,
            Boolean bOfficial) throws Exception {
        TopDocs topDocs = null;
        final BooleanQuery query = new BooleanQuery();
        fiterSafeToQuery(query, bNoAds, bOfficial);

        final BooleanQuery pharseQuery = new BooleanQuery();
        query.add(pharseQuery, Occur.MUST);

        final String escapeQ = QueryParser.escape(targetQ);
        final String fullnameWithWildcard = new StringBuilder("*").append(targetQ).append("*").toString();

        Query catalogNameQuery = null, tagNameQuery = null, analyzedNameQuery = null;
        if (targetQ.length() > 1) {
            catalogNameQuery = new PrefixQuery(new Term(CATALOG_NAME.getName(), targetQ));
            pharseQuery.add(catalogNameQuery, Occur.SHOULD);

            tagNameQuery = new PrefixQuery(new Term(TAG_NAME.getName(), targetQ));
            pharseQuery.add(tagNameQuery, Occur.SHOULD);

            // analyzed 分词
            if (searchRankType != null
                    && (searchRankType == SearchRankType.DOCUMENT || searchRankType == SearchRankType.ONLY_NAME_DOCUMENT)) {
                analyzedNameQuery = new QueryParser(luceneVersion, ANALYZED_NAME.getName(), analyzer).parse(escapeQ);
                pharseQuery.add(analyzedNameQuery, Occur.SHOULD);
            }
        }

        Query fullnameQuery = new WildcardQuery(new Term(NAME.getName(), fullnameWithWildcard));
        pharseQuery.add(fullnameQuery, Occur.SHOULD);

        Query namePrefixQuery = new PrefixQuery(new Term(NAME.getName(), targetQ));
        pharseQuery.add(namePrefixQuery, Occur.SHOULD);

        if (searchRankType != null
                && (searchRankType == SearchRankType.DOWNLOAD || searchRankType == SearchRankType.ONLY_NAME_DOWNLOAD)) {
            topDocs = indexSearcher.search(query, MAX_TOP, sortByDownloadRank);
            // if (topDocs.totalHits < 1 && analyzedNameQuery == null) {
            // analyzedNameQuery = new QueryParser(luceneVersion,
            // ANALYZED_NAME.getName(), analyzer).parse(escapeQ);
            // query.add(analyzedNameQuery, Occur.SHOULD);
            // topDocs = indexSearcher.search(analyzedNameQuery, MAX_TOP,
            // sort);
            // }
        } else {
            if (targetQ.length() > 1) {
                Query anaylzedKeywordsQuery = new QueryParser(luceneVersion, ANALYZED_KEYWORDS.getName(), analyzer)
                        .parse(escapeQ);
                pharseQuery.add(anaylzedKeywordsQuery, Occur.SHOULD);
                anaylzedKeywordsQuery.setBoost(5);
            }
            if (catalogNameQuery != null) {
                catalogNameQuery.setBoost(17);
            }
            if (analyzedNameQuery != null) {
                analyzedNameQuery.setBoost(17);
            }
            fullnameQuery.setBoost(87);
            namePrefixQuery.setBoost(93);
            topDocs = indexSearcher.search(query, MAX_TOP);
            if (topDocs.totalHits < 1) {
                Query analyzedDesc = new QueryParser(luceneVersion, ANALYZED_DESCRIPTION.getName(), analyzer)
                        .parse(escapeQ);
                analyzedDesc.setBoost(1);
                topDocs = indexSearcher.search(analyzedDesc, MAX_TOP);
            }
        }
        return topDocs;
    }

    @Override
    public TopDocs getSearchTotalhitsByMultiplyPhrase(String targetQ, String[] qs, Boolean bNoAds, Boolean bOfficial)
            throws Exception {
        Assert.isTrue(qs.length > 1, "invalid query words length");
        TopDocs topDocs = null;
        BooleanQuery query1 = new BooleanQuery();
        fiterSafeToQuery(query1, bNoAds, bOfficial);

        final BooleanQuery pharseQuery = new BooleanQuery();
        query1.add(pharseQuery, Occur.MUST);

        final int len = qs.length;
        int index = 0;

        final String fullnameWithWildcard = new StringBuilder(targetQ.length() + 2).append("*").append(targetQ)
                .append("*").toString();
        final Query fullnameQuery = new WildcardQuery(new Term(NAME.getName(), fullnameWithWildcard));
        pharseQuery.add(fullnameQuery, Occur.SHOULD);
        final Query firstWordNamePrefixQuery = new PrefixQuery(new Term(NAME.getName(), qs[0]));
        pharseQuery.add(firstWordNamePrefixQuery, Occur.SHOULD);

        firstWordNamePrefixQuery.setBoost(10);
        fullnameQuery.setBoost(111);
        // TODO 可以先按名称的模糊匹配来查第一次.

        index = 0;
        for (String oneQ : qs) {
            // 每个词的长度越长则可信度越高.至少是两个字
            final int oneWordLen = oneQ.length();
            final float f = (1 - index * factor + (oneWordLen - 1) * 0.5f) / 10;
            final String oneQWithWildcard = new StringBuilder(oneWordLen + 2).append("*").append(oneQ).append("*")
                    .toString();
            Query nameQuery = new WildcardQuery(new Term(NAME.getName(), oneQWithWildcard));
            nameQuery.setBoost(f * 17 * len);
            pharseQuery.add(nameQuery, Occur.SHOULD);

            Query tagNameQuery = new PrefixQuery(new Term(TAG_NAME.getName(), oneQ));
            pharseQuery.add(tagNameQuery, Occur.SHOULD);

            index++;
        }

        index = 0;
        final BooleanQuery query = new BooleanQuery();
        fiterSafeToQuery(query, bNoAds, bOfficial);
        for (String oneQ : qs) {
            final float f = (1 - index * factor + (oneQ.length() - 1) * 0.5f) / 10;
            final String escapeQ = QueryParser.escape(oneQ);
            final String oneQWithWildcard = new StringBuilder("*").append(oneQ).append("*").toString();
            if (oneQ.length() > 1) {
                Query anaylzedKeywordsQuery = new QueryParser(luceneVersion, ANALYZED_KEYWORDS.getName(), analyzer)
                        .parse(escapeQ);
                anaylzedKeywordsQuery.setBoost(f * 3 * len);
                query.add(anaylzedKeywordsQuery, Occur.SHOULD);

                Query analyzedNameQuery = new QueryParser(luceneVersion, ANALYZED_NAME.getName(), analyzer)
                        .parse(escapeQ);
                analyzedNameQuery.setBoost(f * 7 * len);
                query.add(analyzedNameQuery, Occur.SHOULD);

                Query catalogNameQuery = new WildcardQuery(new Term(CATALOG_NAME.getName(), oneQWithWildcard));
                catalogNameQuery.setBoost(f * 11 * len);
                query.add(catalogNameQuery, Occur.SHOULD);

                Query tagNameQuery = new PrefixQuery(new Term(TAG_NAME.getName(), oneQ));
                pharseQuery.add(tagNameQuery, Occur.SHOULD);
            }

            index++;
        }
        pharseQuery.add(query, Occur.SHOULD);
        topDocs = indexSearcher.search(query1, MAX_TOP);
        if (topDocs.totalHits < 1) {
            topDocs = indexSearcher.search(query, MAX_TOP, sortByDownloadRank);
        }
        if (topDocs.totalHits < 1) {
            index = 0;
            for (String oneQ : qs) {
                final float f = (1 - index * factor + (oneQ.length() - 1) * 0.5f) / 10;
                final String escapeQ = QueryParser.escape(oneQ);

                Query analyzedDesc = new QueryParser(luceneVersion, ANALYZED_DESCRIPTION.getName(), analyzer)
                        .parse(escapeQ);
                analyzedDesc.setBoost(f * 1 * len);
                query.add(analyzedDesc, Occur.SHOULD);
                index++;
            }
            topDocs = indexSearcher.search(query, MAX_TOP, sortByDownloadRank);
        }
        return topDocs;
    }

    private void fiterSafeToQuery(BooleanQuery query, Boolean bNoAds, Boolean bOfficial) {
        if (bNoAds != null && bNoAds.booleanValue()) {
            NumericRangeQuery<Integer> noAdsQuery = NumericRangeQuery.newIntRange("noAds", 1, 1, true, true);
            query.add(noAdsQuery, Occur.MUST);
        }
        if (bOfficial != null && bOfficial.booleanValue()) {
            NumericRangeQuery<Integer> officialQuery = NumericRangeQuery.newIntRange("official", 1, 1, true, true);
            query.add(officialQuery, Occur.MUST);
        }
    }
}
