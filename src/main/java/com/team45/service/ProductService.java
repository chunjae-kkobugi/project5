package com.team45.service;

import com.team45.entity.Product;
import com.team45.util.Page;
<<<<<<< HEAD
=======
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
>>>>>>> f2731de520cb394afc6ef1e51faaf87536f3b0c5

import java.util.List;

public interface ProductService {
    public List<Product> productList(Page page);
    public Product productGet(Long pno);
    public int productInsert(Product product);
    public int productUpdate(Product product);
    public int productStatusUpdate(Long pno, String status);
    public int productDelete(Long pno);
}
