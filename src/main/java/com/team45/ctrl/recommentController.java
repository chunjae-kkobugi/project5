package com.team45.ctrl;


import com.team45.entity.Member;
import com.team45.entity.Recomment;
import com.team45.service.MemberService;
import com.team45.service.RecommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recomment/*")
public class recommentController {

    @Autowired
    private RecommentService recommentService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/List")
    public String recommentList(Model model, String id) {

        List<Recomment> recommentList = recommentService.recommentList(id);
        model.addAttribute("recommentList", recommentList);
        return "/recomment/recommentList";
    }

    @GetMapping("/Add")
    public String recommentform(Model model, String id){
        Member mem = memberService.memberGet(id);
        model.addAttribute("mem", mem);
        return "/recomment/recommentForm";
    };


    @PostMapping("/Add")
    public String recommentAdd(Recomment recomment){
        recommentService.recommentAdd(recomment);
        String id = recomment.getMem_id();
        return "redirect:/recomment/List?id="+id;
    };

    @GetMapping("/Del")
    public String recommentDel(int no, String id){
        String sid = memberService.memberGet(id).getId();
        recommentService.recommentDel(no);
        return "redirect:/recomment/List?id="+sid;
    };

}
