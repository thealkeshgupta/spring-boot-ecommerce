package com.ecommerce.app_server.service;

import com.ecommerce.app_server.payload.CategoryDTO;
import com.ecommerce.app_server.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
