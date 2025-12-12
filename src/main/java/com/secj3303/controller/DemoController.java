package com.secj3303.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
// @RequestMapping("/demo")
public class DemoController {
    
    // lab 7a
    // @GetMapping("/demo/home")
    // public String Home(Model model) {
    //     Person p = new Person("Ahmad", 2001, 65, 1.70);
    //     model.addAttribute("message", "Hello SECJ3303");
    //     model.addAttribute("person", p);
    //     return "home";
    // }

    // lab 7b
    // hndler method to handle '/demo' http get request
    @GetMapping("/demo")
    public String demoroot(Model model) {
        model.addAttribute("message", "Hello SECJ3303 section 1");
        //Person p = new Person("Johnson", 2014, 30, 1.4);
        //model.addAttribute("person", p);
        return "demo-thymeleaf";
    
    }

    // hndler method to handle '/demo-variable-expression' request
    @GetMapping("/demo-variable-expression")
    public String demoVariableExpression() {
        return "demo-variable-expression";
    }


    // hndler method to handle '/demo-selection-expression' request
    @GetMapping("/demo-selection-expression")
    public String demoSelectionExpression(Model model) {
        model.addAttribute("message", "Hello SECJ3303 section 1");
        //Person p = new Person("Larry", 1989, 80, 1.95);
        //model.addAttribute("person", p);
        return "demo-selection-expression";
    }


    // hndler method to handle '/demo-url-expression' request
    @GetMapping("/demo-url-expression")
    public String demoUrlExpression() {
        return "demo-url-expression";
    }


    // hndler method to handle '/demo-fragment-expression' request
    @GetMapping("/demo-fragment-expression")
    public String demoFragmentExpression() {
        return "demo-fragment-expression";
    }


    // hndler method to handle '/demo-form-handling' get request
    @GetMapping("/demo-form-handling")
    public String demoFormHandling() {
        return "demo-form-handling";
    }


    // hndler method to handle '/demo-form-handling' post request
    @PostMapping("/demo-form-handling")
    public String demoFormPostHandling() {
        return "demo-form-post-handling";
    }



}
