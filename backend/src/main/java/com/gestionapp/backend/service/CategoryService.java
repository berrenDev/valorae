package com.gestionapp.backend.service;

import com.gestionapp.backend.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public Optional<Category> getCategoryById(Long id);

    public Category createCategory(Category category);

    public List<Category> getAllCategories();

    public Category updateCategory(Long id, Category categoryDetails);

    public void deleteCategory(Long id);

}
