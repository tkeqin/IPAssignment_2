package com.secj3303.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secj3303.dao.PersonDao;
import com.secj3303.model.Person;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PersonDao personDao;

    /**
     * Displays the login form.
     */
    @GetMapping({"/", "/login"})
    public String showLoginForm(Model model, HttpSession session) {
        // Redirect if already logged in
        if (session.getAttribute("personId") != null) {
            String role = (String) session.getAttribute("role");
            return "redirect:/" + role + "/dashboard";
        }
        
        model.addAttribute("member", new Person()); // Aligns with th:object="${member}"
        return "auth/login"; // Resolves to /WEB-INF/views/auth/login.html
    }
    
    /**
     * Handles the login form submission.
     */
    @PostMapping("/login")
    public String authenticate(@ModelAttribute("member") Person loginAttempt, HttpSession session, Model model) {
        
        Person person = personDao.findByUsernameAndPassword(
                loginAttempt.getUsername(), loginAttempt.getPassword()
        );

        if (person != null) {
            // 1. Successful Login: Set Session Attributes (manual security)
            session.setAttribute("personId", person.getPersonId());
            session.setAttribute("role", person.getRole()); // Role stored in session
            session.setAttribute("fullName", person.getFullName());
            
            // 2. Redirect to Dashboard based on Role
            return "redirect:/" + person.getRole() + "/dashboard";

        } else {
            // 3. Failed Login
            model.addAttribute("error", "Invalid username or password.");
            model.addAttribute("member", loginAttempt);
            return "auth/login";
        }
    }

    /**
     * Handles user registration (specific for Member, as per scope).
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Person());
        return "auth/register"; // Resolves to /WEB-INF/views/auth/register.html
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("member") Person newMember, Model model) {
        // Manually assign the role for registration
        newMember.setRole("member");
        
        // **IMPORTANT: Hashing passwords is best practice, but assignment allows simple save.**
        personDao.save(newMember);
        
        model.addAttribute("success", "Registration successful. Please log in.");
        return "redirect:/auth/login"; 
    }
    
    /**
     * Handles logout.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear all session data
        return "redirect:/auth/login";
    }
}