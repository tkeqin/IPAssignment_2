package com.secj3303.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.secj3303.dao.ProgramDao;
import com.secj3303.dao.PersonDao;
import com.secj3303.dao.BmiRecordDao;
import com.secj3303.dao.CategoryDao;
import com.secj3303.model.Program;
import com.secj3303.model.Person;
import com.secj3303.model.Category;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProgramDao programDao;
    
    @Autowired
    private PersonDao personDao;
    
    @Autowired
    private BmiRecordDao bmiRecordDao;
    
    @Autowired
    private CategoryDao categoryDao;

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
        
        model.addAttribute("adminName", fullName);

        return "admin/admin-dashboard"; 
    }

    // ============== PROGRAMS MANAGEMENT ==============
    
    @GetMapping("/programs")
    public String listPrograms(HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        List<Program> programs = programDao.findAll();
        model.addAttribute("programs", programs);
        
        return "admin/programs-list";
    }

    @GetMapping("/programs/new")
    public String showAddProgram(HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        model.addAttribute("program", new Program());
        return "admin/program-form";
    }

    @PostMapping("/programs/save")
    public String saveProgram(@ModelAttribute Program program, HttpSession session) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        programDao.save(program);
        return "redirect:/admin/programs";
    }

    @GetMapping("/programs/edit")
    public String editProgram(@RequestParam Integer id, HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        Program program = programDao.findById(id);
        model.addAttribute("program", program);
        return "admin/program-form";
    }

    @GetMapping("/programs/delete")
    public String deleteProgram(@RequestParam Integer id, HttpSession session) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        programDao.delete(id);
        return "redirect:/admin/programs";
    }

    // ============== CATEGORIES MANAGEMENT ==============
    
    @GetMapping("/categories")
    public String listCategories(HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        List<Category> categories = categoryDao.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories-list";
    }

    @GetMapping("/categories/new")
    public String showAddCategory(HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @GetMapping("/categories/edit")
    public String editCategory(@RequestParam Integer id, HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        Category category = categoryDao.findById(id);
        model.addAttribute("category", category);
        return "admin/category-form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category, HttpSession session) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        categoryDao.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete")
    public String deleteCategory(@RequestParam Integer id, HttpSession session) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        categoryDao.delete(id);
        return "redirect:/admin/categories";
    }

    // ============== REPORTS & SYSTEM SUMMARY ==============
    
    @GetMapping("/reports")
    public String showReports(HttpSession session, Model model) {
        if (!checkRole(session, "admin")) {
            return "auth/login";
        }
        
        // Calculate system statistics
        List<Person> allUsers = personDao.findAll();
        List<Person> members = personDao.findByRole("member");
        List<Person> trainers = personDao.findByRole("trainer");
        List<Program> programs = programDao.findAll();
        
        long totalBmiRecords = 0; // TODO: Implement count method in BmiRecordDao
        
        model.addAttribute("totalUsers", allUsers != null ? allUsers.size() : 0);
        model.addAttribute("totalMembers", members != null ? members.size() : 0);
        model.addAttribute("totalTrainers", trainers != null ? trainers.size() : 0);
        model.addAttribute("totalPrograms", programs != null ? programs.size() : 0);
        model.addAttribute("totalBmiRecords", totalBmiRecords);
        
        return "admin/reports";
    }
}