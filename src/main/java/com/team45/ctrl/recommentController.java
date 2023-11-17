package com.team45.ctrl;


import com.team45.entity.Recomment;
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

    @GetMapping("/List")
    public String recommentList(Model model) {
        List<Recomment> recommentList = recommentService.recommentList();
        model.addAttribute("recommentList", recommentList);
        return "/recomment/recommentList";
    }

    @GetMapping("/Add")
    public String recommentform(){
        return "/recomment/recommentForm";
    };

    @PostMapping("/Add")
    public String recommentAdd(Recomment recomment){
        recommentService.recommentAdd(recomment);
        return "redirect:/recomment/List";
    };

    @GetMapping("/Del")
    public String recommentDel(int no){
        recommentService.recommentDel(no);
        return "redirect:/recomment/List";
    };

}
