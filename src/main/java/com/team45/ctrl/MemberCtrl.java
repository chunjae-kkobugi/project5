package com.team45.ctrl;

import com.team45.entity.Member;
import com.team45.service.MemberService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member/**")
public class MemberCtrl {
    @Autowired
    private MemberService memberService;
    @Autowired
    private HttpSession session;

    @GetMapping("list")
    public String memberList(HttpServletRequest request, Model model){
        Page page = Page.pageStart(request, model);
        List<Member> memberList = memberService.memberList(page);

        int total = memberList.size();
        Page.pageEnd(request, model, page, total);

        model.addAttribute("memberList", memberList);
        return "member/memberList";
    }

    @PostMapping("list")
    public String memberListPost(HttpServletRequest request, Model model){
        Page page = Page.pageStart(request, model);
        List<Member> memberList = memberService.memberList(page);

        int total = memberList.size();
        Page.pageEnd(request, model, page, total);

        model.addAttribute("memberList", memberList);
        return "member/memberList";
    }
}
