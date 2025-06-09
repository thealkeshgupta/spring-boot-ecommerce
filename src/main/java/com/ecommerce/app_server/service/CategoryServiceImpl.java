package com.ecommerce.app_server.service;

import com.ecommerce.app_server.exception.APIException;
import com.ecommerce.app_server.exception.ResourceNotFoundException;
import com.ecommerce.app_server.model.Category;
import com.ecommerce.app_server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("No category created till now.");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(existingCategory != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " alreadt exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Category category = optionalCategory.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return "Category with Category Id : " + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Category savedCategory = optionalCategory.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(existingCategory != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " alreadt exists");
        }

        savedCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(savedCategory);

        return savedCategory;
    }
}
