package com.secj3303.dao;
import java.util.List;

import com.secj3303.model.Member;

public interface MemberDao {
  List<Member> findAll();		
  Member findById(int id);		
  int insert(Member p);		
  void update(Member p);		
  int delete(int id);		


  // Custom method for Login (Crucial for the assignment)
    Member findByUsername(String username);

    // Custom method to get members only
    List<Member> findAllMembers();
}
