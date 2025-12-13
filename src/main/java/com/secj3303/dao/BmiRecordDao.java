package com.secj3303.dao;


import java.util.List;

import com.secj3303.model.BmiRecord;

public interface BmiRecordDao {
    void save(BmiRecord bmiRecord);
    List<BmiRecord> findByMemberId(int memberId); // For BMI History
    BmiRecord findLatestByMemberId(int memberId); // For current result
}