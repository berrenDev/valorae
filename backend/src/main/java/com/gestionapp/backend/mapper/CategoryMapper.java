package com.gestionapp.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestionapp.backend.entity.Category;
import com.gestionapp.backend.generated.model.CategoryResponse;
import com.gestionapp.backend.generated.model.CreateCategoryRequest;
import com.gestionapp.backend.generated.model.UpdateCategoryRequest;

@Mapper(
    componentModel = "spring",
    uses = DateMapper.class
)
public interface CategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "userId", source = "user_id")
    @Mapping(
    target = "isDefault",
    expression = "java(Boolean.TRUE.equals(category.isSystemDefault()))")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CategoryResponse toApiDomain(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user_id", source = "userId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(
    target = "systemDefault",
    expression = "java(Boolean.TRUE.equals(createCategoryRequest.getIsDefault()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category fromApiDomain(CreateCategoryRequest createCategoryRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user_id", source = "userId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(
    target = "systemDefault",
    expression = "java(Boolean.TRUE.equals(updateCategoryRequest.getIsDefault()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category fromApiDomain(UpdateCategoryRequest updateCategoryRequest);

}
