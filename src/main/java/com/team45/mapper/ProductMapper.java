package com.team45.mapper;

import com.team45.entity.Category;
import com.team45.entity.Product;
import com.team45.entity.ProductVO;
import com.team45.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    List<ProductVO> productList(Page page);
    int getCount(Page page);
    ProductVO productDetail(Long pno);
    int productInsert(Product product);
    int productUpdate(Product product);
    int productReserved(Long pno);
    int productOut(Long pno);
    int productSale(Long pno);
    int productRemove(Long pno);
    List<Category> categories();
    List<Map<String, Integer>> getCateProCnt();

}
