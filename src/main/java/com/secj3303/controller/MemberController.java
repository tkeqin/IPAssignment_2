package com.secj3303.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.MemberDao;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberDao memberDao;

    /**
     * Dependency Injection (DI) via Constructor:
     * Spring automatically injects the bean implementing the PersonDao interface
     * (which is PersonDaoJdbc, provided in the DAO package).
     */
    @Autowired
    public MemberController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @GetMapping("/bmi")
    public String showBmiForm() {
        return "bmiForm";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
    
    @PostMapping("/bmi")
    public String bmi(@RequestParam String name, @RequestParam int yob, @RequestParam double weight, @RequestParam double height, Model model){
        double bmi = weight / (height * height);
        model.addAttribute("name", name);
        model.addAttribute("yob", yob);
        model.addAttribute("weight", weight);
        model.addAttribute("height", height);
        model.addAttribute("bmi", String.format("%.2f", bmi));
        return "person-info";
    }
}