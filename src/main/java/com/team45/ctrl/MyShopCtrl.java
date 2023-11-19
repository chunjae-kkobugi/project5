package com.team45.ctrl;

import com.team45.entity.Member;
import com.team45.entity.ProductVO;
import com.team45.service.MemberService;
import com.team45.service.ProductService;
import com.team45.service.WishService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/myshop/**")
public class MyShopCtrl {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WishService wishService;
    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String myShop() {
        return "redirect:myshop/products";
    }

    @GetMapping("products")
    public String myProducts(HttpServletRequest request, Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);

        // 페이징 처리
        Page page = new Page();
        String searchType = request.getParameter("type");
        String searchKeyword = request.getParameter("keyword");
        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        page.setSearchType(searchType);
        page.setSearchKeyword(searchKeyword);
        page.setPageNow(pageNow);
        //System.out.println(page.getPageNow());

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);
        //model.addAttribute("page", pageNow);
        // 추가해야됨
        page.setPostTotal(productService.productCountBySeller(sid, page));
        page.makePage();
        model.addAttribute("page", page);

        List<ProductVO> products = productService.productListBySeller(sid, page);
        //System.out.println("total:"+page.getPostTotal());
        model.addAttribute("products", products);
        return "/myshop/myProducts";
    }

    @GetMapping("wish")
    public String myWish(HttpServletRequest request, Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);

        // 페이징 처리
        Page page = new Page();
        String searchType = request.getParameter("type");
        String searchKeyword = request.getParameter("keyword");
        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        page.setSearchType(searchType);
        page.setSearchKeyword(searchKeyword);
        page.setPageNow(pageNow);
        //System.out.println(page.getPageNow());

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);
        page.setPostTotal(wishService.wishProductCount(sid, page));
        page.makePage();
        model.addAttribute("page", page);

        List<ProductVO> wishProductList = wishService.wishProductList(sid, page);
        //System.out.println("찜 상품 : " + wishProductList);
        //System.out.println("total:" + page.getPostTotal());

        model.addAttribute("wishProductList", wishProductList);
        return "/myshop/myWish";
    }

    @GetMapping("keyword")
    public String myKeywords(Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);
        //List<Keyword> keywords =
        return "/myshop/myKeywords";
    }

}
