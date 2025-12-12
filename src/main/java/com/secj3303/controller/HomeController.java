package com.secj3303.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    // @RequestMapping("/bmi")
    // // @ResponseBody()
    // public String home(Model model) {
    //     // create sample person object
    //     Person person = new Person("Ahmad", 2001, 65, 1.70);
    //     // share with view (index.jsp)
    //     model.addAttribute("person", person);
    //     return "person-info";  // JSP: /WEB-INF/views/person-info.jsp
    // }

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
    

    //public String bmi(@ModelAttribute Person p, Model model){
    //     double bmi = p.getWeight() / (p.getHeight() * p.getHeight());
    //     model.addAttribute("name", p.getName());
    //     model.addAttribute("yob", p.getYob());
    //     model.addAttribute("weight", p.getWeight());
    //     model.addAttribute("height", p.getHeight());
    //     model.addAttribute("bmi", String.format("%.2f", bmi));
    //     return "person-info";
    // }

    //public ModelAndView bmi(@ModelAttribute Person p, Model model){
    //     ModelAndView mv = new ModelAndView();
    //     double bmi = p.getWeight() / (p.getHeight() * p.getHeight());
    //     mv.addObject("name", p.getName());
    //     mv.addObject("yob", p.getYob());
    //     mv.addObject("weight", p.getWeight());
    //     mv.addObject("height", p.getHeight());
    //     mv.addObject("bmi", String.format("%.2f", bmi));
    //     mv.setViewName("person-info");
    //     return mv;
    // }
    
}

