package com.team45.service;

import com.team45.entity.Product;
import com.team45.util.Page;

import java.util.List;

public interface ProductService {
    public List<Product> productList(Page page);
    public Product productGet(Long pno);
    public int productInsert(Product product);
    public int productUpdate(Product product);
    public int productStatusUpdate(Long pno, String status);
    public int productDelete(Long pno);
}
