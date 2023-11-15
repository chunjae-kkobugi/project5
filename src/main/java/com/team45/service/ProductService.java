package com.team45.service;

import com.team45.entity.Product;
import com.team45.util.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductService {
    public List<Product> productList(Page page);
    public List<Product> productListBySeller(String seller, Page page);
    public int productCountBySeller(String seller, Page page);
    public Product productGet(Long pno);
    public int productInsert(Product product);
    public int productUpdate(Product product);
    public int productStatusUpdate(Long pno, String status);
    public int productDelete(Long pno);
}
