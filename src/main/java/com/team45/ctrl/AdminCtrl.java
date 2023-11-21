package com.team45.ctrl;

import com.team45.entity.Category;
import com.team45.entity.Member;
import com.team45.entity.Notice;
import com.team45.entity.ProductVO;
import com.team45.service.MemberService;
import com.team45.service.NoticeSerivce;
import com.team45.service.ProductService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/**")
public class AdminCtrl {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private NoticeSerivce noticeSerivce;

    @GetMapping("home")
    public String adminhome(Model model){
        List<Member> memberList = memberService.memberCreateStats();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<String> createLabel = memberList.stream()
                .map(member -> formatter.format(member.getCreateAt()))
                .collect(Collectors.toList());

        model.addAttribute("createLabel", createLabel);

        List<Long> createData = memberList.stream().map(Member::getMno).collect(Collectors.toList());
        model.addAttribute("createData", createData);

        return "admin/home";
    }

    @GetMapping("memberList")
    public String memberList(HttpServletRequest request, Model model){
        Page page = new Page();

        String searchType = request.getParameter("type");
        String searchKeyword = request.getParameter("keyword");
        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        page.setSearchType(searchType);
        page.setSearchKeyword(searchKeyword);
        page.setPageNow(pageNow);
        System.out.println(page.getPageNow());

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);

        page.setPostTotal(memberService.memberCount(page));
        page.makePage();

        model.addAttribute("page", page);

        List<Member> memberList = memberService.memberList(page);
        model.addAttribute("memberList", memberList);

        return "admin/member/memberList.html";
    }

    @GetMapping("productList")
    public String productList(HttpServletRequest request, Model model){

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("cate");

        Page page = new Page();
        page.setPageNow(curPage);
        page.setCategory(category);                                        // 카테고리 데이터
        page.setSearchKeyword(request.getParameter("keyword"));     // 검색 키워드
        page.setSearchType(request.getParameter("type"));           // 검색 타입
        page.setProaddr(request.getParameter("proaddr"));
        page.setStatus(request.getParameter("status"));

        // 페이징에 필요한 데이터 저장
        int total = productService.getCount(page);
        page.setPostTotal(total);
        page.makePage();

        List<ProductVO> productList = productService.productList(page);
        List<Category> categories = productService.categories();


        // 로그인한 회원의 주소 정보 불러오기
        model.addAttribute("proaddr", request.getAttribute("addr3"));
        // 상품의 판매상태 불러오기
        String status = request.getParameter("status");

        model.addAttribute("status", status);
        model.addAttribute("productList", productList);
        model.addAttribute("categories", categories);
        model.addAttribute("curPage", curPage);
        model.addAttribute("curCategory", category);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        return "admin/productList";
    }

    @GetMapping("noticeList")
    public String noticeList(HttpServletRequest request, Model model){
        List<Notice> noticeList = noticeSerivce.boardList();
        model.addAttribute("noticeList", noticeList);

        return "admin/noticeList";
    }

    @GetMapping("freeList")
    public String freeList(HttpServletRequest request, Model model){
        return "admin/freeList";
    }

    @GetMapping("faqList")
    public String faqList(HttpServletRequest request, Model model){
        return "admin/faqList";
    }
}
