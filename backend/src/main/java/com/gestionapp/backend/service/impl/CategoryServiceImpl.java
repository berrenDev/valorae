package com.gestionapp.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestionapp.backend.entity.Category;
import com.gestionapp.backend.exception.DatabaseException;
import com.gestionapp.backend.exception.ResourceNotFoundException;
import com.gestionapp.backend.repository.CategoryRepository;
import com.gestionapp.backend.service.CategoryService;
import com.gestionapp.backend.validation.CategoryValidations;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private static final String CLASS_NAME = CategoryServiceImpl.class.getSimpleName();

    @Override
    @Transactional
    public Category createCategory(Category category) {
        CategoryValidations.validateForCreate(category, categoryRepository.existsByName(category.getName()));
        try {
            Category saved = categoryRepository.save(category);
            return saved;
        } catch (DataAccessException ex) {
            log.error("{} - createCategory - Error saving category - {} - {} - {}", CLASS_NAME, ex.getMessage(), ex.getCause(), ex);
            throw new DatabaseException("Database error while saving category", ex);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (DataAccessException ex) {
            log.error("{} - getAllCategories - Error fetching categories - {} - {} - {}", CLASS_NAME, ex.getMessage(), ex.getCause(), ex);
            throw new DatabaseException("Database error while fetching categories", ex);
        }
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        CategoryValidations.validateForId(id);
        try {
            return categoryRepository.findById(id);
        } catch (DataAccessException ex) {
            log.error("{} - getCategoryById - Error fetching category by id {} - {} - {} - {}", CLASS_NAME, id, ex.getMessage(), ex.getCause(), ex);
            throw new DatabaseException("Database error while fetching category", ex);
        }
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        CategoryValidations.validateForUpdate(id, category, categoryRepository.existsByNameAndIdNot(category.getName(), id));
        try {
            Category existing = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
            CategoryValidations.requireSameUser(category.getUser_id(), existing.getUser_id());
            existing.setName(category.getName());
            existing.setDescription(category.getDescription());
            Category updated = categoryRepository.save(existing);
            return updated;
        } catch (DataAccessException ex) {
            log.error("{} - updateCategory - Error updating category id {} - {} - {} - {}", CLASS_NAME, id, ex.getMessage(), ex.getCause(), ex);
            throw new DatabaseException("Database error while updating category", ex);
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId, String userId) {
        CategoryValidations.deleteValidation(categoryId, categoryRepository.existsById(categoryId), userId, categoryRepository.findById(categoryId));
        try {
            categoryRepository.deleteById(categoryId);
        } catch (DataAccessException ex) {
            log.error("{} - deleteCategory - Error deleting category id {} - {} - {} - {}", CLASS_NAME, categoryId, ex.getMessage(), ex.getCause(), ex);
            throw new DatabaseException("Database error while deleting category", ex);
        }
    }
}
