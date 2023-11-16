package com.team45.service;

import com.team45.entity.Category;
import com.team45.entity.Product;
import com.team45.entity.ProductVO;
import com.team45.util.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public List<ProductVO> productList(Page page);
    public List<ProductVO> saleProductList(Page page);
    public List<ProductVO> productRegionList(Page page);
    public int getCount(Page page);
    public int getRegionCount(Page page);
    public ProductVO productDetail(Long pno);
    public int productInsert(Product product);
    public int productUpdate(Product product);
    public int productReserved(Long pno);
    public int productOut(Long pno);
    public int productSale(Long pno);
    public int productRemove(Long pno);
    public List<Category> categories();
    public List<Map<String, Integer>> getCateProCnt();
}
