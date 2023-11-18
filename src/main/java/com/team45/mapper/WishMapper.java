package com.team45.mapper;

import com.team45.entity.Wish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WishMapper {
    @Select("SELECT * FROM wish WHERE uid=#{uid}")
    List<Wish> wishList(String uid);

    @Select("SELECT count(*) FROM wish WHERE pno=#{pno} AND uid=#{uid}")
    int wishFind(@Param("pno") Long pno, @Param("uid") String uid);

    @Select("SELECT heart FROM product WHERE pno=#{pno}")
    int wishCount(Long pno);

    @Insert("INSERT INTO wish VALUES (default, #{pno}, #{uid}, 1)")
    int wishInsert(Wish wish);

    @Delete("DELETE FROM wish WHERE pno=#{pno} AND uid=#{uid}")
    int wishDelete(Wish wish);

    @Update("UPDATE product set heart=heart+1 where pno=#{pno}")
    void increaseWish(Long pno);

    @Update("UPDATE product set heart=heart-1 where pno=#{pno}")
    void decreaseWish(Long pno);
}
