package com.secj3303.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secj3303.dao.MemberDao;
import com.secj3303.model.Member;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MemberDao personDao;

    /**
     * Dependency Injection (DI) via Constructor:
     * Spring automatically injects the bean implementing the PersonDao interface
     * (which is PersonDaoJdbc, provided in the DAO package).
     */
    @Autowired
    public AdminController(MemberDao personDao) {
        this.personDao = personDao;
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        List<Member> people = personDao.findAll();
        model.addAttribute("people", people);
        return "person-list";
    }

    /**
     * Handles requests to the /person/list URL.
     * 1. Uses the injected DAO to fetch data.
     * 2. Adds the data to the Model for Thymeleaf consumption.
     * * @param model The Spring Model object.
     * @return The logical view name ("person-list").
     */
    @RequestMapping("/person/list")
    public String listPeople(Model model) {
        
        // Fetch all Person records using the injected DAO
        List<Member> people = personDao.findAll();
        
        // Add the list to the model under the attribute name "people"
        // This links to the variable used in person-list.html: ${people}
        model.addAttribute("people", people);
        
        // Return the view name (person-list.html)
        return "person-list"; 
    }

    @RequestMapping("/person/search")
    public String searchById(@RequestParam(value = "id", required = false) Integer id, Model model) {
        // If no id parameter provided, show the search page so user can enter an ID.
        if (id == null) {
            return "person-findbyid"; // show the form that asks for an id
        }

        Member person = personDao.findById(id);
        if (person != null) {
            model.addAttribute("person", person);
        } else {
            model.addAttribute("error", "Person not found with ID " + id);
        }
        return "person-view"; // Display person details or an error message
    }

    // This method simply displays the person-findbyid.html page
    @RequestMapping("/person/find")
    public String showSearchPage() {
        return "person-findbyid"; // Matches the HTML filename
    }


    @RequestMapping("/person/form")
    public String showForm(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            Member person = personDao.findById(id);
            if (person != null) {
                model.addAttribute("person", person);
            } else {
                model.addAttribute("error", "Person not found with ID " + id);
            }
        } else {
            model.addAttribute("person", new Member()); // Create a new person if no ID is provided
        }
        return "person-form"; // The form page for adding or editing a person
    }

    @RequestMapping("/person/save")
    public String savePerson(Member person) {
        if (person.getMemberId() == 0) {
            // If there's no ID, it's a new person (insert operation)
            personDao.insert(person);
        } else {
            // If there's an ID, it's an update operation
            personDao.update(person);
        }
        return "redirect:/person/list"; // Redirect back to the list after saving
    }

    @RequestMapping("/person/godelete")
    public String godeleteById() {

        return "person-delete"; // Redirect back to the list after deletion
    }

    @RequestMapping("/person/delete")
    public String deleteById(@RequestParam(value = "id", required = false) Integer id) {
        personDao.delete(id);
        return "redirect:/person/list"; // Redirect back to the list after deletion
    }

}
