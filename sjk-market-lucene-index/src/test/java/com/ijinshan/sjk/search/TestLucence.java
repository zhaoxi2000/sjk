package com.ijinshan.sjk.search;

import static com.ijinshan.sjk.vo.index.LuceneFieldCollection.ColumnName.NAME;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ijinshan.sjk.vo.index.LuceneFieldCollection;
import com.ijinshan.util.IOUtils;

public class TestLucence {

    private static final Logger logger = LoggerFactory.getLogger(TestLucence.class);

    private static final String indexDir = "/data/appsdata/sjk-market-lucene-index/all/search";

    public static void main(String[] args) {

        Directory directory = null;
        Directory ram = null;
        IndexReader indexReader = null;
        IndexWriter indexWriter = null;
        IndexSearcher indexSearcher = null;
        try {

            directory = FSDirectory.open(new File(indexDir));
            if (!IndexReader.indexExists(directory)) {
                logger.error("Please reset index firstly!");
                return;
            }

            Analyzer anly = new org.wltea.analyzer.lucene.IKAnalyzer(true);
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, anly);
            iwc.setRAMBufferSizeMB(IndexWriterConfig.DEFAULT_RAM_BUFFER_SIZE_MB * 3);
            iwc.setOpenMode(OpenMode.APPEND);
            indexWriter = new IndexWriter(directory, iwc);
            indexWriter.deleteUnusedFiles();
            indexWriter.forceMergeDeletes();
            indexWriter.commit();
            indexWriter.close(true);

            indexReader = IndexReader.open(directory);
            int num = indexReader.numDocs();
            logger.info("total num:{}", num);
            Document doc = null;
            List<String> list = new ArrayList<String>();
            Set<String> set = new HashSet<String>();
            List<String> duplicate = new ArrayList<String>();
            for (int i = 0; i < num; i++) {
                doc = indexReader.document(i);
                // logger.info("id:{},name:{}",doc.get("id"),doc.get("name"));
                list.add(doc.get("id"));
                String appid = doc.get("id");
                if (set.contains(appid)) {
                    duplicate.add(appid);
                } else {

                }
                set.add(doc.get("id"));
            }
            logger.info("set size:{},list size:{}", set.size(), list.size());
            logger.info("duplicate num:{}", list.size() - set.size());
            logger.info("{}", duplicate);

            indexSearcher = new IndexSearcher(indexReader);
            for (String appid : duplicate) {
                int id = Integer.parseInt(appid);
                NumericRangeQuery<Integer> idQuery = NumericRangeQuery.newIntRange(
                        LuceneFieldCollection.ColumnName.ID.getName(), id, id, true, true);
                TopDocs topDocs = indexSearcher.search(idQuery, Integer.MAX_VALUE);
                // Assert.isTrue(topDocs.totalHits > 1);
                logger.info(" appid : {}   totalHits: {} ", appid, topDocs.totalHits);
                String name = null;
                for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                    int docID = scoreDoc.doc;
                    Document document = indexSearcher.doc(docID);
                    name = document.get("name");
                    logger.info("docId: {}  appId: {} name: {}", docID, document.get("id"), name);
                }
                Query namePrefixQuery = new TermQuery(new Term(NAME.getName(), name));
                TopDocs topDocs1 = indexSearcher.search(namePrefixQuery, Integer.MAX_VALUE);
                logger.info(" search by Name , totalHits: {} ", topDocs1.totalHits);
                for (ScoreDoc scoreDoc : topDocs1.scoreDocs) {
                    int docID = scoreDoc.doc;
                    Document document = indexSearcher.doc(docID);
                    logger.info("docId: {}  appId: {} name: {}", docID, document.get("id"), name);
                }
                logger.info("\n\n");
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
        } finally {
            IOUtils.closeQuietly(indexSearcher, indexReader, ram, directory);
        }

    }
}
