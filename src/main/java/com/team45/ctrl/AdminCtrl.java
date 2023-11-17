package com.team45.ctrl;

import com.team45.entity.Member;
import com.team45.service.MemberService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/**")
public class AdminCtrl {
    @Autowired
    private MemberService memberService;

    @GetMapping("home")
    public String adminhome(Model model){
        List<Member> memberList = memberService.memberCreateStats();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<String> createLabel = memberList.stream()
                .map(member -> formatter.format(member.getCreateAt()))
                .collect(Collectors.toList());
        System.out.println(createLabel);
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
        model.addAttribute("page", pageNow);

        page.setPostTotal(memberService.memberCount(page));
        page.makePage();

        model.addAttribute("page", page);

        List<Member> memberList = memberService.memberList(page);
        model.addAttribute("memberList", memberList);

        return "admin/memberList";
    }

    @GetMapping("productList")
    public String productList(HttpServletRequest request, Model model){
        return "admin/productList";
    }

    @GetMapping("noticeList")
    public String noticeList(HttpServletRequest request, Model model){
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
