package com.team45.service;

import com.team45.entity.Recomment;
import com.team45.mapper.RecommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommentService {

    @Autowired
    private RecommentMapper recommentMapper;

    public List<Recomment> recommentList(){
     return recommentMapper.recommentList();
    }

    public void recommentAdd(Recomment recomment){
        recommentMapper.recommentAdd(recomment);

    };
    public void recommentDel(int no){
        recommentMapper.recommentDel(no);
    };
}
