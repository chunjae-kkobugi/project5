package com.team45.service;

import com.team45.entity.Keyword;
import com.team45.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KeywordService {
    List<Keyword> keywordsByUid(String uid, Page page);

    int keywordCountByUid(String uid, Page page);

    void keywordInsert(Keyword keyword);

    void keywordUpdate(Keyword keyword);

    void keywordDelete(Long kno);
}
