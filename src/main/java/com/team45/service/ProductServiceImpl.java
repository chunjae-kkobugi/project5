package com.team45.service;

import com.team45.entity.Category;
import com.team45.entity.FileData;
import com.team45.entity.Product;
import com.team45.entity.ProductVO;
import com.team45.mapper.FileDataMapper;
import com.team45.mapper.MyShopMapper;
import com.team45.mapper.ProductMapper;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private FileDataMapper fileDataMapper;
    @Autowired
    private MyShopMapper myShopMapper;


    @Override
    public List<ProductVO> productList(Page page) {
        return productMapper.productList(page);
    }

    @Override
    public int getCount(Page page) {
        return productMapper.getCount(page);
    }


    @Override
    public ProductVO productDetail(Long pno) {
        return productMapper.productDetail(pno);
    }
    @Override
    public List<ProductVO> productListBySeller(String seller, Page page) {
        return myShopMapper.productListBySeller(seller, page);
    }

    @Override
    public int productCountBySeller(String seller, Page page) {
        return myShopMapper.productCountBySeller(seller, page);
    }


    @Override
    @Transactional
    public int productInsert(Product product) {
        int productNo = productMapper.productGetLast();

        fileDataMapper.fileDataUpdate(productNo);

        for (FileData f : product.getFileDataList()) {
            f.setTableName("product");
            f.setColumnNo((long) productNo);
            fileDataMapper.fileDataInsert(f);
        }
        return productMapper.productInsert(product);
    }

    @Override
    public void productUpdate(Product product) {
        productMapper.productUpdate(product);
    }

    @Override
    public void productReserved(Long pno) {
        productMapper.productReserved(pno);
    }

    @Override
    public void productOut(Long pno) {
        productMapper.productOut(pno);
    }

    @Override
    public void productSale(Long pno) {
        productMapper.productSale(pno);
    }

    @Override
    public void productRemove(Long pno) {
        productMapper.productRemove(pno);
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
