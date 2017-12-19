package com.ijinshan.sjk.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.BaseTest;

public class SuggestionsServiceTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SuggestionsServiceTest.class);

    @Resource(name = "searchSuggestionServiceImpl")
    private QuickTipsService searchSuggestionService;

    @Test
    public void testspellChecker() throws IOException {
        // String[] suggestions = searchSuggestionService.checkSpell("五二");
        // for (String s : suggestions) {
        // logger.debug(s);
        // }
        //
        // suggestions = searchSuggestionService.checkSpell("男十人");
        // for (String s : suggestions) {
        // logger.debug(s);
        // }
        //
        // suggestions = searchSuggestionService.checkSpell("Rilakkima");
        // for (String s : suggestions) {
        // logger.debug(s);
        // }
    }
}
