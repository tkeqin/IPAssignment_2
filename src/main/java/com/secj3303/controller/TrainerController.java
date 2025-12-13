package com.secj3303.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    // You will need this DAO for Trainer-specific functionality
    // @Autowired
    // private FitnessPlanDao fitnessPlanDao; 

    // Helper method for manual role check
    private boolean checkRole(HttpSession session, String expectedRole) {
        String currentRole = (String) session.getAttribute("role");
        return expectedRole.equals(currentRole);
    }
    
    /**
     * Trainer Dashboard landing page.
     */
    @GetMapping("/dashboard")
    public String trainerDashboard(HttpSession session, Model model) {
        if (!checkRole(session, "trainer")) {
            return "auth/login"; // Redirect if not a Trainer
        }

        String fullName = (String) session.getAttribute("fullName");
        
        // Optional: Fetch quick summary data for the dashboard (e.g., plans created, members assigned)
        // Example: List<FitnessPlan> recentPlans = fitnessPlanDao.findRecentPlans((int) session.getAttribute("personId"));
        
        model.addAttribute("trainerName", fullName);
        // model.addAttribute("recentPlans", recentPlans);
        
        // Return the dashboard view
        return "trainer/trainer-dashboard"; // Maps to trainer-dashboard.html
    }

    // TODO: Implement Controller methods for Create/Assign Plan, and Monitor Progress
}