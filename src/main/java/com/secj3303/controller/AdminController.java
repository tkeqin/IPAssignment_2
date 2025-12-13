/*package com.secj3303.controller;

import com.secj3303.dao.CategoryDao;
import com.secj3303.dao.ProgramDao;
import com.secj3303.model.Program;
import com.secj3303.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProgramDao programDao; // DAO for Program CRUD
    
    @Autowired
    private CategoryDao categoryDao; // DAO for populating category dropdowns

    // Helper method for manual role check
    private boolean checkRole(HttpSession session, String expectedRole) {
        // Check if the role attribute exists and matches "admin"
        String currentRole = (String) session.getAttribute("role");
        return expectedRole.equals(currentRole);
    }

    
     //Admin Dashboard
    
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        if (!checkRole(session, "admin")) {
            return "redirect:/login"; // Redirect if not an Admin
        }
        // If role is correct, return the dashboard view
        return "admin-dashboard"; // Maps to admin-dashboard.html
    }

    // =============================================================
    // PROGRAM MANAGEMENT (CRUD)
    // =============================================================
    
   // Displays a list of all programs (Read All)
    
    @GetMapping("/programs")
    public String listPrograms(HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "redirect:/login"; 
        }

        List<Program> programs = programDao.findAll();
        model.addAttribute("programList", programs);
        
        return "admin-program-list"; // Maps to admin-program-list.html
    }

    //Displays the form for adding a new program (Create/Update Form)
     
    @GetMapping("/programs/form")
    public String showProgramForm(HttpSession session, Model model, 
                                  @RequestParam(required = false) Integer programId) {
        if (!checkRole(session, "admin")) {
            return "redirect:/login";
        }
        
        Program program;
        if (programId != null) {
            // Edit existing program
            program = programDao.findById(programId);
        } else {
            // New program
            program = new Program();
        }

        List<Category> categories = categoryDao.findAll(); // Used for dropdown
        
        model.addAttribute("program", program);
        model.addAttribute("categories", categories);
        
        return "admin-program-form"; // Maps to admin-program-form.html
    }
    
    //Handles the submission of the program form (Create/Update logic)
    
    @PostMapping("/programs/save")
    public String saveProgram(HttpSession session, @ModelAttribute("program") Program program) {
        if (!checkRole(session, "admin")) {
            return "redirect:/login";
        }
        
        // The DAO handles both save (new) and update (existing)
        programDao.save(program);

        // Redirect to the list view
        return "redirect:/admin/programs";
    }

    //Deletes a program (Delete)
     
    @GetMapping("/programs/delete")
    public String deleteProgram(HttpSession session, @RequestParam("programId") int programId) {
        if (!checkRole(session, "admin")) {
            return "redirect:/login";
        }

        // ProgramDao should have a delete method that takes ID or Entity
        programDao.deleteById(programId); 

        return "redirect:/admin/programs";
    }

    // =============================================================
    // CATEGORY MANAGEMENT (Similar CRUD structure required here)
    // =============================================================
    
    // TODO: Implement GET mapping for /admin/categories
    // TODO: Implement GET mapping for /admin/categories/form
    // TODO: Implement POST mapping for /admin/categories/save
    // TODO: Implement GET mapping for /admin/categories/delete
    
} */