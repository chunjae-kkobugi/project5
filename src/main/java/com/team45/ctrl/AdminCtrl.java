package com.team45.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/**")
public class AdminCtrl {
    @GetMapping("home")
    public String adminhome(){
        return "admin/home";
    }
<<<<<<< HEAD
=======

    @GetMapping("memberList")
    public String memberList(){
        return "admin/memberList";
    }
>>>>>>> f2731de520cb394afc6ef1e51faaf87536f3b0c5
}
