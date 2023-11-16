package com.team45.ctrl;

import com.team45.entity.Member;
import com.team45.entity.Product;
import com.team45.service.MemberService;
import com.team45.service.ProductService;
import com.team45.util.Page;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/member/**")
public class MemberCtrl {

    BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpSession session;

    @GetMapping("list")
    public String memberList(HttpServletRequest request, Model model){
        // 임시 주석 처리
//        Page page = Page.pageStart(request, model);
//        List<Member> memberList = memberService.memberList(page);
//
//        int total = memberList.size();
//        Page.pageEnd(request, model, page, total);
//
//        model.addAttribute("memberList", memberList);
        return "member/memberList";
    }

    @PostMapping("list")
    public String memberListPost(HttpServletRequest request, Model model){
        // 임시 주석 처리
//        Page page = Page.pageStart(request, model);
//        List<Member> memberList = memberService.memberList(page);
//
//        int total = memberList.size();
//        Page.pageEnd(request, model, page, total);
//
//        model.addAttribute("memberList", memberList);
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
        public String loginPro(String id, String pw, Model model) {
        int pass = memberService.loginPro(id, pw);
            if (pass == 1) {
                session.setAttribute("sid", id);
                model.addAttribute("msg", "로그인 되었습니다.");
                model.addAttribute("url", "/");
                return "/member/alert";
            } else if (pass == 2) {
                model.addAttribute("msg", "해당 계정은 휴면계정입니다. 휴면을 풀어주세요.");
                model.addAttribute("url", "/member/active");
                return "/member/alert";
            } else if (pass==3){
                model.addAttribute("msg", "해당 계정은 탈퇴한 계정입니다.");
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

    @PostMapping("joinPro")
    public String joinPro(Member member, Model model) {
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

    @GetMapping("myPage")
    public String myPage(@RequestParam String id, Model model){
        Member mem = memberService.memberGet(id);
        model.addAttribute("member", mem);
        return "member/myPage";
    }

    @GetMapping("remove")
    public String remove(@RequestParam String id, Model model){
        session.invalidate();
        memberService.memberOutside(id);
        model.addAttribute("msg", "회원 탈퇴가 정상적으로 이루어졌습니다. 감사합니다.");
        model.addAttribute("url", "/");
        return "/member/alert";
    }

    @GetMapping("active")
    public String active(){
        return "/member/active";
    }


    @PostMapping("active")
    public String activePro(String email, String id, String pw,Model model) {
        int pass = memberService.loginPro(id, pw);
        Member mem = memberService.memberGet(id);

       if(mem.getEmail().equals(email)) {
           if (pass == 1) {
               model.addAttribute("msg", "해당 아이디는 휴면 계정이 아닙니다.");
               model.addAttribute("url", "/member/active");
               return "/member/alert";
           } else if (pass == 2) {
               memberService.memberactive(id);
               model.addAttribute("msg", "휴면이 해제되었습니다. 환영합니다.");
               model.addAttribute("url", "/member/login");
               return "/member/alert";
           } else if (pass == 3) {
               model.addAttribute("msg", "해당 계정은 탈퇴한 계정입니다.");
               model.addAttribute("url", "/member/active");
               return "/member/alert";
           } else {
               model.addAttribute("msg", "회원 정보가 맞지 않습니다.");
               model.addAttribute("url", "/member/active");
               return "/member/alert";
           }
       }else {
           model.addAttribute("msg", "회원 정보가 맞지 않습니다.");
           model.addAttribute("url", "/member/active");
           return "/member/alert";
       }
    }

    @GetMapping("myshop")
    public String myShop() {
        return "redirect:myshop/products";
    }

    @GetMapping("myshop/products")
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
        System.out.println(page.getPageNow());

        model.addAttribute("type", searchType);
        model.addAttribute("keyword", searchKeyword);
        //model.addAttribute("page", pageNow);
        // 추가해야됨
        page.setPostTotal(productService.productCountBySeller(sid, page));
        page.makePage();
        model.addAttribute("page", page);

        List<Product> products = productService.productListBySeller(sid, page);
        model.addAttribute("products", products);
        //System.out.println("내 상품 : " + products);
        return "/member/myProducts";
    }

    @GetMapping("myshop/wish")
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
