package com.team45.service;

import com.team45.entity.Recomment;
import com.team45.mapper.RecommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommentServiceImpl implements RecommentService{

    @Autowired
    private RecommentMapper recommentMapper;

    @Override
    public List<Recomment> recommentList(String mem_id) {
        return recommentMapper.recommentList(mem_id);
    }

    @Override
    public void recommentAdd(Recomment recomment) {
        recommentMapper.recommentAdd(recomment);
    }

    @Override
    public void recommentDel(int no) {
        recommentMapper.recommentDel(no);
    }
}
