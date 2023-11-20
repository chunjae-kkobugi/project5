package com.team45.mapper;

import com.team45.entity.ProductVO;
import com.team45.entity.Wish;
import com.team45.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WishMapper {
    @Select("SELECT * FROM wish WHERE uid=#{uid}")
    List<Wish> wishList(String uid);

    @Select({
            "<script>",
            "SELECT p.pno, pname, seller, price, proaddr, image, createAt, baseAt, p.status, visited, cateName",
            "FROM productWithCate p",
            "JOIN wish w ON p.pno = w.pno",
            "WHERE w.uid = #{uid}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY wno DESC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    List<ProductVO> wishProductList(@Param("uid") String uid, @Param("page") Page page);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM productWithCate p",
            "JOIN wish w ON p.pno = w.pno",
            "WHERE w.uid = #{uid}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY wno DESC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    int wishProductCount(@Param("uid") String uid, @Param("page") Page page);


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
