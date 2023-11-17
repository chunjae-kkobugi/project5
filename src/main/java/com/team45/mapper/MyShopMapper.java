package com.team45.mapper;

import com.team45.entity.ProductVO;
import com.team45.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyShopMapper {
    @Select({
            "<script>",
            "SELECT pno, pname, seller, price, proaddr, image, createAt, baseAt, status, visited, cateName FROM product p JOIN category c ON (p.cate = c.cate)",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY createAt ASC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    List<ProductVO> productListBySeller(@Param("seller") String seller, @Param("page") Page page);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM product WHERE seller = #{seller}",
            "<if test='page.searchType != null and page.searchType != \"\"'>",
            "   AND ${page.searchType} LIKE CONCAT('%', #{page.searchKeyword}, '%')",
            "</if>",
            "ORDER BY createAt ASC LIMIT #{page.postStart}, #{page.postScreen}",
            "</script>"
    })
    int productCountBySeller(@Param("seller") String seller, @Param("page") Page page);
}
