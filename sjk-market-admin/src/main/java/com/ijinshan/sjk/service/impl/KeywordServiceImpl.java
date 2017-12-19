package com.ijinshan.sjk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ijinshan.sjk.dao.KeywordDao;
import com.ijinshan.sjk.po.Keyword;
import com.ijinshan.sjk.service.KeywordService;

@Service
public class KeywordServiceImpl implements KeywordService {
    private static final Logger logger = LoggerFactory.getLogger(KeywordServiceImpl.class);

    @Resource(name = "keywordDaoImpl")
    private KeywordDao keywordDao;

    @Override
    public List<Keyword> search(int page, int rows, String q, String sort, String order) {
        Assert.isTrue(page > 0, "page invalid!");
        Assert.isTrue(rows > 0, "rows invalid!");
        return keywordDao.search(page, rows, q, sort, order);
    }

    @Override
    public long countForSearching(String q) {
        return keywordDao.countForSearching(q);
    }

    @Override
    public void saveOrUpdate(Keyword keyword) {
        keywordDao.saveOrUpdate(keyword);
    }

    @Override
    public boolean delete(List<Integer> ids) {
        Assert.isTrue(ids != null && !ids.isEmpty(), "Invalid ids");
        return keywordDao.deleteByIds(ids) == ids.size();
    }

    @Override
    public Keyword get(int id) {
        return keywordDao.get(id);
    }

}
