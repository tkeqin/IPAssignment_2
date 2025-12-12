package com.secj3303.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secj3303.dao.MemberDao;
import com.secj3303.model.Member;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final MemberDao memberDao;

    @Autowired
    public AuthController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // 1. Create a new Person object
        Member member = new Member(); 
        // 2. Set the default role for registration
        member.setRole("member"); 
        
        // 3. Add the Member object to the model so Thymeleaf can bind the fields
        model.addAttribute("member", member);
        
        return "auth/register"; // Maps to auth/register.html
    }

    @GetMapping("/register-test")
    public String showTestRegistrationForm(Model model) {
        Member member = new Member(); 
        member.setRole("member"); 
        model.addAttribute("member", member);
        return "auth/register-test"; // Maps to auth/register-test.html for testing
    }

    // Inside your MemberController or RegistrationController

    @PostMapping("/register/save")
    @Transactional
    public String registerPerson(@Valid @ModelAttribute("member") Member member, 
                                BindingResult bindingResult, 
                                Model model) {

        // 1. Check for validation errors
        if (bindingResult.hasErrors()) {
            // If errors, return to the form to display them
            return "auth/register";
        }

        // 2. Set the role explicitly (always ensure new registrations are members)
        member.setRole("member");

        // 3. Save the person to the database using the DAO
        // Since we're inserting, we use the insert method
        memberDao.insert(member); 

        // 4. Redirect to the login page or a success page
        return "redirect:/auth/login"; 
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new Member());
        return "auth/login"; // Maps to auth/login.html
    }

    @PostMapping("/login")
    @Transactional(readOnly = true)
    public String login(@ModelAttribute("member") Member member, Model model) {
        // Find member by username
        Member existingMember = memberDao.findByUsername(member.getUsername());
        
        // Check if member exists and password matches
        if (existingMember != null && existingMember.getPassword().equals(member.getPassword())) {
            // Login successful - redirect based on role
            if ("admin".equals(existingMember.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/member/home";
            }
        } else {
            // Login failed
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
    }

}
