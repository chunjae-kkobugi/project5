package com.team45.service;

import com.team45.entity.Category;
import com.team45.entity.FileData;
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
    public int getCount(Page page);
    public ProductVO productDetail(Long pno);
    public List<Product> productListBySeller(String seller, Page page);
    public int productCountBySeller(String seller, Page page);
    public int productInsert(Product product);
    public void productUpdate(Product product);
    public void productReserved(Long pno);
    public void productOut(Long pno);
    public void productSale(Long pno);
    public void productRemove(Long pno);
    public List<Category> categories();
    public List<Map<String, Integer>> getCateProCnt();
}
