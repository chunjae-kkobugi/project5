package com.team45.mapper;

import com.team45.entity.Product;
import com.team45.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    // 리스트의 페이지 처리
    @Select({"<script>","SELECT * FROM product WHERE",
            "<if test='searchType != null and searchType != \"\"'> ${searchType} LIKE CONCAT('%', #{searchKeyword}, '%') AND</if>",
            "status!='REMOVE'"+
                    "ORDER BY createAt ASC LIMIT #{postStart}, #{postCount}","</script>"})
    public List<Product> productList(Page page);
    @Select("SELECT * FROM product WHERE pno=#{pno}")
    public Product productGet(Long pno);

    @Insert("INSERT INTO product (pname, content, category, seller, price, proaddr, image) VALUES (#{pname}, #{content}, #{category}, #{seller}, #{price}, #{proaddr}, #{image})")
    public int productInsert(Product product);
    @Update("UPDATE product SET pname=#{pname}, content=#{content}, category=#{category}, seller=#{seller}, price=#{price}, proaddr=#{proaddr}, image=#{image} WHERE pno=#{pno}")
    public int productUpdate(Product product);
    @Update("UPDATE product SET status=#{status} WHERE pno=#{pno}")
    public int productStatusUpdate(Long pno, String status);
    @Delete("DELETE FROM product WHERE pno=#{pno}")
    public int productDelete(Long pno);
}
