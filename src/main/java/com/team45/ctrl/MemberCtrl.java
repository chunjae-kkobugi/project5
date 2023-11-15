package com.team45.ctrl;

import com.team45.entity.Member;
import com.team45.entity.Product;
import com.team45.service.MemberService;
import com.team45.service.ProductService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member/**")
public class MemberCtrl {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
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

    @GetMapping("login")
    public String login(){
        return "member/login";
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, Model model){
        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        boolean keepId = Boolean.parseBoolean(request.getParameter("keepId"));

        return "index";
    }


    @PostMapping("loginpro")
        public String loginPro(@RequestParam String id, @RequestParam String pw, Model model) {
            boolean pass = memberService.loginPro(id, pw);
            if (pass) {
                session.setAttribute("sid", id);
                model.addAttribute("msg", "로그인 되었습니다.");
                model.addAttribute("url", "/");
                return "/member/alert";
            } else {
                model.addAttribute("msg", "로그인 정보가 맞지 않습니다.");
                model.addAttribute("url", "/member/login");
                return "/member/alert";
            }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        session.invalidate();
        model.addAttribute("msg", "로그아웃 되었습니다.");
        model.addAttribute("url", "/");
        return "/member/alert";
    }

    @GetMapping("joinTerm")
    public String joinTerm(){
        return "member/joinTerm";
    }

    @GetMapping("join")
    public String join(){
        return "member/join";
    }

    @PostMapping("joinpro")
    public String joinpro(Member member, Model model) {
        memberService.memberInsert(member);
        model.addAttribute("msg", "가족이 되신걸 환영합니다.");
        model.addAttribute("url", "/member/login");
        return "/member/alert";
          }

    @PostMapping("idCheckPro")
    public ResponseEntity idCheck(@RequestBody Member member) throws Exception {
        String id = member.getId();
        boolean result = memberService.idCheck(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("mypage")
    public String myPage() {
        return "redirect:mypage/products";
    }

    @GetMapping("mypage/products")
    public String myProducts(Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);
        List<Product> products = productService.productListBySeller(sid);
        //System.out.println("내 상품 : " + products);
        model.addAttribute("products", products);

        return "/member/myProducts";
    }

    @GetMapping("mypage/wish")
    public String myWish(Model model) {
        String sid = (String) session.getAttribute("sid");
        Member member = memberService.memberGet(sid);
        model.addAttribute("member", member);
        //List<Product> products = productService.productListBySeller(sid);
        //System.out.println("내 상품 : " + products);
        //model.addAttribute("products", products);
        //List<Product> wishlist = productService.
        return "/member/myWish";
    }

}
