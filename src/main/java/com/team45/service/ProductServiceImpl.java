package com.team45.service;

import com.team45.entity.Product;
import com.team45.mapper.ProductMapper;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public List<Product> productList(Page page) {
        return productMapper.productList(page);
    }

    @Override
    public List<Product> productListBySeller(String seller) {
        return productMapper.productListBySeller(seller);
    }

    @Override
    public Product productGet(Long pno) {
        return productMapper.productGet(pno);
    }

    @Override
    public int productInsert(Product product) {
        return productMapper.productInsert(product);
    }

    @Override
    public int productUpdate(Product product) {
        return productMapper.productUpdate(product);
    }

    @Override
    public int productStatusUpdate(Long pno, String status) {
        return productMapper.productStatusUpdate(pno, status);
    }

    @Override
    public int productDelete(Long pno) {
        return productMapper.productDelete(pno);
    }
}
