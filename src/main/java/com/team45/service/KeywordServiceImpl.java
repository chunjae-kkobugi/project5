package com.team45.service;

import com.team45.entity.Keyword;
import com.team45.mapper.KeywordMapper;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordMapper keywordMapper;

    @Override
    public List<Keyword> keywordsByUid(String uid, Page page) {
        return keywordMapper.keywordsByUid(uid, page);
    }

    @Override
    public int keywordCountByUid(String uid, Page page) {
        return keywordMapper.keywordCountByUid(uid, page);
    }

    @Override
    public void keywordInsert(Keyword keyword) {
        keywordMapper.keywordInsert(keyword);
    }

    @Override
    public void keywordUpdate(Keyword keyword) {
        keywordMapper.keywordUpdate(keyword);
    }

    @Override
    public void keywordDelete(Long kno) {
        keywordMapper.keywordDelete(kno);
    }
}
