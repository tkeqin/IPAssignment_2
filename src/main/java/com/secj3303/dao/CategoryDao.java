package com.secj3303.dao;

import java.util.List;

import com.secj3303.model.Category;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(Integer id);
    void save(Category category);
    int delete(Integer id);
}
