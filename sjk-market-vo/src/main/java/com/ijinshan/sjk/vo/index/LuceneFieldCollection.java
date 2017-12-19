package com.ijinshan.sjk.vo.index;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

public final class LuceneFieldCollection {

    public static enum ColumnName {
        ID("id"), // id in Database
        NAME("name"), //
        ANALYZED_NAME("analyzedName"), //
        ANALYZED_DESCRIPTION("analyzedDesc"), //
        ANALYZED_KEYWORDS("anaylzedKeywords"), //
        TAGS_NAME("tagsName"), //

        CATALOG("catalog"), //
        CATALOG_NAME("catalogName"), //
        TAG_NAME("tagName"), //
        DOWNOLOAD_RANK("downloadRank"), //

        NOADS("noAds"), //
        OFFICIAL("official"), //
        MARKET_RANK("marketRank"), //
        ;
        private String name = null;

        ColumnName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }

    }

    final NumericField idFld = new NumericField("id", Field.Store.YES, true);

    final Field nameFld = new Field(ColumnName.NAME.getName(), "", Field.Store.YES, Field.Index.NOT_ANALYZED);
    final Field analyzedNameFld = new Field(ColumnName.ANALYZED_NAME.getName(), "", Field.Store.NO,
            Field.Index.ANALYZED);
    final Field analyzedDescFld = new Field(ColumnName.ANALYZED_DESCRIPTION.getName(), "", Field.Store.NO,
            Field.Index.ANALYZED);
    final Field analyzedKeywordsFld = new Field(ColumnName.ANALYZED_KEYWORDS.getName(), "", Field.Store.NO,
            Field.Index.NOT_ANALYZED);
    final Field tagsFld = new Field(ColumnName.TAGS_NAME.getName(), "", Field.Store.NO, Field.Index.NOT_ANALYZED);

    final Field catalogFld = new Field(ColumnName.CATALOG_NAME.getName(), "", Field.Store.NO, Field.Index.NOT_ANALYZED);
    final NumericField catalogNumFld = new NumericField(ColumnName.CATALOG.getName(), Field.Store.NO, true);

    final NumericField marketRankFld = new NumericField(ColumnName.MARKET_RANK.getName(), Field.Store.NO, true);
    final NumericField downloadRankFld = new NumericField(ColumnName.DOWNOLOAD_RANK.getName(), Field.Store.YES, true);
    final NumericField noAdsNumFld = new NumericField(ColumnName.NOADS.getName(), Field.Store.NO, true);
    final NumericField officialNumFld = new NumericField(ColumnName.OFFICIAL.getName(), Field.Store.NO, true);

    public LuceneFieldCollection() {
        this.nameFld.setBoost(10);
        this.analyzedNameFld.setBoost(5);
        this.analyzedDescFld.setBoost(1);
        this.analyzedKeywordsFld.setBoost(4);
        this.tagsFld.setBoost(9);
        this.catalogFld.setBoost(9.5f);
    }

    public NumericField getIdFld() {
        return idFld;
    }

    public Field getNameFld() {
        return nameFld;
    }

    public Field getAnalyzedNameFld() {
        return analyzedNameFld;
    }

    public Field getAnalyzedDescFld() {
        return analyzedDescFld;
    }

    public Field getAnalyzedKeywordsFld() {
        return analyzedKeywordsFld;
    }

    public Field getTagsFld() {
        return tagsFld;
    }

    public Field getCatalogFld() {
        return catalogFld;
    }

    public NumericField getCatalogNumFld() {
        return catalogNumFld;
    }

    public NumericField getDownloadRankFld() {
        return downloadRankFld;
    }

    public NumericField getNoAdsNumFld() {
        return noAdsNumFld;
    }

    public NumericField getOfficialNumFld() {
        return officialNumFld;
    }

    public NumericField getMarketRankFld() {
        return marketRankFld;
    }

    public final Field newTagNameField() {
        return new Field(ColumnName.TAG_NAME.getName(), "", Field.Store.NO, Field.Index.NOT_ANALYZED);
    }

}
