package com.secj3303.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // You will need this DAO for Admin-specific functionality
    // @Autowired
 
    
    // Helper method for manual role check
    private boolean checkRole(HttpSession session, String expectedRole) {
        String currentRole = (String) session.getAttribute("role");
        return expectedRole.equals(currentRole);
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login"; // Redirect if not an Admin
        }

        String fullName = (String) session.getAttribute("fullName");
        
        model.addAttribute("fullName", fullName);

        return "admin/admin-dashboard"; 
    }


    
}