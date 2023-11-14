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
}
