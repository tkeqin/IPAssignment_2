package com.secj3303.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.ProgramDao;
import com.secj3303.model.Program;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProgramDao programDao;
 
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
    
    private static final String ADMIN_ROLE = "admin"; 

    // --- Example 1: READ (List All Programs) ---
    @GetMapping("/programs")
    public String listPrograms(HttpSession session, Model model) {
        // Use the helper method for checking
        if (!checkRole(session, ADMIN_ROLE)) {
            return "redirect:/login"; // Redirect if not admin
        }
        
        List<Program> programs = programDao.findAll();
        model.addAttribute("programs", programs);
        return "admin/program-list"; 
    }
    
    // --- Example 2: CREATE / UPDATE (Show Form) ---
    @GetMapping("/programForm")
    public String showFormForAdd(HttpSession session, @RequestParam(required = false) Integer id, Model model) {
        // Use the helper method for checking
        if (!checkRole(session, ADMIN_ROLE)) {
            return "redirect:/login"; // Redirect if not admin
        }
        
        // ... rest of the logic ...
        Program program;
        if (id != null) {
            program = programDao.findById(id);
            if (program == null) {
                // If id provided but not found, redirect to list to avoid null render errors
                return "redirect:/admin/programs";
            }
        } else {
            program = new Program();
        }

        model.addAttribute("program", program);
        return "admin/program-form"; 
    }

    @PostMapping("/saveProgram")
    public String saveProgram(HttpSession session, @ModelAttribute("program") Program program) {
        if (!checkRole(session, ADMIN_ROLE)) {
            return "redirect:/login"; // Redirect if not admin
        }
        
        // programDao.save() handles both INSERT (new program) and UPDATE (existing program)
        programDao.save(program); 
        
        // Redirect back to the program list
        return "redirect:/admin/programs";
    }
    
    // --- Example 3: DELETE ---
    @GetMapping("/deleteProgram")
    public String deleteProgram(HttpSession session, @RequestParam("programId") Integer id) {
        // Use the helper method for checking
        if (!checkRole(session, ADMIN_ROLE)) {
            return "redirect:/login"; // Redirect if not admin
        }
        
        programDao.delete(id); 
        return "redirect:/admin/programs";
    }


    
}