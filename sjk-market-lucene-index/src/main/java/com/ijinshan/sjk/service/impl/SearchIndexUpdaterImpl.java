package com.ijinshan.sjk.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
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

import com.ijinshan.sjk.config.AppConfig;
import com.ijinshan.sjk.config.EnumMarket;
import com.ijinshan.sjk.mapper.AppAndTagMapper;
import com.ijinshan.sjk.mapper.TagMapper;
import com.ijinshan.sjk.po.App;
import com.ijinshan.sjk.po.Catalog;
import com.ijinshan.sjk.po.Market;
import com.ijinshan.sjk.po.Tag;
import com.ijinshan.sjk.service.AbastractIndexUpdater;
import com.ijinshan.sjk.service.DataObserable;
import com.ijinshan.sjk.txservice.CatalogService;
import com.ijinshan.sjk.txservice.MarketService;
import com.ijinshan.sjk.vo.index.LuceneFieldCollection;
import com.ijinshan.sjk.vo.index.Tags4App;

@Service
public class SearchIndexUpdaterImpl extends AbastractIndexUpdater {
    private static final Logger logger = LoggerFactory.getLogger(SearchIndexUpdaterImpl.class);

    /**
     * 按约定将连续或是中文空格将全部换成单个英文空格.
     */
    public final String chSpacesRegex = "( |　)+";
    public final String space = " ";

    @Resource(name = "appConfig")
    private AppConfig appConfig;

    @Resource(name = "dataObserableImpl")
    private DataObserable dataObserable;

    @Resource(name = "catalogServiceImpl")
    private CatalogService catalogService;

    @Resource(name = "appAndTagMapper")
    private AppAndTagMapper appAndTagMapper;
    @Resource(name = "tagMapper")
    private TagMapper tagMapper;

    Map<Integer, Tag> tagsMap = new ConcurrentHashMap<Integer, Tag>(100);

    Map<Integer, List<Integer>> allTags4AppHashMap = new ConcurrentHashMap<Integer, List<Integer>>(100);

    @Resource(name = "marketServiceImpl")
    private MarketService marketService;

    private Map<Integer, Catalog> catalogMap = new ConcurrentHashMap<Integer, Catalog>(57);
    /**
     * key: marketName
     */
    private Map<String, Integer> marketRankMap = new ConcurrentHashMap<String, Integer>(10);

    @PostConstruct
    public synchronized void resetProps() {
        List<Catalog> catalogs = catalogService.list();
        if (catalogs != null) {
            catalogMap.clear();
            for (Catalog catalog : catalogs) {
                catalogMap.put(catalog.getId(), catalog);
            }
        }
        List<Market> markets = marketService.listMarketRank();
        if (markets != null) {
            marketRankMap.clear();
            for (Market m : markets) {
                marketRankMap.put(m.getMarketName(), m.getRank());
            }
        }

        List<Tag> tags = tagMapper.getTags();
        if (tags != null) {
            tagsMap.clear();
            for (Tag tag : tags) {
                tagsMap.put(tag.getId(), tag);
            }
        }

    }

