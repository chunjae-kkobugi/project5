package com.team45.service;


import com.team45.entity.Recomment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


public interface RecommentService {
    public List<Recomment> recommentList(String mem_id);
    Recomment recommentOne(int no);
    public void recommentAdd(Recomment recomment);
    public void recommentDel(int no);


}
