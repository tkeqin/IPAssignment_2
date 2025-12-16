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
 

    // --- Helper for Security - Manual Role Check ---
    private boolean checkRole(HttpSession session, String expectedRole) {
        if (session == null || expectedRole == null) {
            return false;
        }
        String currentRole = (String) session.getAttribute("role");
        return expectedRole.equals(currentRole);
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        // Manual role check - ensure user is admin
        if (!checkRole(session, "admin")) {
            return "redirect:/auth/login"; // Redirect if not an Admin
        }

        String fullName = (String) session.getAttribute("fullName");
        
        model.addAttribute("adminName", fullName);

        return "admin/admin-dashboard"; 
    }


    
}