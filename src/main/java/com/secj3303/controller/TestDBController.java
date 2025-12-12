/**package com.secj3303.controller;

import java.sql.Connection;
import java.sql.ResultSet; // <-- CORRECT IMPORT (java.sql)
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secj3303.model.Member;

@Controller
public class TestDBController {

    private final DataSource ds;

    @Autowired
    public TestDBController(DataSource dataSource) {
        this.ds = dataSource;
    }

    @RequestMapping("/testdb")
    public String testdb() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM person";
        List<Member> personList = new ArrayList<>();
        
        try {
            conn = ds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            System.out.println("execute query SELECT * FROM PERSON");
            
            while(rs.next()) {
            
                System.out.println(
                    rs.getString("name") + " | " +
                    rs.getInt("yob") + " | " +
                    rs.getDouble("weight") + " | " +
                    rs.getDouble("height")  
                );

            }
        
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return"testdb";    
    }

}
**/