package com.ijinshan.sjk.ntxservice.impl;

import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.DOWNOLOAD_RANK;
import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.NAME;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.ntxservice.QuickTipsService;
import com.ijinshan.sjk.util.TopDocsUtil;
import com.ijinshan.util.IOUtils;

@Service
public class QuickTipsServiceImpl implements QuickTipsService {
    private static final Logger logger = LoggerFactory.getLogger(QuickTipsServiceImpl.class);

    public final Sort sort = new Sort(new SortField("downloadRank", SortField.INT, true));

    public final int MIN_DOWNLOAD_RANK = 2000;
    public final int MAX_TOP = 1000;
    
    @Resource(name = "appConfig")
    private AppConfig appConfig;

    private IndexReader indexReader = null;
    private IndexSearcher indexSearcher = null;
    
    @PostConstruct
    public void reset() {
        final String dirPath = appConfig.getAllQuickTipsIndexDir();
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
                logger.info("QuickTips' new indexReader!");
                final IndexReader preIndexReader = this.indexReader;
                final IndexSearcher preIndexSearcher = this.indexSearcher;
                reopenIndexSearcher(ram);
                IOUtils.closeQuietly(preIndexSearcher, preIndexReader);
                logger.info("Reload QuickTips' index!");
            }
            logger.info("QuickTips' IndexReader has numDos: {}", this.indexReader.numDocs());
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    private void reopenIndexSearcher(Directory ram) throws CorruptIndexException, IOException {
        this.indexReader = IndexReader.open(ram);
        this.indexSearcher = new IndexSearcher(this.indexReader);
    }

    @PreDestroy
    public void destory() {
        IOUtils.closeQuietly(indexSearcher, indexReader);
    }

    @Override
    public ScoreDoc[] prefixSearch(String q) throws IOException {
        if (StringUtils.isEmpty(q) || q.length() > appConfig.getKeywordMaxLength()) {
            logger.error("empty keywords or over-length! {}", q);
            return null;
        }
        
        final TopDocs[] rstTopDocs = new TopDocs[2];
        final Query nameFldQuery = new PrefixQuery(new Term(NAME.getName(), q));
        rstTopDocs[0] = indexSearcher.search(nameFldQuery, appConfig.getQuickTipsNum() * 2, sort);

        final Query downLoadRankQuery = NumericRangeQuery.newIntRange(DOWNOLOAD_RANK.getName(), MIN_DOWNLOAD_RANK, Integer.MAX_VALUE, true, false);
        //从下载量最高的1000条记录中，再过滤符合关键字的记录
        rstTopDocs[1] = indexSearcher.search(downLoadRankQuery, MAX_TOP, sort);
        TopDocs rst = TopDocsUtil.mergeDuplicateDocId(TopDocs.merge(sort, MAX_TOP + appConfig.getQuickTipsNum() * 2, rstTopDocs));
        if(rst != null) {
            return rst.scoreDocs;
        }
        return null;
    }

    @Override
    public String[] quickTips(String q) throws IOException {
        if (q == null || q.isEmpty()) {
            return null;
        }
        q = q.trim().toLowerCase();
        ScoreDoc[] docs = this.prefixSearch(q);
        if (docs != null) {
            Set<String> tipsSet = new LinkedHashSet<String>();
            for (ScoreDoc hit : docs) {
                int docID = hit.doc;
                Document doc = indexSearcher.doc(docID);
                String name = doc.get(NAME.getName());
                // 去重的提示.
                if (name != null && !name.isEmpty() && name.contains(q)) {
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
}
