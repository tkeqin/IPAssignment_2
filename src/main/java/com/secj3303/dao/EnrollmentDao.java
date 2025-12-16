package com.secj3303.dao;


import com.secj3303.model.Enrollment;
import java.util.List;

public interface EnrollmentDao {

    void save(Enrollment enrollment);
    void update(Enrollment enrollment);
    void delete(Enrollment enrollment);
    Enrollment findById(int id);
    List<Enrollment> findAll();
    Enrollment findByMemberAndProgram(int memberId, int programId);
    List<Enrollment> findByMemberId(int memberId);
}