    @Override
    public void beginAll() throws IOException {
        super.beginAll();
        synchronized (lock) {
            try {
                if (indexWriter == null) {
                    final String dir = appConfig.getAllIndexDir();
                    Directory directory = FSDirectory.open(new File(dir));

                    IndexWriterConfig iwc = new IndexWriterConfig(luceneVersion, analyzer);
                    iwc.setRAMBufferSizeMB(IndexWriterConfig.DEFAULT_RAM_BUFFER_SIZE_MB * 3);
                    iwc.setOpenMode(OpenMode.CREATE);

                    indexWriter = new IndexWriter(directory, iwc);
                }
                List<Tags4App> tags4AppList = appAndTagMapper.getTags4App(null);
                if (tags4AppList != null) {
                    allTags4AppHashMap.clear();
                    for (Tags4App tags4App : tags4AppList) {
                        allTags4AppHashMap.put(tags4App.getAppId(), tags4App.getTagIds());
                    }
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
            this.addDocucmentToIndexer(apps);
          //  indexWriter.commit();
        }
    }

    @Override
    public void endAll() {
        super.endAll();
        logger.info("close search indexWriter!");
    }

    @Override
    public void beginIncrement() throws IOException {
        super.beginIncrement();
        synchronized (lock) {
            try {
                if (indexWriter == null) {
                    final String dir = appConfig.getAllIndexDir();
                    Directory directory = FSDirectory.open(new File(dir));
                    Assert.isTrue(IndexReader.indexExists(directory), "Invalid search index!");

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
    public void updateIncrement(List<App> apps) throws IOException, CorruptIndexException {
        if (CollectionUtils.isEmpty(apps)) {
            return;
        }
        logger.info("Before updating .Search's indexWriter has numDos: {}", indexWriter.numDocs());
        Document doc = null;
        final Map<Integer, List<Integer>> tags4AppHashMap = this.getTags4AppHashMapByAppIds(getAppIdsByAppList(apps));
        synchronized (lock) {
            for (App app : apps) {
                int id = app.getId().intValue();
                NumericRangeQuery<Integer> idQuery = NumericRangeQuery.newIntRange(
                        LuceneFieldCollection.ColumnName.ID.getName(), id, id, true, true);
                indexWriter.deleteDocuments(idQuery);
            }
            indexWriter.commit();
            logger.info("After deleting .Search's indexWriter has numDos: {}", indexWriter.numDocs());
        }

        synchronized (lock) {
            for (App app : apps) {
                doc = newDocument(app, tags4AppHashMap);// 构造文档并 添加域
                indexWriter.addDocument(doc);
            }
            indexWriter.commit();
            logger.info("After adding .Search's indexWriter has numDos: {} \n", indexWriter.numDocs());
        }
    }

    @Override
    @PostConstruct
    public void registerToObserable() {
        Assert.notNull(dataObserable);
        dataObserable.registerObservers(this);
    }

    @Override
    public void addDocucmentToIndexer(List<App> apps) {
        Document doc = null;
        synchronized (lock) {
            if (CollectionUtils.isEmpty(apps)) {
                return;
            }
            for (App app : apps) {
                try {
                    doc = newDocument(app, allTags4AppHashMap);
                    indexWriter.addDocument(doc);
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
            }
        }
    }

    private Document newDocument(App app, Map<Integer, List<Integer>> tags4AppHashMap) {
        Document doc = new Document();
        luceneFlds.getIdFld().setIntValue((app.getId().intValue()));
        luceneFlds.getNameFld().setValue(app.getName().replaceAll(chSpacesRegex, " "));
        luceneFlds.getAnalyzedNameFld().setValue(app.getName());
        luceneFlds.getAnalyzedDescFld().setValue(app.getDescription());

        List<Integer> tagIds = tags4AppHashMap.get(Integer.valueOf(app.getId()));

        if (tagIds != null && !tagIds.isEmpty()) {
            for (Integer tagId : tagIds) {
                final Tag tag = tagsMap.get(tagId);
                if (tag != null && tag.getName() != null) {
                    final String tagName = tag.getName();
                    Field field = luceneFlds.newTagNameField();
                    field.setValue(tagName.toLowerCase());
                    doc.add(field);
                }
            }
        }

        final String keywordsVal = app.getKeywords() == null ? "" : app.getKeywords();
        luceneFlds.getAnalyzedKeywordsFld().setValue(keywordsVal);

        Catalog catalog = catalogMap.get(app.getSubCatalog());
        final String catalogValue = catalog == null ? "" : catalog.getName();
        luceneFlds.getCatalogFld().setValue(catalogValue);
        doc.add(luceneFlds.getCatalogFld());

        doc.add(luceneFlds.getIdFld());
        doc.add(luceneFlds.getNameFld());
        doc.add(luceneFlds.getAnalyzedNameFld());
        doc.add(luceneFlds.getAnalyzedDescFld());
        doc.add(luceneFlds.getAnalyzedKeywordsFld());

        luceneFlds.getDownloadRankFld().setIntValue(app.getDownloadRank());
        doc.add(luceneFlds.getDownloadRankFld());

        luceneFlds.getCatalogNumFld().setIntValue(app.getCatalog());
        doc.add(luceneFlds.getCatalogNumFld());

        // advertisement
        String adPopupTypes = app.getAdPopupTypes();
        String adActionTypes = app.getAdActionTypes();
        if (adPopupTypes != null) {
            adPopupTypes = adPopupTypes.trim().replace(",,", ",");
        }
        if (adActionTypes != null) {
            adActionTypes = adActionTypes.trim().replace(",,", ",");
        }
        if ((adPopupTypes == null || adPopupTypes.isEmpty()) && (adActionTypes == null || adActionTypes.isEmpty())) {
            luceneFlds.getNoAdsNumFld().setIntValue(1);
        } else {
            luceneFlds.getNoAdsNumFld().setIntValue(0);
        }
        // official
        if (app.getSignatureSha1() != null && app.getSignatureSha1().equals(app.getOfficialSigSha1())) {
            luceneFlds.getOfficialNumFld().setIntValue(1);
        } else {
            luceneFlds.getOfficialNumFld().setIntValue(0);
        }

        doc.add(luceneFlds.getNoAdsNumFld());
        doc.add(luceneFlds.getOfficialNumFld());

        Integer rank = Integer.MAX_VALUE;
        s: switch (EnumMarket.valueOf(app.getMarketName().toUpperCase())) {
            case SHOUJIKONG_CHANNEL: {
                rank = 1;
                break s;
            }
            case SHOUJIKONG: {
                rank = 1;
                break s;
            }
            case IJINSHAN: {
                rank = 1;
                break s;
            }
            default: {
                break s;
            }
        }
        luceneFlds.getMarketRankFld().setIntValue(rank);
        doc.add(luceneFlds.getMarketRankFld());
        return doc;
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

    private Map<Integer, List<Integer>> getTags4AppHashMapByAppIds(List<Integer> appIds) {
        Map<Integer, List<Integer>> localTags4AppHashMap = new HashMap<Integer, List<Integer>>();
        if (CollectionUtils.isEmpty(appIds)) {
            return localTags4AppHashMap;
        }
        List<Tags4App> tags4AppList = appAndTagMapper.getTags4App(appIds);
        if (tags4AppList != null) {
            for (Tags4App tags4App : tags4AppList) {
                localTags4AppHashMap.put(tags4App.getAppId(), tags4App.getTagIds());
            }
        }
        return localTags4AppHashMap;
    }

    private List<Integer> getAppIdsByAppList(List<App> appList) {
        List<Integer> appIds = new ArrayList<Integer>();
        if (CollectionUtils.isEmpty(appList)) {
            return appIds;
        }
        for (App app : appList) {
            appIds.add(app.getId());
        }
        return appIds;
    }
}
