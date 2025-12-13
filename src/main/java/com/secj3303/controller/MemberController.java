package com.secj3303.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secj3303.dao.BmiRecordDao;
import com.secj3303.dao.PersonDao;
import com.secj3303.model.BmiRecord;
import com.secj3303.model.Person;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private BmiRecordDao bmiRecordDao;
    
    @Autowired
    private PersonDao personDao; // Needed to fetch the full Person object

    // Helper method for manual role check
    private boolean checkRole(HttpSession session, String expectedRole) {
        return expectedRole.equals(session.getAttribute("role"));
    }

    @GetMapping("/dashboard")
    public String memberDashboard(HttpSession session, Model model) {
        if (!checkRole(session, "member")) {
            return "redirect:auth/login"; // Redirect if not a member
        }

        // Get basic user info from session for personalization
        String fullName = (String) session.getAttribute("fullName");
        
        // Optional: Fetch the latest BMI record to display on the dashboard summary
        int memberId = (int) session.getAttribute("personId");
        BmiRecord latestBmi = bmiRecordDao.findLatestByMemberId(memberId);
        
        model.addAttribute("fullName", fullName);
        model.addAttribute("latestBmi", latestBmi);
        
        // Return the dashboard view
        return "member/member-dashboard"; // Maps to member-dashboard.html
    }
    
    // --- BMI Functionality ---

    @GetMapping("/bmi")
    public String showBmiForm(HttpSession session, Model model) {
        if (!checkRole(session, "member")) { return "redirect:auth/login"; }

        int memberId = (int) session.getAttribute("personId");
        
        // Load latest BMI for display
        BmiRecord latestBmi = bmiRecordDao.findLatestByMemberId(memberId);
        
        model.addAttribute("bmiRecord", new BmiRecord()); // For the form input
        model.addAttribute("latestBmi", latestBmi); // To show the current status
        
        return "member/bmiForm";
    }

    @PostMapping("/bmi/calculate")
    public String calculateAndSaveBmi(@ModelAttribute("bmiRecord") BmiRecord newRecord, HttpSession session) {
        if (!checkRole(session, "member")) { return "redirect:auth/login"; }
        
        int memberId = (int) session.getAttribute("personId");
        Person member = personDao.findById(memberId); // Get the associated Person object

        // 1. BMI Calculation
        double heightInMeters = newRecord.getHeightCm() / 100.0;
        double bmiValue = newRecord.getWeightKg() / (heightInMeters * heightInMeters);
        
        // Round the BMI value
        bmiValue = Math.round(bmiValue * 100.0) / 100.0;
        
        // 2. Set necessary fields before saving
        newRecord.setBmiValue(bmiValue);
        newRecord.setRecordDate(LocalDateTime.now());
        newRecord.setMember(member); // Set the foreign key reference
        
        // 3. Save to Database using Hibernate
        bmiRecordDao.save(newRecord);

        return "redirect:/member/bmi"; // Redirect back to show updated result
    }

    @GetMapping("/bmi/history")
    public String showBmiHistory(HttpSession session, Model model) {
        if (!checkRole(session, "member")) { return "redirect:auth/login"; }
        
        int memberId = (int) session.getAttribute("personId");
        
        List<BmiRecord> history = bmiRecordDao.findByMemberId(memberId);
        model.addAttribute("bmiHistory", history);
        
        return "member/member-bmi-history";
    }
}