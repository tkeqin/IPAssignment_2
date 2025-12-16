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
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.BmiRecordDao;
import com.secj3303.dao.PersonDao;
import com.secj3303.dao.ProgramDao; 
import com.secj3303.dao.TrainerDao;
import com.secj3303.model.BmiRecord;
import com.secj3303.model.Person;
import com.secj3303.model.PlanAssignment; 
import com.secj3303.model.Program;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private BmiRecordDao bmiRecordDao;
    
    @Autowired
    private PersonDao personDao;

    @Autowired
    private TrainerDao trainerDao; 

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
    public String memberDashboard(HttpSession session, Model model) {
        // Manual role check - ensure user is member
        if (!checkRole(session, "member")) {
            return "redirect:/auth/login"; 
        }

        // Get basic user info from session for personalization
        String fullName = (String) session.getAttribute("fullName");
        
        // Fetch the latest BMI record
        int memberId = (int) session.getAttribute("personId");
        BmiRecord latestBmi = bmiRecordDao.findLatestByMemberId(memberId);
        
        model.addAttribute("fullName", fullName);
        model.addAttribute("latestBmi", latestBmi);
        
        return "member/member-dashboard";
    }
    
    // --- BMI Functionality ---

    @GetMapping("/bmi")
    public String showBmiForm(HttpSession session, Model model) {
        // Manual role check - ensure user is member
        if (!checkRole(session, "member")) { return "redirect:/auth/login"; }

        int memberId = (int) session.getAttribute("personId");
        
        // Load latest BMI for display
        BmiRecord latestBmi = bmiRecordDao.findLatestByMemberId(memberId);
        
        model.addAttribute("bmiRecord", new BmiRecord()); 
        model.addAttribute("latestBmi", latestBmi); 
        
        return "member/bmiForm";
    }

    @PostMapping("/bmi/calculate")
    public String calculateAndSaveBmi(@ModelAttribute("bmiRecord") BmiRecord newRecord, HttpSession session) {
        // Manual role check - ensure user is member
        if (!checkRole(session, "member")) { return "redirect:/auth/login"; }
        
        int memberId = (int) session.getAttribute("personId");
        Person member = personDao.findById(memberId); 

        // 1. BMI Calculation
        double heightInMeters = newRecord.getHeightCm() / 100.0;
        double bmiValue = newRecord.getWeightKg() / (heightInMeters * heightInMeters);
        
        // Round the BMI value
        bmiValue = Math.round(bmiValue * 100.0) / 100.0;
        
        // 2. Set necessary fields before saving
        newRecord.setBmiValue(bmiValue);
        newRecord.setRecordDate(LocalDateTime.now());
        newRecord.setMember(member); 
        
        // 3. Save to Database
        bmiRecordDao.save(newRecord);

        return "redirect:/member/bmi"; 
    }

    @GetMapping("/bmi/history")
    public String showBmiHistory(HttpSession session, Model model) {
        // Manual role check - ensure user is member
        if (!checkRole(session, "member")) { return "redirect:/auth/login"; }
        
        int memberId = (int) session.getAttribute("personId");
        
        List<BmiRecord> history = bmiRecordDao.findByMemberId(memberId);
        model.addAttribute("bmiHistory", history);
        
        return "member/member-bmi-history";
    }

    // --- NEW: Fitness Plan Functionality ---

    // 1. Member's My Plans Page
    @GetMapping("/my-plans")
    public String viewMyPlans(HttpSession session, Model model) {
        // Manual role check - ensure user is member
        if (!checkRole(session, "member")) { return "redirect:/auth/login"; }

        int memberId = (int) session.getAttribute("personId");
        
        // Fetch assignments for this logged-in member using the TrainerDAO
        List<PlanAssignment> myAssignments = trainerDao.findAllAssignmentsByMemberId(memberId);
        
        model.addAttribute("assignments", myAssignments);
        return "member/my-plans";
    }

    // 2. Update Plan Status (Member Action)
    @PostMapping("/plans/updateStatus")
    public String updatePlanStatus(@RequestParam("assignmentId") int assignmentId,
                                   @RequestParam("status") String status,
                                   HttpSession session) {
        // Manual role check - ensure user is member
        if (!checkRole(session, "member")) { return "redirect:/auth/login"; }

        int memberId = (int) session.getAttribute("personId");

        // Securely find the assignment to ensure it belongs to this member
        // (You must have added findAssignmentByMemberAndId to TrainerModuleDao as discussed previously)
        PlanAssignment assignment = trainerDao.findAssignmentByMemberAndId(memberId, assignmentId);
        
        if (assignment != null) {
            assignment.setStatus(status);
            trainerDao.saveAssignment(assignment);
        } 

        return "redirect:/member/my-plans";
    }

     @GetMapping("/programs")
    public String browsePrograms(HttpSession session, Model model) {
        // Manual role check - ensure user is member
        if (!checkRole(session, "member")) { return "redirect:/auth/login"; }

        List <Program> programs = programDao.findAll();
        model.addAttribute("programs", programs);
        return "member/browse-programs";
    }
    
}