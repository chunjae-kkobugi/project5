package com.team45.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
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
    @Autowired
    private ObjectMapper mapper;


    @Override
    public List<ProductVO> productList(Page page) {
        List<ProductVO> productList = new ArrayList<>();
        for (ProductVO p : productMapper.productList(page)) {
            p.setFileDataList(fileDataMapper.fileDataBoardList("product", p.getPno()));
            productList.add(p);
        }

        return productList;
    }

    @Override
    public int getCount(Page page) {
        return productMapper.getCount(page);
    }


    @Override
    public ProductVO productDetail(Long pno) {
        List<FileData> fileDataList = fileDataMapper.fileDataBoardList("product", pno);
        ProductVO product = productMapper.productDetail(pno);
        product.setFileDataList(fileDataList);
        return product;
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
        int returnNo = productMapper.productInsert(product);
        int productNo = productMapper.productGetLast();

        for (FileData f : product.getFileDataList()) {
            f.setTableName("product");
            f.setColumnNo((long) productNo);
            fileDataMapper.fileDataInsert(f);
        }
        ProductVO pvo = productMapper.productDetail((long)productNo);
        FileData thumb = fileDataMapper.fileDataGetLast();
        pvo.setImage(thumb.getFileNo());

        Product p = mapper.convertValue(pvo, Product.class);
        System.out.println(p);

        productMapper.productUpdate(p);

        return returnNo;
    }

    @Override
    @Transactional
    public int productUpdate(Product product) {
        Long pno = product.getPno();
        int returnNo = productMapper.productUpdate(product);

        for (FileData f : product.getFileDataList()) {
            f.setTableName("product");
            f.setColumnNo(pno);
            fileDataMapper.fileDataInsert(f);
        }
        ProductVO pvo = productMapper.productDetail(pno);
        FileData thumb = fileDataMapper.fileDataGetLast();
        pvo.setImage(thumb.getFileNo());

        Product p = mapper.convertValue(pvo, Product.class);
        System.out.println(p);

        productMapper.productUpdate(p);

        return returnNo;

    }

    @Override
    public int fileDataDelete(Long fileNo) {
        return fileDataMapper.fileDataDelete(fileNo);
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
