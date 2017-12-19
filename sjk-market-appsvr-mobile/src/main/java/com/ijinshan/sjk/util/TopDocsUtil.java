package com.ijinshan.sjk.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class TopDocsUtil {

    /**
     * 对排好序的TopDocs，进行ScoreDoc去重操作
     * 
     * @param srcTopDocs
     * @return
     * @throws IOException
     */
    public static TopDocs mergeDuplicateDocId(TopDocs srcTopDocs) throws IOException {
        if (srcTopDocs == null) {
            return null;
        }
        final ScoreDoc[] scoreDocs = srcTopDocs.scoreDocs;
        int totalHits = srcTopDocs.totalHits;
        List<ScoreDoc> scoreDocList = new ArrayList<ScoreDoc>();
        ScoreDoc preScoreDoc = null;
        int scoreDocSize = 0;
        for (int i = 0; i < scoreDocs.length; i++) {
            if (i > 0) {
                preScoreDoc = scoreDocList.get(scoreDocSize - 1);
                if (preScoreDoc.doc == scoreDocs[i].doc) {
                    totalHits--;
                    continue;
                }
            }
            scoreDocList.add(scoreDocs[i]);
            scoreDocSize++;
        }
        final ScoreDoc[] hits = new ScoreDoc[scoreDocSize];
        scoreDocList.toArray(hits);
        return new TopDocs(totalHits, hits, srcTopDocs.getMaxScore());
    }

}
