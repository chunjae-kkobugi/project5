package com.team45.mapper;

import com.team45.entity.Wish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WishMapper {
    @Select("SELECT * FROM wish WHERE pno=#{pno} AND uid=#{uid}")
    public List<Wish> wishList();



}
