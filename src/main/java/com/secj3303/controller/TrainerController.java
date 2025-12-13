package com.secj3303.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.secj3303.dao.PersonDao;
import com.secj3303.dao.TrainerDao;
import com.secj3303.dao.BmiRecordDao; // Import the existing DAO
import com.secj3303.model.*;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private PersonDao personDao;
    
    @Autowired
    private BmiRecordDao bmiRecordDao; // Inject the existing DAO

    // --- Helper for Security ---
    private boolean isTrainer(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "trainer".equals(role);
    }
    
    private Person getSessionPerson(HttpSession session) {
        Integer id = (Integer) session.getAttribute("personId");
        if(id == null) return null;
        return personDao.findById(id);
    }

    // --- 1. Dashboard ---
    @GetMapping("/dashboard")
    public String trainerDashboard(HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/auth/login";

        int trainerId = (int) session.getAttribute("personId");
        String trainerName = (String) session.getAttribute("fullName");

        // Fetch stats
       // List<PlanAssignment> assignments = trainerDao.findAssignmentsByTrainer(trainerId);
        List<TrainingSession> sessions = trainerDao.findSessionsByTrainer(trainerId);
        List<FitnessPlan> allPlans = trainerDao.findAllPlans(); 
        
        // Fetch all members to show total registered members count
        List<Person> allMembers = trainerDao.findAllMembers();
        
        model.addAttribute("trainerName", trainerName);
        model.addAttribute("activePlans", allPlans.size()); 
        // UPDATED: Show total members in system, not just assigned ones
        model.addAttribute("totalMembers", allMembers.size());
        model.addAttribute("upcomingSessions", sessions.stream().filter(s -> "Scheduled".equals(s.getStatus())).count());
        model.addAttribute("completionRate", 92); 

        return "trainer/trainer-dashboard";
    }

    // --- 2. Create Fitness Plan ---
    @GetMapping("/plans")
    public String listPlans(HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/auth/login";
        model.addAttribute("plans", trainerDao.findAllPlans());
        return "trainer/plan-list";
    }

    @GetMapping("/plans/create")
    public String showCreatePlanForm(HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/auth/login";
        model.addAttribute("plan", new FitnessPlan());
        return "trainer/plan-form";
    }

    @PostMapping("/plans/save")
    public String savePlan(@ModelAttribute("plan") FitnessPlan plan, HttpSession session) {
        if (!isTrainer(session)) return "redirect:/auth/login";
        trainerDao.savePlan(plan);
        return "redirect:/trainer/plans";
    }

    // --- 3. Assign Plan to Member (UPDATED) ---
    @GetMapping("/plans/assign")
    public String showAssignPlanForm(
            @RequestParam(value = "selectedMemberId", required = false) Integer selectedMemberId,
            HttpSession session, 
            Model model) {
        
        if (!isTrainer(session)) return "redirect:/auth/login";
        
        model.addAttribute("assignment", new PlanAssignment());
        model.addAttribute("members", trainerDao.findAllMembers()); 
        model.addAttribute("plans", trainerDao.findAllPlans());     
        
        // --- NEW LOGIC: Fetch BMI History if a member is selected ---
        if (selectedMemberId != null) {
            model.addAttribute("selectedMemberId", selectedMemberId);
            List<BmiRecord> history = bmiRecordDao.findByMemberId(selectedMemberId);
            model.addAttribute("bmiHistory", history);
        }
        
        return "trainer/assign-plan";
    }

    @PostMapping("/plans/assign/save")
    public String saveAssignment(@ModelAttribute("assignment") PlanAssignment assignment, 
                                 @RequestParam("memberId") int memberId,
                                 @RequestParam("planId") int planId,
                                 HttpSession session) {
        if (!isTrainer(session)) return "redirect:/auth/login";

        Person trainer = getSessionPerson(session);
        Person member = personDao.findById(memberId);
        FitnessPlan plan = trainerDao.findPlanById(planId);

        assignment.setTrainer(trainer);
        assignment.setMember(member);
        assignment.setFitnessPlan(plan);
        assignment.setAssignedDate(LocalDate.now());
        assignment.setStatus("Active");

        trainerDao.saveAssignment(assignment);
        return "redirect:/trainer/dashboard";
    }

    // --- 4. Monitor Member Progress ---
    @GetMapping("/members/progress")
    public String monitorProgress(HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/auth/login";
        
        int trainerId = (int) session.getAttribute("personId");
        
        // Use the new DAO method to get unique members
        List<Person> uniqueMembers = trainerDao.findDistinctMembersWithAssignments(trainerId);
        
        model.addAttribute("members", uniqueMembers);
        return "trainer/member-progress";
    }
    
    // --- 4b. View Member Details ---
    @GetMapping("/members/progress/details")
    public String viewMemberDetails(@RequestParam("memberId") int memberId, HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/auth/login";
        
        Person member = personDao.findById(memberId);
        
        // Fetch ALL assignments (History) for this member
        List<PlanAssignment> history = trainerDao.findAllAssignmentsByMemberId(memberId);
        
        model.addAttribute("member", member);
        model.addAttribute("assignments", history); 
        
        return "trainer/member-details";
    }

    // --- 5. Schedule Sessions ---
    @GetMapping("/sessions/schedule")
    public String showScheduleForm(HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/auth/login";
        model.addAttribute("session", new TrainingSession());
        return "trainer/session-form";
    }

    @PostMapping("/sessions/save")
    public String saveSession(@ModelAttribute("session") TrainingSession trainingSession,
                              @RequestParam("dateInput") String dateStr,
                              @RequestParam("startTimeInput") String startStr,
                              @RequestParam("endTimeInput") String endStr,
                              HttpSession session) {
        if (!isTrainer(session)) return "redirect:/auth/login";

        Person trainer = getSessionPerson(session);

        // 1. Parse Inputs directly to Model fields
        trainingSession.setSessionDate(LocalDate.parse(dateStr));
        trainingSession.setStartTime(LocalTime.parse(startStr));
        trainingSession.setEndTime(LocalTime.parse(endStr));
        
        trainingSession.setTrainer(trainer);
        trainingSession.setStatus("Scheduled");
        
        trainerDao.saveSession(trainingSession);
        return "redirect:/trainer/sessions/list";
    }

    // --- Mark Session Complete ---
    @PostMapping("/sessions/complete")
    public String completeSession(@RequestParam("sessionId") int sessionId, HttpSession session) {
        if (!isTrainer(session)) return "redirect:/auth/login";

        TrainingSession trainingSession = trainerDao.findSessionById(sessionId);
        if (trainingSession != null) {
            int trainerId = (int) session.getAttribute("personId");
            // Ensure only the assigned trainer can mark complete
            if (trainingSession.getTrainer().getPersonId() == trainerId) {
                trainingSession.setStatus("Completed");
                trainerDao.saveSession(trainingSession);
            }
        }
        return "redirect:/trainer/sessions/list";
    }

    @GetMapping("/sessions/list")
    public String listSessions(HttpSession session, Model model) {
        if (!isTrainer(session)) return "redirect:/auth/login";
        
        int trainerId = (int) session.getAttribute("personId");
        model.addAttribute("sessions", trainerDao.findSessionsByTrainer(trainerId));
        return "trainer/session-list";
    }
}