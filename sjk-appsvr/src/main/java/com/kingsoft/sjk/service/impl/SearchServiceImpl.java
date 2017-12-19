package com.kingsoft.sjk.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kingsoft.sjk.config.AppConfig;
import com.kingsoft.sjk.po.AndroidApp;
import com.kingsoft.sjk.po.App;
import com.kingsoft.sjk.service.AppsService;
import com.kingsoft.sjk.service.SearchService;
import com.kingsoft.sjk.util.DATrieTree;
import com.kingsoft.sjk.util.FileOPHelper;
import com.kingsoft.sjk.util.GameTrieTree;
import com.kingsoft.sjk.util.SoftTrieTree;

/**
 * @author wuzhenyu
 * @since 2012-07-25
 */
@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "appsServiceImpl")
    private AppsService appsService;

    private DATrieTree gameTrieTree = GameTrieTree.getInstance();
    private DATrieTree softTrieTree = SoftTrieTree.getInstance();

    private Searcher gameSearcher;
    private Searcher softSearcher;

    public SearchServiceImpl() {
    }

    /**
     * 搜索
     * 
     * @param keyword
     * @return
     */
    @Override
    public List<App> search(Integer typeId, String keyword) {
        try {
            List<Document> docs = this.searchIndex(typeId, keyword);
            docs = docs.size() > appConfig.getSearchResNum() ? docs.subList(0, appConfig.getSearchResNum()) : docs;
            List<Integer> ids = new ArrayList<Integer>();
            for (Document doc : docs)
                ids.add(Integer.parseInt(doc.get("id")));
            return ids.size() > 0 ? appsService.findByIds(ids) : null;
        } catch (Exception e) {
            logger.error("Exception", e);

            return null;
        }
    }

    /**
     * 关键字提示
     * 
     * @param keyword
     * @return
     */
    @Override
    public List<String> getSpellSuggestion(Integer typeId, String keyword) {
        if (typeId == appConfig.getGameTypeId())
            return gameTrieTree.FindAllWords(keyword);
        else
            return softTrieTree.FindAllWords(keyword);
    }

    /**
     * 拼写错误的提示修正
     * 
     * @param keyword
     * @return
     */
    @Override
    public List<String> spellChecker(Integer typeId, String sword) {
        try {
            List<String> strList = new ArrayList<String>();
            SpellChecker sp = new SpellChecker(FSDirectory.getDirectory(typeId == appConfig.getGameTypeId() ? appConfig
                    .getGameSpellIndexDir() : appConfig.getSoftSpellIndexDir()), new JaroWinklerDistance());
            String[] suggestions = sp.suggestSimilar(sword, appConfig.getSuggestNum());
            for (int i = 0; i < suggestions.length; i++)
                strList.add(suggestions[i]);
            return strList;
        } catch (IOException e) {
            logger.error("Exception", e);

            return null;
        }
    }

    /**
     * 初始化索引
     * 
     * @param typeId
     * @param subTypeId
     * @throws Exception
     */
    @Override
    public void initAppIndex(Integer typeId, Integer subTypeId) throws Exception {

        List<AndroidApp> applist = appsService.list(typeId, subTypeId);
        List<String> appNameList = new ArrayList<String>();

        // 1 init
        boolean isGame = (typeId == appConfig.getGameTypeId() ? true : false);
        String indexPath = (isGame ? appConfig.getGameIndexDir() : appConfig.getSoftIndexDir());
        FileOPHelper.del(indexPath);
        IndexWriter indexWriter = new IndexWriter((indexPath), new PaodingAnalyzer(), true,
                IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(true);
        indexWriter.setMaxBufferedDocs(500);
        indexWriter.setMergeFactor(1000);
        indexWriter.setMaxMergeDocs(10000);

        // 2 write Index
        if (applist != null && applist.size() > 0) {
            for (AndroidApp tapp : applist) {
                Document doc = new Document();
                Field nameField = new Field("name", tapp.getName(), Field.Store.YES, Field.Index.ANALYZED);
                nameField.setBoost(2);
                Field idField = new Field("id", String.valueOf(tapp.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED);
                Field descriptionField = new Field("description", tapp.getDescription(), Field.Store.YES,
                        Field.Index.ANALYZED);
                doc.add(nameField);
                doc.add(descriptionField);
                doc.add(idField);
                appNameList.add(tapp.getName());
                indexWriter.addDocument(doc);
                logger.trace("#initAppIndex name:{}", tapp.getName());
            }
        }
        indexWriter.close();

        // 3.update TrieTree & dic
        FileOPHelper.del(isGame ? appConfig.getGameSuggestDict() : appConfig.getSoftSuggestDict());
        if (isGame) {
            for (String appname : appNameList) {
                gameTrieTree.insert(appname);
                FileOPHelper.write(appConfig.getGameSuggestDict(), appname);
            }
        } else {
            for (String appname : appNameList) {
                softTrieTree.insert(appname);
                FileOPHelper.write(appConfig.getSoftSuggestDict(), appname);
            }
        }

        // 4.init initSpellIndex
        initSpellIndex(isGame);
    }

    /**
     * 初始化TrieTree
     * 
     * @throws Exception
     */
    @Override
    public void initTrieDict(Integer typeId) throws Exception {
        if (typeId == appConfig.getGameTypeId())
            gameTrieTree.initTrieDict(appConfig.getGameSuggestDict());
        else
            softTrieTree.initTrieDict(appConfig.getSoftSuggestDict());
    }

    /**
     * 初始化SpellIndex
     * 
     * @throws IOException
     */
    public void initSpellIndex(boolean isGame) throws IOException {
        FileOPHelper.del(isGame ? appConfig.getGameSpellIndexDir() : appConfig.getSoftSpellIndexDir());
        Directory spellIndexDir = FSDirectory.getDirectory(new File(isGame ? appConfig.getGameSpellIndexDir()
                : appConfig.getSoftSpellIndexDir()));
        SpellChecker spellChecker = new SpellChecker(spellIndexDir);
        spellChecker.indexDictionary(new PlainTextDictionary(new File(isGame ? appConfig.getGameSuggestDict()
                : appConfig.getSoftSuggestDict())));
        spellIndexDir.close();
    }

    /**
     * 查询索引
     * 
     * @param keywords
     * @return
     * @throws Exception
     */
    public List<Document> searchIndex(Integer typeId, String keywords) throws Exception {
        // 1.init searcher
        Analyzer analyzer = new PaodingAnalyzer();
        IndexReader reader = IndexReader.open(typeId == appConfig.getGameTypeId() ? appConfig.getGameIndexDir()
                : appConfig.getSoftIndexDir());
        BooleanClause.Occur[] flags = new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD,
                BooleanClause.Occur.SHOULD };
        Query query = MultiFieldQueryParser.parse(keywords, appConfig.getQueryFields(), flags, analyzer);
        query = query.rewrite(reader);

        // 2.search
        List<Document> docs = new ArrayList<Document>();
        Hits hits = (typeId == appConfig.getGameTypeId() ? gameSearcher.search(query, Sort.RELEVANCE) : softSearcher
                .search(query, Sort.RELEVANCE));// searcher.search(query,
                                                // Sort.RELEVANCE);
        for (int i = 0; i < hits.length(); i++) {
            docs.add(hits.doc(i));
        }

        // 3.return
        reader.close();
        return docs;
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            FileUtils.forceMkdir(new File(appConfig.getSoftIndexDir()));
            FileUtils.forceMkdir(new File(appConfig.getGameIndexDir()));
            if (appConfig.isInitIndex()) {
                this.initAppIndex(appConfig.getGameTypeId(), null);
                logger.info("Init game index done!");
                this.initAppIndex(appConfig.getSoftTypeId(), null);
                logger.info("Init soft index done!");
            }

            this.initTrieDict(appConfig.getGameTypeId());
            logger.info("Init game tips done!");

            this.initTrieDict(appConfig.getSoftTypeId());
            logger.info("Init soft tips done!");

            this.softSearcher = new IndexSearcher(appConfig.getSoftIndexDir());
            this.gameSearcher = new IndexSearcher(appConfig.getGameIndexDir());
            logger.info("Paoding db init done!");
        } catch (Exception e) {
            logger.error("Fatal error!", e);
        } finally {

        }
    }

    @PreDestroy
    public final synchronized void destroy() {
        try {
            if (gameSearcher != null) {
                gameSearcher.close();
            }
            if (softSearcher != null) {
                softSearcher.close();
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    @Override
    public boolean updateIndex(Integer typeId) throws Exception {
        // final boolean isGame = (typeId == appConfig.getGameTypeId() ? true :
        // false);
        // final String indexPath = (isGame ? appConfig.getGameIndexDir() :
        // appConfig.getSoftIndexDir());
        // FileOPHelper.del(indexPath);
        // IndexWriter indexWriter = new IndexWriter((indexPath), new
        // PaodingAnalyzer(), true,
        // IndexWriter.MaxFieldLength.UNLIMITED);
        // indexWriter.setUseCompoundFile(true);
        // indexWriter.setMaxBufferedDocs(500);
        // indexWriter.setMergeFactor(1000);
        // indexWriter.setMaxMergeDocs(10000);
        return false;
    }

}