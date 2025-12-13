package com.secj3303.dao;

import java.util.List;

import com.secj3303.model.Person;

public interface PersonDao {
    
    // Basic CRUD Operations
    Person findById(int id);
    List<Person> findAll();
    void save(Person person); // Used for registration and updates
    void delete(Person person);
    
    // Specific method for Login/Authentication
    Person findByUsernameAndPassword(String username, String password);
}
