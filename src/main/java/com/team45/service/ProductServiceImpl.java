package com.team45.service;

import com.team45.entity.Category;
import com.team45.entity.Product;
import com.team45.entity.ProductVO;
import com.team45.mapper.ProductMapper;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductVO> productList(Page page) {
        return productMapper.productList(page);
    }

    @Override
    public List<ProductVO> saleProductList(Page page) {
        return productMapper.saleProductList(page);
    }

    @Override
    public List<ProductVO> productRegionList(Page page) {
        return productMapper.productRegionList(page);
    }

    @Override
    public int getCount(Page page) {
        return productMapper.getCount(page);
    }

    @Override
    public int getRegionCount(Page page) {
        return productMapper.getRegionCount(page);
    }

    @Override
    public ProductVO productDetail(Long pno) {
        return productMapper.productDetail(pno);
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
    public int productReserved(Long pno) {
        return productMapper.productReserved(pno);
    }

    @Override
    public int productOut(Long pno) {
        return productMapper.productOut(pno);
    }

    @Override
    public int productSale(Long pno) {
        return productMapper.productSale(pno);
    }

    @Override
    public int productRemove(Long pno) {
        return productMapper.productRemove(pno);
    }

    @Override
    public List<Category> categories() {
        return productMapper.categories();
    }

    @Override
    public List<Map<String, Integer>> getCateProCnt() {
        return productMapper.getCateProCnt();
    }
}
