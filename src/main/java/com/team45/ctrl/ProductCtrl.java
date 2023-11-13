package com.team45.ctrl;

import com.team45.entity.Product;
import com.team45.service.ProductService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/product/**")
public class ProductCtrl {
    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public String productList(HttpServletRequest request, Model model){
        Page page = Page.pageStart(request, model);
        List<Product> productList = productService.productList(page);

        int total = productList.size();
        Page.pageEnd(request, model, page, total);

        model.addAttribute("productList", productList);
        return "product/productList";
    }

    @PostMapping("list")
    public String productListPost(HttpServletRequest request, Model model){
        Page page = Page.pageStart(request, model);
        List<Product> productList = productService.productList(page);

        int total = productList.size();
        Page.pageEnd(request, model, page, total);

        model.addAttribute("productList", productList);
        return "product/productList";
    }
}
