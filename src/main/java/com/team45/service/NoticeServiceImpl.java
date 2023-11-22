package com.team45.service;

import com.team45.entity.Notice;
import com.team45.mapper.NoticeMapper;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeSerivce {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> boardList() {
        return noticeMapper.boardList();
    }

    @Override
    public Notice boardGet(int no) {
        return noticeMapper.boardGet(no);
    }

    @Override
    public void boardAdd(Notice notice) {
        noticeMapper.boardAdd(notice);
    }

    @Override
    public void boardEdit(Notice notice) {
        noticeMapper.boardEdit(notice);
    }

    @Override
    public void boardDel(int no) {
        noticeMapper.boardDel(no);
    }

    @Override
    public List<Notice> boardPage(Page page) {
        return noticeMapper.boardPage(page);
    }

    @Override
    public int noticeCount(Page page) {
        return noticeMapper.noticeCount(page);
    }
}
