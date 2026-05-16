package com.gestionapp.backend.controller;

import com.gestionapp.backend.entity.Category;
import com.gestionapp.backend.mapper.CategoryMapper;
import com.gestionapp.backend.generated.api.CategoriesApi;
import com.gestionapp.backend.generated.model.CategoryResponse;
import com.gestionapp.backend.generated.model.CreateCategoryRequest;
import com.gestionapp.backend.generated.model.UpdateCategoryRequest;
import com.gestionapp.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoriesApi {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    //AÑADIR ENDPOINT LISTADO DE CATEGORIAS POR USUARIO
    //AÑADIR LOGS 
    @Override
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        CategoryResponse createdCategory = categoryMapper.toApiDomain(categoryService.createCategory(categoryMapper.fromApiDomain(createCategoryRequest)));
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(categoryMapper::toApiDomain)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories.stream().map(categoryMapper::toApiDomain).toList());
    }

    @Override
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest categoryDetails) {
        //AÑADIR CONTROL DE USUARIO ACTUAL PARA VER SI ES EL PROPIETARIO DE LA CATEGORIA
        Category updatedCategory = categoryService.updateCategory(id, categoryMapper.fromApiDomain(categoryDetails));
        return ResponseEntity.ok(categoryMapper.toApiDomain(updatedCategory));
    }

    @Override
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        //AÑADIR CONTROL DE USUARIO ACTUAL PARA VER SI ES EL PROPIETARIO DE LA CATEGORIA
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
