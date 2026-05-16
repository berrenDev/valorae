package com.gestionapp.backend.validation;

import com.gestionapp.backend.entity.Category;
import com.gestionapp.backend.exception.ResourceNotFoundException;
import com.gestionapp.backend.exception.ValidationException;

public final class CategoryValidations {

    private CategoryValidations() {}

    public static void requireNonNull(Category category) {
        if (category == null) {
            throw new ValidationException("Category cannot be null");
        }
    }

    public static void requireNamePresent(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new ValidationException("Category name is required");
        }
    }

    public static void validateForCreate(Category category, boolean existsByName) {
        requireNonNull(category);
        requireNamePresent(category);
        requireUniqueName(category, existsByName);
    }

    public static void validateForId(Long id) {
        if (id == null) {
            throw new ValidationException("Category id cannot be null");
        }
    }

    public static void validateForUpdate(Long id, Category category, boolean duplicateName) {
        validateForId(id);
        requireNonNull(category);
        requireNamePresent(category);
        requireUniqueName(category, duplicateName);
    }

    public static void requireEntityExists(boolean exists, Long id) {
        if (!exists) {
            throw new ResourceNotFoundException("Category not found with id " + id);
        }
    }

    public static void requireUniqueName(Category category, boolean existsByName) {
        if (existsByName) {
            throw new ValidationException("Category with that name already exists");
        }
    }
}
